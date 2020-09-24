package spring_boot_tutorial.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  // Webリソースに関するセキュリティ設定
  @Override
  public void configure(WebSecurity web) throws Exception {

    // 静的リソースをセキュリティの対象外とする
    web.ignoring().antMatchers("/css/**", "/js/**");

  }

  // httpに関するセキュリティ設定（直リンクによるアクセス許可など）
  @Override
  public void configure(HttpSecurity http) throws Exception {

    // ログインなしでアクセスできるURLの設定
    http
      .authorizeRequests()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/signup").permitAll()
        
        .anyRequest().authenticated();  // 上記以外は直リンク禁止

    // ログイン処理に関する設定
    http
      .formLogin()
        .loginProcessingUrl("/login")
        .loginPage("/login")
        .failureUrl("/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/todo/index", true);

  }

}
