package thiyagu.postman.com.postmanandroid;

import android.app.Application;


import thiyagu.postman.com.postmanandroid.DI.ApplicationComponent;
import thiyagu.postman.com.postmanandroid.DI.ApplicationModule;
import thiyagu.postman.com.postmanandroid.DI.DaggerApplicationComponent;
import thiyagu.postman.com.postmanandroid.DI.DatabaseModule;


public class BaseActivity extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    applicationComponent = DaggerApplicationComponent.builder().databaseModule(new DatabaseModule(this)).applicationModule(new ApplicationModule(this)).build();
    applicationComponent.inject(this);


    }
    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
