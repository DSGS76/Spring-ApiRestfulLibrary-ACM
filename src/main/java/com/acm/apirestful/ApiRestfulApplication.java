package com.acm.apirestful;

import com.acm.apirestful.persistence.entity.Cliente;
import com.acm.apirestful.persistence.entity.Role;
import com.acm.apirestful.persistence.entity.UserEntity;
import com.acm.apirestful.persistence.entity.enums.EnumRol;
import com.acm.apirestful.persistence.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
@Slf4j
@EnableCaching
public class ApiRestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestfulApplication.class, args);
	}

	@Bean
	@Order(1)
	ApplicationRunner applicationRunner(ClienteRepository clienteRepository) {
		return args -> {
			Cliente c = Cliente.builder()
					.nombre("Jose")
					.correo("Jose@gmail.com")
					.telefono("3123249245")
					.estadoCuenta(true)
					.user(UserEntity.builder()
							.username("Jose")
							.password(new BCryptPasswordEncoder().encode("123"))
							.roles(Set.of(Role.builder()
									.rol(EnumRol.ADMIN)
									.build()))
							.build())
					.build();
			log.info(c.toString());
			clienteRepository.save(c);
		};
	}
}
