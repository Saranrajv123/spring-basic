package com.practice.ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.practice.ecommerce.config.AppConstants;
import com.practice.ecommerce.entities.Role;
import com.practice.ecommerce.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@SpringBootApplication
// @SecurityScheme(name = "E-Commerce Application", scheme = "bearer", type =
// SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class EcommerceApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		Role adminRole = new Role();
		// adminRole.setRoleId(AppConstants.ADMIN_ID);
		adminRole.setRoleName("ADMIN");

		Role userRole = new Role();
		userRole.setRoleName("USER");

		Set<Role> roles = Set.of(adminRole, userRole);

		List<Role> savedRoles = roleRepository.saveAll(roles);

		savedRoles.forEach(System.out::println);

	}

}
