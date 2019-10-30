package app_manager;

import model.Booking;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchHelper extends HelperBase{

    public SearchHelper(WebDriver wd) {
        super(wd);
    }

    public void enterSearchParameters(Booking booking, boolean isComplexBooking) {
        switchOffShowingHotels();
        if (isComplexBooking) selectComplexRoute();

        enterRouteSearchParameters(isComplexBooking, booking.getDepartureAirports(), booking.getDestinationAirports(),
                booking.getDurations());
        addPassengers(booking.getAdultsAmount(), booking.getKidsAmount());
        if (booking.getServiceClass() != null) selectServiceClass(booking.getServiceClass());

        search(isComplexBooking);
    }

    private void enterRouteSearchParameters(boolean isComplex, List<String> depAirports, List<String> arrAirports, int[] durations) {

        for (int i = 1; i <= depAirports.size(); i ++) {
            if (i >= 3) addRoute();
            enterWhereFrom(depAirports.get(i-1), i);
            enterWhereTo(arrAirports.get(i-1), i);

            if (i == 1 ) moveOneMonthForward();

            if (!isComplex) {
                selectDayFrom();
                selectDayTo(durations[i-1]);
            } else {
                if (i % 2 != 0) {
                    selectDayFrom();
                } else {
                    selectDayTo(durations[i-1]);
                }
            }
        }
    }

    private void addRoute() {
        wd.findElement(By.cssSelector("a.of_multiway_segment__add")).click();
    }

    public void enterWhereTo(String whereTo, int numOfRoutes) {
        while (!checkThatTextIsEntereCorrectly(whereTo, By.xpath("(//input[@id='destination'])[" + numOfRoutes + "]"))) {
            WebElement toField = wd.findElement(By.xpath("(//input[@id='destination'])[" + numOfRoutes + "]"));
            toField.click();
            toField.sendKeys(whereTo, Keys.TAB);
        }
    }

    public void enterWhereFrom(String origin, int numOfRoutes) {
        while (!checkThatTextIsEntereCorrectly(origin, By.xpath("(//input[@id='origin'])[" + numOfRoutes + "]"))) {
            WebElement fromField = wd.findElement(By.xpath("(//input[@id='origin'])[" + numOfRoutes + "]"));
            fromField.click();
            fromField.sendKeys(origin);
        }
    }

    private boolean checkThatTextIsEntereCorrectly(String origin, By locator) {
        return origin.equals(wd.findElement(locator).getAttribute("value"));
    }

    private void selectComplexRoute() {
        wd.findElement(By.cssSelector("a.of_main_form__change_form_link")).click();
    }

    private void search(boolean isComplexBooking) {
        if (!isComplexBooking) {
            wd.findElement(By.cssSelector("div.--mobile-search button.of_main_form__submit")).click();
        } else {
            wd.findElement(By.cssSelector("button.of_main_form__submit")).click();
        }
    }

    private void switchOffShowingHotels() {
        clickWithRetrial(By.cssSelector("label[for='clicktripz']"));
    }

    private void addPassengers(int adults, int kids) {
        clickWithRetrial(By.cssSelector("div.additional-fields__label"));

        if (adults > 1) {
            for (int i = 1; i < adults; i++) {
                clickWithRetrial(By.cssSelector("div.additional-fields__passenger-row:nth-child(1) a.--increment"));
            }
        }

        if (kids > 0) {
            for (int i = 0; i < kids; i++) {
                wd.findElement(By.cssSelector("div.additional-fields__passenger-row:nth-child(2) a.--increment")).click();
            }
        }
    }

    private void selectServiceClass(String serviceClass) {
        if (serviceClass != wd.findElement(By.cssSelector("label.custom-radio.--is-active div.custom-radio__caption")).getText()) {
            By locator = By.xpath("//div[contains(text(),'" + serviceClass + "')]");
            clickWithRetrial(locator);
        }
        wd.findElement(By.cssSelector("div.additional-fields__label")).click();
    }

    public void selectDayTo(int duration) {
        int size = 0;
        while (size == 0) {
            try {
                List<WebElement> daysAvailable = wd.findElements(By.xpath("//div[@class ='daypicker__day-wrap']"));
                if (daysAvailable.size() < duration) {
                    daysAvailable.get(daysAvailable.size() - 1).click();
                } else {
                    daysAvailable.get(duration).click();
                }
                size = daysAvailable.size();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectDayFrom() {
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(wd, 2);
            wait.until((WebDriver wd) -> wd.findElement(By.cssSelector("div.datefield-dropdown__content")));
        } catch (TimeoutException | StaleElementReferenceException e) {
            WebDriverWait wait = new WebDriverWait(wd, 2);
            wait.until((WebDriver wd) -> wd.findElement(By.cssSelector("input#departDate")));
            wd.findElement(By.cssSelector("input#departDate")).click();
            moveOneMonthForward();
        }
        clickWithRetrial(By.xpath("(//div[@class ='daypicker__day-wrap'])[1]"));
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public int getAvailableDepartureDays() {
        return  wd.findElements(By.xpath("//div[@class ='daypicker__day-wrap']")).size();
    }

    public void moveOneMonthForward() {
        clickWithRetrial(By.cssSelector("span[aria-label='Next Month']"));
        if (getAvailableDepartureDays() < 10) {
            clickWithRetrial(By.cssSelector("span[aria-label='Next Month']"));
        }
    }
}
