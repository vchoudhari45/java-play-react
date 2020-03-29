package controllers;

import core.SearchModule;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class BookingController extends Controller {

    private SearchModule searchModule;

    @Inject
    public BookingController(SearchModule searchModule) {
        this.searchModule = searchModule;
    }

    public Result index(String name, String pickupLocation) {
        return ok(Json.toJson(searchModule.getNearByDrivers(name, pickupLocation)));
    }

    public CompletionStage<Result> listAllDrivers() {
        return searchModule.getAllDrivers().thenApplyAsync(drivers ->
                ok(Json.toJson(drivers))
        );
    }

    public CompletionStage<Result> listAllOrders() {
        return searchModule.getAllOrders().thenApplyAsync(orders ->
                ok(Json.toJson(orders))
        );
    }

}

