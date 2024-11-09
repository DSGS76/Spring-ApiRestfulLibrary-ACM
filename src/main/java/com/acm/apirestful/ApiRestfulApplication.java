package com.acm.apirestful;

import com.acm.apirestful.persistence.entity.Cliente;
import com.acm.apirestful.persistence.repository.ClienteRepository;
import com.acm.apirestful.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Slf4j
public class ApiRestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestfulApplication.class, args);
	}

	@Bean
	@Order(1)
	ApplicationRunner applicationRunner(ClienteRepository clienteRepository) {
		return args -> {
			Cliente c = Cliente.builder()
					.id((short) 1)
					.nombre("Jose")
					.correo("Jose@gmail.com")
					.telefono("3123249245")
					.estadoCuenta(true)
					.build();
			clienteRepository.save(c);
		};
	}
}
