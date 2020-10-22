//package io.zenbydef.usertracker.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//
//
//import javax.sql.DataSource;
//import java.util.Objects;
//import java.util.Properties;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan("io")
//@PropertySource("../../../../../resources/persistence-mysql.properties")
//@EnableTransactionManagement
//public class UserTrackerConfig implements WebMvcConfigurer {
//    @Autowired
//    private Environment env;
//
//    private final ApplicationContext applicationContext;
//
//    public UserTrackerConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    // Работа с персистентностью(Model)
//    @Bean
//    public DataSource dataSource() {
//        // Создаем DataSource
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        // Подключаем драйвер
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getRequiredProperty("jdbc.driver")));
//
//        // Задаем credentials для подключения к БД
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        // Устанаваливаем DataSource
//        em.setDataSource(dataSource());
//        // Устанавливаем пакет для рекурсивного сканирования
//        em.setPackagesToScan(env.getProperty("jpa.packagesToScan"));
//        // Уточнить
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(getProperties());
//
//        return em;
//    }
//
//    private Properties getProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("jpa.dialect", env.getProperty("jpa.dialect"));
//        properties.setProperty("jpa.show_sql", env.getProperty("jpa.show_sql"));
//        return properties;
//    }
//
//    @Bean
//    public PlatformTransactionManager platformTransactionManager() {
//        JpaTransactionManager tx = new JpaTransactionManager();
//        tx.setEntityManagerFactory(entityManagerFactory().getObject()); // уточнить
//        return tx;
//    }
//
//    // Работа с отображениями(View)
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setCharacterEncoding("UTF-8");
//        templateResolver.setPrefix("/WEB-INF/pages/");
//        templateResolver.setSuffix(".html");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setContentType("text/html; charset=UTF-8");
//        registry.viewResolver(resolver);
//    }
//}
