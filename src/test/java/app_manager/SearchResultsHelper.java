package app_manager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class SearchResultsHelper extends HelperBase {
    public SearchResultsHelper (WebDriver wd) {
        super(wd);
    }

    public void filterByCheapest() {
        waitForSearchResultsToLoad();
        if (wd.findElement(By.xpath("//li[@class='sorting__tab is-active']//span[@class='sorting__title-wrap']")).getText()
                != "Самый дешевый") {
            wd.findElement(By.xpath("//span[@class='sorting__title-wrap'][contains(text(),'Самый дешевый')]")).click();
        }
    }

    private void waitForSearchResultsToLoad() {
        switchToSearchResultsInNewWindow();
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(wd, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.sorting__tab.is-active")));

        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void switchToSearchResultsInNewWindow() {
        for(String winHandle : wd.getWindowHandles()){
            wd.switchTo().window(winHandle);
        }
    }

    public void filterByTransfer(int transfer) {
        if (transfer == 0) {
            wd.findElement(By.cssSelector("div.checkboxes-list__list div.checkboxes-list__item:nth-child(2) span.checkbox")).click();
            wd.findElement(By.cssSelector("div.checkboxes-list__list div.checkboxes-list__item:nth-child(3) span.checkbox")).click();
        } else if (transfer == 1) {
            wd.findElement(By.cssSelector("div.checkboxes-list__list div.checkboxes-list__item:nth-child(3) span.checkbox")).click();
            wd.findElement(By.cssSelector("div.checkboxes-list__list div.checkboxes-list__item:nth-child(1) span.checkbox")).click();
        } 
    }

    public void filterByBuggage(boolean hasBuggage) {
        WebElement depAndArrTime = wd.findElement(By.cssSelector("div.filter__content div:nth-child(2) div.filter__segment:nth-child(2)"));

        ((JavascriptExecutor)wd).executeScript("arguments[0].scrollIntoView();", depAndArrTime);
        clickWithRetrial(By.cssSelector("div.--baggage div"));

        if (hasBuggage) {
            wd.findElement(By.cssSelector("label[for='baggage_no_baggage'] span")).click();
        } else {
            wd.findElement(By.cssSelector("label[for='baggage_full_baggage'] span.checkbox")).click();
        }

        scrollToTop();
    }

    private void scrollToTop() {
        WebElement element = wd.findElement(By.cssSelector("span.navbar__logo-text"));

        JavascriptExecutor js = (JavascriptExecutor)wd;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public String getDepartureAirportTo() {
        return wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[1]//div[@class='segment-route__endpoint origin']//div[@class='segment-route__city']")).getText();
    }

    public String getArrivalAirportTo() {
        return wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[1]//div[@class='segment-route__endpoint destination']//div[@class='segment-route__city']")).getText();
    }

    public String getDepartureAirportBack() {
        return wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[2]//div[@class='segment-route__endpoint origin']//div[@class='segment-route__city']")).getText();
    }

    public String getArrivalAirportBack() {
        return wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[2]//div[@class='segment-route__endpoint destination']//div[@class='segment-route__city']")).getText();
    }


}