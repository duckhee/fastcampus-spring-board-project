package kr.co.won;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan // 생성자로 바인딩을 하기 위해서는 설정 프로퍼티에 대한 값을 scan 을 해야한다.
@SpringBootApplication
public class SpringProjectBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProjectBoardApplication.class, args);
	}

}
