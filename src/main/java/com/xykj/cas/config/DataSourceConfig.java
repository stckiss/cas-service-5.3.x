package com.xykj.cas.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 配置数据源,使用阿里Druid数据源
 * @author stc
 *
 */
@Configuration
//@EnableConfigurationProperties(CasConfigurationProperties.class)
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	//private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class.getName());
//	@Autowired
//  private CasConfigurationProperties casProperties;
    static final String PACKAGE = "com.xykj.cas.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
 
    @Value("${cas.custom.properties.ds1.datasource.url}")
    private String url;
    @Value("${cas.custom.properties.ds1.datasource.username}")
    private String user;
    @Value("${cas.custom.properties.ds1.datasource.password}")
    private String password;
    @Value("${cas.custom.properties.ds1.datasource.driverClassName}")
    private String driverClass;
    
    @Value("${cas.custom.properties.ds1.datasource.maxActive}")
    private Integer maxActive;
    @Value("${cas.custom.properties.ds1.datasource.minIdle}")
    private Integer minIdle;
    @Value("${cas.custom.properties.ds1.datasource.initialSize}")
    private Integer initialSize;
    @Value("${cas.custom.properties.ds1.datasource.maxWait}")
    private Long maxWait;
    @Value("${cas.custom.properties.ds1.datasource.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${cas.custom.properties.ds1.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${cas.custom.properties.ds1.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${cas.custom.properties.ds1.datasource.testWhileIdle}")
    private Boolean testOnBorrow;
    @Value("${cas.custom.properties.ds1.datasource.testOnBorrow}")
    private Boolean testOnReturn;
 
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
    	//jdbc配置
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        
        //连接池配置
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setValidationQuery("SELECT 'x'");
        
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
//        logger.info(casProperties.getCustom().getProperties().get("ds1.datasource.url"));
//        logger.info(url2);
        try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return dataSource;
    }
 
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
 
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.xykj.cas.pojo");
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
