package com.cesar31.schedulesystem;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/rest")
public class ScheduleApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {
        return new HashMap<>(
                Map.of("jersey.config.jsonFeature", "JacksonFeature")
        );
    }
}
