package thiyagu.postman.com.postmanandroid.DI;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import thiyagu.postman.com.postmanandroid.Activities.RequestActivity;
import thiyagu.postman.com.postmanandroid.BaseActivity;
import thiyagu.postman.com.postmanandroid.MovieCategoryAdapter;

@Singleton
@Component(modules = {ApplicationModule.class,DatabaseModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(RequestActivity requestActivity);
   // void inject(MovieCategoryAdapter movieCategoryAdapter);

    @ApplicationContext
    Context getContext();

    Application getApplication();
}
