package xiao.li.com.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
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
import xiao.li.com.base.interceptor.SubsystemAuthenticationInterceptor;
import xiao.li.com.base.interceptor.WebAuthenticationInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableAsync
public class WebMvcApplicationContext extends WebMvcConfigurationSupport {

    @Bean
    public WebAuthenticationInterceptor webAuthenticationInterceptor() {
        return new WebAuthenticationInterceptor();
    }

    @Bean
    public SubsystemAuthenticationInterceptor subsystemAuthenticationInterceptor(){
        return new SubsystemAuthenticationInterceptor();
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(webAuthenticationInterceptor()).addPathPatterns("/**").excludePathPatterns("/s_api/**");
        registry.addInterceptor(subsystemAuthenticationInterceptor()).addPathPatterns("/s_api/**")
                .excludePathPatterns("/s_api/inventory/**", "/s_api/inbound/**", "/s_api/spsc_to_wms/**", "/s_api/integration/**", "/s_api/erp_to_wms/purchase_return/add");
    }

}
