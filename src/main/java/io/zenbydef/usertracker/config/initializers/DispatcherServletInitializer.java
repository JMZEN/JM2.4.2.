package io.zenbydef.usertracker.config.initializers;

import io.zenbydef.usertracker.config.PersistenceConfig;
import io.zenbydef.usertracker.config.SecurityConfig;
import io.zenbydef.usertracker.config.ViewConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ViewConfig.class, SecurityConfig.class, PersistenceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
