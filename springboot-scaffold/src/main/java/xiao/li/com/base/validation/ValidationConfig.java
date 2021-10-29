package xiao.li.com.base.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author shuai.zhang
 * @projectName rule-engine
 * @Description
 * @createTime 2020-06-10 14:09
 */
@Configuration
public class ValidationConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

    @Bean
    public static Validator validator() {
        //快速返回模式，有一个验证失败立即返回错误信息
        return Validation.byProvider(HibernateValidator.class).configure().failFast(true).
                buildValidatorFactory().getValidator();
    }
}