package mezzari.torres.lucas.dietbox_challenge.di;

import android.app.Application;

import mezzari.torres.lucas.dietbox_challenge.di.component.DaggerMainComponent;
import mezzari.torres.lucas.dietbox_challenge.di.component.MainComponent;
import mezzari.torres.lucas.dietbox_challenge.di.module.ApplicationModule;
import mezzari.torres.lucas.dietbox_challenge.di.module.DatabaseModule;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public final class DaggerHelper {

    private static DaggerHelper instance;
    private final MainComponent mainComponent;

    private DaggerHelper(Application application) {
        mainComponent = DaggerMainComponent
                .builder()
                .databaseModule(new DatabaseModule(application))
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public static void initialize(Application application) {
        instance = new DaggerHelper(application);
    }

    public static DaggerHelper getInstance() {
        if (instance == null) {
            throw new RuntimeException("DaggerHelper should be initialized");
        }
        return instance;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
