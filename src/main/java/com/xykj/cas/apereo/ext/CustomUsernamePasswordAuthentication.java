package com.xykj.cas.apereo.ext;

import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.commons.beanutils.BeanUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.xykj.cas.pojo.DsUserCas;
import com.xykj.cas.service.UserService;
import com.xykj.cas.utils.Constants;

/**
 * 自定义验证类
 * 
 * @author stc
 *
 */
public class CustomUsernamePasswordAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {
	// slf4j日志记录器
	private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthentication.class.getName());
	private UserService userService;
	private CasConfigurationProperties casProperties;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCasProperties(CasConfigurationProperties casProperties) {
		this.casProperties = casProperties;
	}

	public CustomUsernamePasswordAuthentication(String name, ServicesManager servicesManager,
			PrincipalFactory principalFactory, Integer order) {
		super(name, servicesManager, principalFactory, order);
	}

	@Override
	public boolean supports(Credential credential) {
		// 判断传递过来的Credential 是否是自己能处理的类型
		return credential instanceof CustomUsernamePasswordCredential;
	}

	/**
	 * 自定义登录处理方法类
	 */
	@Override
	protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential)
			throws GeneralSecurityException, PreventedException {

		CustomUsernamePasswordCredential loginDTO = (CustomUsernamePasswordCredential) credential;
//        String requestCaptcha = loginDTO.getVerificationCode();
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        Object attribute = attributes.getRequest().getSession().getAttribute("captcha");
//
//        String realCaptcha = attribute == null ? null : attribute.toString();

		String username = loginDTO.getUsername();
		String password = loginDTO.getPassword();
		DsUserCas cas = new DsUserCas();
		cas.setLogonName(username);
		cas.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
		String retStr = userService.findByName(cas);
		logger.info("authenticateUsernamePasswordInternal->findByName()-> retStr = " + retStr);

		if (retStr.equalsIgnoreCase(Constants.loginSuccess)) {
			DsUserCas cas1 = userService.findUserInfo(cas);
			Map<String, ?> map = null;
			try {
				map = BeanUtils.describe(cas1);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, Object> map2 = new HashMap<>(map.size());
			map2.putAll(map);
			return createHandlerResult(credential, this.principalFactory.createPrincipal(loginDTO.getUsername(), map2),
					new ArrayList<>(0));

			// return createHandlerResult(credential,
			// this.principalFactory.createPrincipal("admin"));

		} else {
			throw new AccountNotFoundException("未找到相关的账户信息!请联系统管理员.");

		}
	}

}
