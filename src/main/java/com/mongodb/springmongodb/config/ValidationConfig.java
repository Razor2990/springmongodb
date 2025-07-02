package com.mongodb.springmongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    LocalValidatorFactoryBean validator() {
	        return new LocalValidatorFactoryBean();
	    }

    @SuppressWarnings("deprecation")
	@Bean
    ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
	        return new ValidatingMongoEventListener(validator);
	    }
}
