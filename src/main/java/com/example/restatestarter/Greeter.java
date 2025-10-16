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

        ctx.run(() -> {
            var stepName = "";
            System.out.println("step 1: " + stepName);
        });

        ctx.sleep(Duration.ofSeconds(10));
        String accountNumber = ctx.run(String.class, () -> {
            var stepName = "";
            System.out.println("step 2:" + stepName);
            return "123456789";
        });

        ctx.sleep(Duration.ofSeconds(10));
        ctx.run(() -> {
            var stepName = "";
            System.out.println("step 3:" + stepName);
            sendNotification(accountNumber, req.name);
        });

        ctx.sleep(Duration.ofSeconds(10));
        ctx.run(() -> {
            var stepName = "";
            System.out.println("step 4:" + stepName);
        });

        // Respond to caller
        return new GreetingResponse("You said " + greetingPrefix + " to " + req.name + "!");
    }
}


