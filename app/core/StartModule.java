package core;

import com.google.inject.AbstractModule;
import dao.DriverDAO;

public class StartModule extends AbstractModule {
    protected void configure() {
        bind(GeocodeModule.class).asEagerSingleton();
        bind(SearchModule.class).asEagerSingleton();
        bind(DriverDAO.class).asEagerSingleton();
    }
}
