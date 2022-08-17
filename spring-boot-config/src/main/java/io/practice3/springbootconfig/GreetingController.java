package io.practice3.springbootconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

        @Value("${my.greeting}")
        private String greetingMessage;

        @GetMapping("/greeting")
        public String greeting(){
            return greetingMessage;
        }
}
