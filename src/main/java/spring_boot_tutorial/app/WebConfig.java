package spring_boot_tutorial.app;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebConfig {
  
  @Bean
  public MessageSource messageSource() {
    
    ReloadableResourceBundleMessageSource bean =
      new ReloadableResourceBundleMessageSource();
    
    // メッセージプロパティのファイル名を指定する
    bean.setBasename("classpath:messages");

    // 日本語を扱えるようにするために、文字コードをUTF-8にする
    bean.setDefaultEncoding("UTF-8");

    return bean;

  }

  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {

    LocalValidatorFactoryBean localValidatorFactoryBean =
      new LocalValidatorFactoryBean();

    localValidatorFactoryBean.setValidationMessageSource(messageSource());

    return localValidatorFactoryBean;

  }

}
