package br.com.fiap.mottu_challenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Money Flow", description = "Aqui vai a descrição", version = "v1"))
public class MottuChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MottuChallengeApplication.class, args);
	}

}
