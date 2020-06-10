package de.wps.bikehh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/logout").setViewName("logout");
		registry.addViewController("/umfragen").setViewName("survey_list");
		registry.addViewController("/karte").setViewName("map");
		registry.addViewController("/meldungen").setViewName("report_list");
		registry.addViewController("/umfrage-erstellen").setViewName("create_survey");
		registry.addViewController("/uebersicht").setViewName("dashboard");
		registry.addRedirectViewController("/", "/uebersicht"); // alt: "/welcome/"

	}
}