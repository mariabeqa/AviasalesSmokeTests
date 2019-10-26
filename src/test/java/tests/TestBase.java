package tests;

import app_manager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class TestBase {
    protected static ApplicationManager app = new ApplicationManager();

    @BeforeMethod
    public static void setUp() throws IOException {
        app.init();
    }

    @AfterMethod
    public static void tearDown() {
        app.stop();
    }

}
