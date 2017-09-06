package ru.aol_panchenko.tables.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.aol_panchenko.tables.di.module.AppModule;

/**
 * Created by alexey on 29.08.17.
 */
@Singleton
@Component(modules = {AppModule.class})
    public interface AppComponent {
}
