package com.training.aem.core.servlets;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;

@Component(service = Servlet.class,
           property = "sling.servlet.methods=")
public class AlertComponentServlet extends SlingSafeMethodsServlet {
}
