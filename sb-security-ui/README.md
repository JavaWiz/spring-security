### Securing a Web Application

This guide walks you through the process of creating a simple web application with resources that are protected by Spring Security.

### What you’ll build

You’ll build a Spring MVC application that secures the page with a login form.

### Default Security Setup

In order to add security to our Spring Boot application, we need to add the security starter dependency:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

This will include the SecurityAutoConfiguration class – containing the initial/default security configuration.

Simply put, by default, the Authentication gets enabled for the Application. Also, content negotiation is used to determine if basic or formLogin should be used.

There are some predefined properties, such as:

```
spring.security.user.name
spring.security.user.password
```

If we don't configure the password using the predefined property spring.security.user.password and start the application, we'll notice that a default password is randomly generated and printed in the console log:

```
Using default security password: c8be15de-4488-4490-9dc6-fab3f91435c6
```

### Configuring Spring Boot Security


Above is the default security configuration; we can customize it by modifying the property file.

We can, for example, override the default password by adding our own:

```
spring.security.user.name=javawiz
spring.security.user.password=javawiz@123
```

If we want a more flexible configuration, with multiple users and roles for example – you now need to make use of a full @Configuration class:

```
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser("javawiz").password("{noop}javawiz@123").roles("USER")
          .and()
          .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
        .antMatchers("/actuatorHome").hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin();

    }
}

```
