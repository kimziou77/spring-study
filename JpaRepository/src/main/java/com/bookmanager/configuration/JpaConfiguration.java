package com.bookmanager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 이렇게 하면 어떤 사람이 생성을 하더라도 오류가 발생하지 않을 것임.
public class JpaConfiguration {


}
