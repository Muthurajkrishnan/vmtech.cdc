package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

//@SuppressWarnings("restriction")
//@EntityScan
//@SpringBootApplication
//public class App extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    	System.out.println("Inside SpringApplicationBuilder");
//        return application.sources(App.class).properties("spring.config.name:application");
//    }
//
//    public static void main(String[] args) throws Exception {
//    	System.out.println("Inside Main");
//    	new SpringApplicationBuilder(App.class).properties("spring.config.name:application").build().run(args);
//        System.out.println("End Main");
//    }
//
//}