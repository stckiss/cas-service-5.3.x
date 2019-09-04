package com.xykj.cas.apereo.ext;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xykj.cas.service.UserService;

/**
 * 注册验证处理类
 * @author stc
 *
 */
@Configuration("CustomAuthenticationConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomAuthenticationConfiguration implements AuthenticationEventExecutionPlanConfigurer {
	/**
	 * 如果有控制开关可把此类注入进来
	 */
	@Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;
    
    /**
     * 注入服务类
     */
	@Autowired
	private UserService userService;

    @Bean
    public AuthenticationHandler CustomAuthenticationHandler() {
        // 参数: name, servicesManager, principalFactory, order
        // 定义为优先使用它进行认证.如有其它处理类需要根据系统定义优化级
    	CustomUsernamePasswordAuthentication handler = new CustomUsernamePasswordAuthentication(CustomUsernamePasswordAuthentication.class.getName(),
                servicesManager, new DefaultPrincipalFactory(), 1);
    	handler.setUserService(userService);
    	handler.setCasProperties(casProperties);
    	return handler;
    }

    
	@Override
	public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
		plan.registerAuthenticationHandler(CustomAuthenticationHandler());
		
	}

}
