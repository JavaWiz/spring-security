## Spring REST + Spring Security Example


Start the Spring Boot application.

```
mvn spring-boot:run
```
A normal GET and POST will return a 401, all endpoints are protected, need authentication.

```
curl localhost:8080/books
```
Response:

```
{"timestamp":"2022-07-07T19:30:05.673+00:00","status":401,"error":"Unauthorized","path":"/books"}
```
Send a GET request along with user login.

```
curl localhost:8080/books -u user:password
```
Response:

```
[
	{
	"id": 1,
	"name": "A Guide to the Bodhisattva Way of Life",
	"author": "Santideva",
	"price": 15.41
	},
	{
	"id": 2,
	"name": "The Life-Changing Magic of Tidying Up",
	"author": "Marie Kondo",
	"price": 9.69
	},
	{
	"id": 3,
	"name": "Refactoring: Improving the Design of Existing Code",
	"author": "Martin Fowler",
	"price": 47.99
	}
]
```
Try to send a POST request with ‘user’ login, it will return 403, Forbidden error. This is because the user has no right to send a POST request.

```
curl -X POST localhost:8080/books -H "Content-type:application/json" -d {\"name\":\"ABC\",\"author\":\"James\",\"price\":\"8.88\"} -u user:password
```
Response:

```
{"timestamp":"2022-07-07T20:18:09.522+00:00","status":403,"error":"Forbidden","path":"/books"}
```
Review the Spring Security configuration again. To send POST,PUT,PATCH or DELETE request, we need admin

```
@Configuration
public class SecurityConfiguration {
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("{noop}password")
            .roles("USER")
            .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .build();
        InMemoryUserDetailsManager udm = new InMemoryUserDetailsManager();
        udm.createUser(user);
        udm.createUser(admin);
        return udm;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
		.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
		.and()
		.csrf().disable()
		.formLogin().disable().build();
    }
}

```
Try to send a POST request with admin login response we will get

```
{"id":4,"name":"ABC","author":"James","price":8.88}
```
If we try again to retrieve the all books details

```
curl localhost:8080/books -u user:password
```
We will get

```
[
	{
	"id": 1,
	"name": "A Guide to the Bodhisattva Way of Life",
	"author": "Santideva",
	"price": 15.41
	},
	{
	"id": 2,
	"name": "The Life-Changing Magic of Tidying Up",
	"author": "Marie Kondo",
	"price": 9.69
	},
	{
	"id": 3,
	"name": "Refactoring: Improving the Design of Existing Code",
	"author": "Martin Fowler",
	"price": 47.99
	},
	{
	"id": 4,
	"name": "ABC",
	"author": "James",
	"price": 8.88
	}
]
```
### Spring Security Integration Test




