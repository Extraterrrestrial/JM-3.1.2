package springboot.springBootMVC.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl")
                                      UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
        System.out.println("SecurityConfig - конструктор -> SecurityConfig: userDetailsService=" + userDetailsService + " successUserHandler=" + successUserHandler);
    }

        @Bean
    public PasswordEncoder passwordEncoder() {
            System.out.println("UsersController - passwordEncoder()");
            return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("UsersController - configure(HttpSecurity http)");
        http.authorizeRequests()
                .antMatchers("/", "/user")
                .hasAnyRole("ADMIN, USER")
                .antMatchers("/**")
                .hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(successUserHandler) // подключаем наш SuccessHandler для перенеправления по ролям
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .csrf()
                .disable();
    }
    // конфигурация для прохождения аутентификации
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("UsersController - configure(AuthenticationManagerBuilder auth)");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
