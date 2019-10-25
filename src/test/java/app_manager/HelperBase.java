package app_manager;

import org.openqa.selenium.*;

public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    public boolean clickWithRetrial(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                wd.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException | ElementClickInterceptedException e) {
            }
            attempts++;
        }
        return result;
    }

}
