package models;

public class Driver {
    private long id;
    private String driverName;
    private double currentLatitude, currentLongitude;
    private Status status;

    public Driver(long id, String driverName, double currentLatitude, double currentLongitude, Status status) {
        this.id = id;
        this.driverName = driverName;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
