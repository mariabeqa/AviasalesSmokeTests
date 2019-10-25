package tests;

import app_manager.ApplicationManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class TestBase {
    protected static ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public static void setUp() throws IOException {
        app.init();
    }

    @AfterSuite
    public static void tearDown() {
        app.stop();
    }

}
