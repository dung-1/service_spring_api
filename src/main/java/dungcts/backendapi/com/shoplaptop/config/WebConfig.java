// package dungcts.backendapi.com.shoplaptop.config;

// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

// @Override
// public void addResourceHandlers(ResourceHandlerRegistry registry) {
// // Thư mục chứa CSS, JS, hình ảnh, v.v.
// registry.addResourceHandler("/vendor/**")
// .addResourceLocations("classpath:/static/vendor/");
// registry.addResourceHandler("/img/**")
// .addResourceLocations("classpath:/static/img/");
// registry.addResourceHandler("/css/**")
// .addResourceLocations("classpath:/static/css/");
// registry.addResourceHandler("/js/**")
// .addResourceLocations("classpath:/static/js/");
// }
// }

// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http.csrf(csrf -> csrf.disable())
// .exceptionHandling(handling ->
// handling.authenticationEntryPoint(unauthorizedHandler))
// .sessionManagement(management ->
// management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
// .authorizeRequests(requests -> requests
// .antMatchers("/static/**", "/css/**", "/img/**", "/vendor/**", "/js/**",
// "/fonts/**",
// "/favicon.ico")
// .permitAll()
// .antMatchers("/auth/**", "/login", "/").permitAll()
// .antMatchers("/product/**", "/categories/**", "/uploads/**",
// "/contacts/**").permitAll()
// .antMatchers("/admin/**").authenticated()
// .anyRequest().authenticated())
// .formLogin(login -> login
// .loginPage("/login")
// .defaultSuccessUrl("/admin/index", true)
// .permitAll())
// .logout(logout -> logout.permitAll());

// http.authenticationProvider(authenticationProvider());
// http.addFilterBefore(authenticationJwtTokenFilter(),
// UsernamePasswordAuthenticationFilter.class);
// }