package app_manager;

import model.Booking;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchHelper extends HelperBase{

    public SearchHelper(WebDriver wd) {
        super(wd);
    }

    public void enterSearchParameters(Booking booking, boolean showHotels) {
        enterWhereFrom(booking.getFrom());
        enterWhereTo(booking.getWhereTo());
        moveOneMonthForward();
        selectDayFrom();
        selectRandomDayTo(booking.getDuration());
        addPassengers(booking.getAdultsAmount(), booking.getKidsAmount());
        selectServiceClass(booking.getServiceClass());
        if (!showHotels) {
            switchOffShowingHotels();
        }
        search();
    }

    private void search() {
        wd.findElement(By.cssSelector("div.--mobile-search button.of_main_form__submit")).click();
    }

    private void switchOffShowingHotels() {
        clickWithRetrial(By.cssSelector("label[for='clicktripz']"));
    }

    private void addPassengers(int adults, int kids) {
        clickWithRetrial(By.cssSelector("div.additional-fields__label"));

        for (int i = 1; i < adults; i++) {
            clickWithRetrial(By.cssSelector("div.additional-fields__passenger-row:nth-child(1) a.--increment"));
        }

        for (int i = 0; i < kids; i ++) {
            wd.findElement(By.cssSelector("div.additional-fields__passenger-row:nth-child(2) a.--increment")).click();
        }
    }

    private void selectServiceClass(String serviceClass) {
        if (serviceClass != wd.findElement(By.cssSelector("label.custom-radio.--is-active div.custom-radio__caption")).getText()) {
            wd.findElement(By.xpath("//div[contains(text(),'" + serviceClass + "')]")).click();
        }

        wd.findElement(By.cssSelector("div.additional-fields__label")).click();
    }

    public void selectRandomDayTo(int duration) {
        wd.findElements(By.xpath("//div[@class ='daypicker__day-wrap']")).get(duration).click();
    }

    private void selectDayFrom() {
        clickWithRetrial(By.xpath("(//div[@class ='daypicker__day-wrap'])[1]"));
    }

    public void moveOneMonthForward() {
        wd.findElement(By.cssSelector("span[aria-label='Next Month']")).click();
    }

    public void enterWhereTo(String whereTo) {
        WebElement input = wd.findElement(By.cssSelector("input#destination"));
        input.click();
        input.sendKeys(whereTo);
    }

    public void enterWhereFrom(String origin) {
       wd.findElement(By.cssSelector("input#origin")).sendKeys(origin);
   }

}
