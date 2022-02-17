package com.exchange.exchangeApp;

import com.exchange.exchangeApp.model.Latest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExchangeAppApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeAppApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
/*
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Latest latestRates = restTemplate.getForObject(
					"http://data.fixer.io/api/latest?access_key=ccc410e1223409c690c5aa840d2b151d&base=EUR&symbols=TRY,USD,CHF", Latest.class);
			System.out.println(latestRates.getRates().getCHF());
			System.out.println(latestRates.getRates().getUSD());
			System.out.println(latestRates.getRates().getTRY());

		};
	}*/
}
