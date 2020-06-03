package de.wps.bikehh.benutzerverwaltung.service;

import ch.qos.logback.classic.spi.LoggerContextListener;
import org.slf4j.LoggerFactory;

public class Logger {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerContextListener.class);
}
