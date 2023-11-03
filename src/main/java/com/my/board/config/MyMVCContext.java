package com.my.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//CORS문제 해결용 @Bean

@Configuration
@EnableWebMvc //mvc:annotation-driven
			  //HandlerAdapter가 messageConvert하도록 설정, 
			  //WebApplicationContext타입의 스프링컨테이너가 된다
public class MyMVCContext implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowCredentials(true)
				.allowedMethods("GET","POST","PUT","DELETE")
				//put방식은 꼭 cors문제 해결해야함
				.allowedOrigins("http://192.168.1.14:5500");
	}//mvc:cors 대신



	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver r = new InternalResourceViewResolver();
		r.setPrefix("/WEB-INF/view/");
		r.setSuffix(".jsp");
		return r;
	}
	

	
//	@Bean
//	public CommonsMultipartResolver multipartResolver() {
//		CommonsMultipartResolver mr = new CommonsMultipartResolver();
//		mr.setDefaultEncoding("UTF-8");
//		mr.setMaxUploadSize(1024*100);
//		mr.setMaxUploadSizePerFile(1024*10);
//		return mr;
//	}
	
}
