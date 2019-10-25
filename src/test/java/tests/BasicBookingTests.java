package tests;

import model.Booking;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ServiceClass;

public class BasicBookingTests extends TestBase {

    @Test
    public void testBasicBookingFlow() {
        app.search().enterSearchParameters(new Booking().withFrom("Санкт-Петербург").withWhereTo("Екатеринбург")
        .withDuration(10).withAdultsAmount(2).withKidsAmount(2).withServiceClass(ServiceClass.ECONOMY.getValue()).withTransfersAmount(0), false);
        app.searchResults().filterByCheapest();
        app.searchResults().filterByTransfer(0);
        app.searchResults().filterByBuggage(true);

        Assert.assertEquals(app.searchResults().getDepartureAirportTo(), "Санкт-Петербург");
        Assert.assertEquals(app.searchResults().getArrivalAirportTo(), "Екатеринбург");

        Assert.assertEquals(app.searchResults().getDepartureAirportBack(), "Екатеринбург");
        Assert.assertEquals(app.searchResults().getArrivalAirportBack(), "Санкт-Петербург");
    }
}
