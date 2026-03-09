package lk.taskmanager.api.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "task_management_resource";
    private final Environment environment;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        String base = environment.getRequiredProperty("spring.mvc.servlet.path");
        httpSecurity
                .authorizeRequests()
                .antMatchers(
                        base + "/user/signup",
                        base + "/application/version",
                        base + "/application/health"
                ).permitAll()

                .antMatchers(base + "/admin/**")
                .access("hasAnyRole('ROLE_ADMIN')")

                .antMatchers(base + "/task/**")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

                .antMatchers(base + "/user/**")
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
