package spring_boot_tutorial.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  private static final String USER_SQL = "SELECT email, password, enabled FROM todo_user WHERE email = ?";
  private static final String ROLE_SQL = "SELECT email, role FROM todo_user WHERE email = ?";
  
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

    // ログアウト処理に関する設定
    http
      .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login");

  }

  // 認証に関する設定
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.jdbcAuthentication()
      .dataSource(dataSource)
      .usersByUsernameQuery(USER_SQL)
      .authoritiesByUsernameQuery(ROLE_SQL)
      .passwordEncoder(passwordEncoder()); // パスワードエンコーダの指定

  }

  // パスワードは暗号化してデータベースに登録されるため、
  // パスワードを暗号化する方式を指定する
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
