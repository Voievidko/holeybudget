package com.yourfounds.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // This method should return config class
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    // Equivalent to <servlet-mapping> tag in xml configuration
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
