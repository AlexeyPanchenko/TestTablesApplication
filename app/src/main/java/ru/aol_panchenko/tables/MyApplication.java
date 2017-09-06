package ru.aol_panchenko.tables;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import ru.aol_panchenko.tables.di.component.AppComponent;
import ru.aol_panchenko.tables.di.component.DaggerAppComponent;
import ru.aol_panchenko.tables.di.module.AppModule;

/**
 * Created by alexey on 29.08.17.
 */

public class MyApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
