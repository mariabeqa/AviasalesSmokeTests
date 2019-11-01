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

public class ComplexBookingTests extends TestBase {
    public static final String filePath = "./src/test/resources/ticket_prices.txt";

    @DataProvider
    public Iterator<Object[]> testData() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Омск","Екатеринбург"))
                .withDestinationAirports(Arrays.asList("Екатеринбург", "Санкт-Петербург"))
                .withDurations(new int[] {10, 5}).withAdultsAmount(2).withKidsAmount(2)
                .withTransfersAmount(0).withBuggage(true).withNumberOfRoutes(2)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Омск", "Москва"))
                .withDestinationAirports(Arrays.asList("Москва", "Минск"))
                .withDurations(new int[] {3, 7}).withAdultsAmount(1).withKidsAmount(1)
                .withTransfersAmount(0).withBuggage(true).withNumberOfRoutes(2)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Омск", "Москва", "Лондон"))
                .withDestinationAirports(Arrays.asList("Москва", "Лондон", "Нью-Йорк"))
                .withDurations(new int[] {5, 5, 10}).withAdultsAmount(1).withKidsAmount(0).withServiceClass(ServiceClass.BUSINESS.getValue())
                .withTransfersAmount(0).withBuggage(true).withNumberOfRoutes(3)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Омск","Екатеринбург"))
                .withDestinationAirports(Arrays.asList("Екатеринбург", "Санкт-Петербург"))
                .withDurations(new int[] {14, 5}).withAdultsAmount(4).withKidsAmount(2)
                .withTransfersAmount(1).withBuggage(false).withNumberOfRoutes(2)});
        list.add(new Object[] {new Booking().withDepartureAirports(Arrays.asList("Санкт-Петербург", "Амстердам"))
                .withDestinationAirports(Arrays.asList("Амстердам", "Краков"))
                .withDurations(new int[] {7, 7}).withAdultsAmount(4).withKidsAmount(1).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(true).withNumberOfRoutes(2)});

        return list.iterator();
    }

    @Test(dataProvider = "testData")
    public void testComplexBookingFlow(Booking booking) {
        app.search().enterSearchParameters(booking, true);
        if (app.searchResults().isAnySearchResultsFound()) {
            app.searchResults().filterByCheapest();
            app.searchResults().filterByTransfer(booking.getTransfersAmount());
            app.searchResults().filterByBuggage(booking.hasBuggage());

            Assert.assertEquals(app.searchResults().getDepartureAirport(1), booking.getDepartureAirports().get(0));
            Assert.assertEquals(app.searchResults().getArrivalAirport(1), booking.getDestinationAirports().get(0));
            Assert.assertEquals(app.searchResults().getDepartureAirport(2), booking.getDepartureAirports().get(1));
            Assert.assertEquals(app.searchResults().getArrivalAirport(2), booking.getDestinationAirports().get(1));
            if (booking.getNumberOfRoutes() == 3) {
                Assert.assertEquals(app.searchResults().getDepartureAirport(3), booking.getDepartureAirports().get(2));
                Assert.assertEquals(app.searchResults().getArrivalAirport(3), booking.getDestinationAirports().get(2));
            }

            if (booking.hasBuggage()) {
                Assert.assertEquals(app.searchResults().getTicketsWithBaggageOption().stream()
                        .map(e -> e.getText()).collect(Collectors.toList()).size(), app.searchResults().getAmountOfTicketsFound());
            }
            app.file().saveTickets(filePath);
        }
    }
}
