package com.mcc.tem.falsework.base.sysetem.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mcc.tem.falsework.base.sysetem.interceptor.SystemAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



/****
* 拦截器 <br>
*
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2022/3/3 16:46 <br>
**/
@Configuration
@EnableAsync
public class WebMvcApplicationContext extends WebMvcConfigurationSupport {




    /**
    * 自定义拦截器注册 <br>
    *
    * @param registry <br>
    * @return void <br>
    * @throws Exception <br>
    * @author lixiao <br>
    * @createDate 2022/3/3 16:43 <br>
    **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(systemAuthenticationInterceptor()).excludePathPatterns("/actuator/health/check");
    }

    public SystemAuthenticationInterceptor systemAuthenticationInterceptor() {
        return new SystemAuthenticationInterceptor();
    }

    /**
    * 自定义消息转换器，转换http文本为fastjson <br>
    *
    * @param converters <br>
    * @return void <br>
    * @throws Exception <br>
    * @author lixiao <br>
    * @createDate 2022/3/3 16:43 <br>
    **/
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new ResourceHttpMessageConverter());
        converters.add(fastJsonHttpMessageConverters());
        super.addDefaultHttpMessageConverters(converters);
    }

    public static FastJsonHttpMessageConverter fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNonStringValueAsString);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }



}
