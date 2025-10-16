package com.example.restatestarter;

import dev.restate.sdk.Context;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.springboot.RestateService;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

import static com.example.restatestarter.Utils.sendNotification;
import static com.example.restatestarter.Utils.sendReminder;

@RestateService
public class Greeter {

    @Value("${greetingPrefix}")
    private String greetingPrefix;

    public record Greeting(String name) {
    }

    public record GreetingResponse(String message) {
    }

    @Handler
    public GreetingResponse greet(Context ctx, Greeting req) {
        System.out.println("prints every invocation");

        // Durably execute a set of steps; resilient against failures
        String greetingId = ctx.random().nextUUID().toString();
        ctx.run("Notification", () -> {
            System.out.println("running step 1: " + req.name);
        });

        ctx.sleep(Duration.ofSeconds(10));

        ctx.run("Reminder", () -> {
            System.out.println("running step 2:" + req.name);
        });

        ctx.sleep(Duration.ofSeconds(5));

        ctx.run("Reminder", () -> {
            System.out.println("running step 3:" + req.name);
        });

        ctx.sleep(Duration.ofSeconds(5));

        ctx.run("Reminder", () -> {
            System.out.println("running step 4:" + req.name);
        });

        // Respond to caller
        return new GreetingResponse("You said " + greetingPrefix + " to " + req.name + "!");
    }
}


