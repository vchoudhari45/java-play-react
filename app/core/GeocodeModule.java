package core;

import models.Location;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
class GeocodeModule {

    private Random random = new Random();

    Location getLocationFromAddress(String address) {
        //TODO We can use s2Geometry library but for simplicity purpose returning random value
        int latitudeRange = 90;
        int latitude = random.nextInt(latitudeRange) * (random.nextBoolean() ? -1 : 1);
        int longitudeRange = 180;
        int longitude = random.nextInt(longitudeRange) * (random.nextBoolean() ? -1 : 1);
        return new Location(latitude, longitude);
    }
}
