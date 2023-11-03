package com.my.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//DB처리용 @Bean

//스프링부트가 만들어준 패키지 아래의 하위 패키지들은 @ComponentScan없어도 자동으로 해줌 
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/db.properties") //db.properties에서 값을 찾아라
public class MyApplicationContext {
	@Value("${spring.datasource.hikari.driver-class-name}") //이런 이름의 값을
	private String hikariDriverClassName; //변수설정(찾은 거 여기 넣을거임)
	
	@Autowired
	Environment env;
	
	//@PropertySource, @ConfigurationProperties 스프링부트에서만 사용가능
	@Bean
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		return config;
	}
	
	@Bean
	public HikariDataSource dataSourceHikari() {
		return new HikariDataSource(hikariConfig());
	}
	
}
