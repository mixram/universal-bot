package com.mixram.telegram.bot.config.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixram.telegram.bot.services.domain.Data3DPlastic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author mixram on 2019-04-14.
 * @since ...
 */
@Configuration
public class RedisConfig {

    // <editor-fold defaultstate="collapsed" desc="***API elements***">

    private RedisConnectionFactory connectionFactory;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="***Util elements***">

    @Autowired
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // </editor-fold>


    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public RedisTemplate<String, Data3DPlastic> data3DPlasticRedisTemplate() {
        RedisSerializer<Object> jacksonSerializer = genericJackson2JsonRedisSerializer();
        RedisSerializer<String> stringSerializer = stringRedisSerializer();

        RedisTemplate<String, Data3DPlastic> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        return template;
    }

    @Bean
    public RedisSerializer<Object> genericJackson2JsonRedisSerializer() {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
                .failOnEmptyBeans(false)
                .build();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }


    // <editor-fold defaultstate="collapsed" desc="***Private elements***">

    //

    // </editor-fold>

}