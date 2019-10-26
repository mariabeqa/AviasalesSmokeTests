package tests;

import model.Booking;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ServiceClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BasicBookingTests extends TestBase {
    public static final String filePath = "./src/test/resources/ticket_prices.txt";

    @DataProvider
    public Iterator<Object[]> testData() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] {new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
                .withDuration(10).withAdultsAmount(2).withKidsAmount(2).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        list.add(new Object[] {new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
                .withDuration(7).withAdultsAmount(1).withKidsAmount(1).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(false)});
        list.add(new Object[] {new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
                .withDuration(3).withAdultsAmount(1).withKidsAmount(0).withServiceClass(ServiceClass.BUSINESS.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        list.add(new Object[] {new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
                .withDuration(5).withAdultsAmount(4).withKidsAmount(2).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(1).withBuggage(false)});
        list.add(new Object[] {new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
                .withDuration(14).withAdultsAmount(4).withKidsAmount(1).withServiceClass(ServiceClass.ECONOMY.getValue())
                .withTransfersAmount(0).withBuggage(true)});
        return list.iterator();
    }

    @Test(dataProvider = "testData")
    public void testBasicBookingFlow(Booking booking) {

        app.search().enterSearchParameters(booking, false);
        app.searchResults().filterByCheapest();
        app.searchResults().filterByTransfer(booking.getTransfersAmount());
        app.searchResults().filterByBuggage(booking.hasBuggage());

        Assert.assertEquals(app.searchResults().getDepartureAirportTo(), "Санкт-Петербург");
        Assert.assertEquals(app.searchResults().getArrivalAirportTo(), "Екатеринбург");
        Assert.assertEquals(app.searchResults().getDepartureAirportBack(), "Екатеринбург");
        Assert.assertEquals(app.searchResults().getArrivalAirportBack(), "Санкт-Петербург");
        if (booking.hasBuggage()) {
            Assert.assertEquals(app.searchResults().getTicketsWithBaggageOption().stream().filter(e -> e.getText() != "С багажом")
                    .map(e -> e.getText()).collect(Collectors.toList()).size(), app.searchResults().getAmountOfTicketsFound());
        }
        app.file().saveTickets(filePath);
    }
}
