package com.instagram.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class LocalCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());          //생략 가능 (생략시 캐시 value 객체 수의 제한이 없어지고,  만료시간 제한도 없어짐)
        cacheManager.setCacheNames(Arrays.asList("postCache"));    //캐시로 사용할 이름 지정.. @Cachable 등의 어노테이션에서 사용하는 이름과 같아야 함
        cacheManager.setAllowNullValues(false);                    //캐시 값에 null을 허용하지 않음
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(100)                                   //최대 저장 가능한 캐시 Value 객체 수
                .expireAfterAccess(60, TimeUnit.MINUTES)    //마지막으로 캐시에 접근한 시간부터 60분이 지나면 만료
                //.expireAfterWrite(60, TimeUnit.MINUTES)           //캐시가 마지막으로 쓰여진 시간부터 60분이 지나면 만료
                .recordStats();
    }

}