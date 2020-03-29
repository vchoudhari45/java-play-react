package controllers;

import play.libs.Json;
import play.mvc.*;
import models.*;

import java.util.Arrays;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class BookingController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(String name, String pickupLocation) {
        System.out.println(name);
        Driver d1 = new Driver(1,"Driver1", 23.00, 13.00, Status.AVAILABLE);
        Driver d2 = new Driver(2,"Driver2", -10.00, 13.00, Status.BUSY);
        Driver d3 = new Driver(3,"Driver3", 3.00, 45.00, Status.AVAILABLE);
        Driver d4 = new Driver(4,"Driver4", 43.00, -13.00, Status.BUSY);
        Driver d5 = new Driver(5,"Driver5", 78.00, 20.00, Status.AVAILABLE);
        List<Driver> drivers = Arrays.asList(d1, d2, d3, d4, d5);
        return ok(Json.toJson(drivers));
    }

}

