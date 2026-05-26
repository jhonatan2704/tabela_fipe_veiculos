package com.jhonatan.tabelafipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelafipeApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.inicializarSistema();
	}

	public static void main(String[] args) {
		SpringApplication.run(TabelafipeApplication.class, args);
	}

}
