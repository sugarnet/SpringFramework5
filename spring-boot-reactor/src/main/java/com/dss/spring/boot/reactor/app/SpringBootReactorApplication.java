package com.dss.spring.boot.reactor.app;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dss.spring.boot.reactor.app.models.User;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<String> namesList = Arrays.asList("Diego Scifo", "Alma Scifo", "Sol Mauna");

		Flux<String> names =  Flux.fromIterable(namesList); // Flux.just("Diego Scifo", "Alma Scifo", "Sol Mauna");

		Flux<User> users = names.map(name -> {
			String fnames[] = name.split(" ");
			return new User(fnames[0].trim().toUpperCase(), fnames[1].trim().toUpperCase());
		}).filter(user -> user.getLastname().toLowerCase().equals("scifo")).doOnNext(name -> {
			if (name.getName().isEmpty()) {
				throw new RuntimeException("Name can't be empty");
			}
			System.out.println(name);
		}).map(user -> {
			user.setName(user.getName().toLowerCase());
			return user;
		});
		
		users.subscribe(name -> LOGGER.info(name.toString()), error -> LOGGER.error(error.getMessage()),
				new Runnable() {

					@Override
					public void run() {

						LOGGER.info("Finishing!!!");
					}
				});
	}

}
