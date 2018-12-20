package thiyagu.postman.com.postmanandroid;

import javax.inject.Inject;

import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

public class MyDatabaseReference {

    private FeedReaderDbHelper feedReaderDbHelper;

    @Inject
    public MyDatabaseReference(FeedReaderDbHelper feedReaderDbHelper) {
        this.feedReaderDbHelper = feedReaderDbHelper;

    }

    public FeedReaderDbHelper getFeedReaderDbHelper() {
        return feedReaderDbHelper;
    }
}
