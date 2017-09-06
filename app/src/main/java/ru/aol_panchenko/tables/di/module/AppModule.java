package ru.aol_panchenko.tables.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.aol_panchenko.tables.AppPreferences;
import ru.aol_panchenko.tables.MyApplication;

/**
 * Created by alexey on 29.08.17.
 */
@Module
public class AppModule {

    private MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    AppPreferences preferencesPreferences(Context context) {
        return new AppPreferences(context);
    }
}
