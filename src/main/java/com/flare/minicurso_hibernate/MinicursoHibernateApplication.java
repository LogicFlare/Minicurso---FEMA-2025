package com.flare.minicurso_hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinicursoHibernateApplication {

	public static void main(String[] args) {

        SpringApplication.run(MinicursoHibernateApplication.class, args);
        System.out.println("Aplicação iniciada com sucesso!");
        System.out.println("URL do swagger: http://localhost:8080/api/swagger-ui/index.html " );
	}

}
