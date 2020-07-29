package br.com.rhribeiro25.manageLabs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@SpringBootApplication
@EntityScan(basePackages = { "br.com.rhribeiro25.manageLabs.model" })
@EnableJpaRepositories(basePackages = { "br.com.rhribeiro25.manageLabs.repository" })
@EnableSwagger2
public class ManageLabsApplication {
	private static final Logger log = LoggerFactory.getLogger(ManageLabsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ManageLabsApplication.class, args);
		log.info("Starting ManageLabs");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.rhribeiro25.manageLabs.controller")).build();
	}


}
