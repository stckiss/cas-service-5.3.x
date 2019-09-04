package com.xykj.cas.apereo.ext;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 注册登录配置
 * @author stc
 *
 */
@Configuration("CustomWebflowConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomWebflowConfiguration implements CasWebflowExecutionPlanConfigurer  {
	@Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CasConfigurationProperties casProperties;
    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Bean("customWebflowConfigurer")
    public CasWebflowConfigurer customWebflowConfigurer() {
    	CustomLoginWebflowConfigurer c = new CustomLoginWebflowConfigurer(flowBuilderServices, loginFlowRegistry, applicationContext, casProperties);
        c.initialize();
        return c;
    }
    
    @Override
    public void configureWebflowExecutionPlan(CasWebflowExecutionPlan plan) {
    	plan.registerWebflowConfigurer(customWebflowConfigurer());
    }

}
