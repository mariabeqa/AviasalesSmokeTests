package app_manager;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;


    public ApplicationManager() {
        properties = new Properties();
    }


    public void init() throws IOException {
        properties.load(new FileReader(new File("./src/test/resources/local.properties")));

        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().window().fullscreen();

        wd.get(properties.getProperty("web.baseurl"));
    }

    public void stop() {
        wd.quit();
    }
}
