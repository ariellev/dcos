package de.bfft.de;/*
 * de.bfft.de.HelloService.java
 *
 * Created on 21.05.2017
 *
 * Copyright (C) 2017 Volkswagen AG, All rights reserved.
 */

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class HelloService extends AbstractVerticle {
    private static Logger log = LoggerFactory.getLogger(HelloService.class);

    @Override
    public void start() {
        log.info("starting server");
        Router router = Router.router(vertx);
        String elastic = System.getProperty("es.host");
        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(rC -> {
            HttpServerResponse response = rC.response();
            response
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject().put("status", "ok").put("es.host", elastic).encode());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloService());
    }
}
