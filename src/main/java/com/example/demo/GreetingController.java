package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restService.Greeting;


@RestController
public class GreetingController{
    private static final String template ="Hello,%s";
    private static final AtomicLong counter = new AtomicLong();

    /*
     * The Greeting object must be converted to JSON. 
     * Thanks to Spring’s HTTP message converter support, you need not do this conversion manually..
     * Because Jackson 2 is on the classpath, 
     * Spring’s MappingJackson2HttpMessageConverter is automatically chosen to convert the Greeting instance to JSON.
     *  It should resemble the following output:
     * {
    "id": 1,
    "content": "Hello, World!"
    }
     */
    @GetMapping("/greeting")//访问网址http://localhost:8080/greating?name=无上天帝,注意greet后直接?不是/
    public Greeting getMethodName(@RequestParam(value="name",defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template,name));
    }
    
    
}
