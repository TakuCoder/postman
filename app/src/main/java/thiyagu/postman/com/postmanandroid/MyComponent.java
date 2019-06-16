package thiyagu.postman.com.postmanandroid;

import dagger.Component;
import thiyagu.postman.com.postmanandroid.Activities.Activity_Request;

@Component(modules = MyDatabaseModule.class)
@MyApplicationScope
public interface MyComponent {
void inject(Activity_Request activityRequest);


}
