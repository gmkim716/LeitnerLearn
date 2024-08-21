package com.TMDB.backend.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Leitner Learn API", // API 문서의 제목
    version = "1.0.0", // API 버전
    description = "API documentation for Leitner Learn application", // API 설명
    contact = @Contact(
      name = "Gyeongmin Kim", // 연락처 정보 (필요에 따라)
      email = "gmkim716@gmail.com",
      url = "https://www.github.com/gmkim716"
    )
  )
)
public class SwaggerConfig implements WebMvcConfigurer {

}
