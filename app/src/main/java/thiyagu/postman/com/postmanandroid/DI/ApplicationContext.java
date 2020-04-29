package thiyagu.postman.com.postmanandroid.DI;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@interface ApplicationContext {
}
