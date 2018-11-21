package thiyagu.postman.com.postmanandroid.Event;

import com.squareup.otto.Bus;

public class BusProvider {

private static Bus bus;
    public static Bus getBus()
    {
        return bus= new Bus();

    }

}
