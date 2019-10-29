package app_manager;

import model.Booking;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import java.util.*;

public class CalendarHelper extends HelperBase{
    public CalendarHelper (WebDriver wd) {
        super(wd);
    }

    public void enterSearchParameters(Booking booking, boolean isOneWay) {
        if (isElementPresent(By.cssSelector("div#popup-calendar-tooltip"))) clickWithRetrial(By.cssSelector("div#popup-calendar-tooltip a.close-popup"));
        enterWhereFrom(booking.getDepartureAirports().get(0));
        enterWhereTo(booking.getDestinationAirports().get(0));
        if (isOneWay) wd.findElement(By.cssSelector("a[data-goal='one_side']")).click();
    }

    public String selectMonth() {
        deselectCurrentMonths();
        String month =  selectRandomMonth();
        return month.toLowerCase();
    }

    private String selectRandomMonth() {
        List<WebElement> months = wd.findElements(By.cssSelector("div.l-input ul.year:nth-child(2) li.month"));
        Random rand = new Random();
        WebElement month = months.get(rand.nextInt(months.size()));
        month.click();
        return month.getText();
    }

    private void deselectCurrentMonths() {
        wd.findElement(By.cssSelector("label.checked")).click();
        wd.findElement(By.cssSelector("label.checked")).click();
    }

    public void enterWhereFrom(String origin) {
        WebElement field = wd.findElement(By.cssSelector("input#origin"));
        clearPrepopulatedText();
        field.sendKeys(origin, Keys.ALT);
    }

    private void clearPrepopulatedText() {
        String prepopulatedText = getAttributeTextWithRetrial(By.cssSelector("input#origin"), "data-old-value");

        while (wd.findElement(By.cssSelector("input#origin")).getAttribute("data-old-value").equals(prepopulatedText)) {
            clickWithRetrial(By.cssSelector("label[for='origin'] span.clear"));
        }
    }

    public void enterWhereTo(String destination) {
        WebElement field = wd.findElement(By.cssSelector("input#destination"));
        field.clear();
        field.sendKeys(destination, Keys.ALT);
    }

    public void setDurationTo10Days() {
        WebElement rightSlider = wd.findElement(By.xpath("(//span[@class='ui-slider-handle ui-state-default ui-corner-all'])[1]"));
        WebElement leftSlider = wd.findElement(By.xpath("(//span[@class='ui-slider-handle ui-state-default ui-corner-all'])[2]"));
        Actions move = new Actions(wd);
        Action moveLeftSlider = move.dragAndDropBy(rightSlider, 60, 0).build();
        moveLeftSlider.perform();
        Action moveRightSlider = move.dragAndDropBy(leftSlider, -80, 0).build();
        moveRightSlider.perform();
    }

    public void search() {
        wd.findElement(By.cssSelector("label[for='search-form-button']")).click();
    }

    public String getMonthInSearchResults() {
        return wd.findElement(By.cssSelector("h3.months-header span:nth-child(1)")).getText().substring(0, 3).toLowerCase();
    }

    public List<String> getMonthsInSearchResults() {
        ArrayList<String> monthNames = new ArrayList<>();
        List<WebElement> months = wd.findElements(By.cssSelector("h3.months-header span:nth-child(1)"));
        for (WebElement el : months) {
            monthNames.add(el.getText().substring(0,3).toLowerCase());
        }
        return monthNames;
    }

    public String showCheapest() {
        wd.findElement(By.cssSelector("a.value.best-link")).click();
        return wd.findElement(By.cssSelector("a.value.best-link span span span:nth-child(1)")).getText().replace(".", "");
    }

    public String getCheapestPriceFromTheTable() {
        List<WebElement> cells = wd.findElements(By.cssSelector("a.selectable.calendar-day span.calendar-price span span:nth-child(1)"));
        List<String> prices = new ArrayList<>();
        for (WebElement cell : cells) {
            if (!cell.getText().isEmpty()) prices.add(cell.getText().replace(".",""));
        }
        int n = prices.stream().mapToInt(el -> Integer.valueOf(el)).min().getAsInt();
        return String.valueOf(n);
    }

    public List<String> selectSeason(String season) {
        deselectCurrentMonths();
        wd.findElement(By.xpath("//a[contains(text(),'" + season + "')]")).click();
        List<WebElement> monthsSelected = wd.findElements(By.cssSelector("label.checked span"));
        List<String> months = new ArrayList<>();
        for (WebElement el : monthsSelected) {
            months.add(el.getText().toLowerCase());
        }
        return months;
    }

    public HashMap<String, String> selectExactDepartureDate() {
        wd.findElement(By.cssSelector("a#ui-id-2")).click();
        HashMap<String, String> date = new HashMap<>();
        String month = wd.findElement(By.cssSelector("div.ui-datepicker-group.ui-datepicker-group-last span.ui-datepicker-month"))
                .getText().substring(0, 3).toLowerCase();
        List<WebElement> daysAvailable = wd.findElements(By.cssSelector("div.ui-datepicker-group.ui-datepicker-group-last td[data-handler='selectDay']"));
        Random random = new Random();
        int randomNum = random.nextInt(daysAvailable.size());
        String day = daysAvailable.get(randomNum).getText();
        daysAvailable.get(randomNum).click();
        date.put("month", month);
        date.put("day", day);
        return date;
    }

    public String getDayInSearchResults() {
        return wd.findElement(By.cssSelector("a.best-value.selectable.calendar-day span.day-number")).getText();
    }

    public boolean isSearchResultsFound() {
        boolean searchResultsFound;
        try {
            searchResultsFound = !wd.findElement(By.cssSelector("div.c-calendar div.months_no_found strong")).isDisplayed();
        } catch (NoSuchElementException e) {
            searchResultsFound = true;
        }
        return searchResultsFound;
    }
}
