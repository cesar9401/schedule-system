package com.cesar31.schedulesystem.controller;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
