package br.com.perfilnet;

import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import br.com.perfilnet.common.jpa.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SistemaPerfilnetV2Application {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

		var app = new SpringApplication(SistemaPerfilnetV2Application.class);

		app.run(args);
	}

}
