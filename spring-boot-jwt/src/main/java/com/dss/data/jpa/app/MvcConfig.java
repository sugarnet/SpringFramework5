package com.dss.data.jpa.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.dss.data.jpa.app.view.xml.CustomerList;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * WebMvcConfigurer.super.addResourceHandlers(registry);
	 * 
	 * String resourcePath =
	 * Paths.get("uploads").toAbsolutePath().toUri().toString();
	 * 
	 * registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath)
	 * ; }
	 */

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("lang");
		return changeInterceptor;
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { CustomerList.class });
		return marshaller;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
		viewControllerRegistry.addViewController("/error_403").setViewName("error_403");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

}
