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
		registry.addViewController("/umfragen").setViewName("umfrage_liste");
		registry.addViewController("/karte").setViewName("karte");
		registry.addViewController("/meldungen").setViewName("meldungen_list");
		registry.addViewController("/umfrage-erstellen").setViewName("umfrage_erstellen");
		registry.addViewController("/uebersicht").setViewName("uebersicht");
		registry.addRedirectViewController("/", "/uebersicht"); // alt: "/welcome/"

	}
}