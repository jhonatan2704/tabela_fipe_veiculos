package com.jhonatan.tabelafipe.com.automoveisPrecos.valores_automoveis;

import com.jhonatan.tabelafipe.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

@SpringBootApplication
public class ValoresAutomoveisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ValoresAutomoveisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.Principal();
	}
}
