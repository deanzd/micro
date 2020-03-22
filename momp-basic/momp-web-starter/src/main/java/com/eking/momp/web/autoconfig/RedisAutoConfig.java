package com.eking.momp.web.autoconfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisAutoConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // key序列化规则
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        // value序列化规则
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.setDefaultTyping(ObjectMapper.DefaultTypeResolverBuilder
//                .construct(ObjectMapper.DefaultTyping.NON_FINAL, BasicPolymorphicTypeValidator.builder().build()));
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        objectMapper.registerModule(timeModule);
        valueSerializer.setObjectMapper(objectMapper);

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration configuration = redisCacheConfiguration();
//        Set<String> cacheNames = new HashSet<>();
//        cacheNames.add("model");
//        Map<String, RedisCacheConfiguration> cacheCfgs = new HashMap<>();
//        cacheCfgs.put("model", defaultCacheCfg.entryTtl(Duration.ofMinutes(60)));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
//                .cacheDefaults(configuration)
//                .initialCacheNames(cacheNames)
//                .withInitialCacheConfigurations(cacheCfgs)
                .build();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisTemplate redisTemplate) {
        RedisCacheConfiguration defaultCacheCfg = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(60));
        return defaultCacheCfg;
    }
}
