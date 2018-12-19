package thiyagu.postman.com.postmanandroid;

import dagger.Component;
import thiyagu.postman.com.postmanandroid.Activities.NavDrawerActivityMain;

@Component(modules = MyDatabaseModule.class)
@MyApplicationScope
public interface MyComponent {
void inject(NavDrawerActivityMain navDrawerActivityMain);


}
