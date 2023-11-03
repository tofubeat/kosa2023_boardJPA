package com.my.board;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

//자동생성된 단위테스트용 파일

@SpringBootTest
@Slf4j //@Slf4j를 쓰면 아래 Logger log = ~~ 코드가 자동으로 써진거나 마찬가지
class SpringbootBoardApplicationTests {
//	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	Environment env;
	
	@Value("${my.info.name}")
	private String name;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@DisplayName("Environment")
	void testEnv() {
		log.error("현재사용중인 profile={}", Arrays.toString(env.getActiveProfiles()));
		//지금 사용되는 profile이 가져와질 것을 예상
		log.error("현재사용중인 profile=" + Arrays.toString(env.getActiveProfiles()));
		//java의 String은 객체내용불변이라 +를 만날 때마다 새로운 String객체가 만들어짐 -> 비효율적
	
		log.error("현재사용중인 my.info.deploy.msg={}",
				  env.getProperty("my.info.deploy.msg"));
		log.error("현재사용중인 my.info.dev.msg={}",
				  env.getProperty("my.info.dev.msg"));
		
		//중복된 property찾기 (dev쓰고 있어서 dev예상)
		log.error("현재사용중인 my.info.name={}",
				  env.getProperty("my.info.name"));
		
		//@Value
		log.error("@Value(my.info.name)={}", name);
		
		env.getProperty("a", "없음"); //있으면 a값 없으면 없음 가져옴
	}

}
