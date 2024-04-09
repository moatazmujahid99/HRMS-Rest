package com.hrms.rest.presentation.listener;

import com.hrms.rest.persistence.Database;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ResourcesCleaner implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Database.close();
    }
}
