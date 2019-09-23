package springrestapi.demo.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import springrestapi.demo.models.User;
import springrestapi.demo.repo.UserRepo;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor extractor(UserRepo userRepo){
        return map -> {
            String id = (String) map.get("sub");
            User user = userRepo.findById(id).orElseGet(() -> {
                User user1 = User.builder()
                        .id(id)
                        .userPic((String) map.get("picture"))
                        .email((String) map.get("email"))
                        .gender((String) map.get("gender"))
                        .locale((String) map.get("locale"))
                        .name((String) map.get("name"))
                        .build();
                return user1;
            });
            user.setLastVisit(LocalDateTime.now());
            return userRepo.save(user);
        };
    }
}
