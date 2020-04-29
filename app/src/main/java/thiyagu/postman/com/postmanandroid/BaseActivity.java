package thiyagu.postman.com.postmanandroid;

import android.app.Application;

import thiyagu.postman.com.postmanandroid.DI.ApplicationComponent;
import thiyagu.postman.com.postmanandroid.DI.ApplicationModule;
import thiyagu.postman.com.postmanandroid.DI.DaggerApplicationComponent;
import thiyagu.postman.com.postmanandroid.DI.DatabaseModule;

public class BaseActivity extends Application {

    protected ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
        applicationComponent.inject(this);
    }
    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
