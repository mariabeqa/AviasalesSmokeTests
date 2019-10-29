package tests;

import model.Booking;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ServiceClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BasicBookingTests extends TestBase {
    public static final String filePath = "./src/test/resources/ticket_prices.txt";

    @DataProvider
    public Iterator<Object[]> testData() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Санкт-Петербург")).withDestinationAirports(Arrays.asList("Екатеринбург"))
                .withDurations(new int[] {10}).withAdultsAmount(2).withKidsAmount(2).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Омск")).withDestinationAirports(Arrays.asList("Екатеринбург"))
                .withDurations(new int[] {7}).withAdultsAmount(1).withKidsAmount(1).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(false)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Санкт-Петербург")).withDestinationAirports(Arrays.asList("Омск"))
                .withDurations(new int[] {8}).withAdultsAmount(1).withKidsAmount(0).withServiceClass(ServiceClass.BUSINESS.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Москва")).withDestinationAirports(Arrays.asList("Екатеринбург"))
                .withDurations(new int[] {5}).withAdultsAmount(4).withKidsAmount(2).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(false)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Санкт-Петербург")).withDestinationAirports(Arrays.asList("Москва"))
                .withDurations(new int[] {14}).withAdultsAmount(4).withKidsAmount(1).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        return list.iterator();
    }

    @Test(dataProvider = "testData")
    public void testBasicBookingFlow(Booking booking) {
        app.search().enterSearchParameters(booking, false);
        if (app.searchResults().isAnySearchResultsFound()) {
            app.searchResults().filterByCheapest();
            app.searchResults().filterByTransfer(booking.getTransfersAmount());
            app.searchResults().filterByBuggage(booking.hasBuggage());

            Assert.assertEquals(app.searchResults().getDepartureAirport(1), booking.getDepartureAirports().get(0));
            Assert.assertEquals(app.searchResults().getArrivalAirport(1), booking.getDestinationAirports().get(0));
            Assert.assertEquals(app.searchResults().getDepartureAirport(2), booking.getDestinationAirports().get(0));
            Assert.assertEquals(app.searchResults().getArrivalAirport(2), booking.getDepartureAirports().get(0));
            if (booking.hasBuggage()) {
                Assert.assertEquals(app.searchResults().getTicketsWithBaggageOption().stream()
                        .map(e -> e.getText()).collect(Collectors.toList()).size(), app.searchResults().getAmountOfTicketsFound());
            }
            app.file().saveTickets(filePath);
        }
    }
}
