package thiyagu.postman.com.postmanandroid.DI;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;


    public ApplicationModule(Application app) {
        this.mApplication = app;
    }


    @Singleton
    @Provides
    @ApplicationContext
    Context provideContext()
    {
        return mApplication;

    }


    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }
}