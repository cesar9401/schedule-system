package com.cesar31.schedulesystem.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.slf4j.Logger;

@Path("/hello-world")
public class HelloController {

    @Inject
    Logger logger;

    @GET
    @Produces("text/plain")
    public String hello() {
        logger.info("Hello there");
        return "Hello, World!";
    }
}
