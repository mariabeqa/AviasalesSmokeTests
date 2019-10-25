package app_manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;
    private SearchHelper searchHelper;
    private SearchResultsHelper searchResultsHelper;

    public ApplicationManager() {
        properties = new Properties();
    }


    public void init() throws IOException {
        properties.load(new FileReader(new File("./src/test/resources/local.properties")));

        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().window().fullscreen();
        wd.get(properties.getProperty("web.baseurl"));
        searchHelper = new SearchHelper(wd);
        searchResultsHelper = new SearchResultsHelper(wd);
    }

    public void stop() {
        wd.quit();
    }

    public SearchHelper search() {
        return searchHelper;
    }

    public SearchResultsHelper searchResults() {
        return searchResultsHelper;
    }
}
