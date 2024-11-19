package com.acm.apirestful;

import com.acm.apirestful.persistence.entity.*;
import com.acm.apirestful.persistence.entity.enums.EnumRol;
import com.acm.apirestful.persistence.repository.ClienteRepository;
import com.acm.apirestful.persistence.repository.LibroRepository;
import com.acm.apirestful.persistence.repository.PrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
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
	ApplicationRunner applicationRunner(ClienteRepository clienteRepository,
										LibroRepository libroRepository,
										PrestamoRepository prestamoRepository) {
		return args -> {
			Cliente c = Cliente.builder()
					.nombre("Jose")
					.correo("Jose@correo.com")
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

//			Set<Libro> l = new HashSet<>(libroRepository.findAllById(Set.of((short) 1,(short) 2)));
//
//			Prestamo p = Prestamo.builder()
//					.fechaInicioPrestamo(LocalDate.now())
//					.fechaFinPrestamo(LocalDate.now().plusDays(15))
//					.libros(l)
//					.cliente(c)
//					.build();
//			prestamoRepository.save(p);
		};
	}

	@Bean
	@Order(2)
	ApplicationRunner applicationRunner2(ClienteRepository clienteRepository,
										 LibroRepository libroRepository,
										 PrestamoRepository prestamoRepository) {
		return args -> {
			Cliente c = Cliente.builder()
					.nombre("Maria")
					.correo("Maria@correo.com")
					.telefono("3145248719")
					.estadoCuenta(true)
					.user(UserEntity.builder()
							.username("Maria")
							.password(new BCryptPasswordEncoder().encode("321"))
							.roles(Set.of(Role.builder()
									.rol(EnumRol.CLIENT)
									.build()))
							.build())
					.build();
			log.info(c.toString());
			clienteRepository.save(c);

//			Set<Libro> l = new HashSet<>(libroRepository.findAllById(Set.of((short) 3,(short) 4)));
//
//			Prestamo p = Prestamo.builder()
//					.fechaInicioPrestamo(LocalDate.now().plusMonths(1))
//					.fechaFinPrestamo(LocalDate.now().plusMonths(1).plusDays(15))
//					.libros(l)
//					.cliente(c)
//					.build();
//			prestamoRepository.save(p);
		};
	}
}
