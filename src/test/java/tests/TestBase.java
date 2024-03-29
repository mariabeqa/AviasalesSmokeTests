package tests;

import app_manager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class TestBase {
    protected static ApplicationManager app = new ApplicationManager();

    @BeforeSuite(alwaysRun=true)
    public static void cleanResults() throws FileNotFoundException {
        PrintWriter cleanPreviousTestInfo = new PrintWriter("./src/test/resources/ticket_prices.txt");
        cleanPreviousTestInfo.print("");
    }

    @BeforeMethod(alwaysRun=true)
    public static void setUp() throws IOException {
        app.init();
    }

    @AfterMethod(alwaysRun=true)
    public static void tearDown() throws IOException {
        app.stop();
    }
}
