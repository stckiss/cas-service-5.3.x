package com.xykj.cas.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 让spring加载这个包下的类
 * 需要在spring.factories中配置
 * @author stc
 *
 */
@Configuration
@ComponentScan("com.xykj.cas")
public class SpringConfig {

}
