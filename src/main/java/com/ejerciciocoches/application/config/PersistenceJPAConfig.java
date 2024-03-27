package com.ejerciciocoches.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ejerciciocoches.infrastucture.repository")
public class PersistenceJPAConfig {}
