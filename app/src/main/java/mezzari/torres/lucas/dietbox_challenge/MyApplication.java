package mezzari.torres.lucas.dietbox_challenge;

import android.app.Application;

import mezzari.torres.lucas.dietbox_challenge.di.DaggerHelper;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerHelper.initialize(this);
    }
}
