package com.project.online_library.camunda;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@Configuration
@Import( SpringProcessEngineServicesConfiguration.class )
public class CamundaConfiguration {

    @Value("${camunda.bpm.history-level:full}")
    private String historyLevel;

    @Autowired
    private ResourcePatternResolver resourceLoader;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        config.setDatabaseSchemaUpdate("true");
        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager());
        config.setJobExecutorActivate(true);
        config.setHistory(historyLevel);
        if(config.getCustomFormTypes()==null){
            config.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }
        config.setDefaultSerializationFormat("application/json");
        config.getProcessEnginePlugins().add(new SpinProcessEnginePlugin());
        config.getCustomFormTypes().add(new StringMultiSelect("genre-multiselect"));
        config.setDeploymentResources(resourceLoader.getResources("/*.bpmn"));

        return config;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() throws IOException {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
