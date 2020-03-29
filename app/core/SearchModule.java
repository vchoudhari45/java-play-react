package core;

import dao.DriverDAO;
import dao.OrderDAO;
import models.*;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletionStage;


public class SearchModule {

    private GeocodeModule geocodeModule;
    private DriverDAO driverDAO;
    private OrderDAO orderDAO;

    @Inject
    public SearchModule(GeocodeModule geocodeModule, DriverDAO driverDAO, OrderDAO orderDAO) {
        this.geocodeModule = geocodeModule;
        this.driverDAO = driverDAO;
        this.orderDAO = orderDAO;
    }

    public List<DriverDistance> getNearByDrivers(String customerName, String address) {
        //convert address to lat & long
        Location location = geocodeModule.getLocationFromAddress(address);

        List<DriverDistance> bestDrivers = new ArrayList<>();
        driverDAO.list().whenComplete((driverSeq, throwable) -> {
            if(throwable == null) {
                //pickup best drivers around the customer's address sorted by distance from customer
                getBestDriver(driverSeq, location, bestDrivers);

                if(bestDrivers.size() > 0) {
                    //create order & update driver's status
                    Order order = new Order(0, customerName, location.latitude(), location.longitude());
                    CompletionStage<java.lang.Boolean> orderCreated = orderDAO.create(order);
                    CompletionStage<java.lang.Integer> driverStatusUpdated = driverDAO.markDispatched(bestDrivers.get(0).driver().driverId());
                    orderCreated.thenCombine(driverStatusUpdated, (o, s) -> s+"");
                }
            }
            else System.out.println("Something went wrong "+throwable.getMessage());
        }).toCompletableFuture().join();
        return bestDrivers;
    }

    private void getBestDriver(Seq<Driver> drivers, Location location, List<DriverDistance> bestDrivers) {
        Iterator<Driver> itr = drivers.iterator();
        while(itr.hasNext()) {
            Driver driver = itr.next();
            if(driver.status().equals(Status.AVAILABLE.toString()))
                bestDrivers.add(new DriverDistance(driver, calculateDistance(location.latitude(), location.longitude(), driver.latitude(), driver.longitude(), "K")));
        }
        bestDrivers.sort(Comparator.comparingDouble(DriverDistance::distance));
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;          //"Distance in Miles"
            if (unit.equals("K")) {
                dist = dist * 1.609344;         //"Distance in Kilometers
            } else if (unit.equals("N")) {      //"Distance in Nautical Miles"
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    public CompletionStage<Seq<Driver>> getAllDrivers() {
        return driverDAO.list();
    }
    public CompletionStage<Seq<Order>> getAllOrders() {
        return orderDAO.list();
    }
}
