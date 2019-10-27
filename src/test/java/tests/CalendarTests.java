package tests;

import model.Booking;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

public class CalendarTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> testData() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Москва"))
                .withDestinationAirports(Arrays.asList("Санкт-Петербург"))});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Москва"))
                .withDestinationAirports(Arrays.asList("Анталия"))});
        return list.iterator();
    }

    @Test(dataProvider = "testData")
    public void testCalendarMonthSelection(Booking booking) {
        app.openCalendar();
        app.calendar().enterSearchParameters(booking, false);
        String selectedMonth = app.calendar().selectMonth();
        app.calendar().setDurationTo10Days();
        app.calendar().search();
        Assert.assertEquals(app.calendar().getMonthInSearchResults(), selectedMonth);
        String cheapesPriceOnTheButton = app.calendar().showCheapest();
        Assert.assertEquals(app.calendar().getCheapestPriceFromTheTable(), cheapesPriceOnTheButton);
    }

    @Test(dataProvider = "testData")
    public void testCalendarSeasonSelection(Booking booking) {
        app.openCalendar();
        app.calendar().enterSearchParameters(booking, false);
        List<String> selectedMonths = app.calendar().selectSeason("Зима");
        app.calendar().setDurationTo10Days();
        app.calendar().search();
        Assert.assertEquals(app.calendar().getMonthsInSearchResults(), selectedMonths);
        String cheapestPriceOnTheButton = app.calendar().showCheapest();
        Assert.assertEquals(app.calendar().getCheapestPriceFromTheTable(), cheapestPriceOnTheButton);
    }

    @Test(dataProvider = "testData")
    public void testExactDepartureDate(Booking booking) {
        app.openCalendar();
        app.calendar().enterSearchParameters(booking, true);
        HashMap<String, String> selectedDate = app.calendar().selectExactDepartureDate();
        app.calendar().search();
        Assert.assertEquals(app.calendar().getMonthInSearchResults(), selectedDate.get("month"));
        Assert.assertEquals(app.calendar().getDayInSearchResults(), selectedDate.get("day"));
    }
}
