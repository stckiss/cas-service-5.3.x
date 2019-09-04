package com.xykj.cas.apereo.ext;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 重新定义 Credential model
 * 
 * @author stc
 *
 */
public class CustomLoginWebflowConfigurer extends DefaultLoginWebflowConfigurer {

	public CustomLoginWebflowConfigurer(FlowBuilderServices flowBuilderServices,
			FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext,
			CasConfigurationProperties casProperties) {
		super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
	}

	/**
	 * 重写父类方法
	 */
	@Override
	protected void doInitialize() {
		//super.doInitialize();
		
		final Flow flow = super.getLoginFlow();

		if (flow != null) {
			createCustomAuthnWebflowConfig(flow);
		}
	}

	/**
	 * 绑定CustomUsernamePasswordCredential　参数
	 * @param flow
	 */
	protected void createCustomAuthnWebflowConfig(Flow flow) {
		createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL,
				CustomUsernamePasswordCredential.class);
		final ViewState state = getState(flow, CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM, ViewState.class);
		final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
		cfg.addBinding(new BinderConfiguration.Binding("rememberMe", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("orgId", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("depId", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("verificationCode", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("systemId", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("loginType", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("nonceStr", null, false));
		cfg.addBinding(new BinderConfiguration.Binding("sign", null, false));

	}

}
