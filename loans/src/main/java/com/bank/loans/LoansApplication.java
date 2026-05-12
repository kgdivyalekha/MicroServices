package com.bank.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info=@Info(
				title = "Loans Microservice REST API documentation",
				description = "Bank's Loans Microservice",
				version = "v1",
				contact = @Contact(
						name = "Lekha Gunasekaran",
						email = "lekha@bank.com"
				),
				license = @License(
						name="Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Bank Loans Microservice Swagger Documentation"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
