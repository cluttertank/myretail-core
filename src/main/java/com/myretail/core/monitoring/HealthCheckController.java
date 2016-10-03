package com.myretail.core.monitoring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public void doHealthCheck() {
    }

}