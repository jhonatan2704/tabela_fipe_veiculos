package com.jhonatan.tabelafipe;

import com.jhonatan.tabelafipe.principal.Principal;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import com.jhonatan.tabelafipe.repository.ModeloVeicularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelafipeApplication implements CommandLineRunner {
	@Autowired
	private ModeloVeicularRepository repository;
	@Autowired
	private MarcaVeiculoRepository marcaRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, marcaRepository);
		principal.inicializarSistema();
	}

	public static void main(String[] args) {
		SpringApplication.run(TabelafipeApplication.class, args);
	}

}
