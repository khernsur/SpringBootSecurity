    package com.example.SpringBootSecurity.Security;
    
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    
    
    
        @Configuration
        @EnableWebSecurity
        @EnableGlobalMethodSecurity(prePostEnabled=true,proxyTargetClass=true)
        public class SecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
                auth.inMemoryAuthentication()
                        .withUser("user").password("{noop}password").roles("USER")
                        .and()
                        .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    
            }
    
            @Override
            protected void configure(HttpSecurity http) throws Exception {
    
                http
                        //HTTP Basic authentication
                        .httpBasic()
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/student/getAllStudents").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/student/getStudentById{/id}").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/student/addStudent").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/student/updateStudent/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/student/deleteStudent").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/student/{id}/enroll").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/student/{id}/drop").hasRole("ADMIN")
    
                        .antMatchers(HttpMethod.GET, "/courses/getAllCourse").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/courses/getCourseById").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/courses/addCourse").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/courses/updateCourse/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/courses/deleteCourseById/{id}").hasRole("ADMIN")
                        .and()
                        .csrf().disable()
                        .formLogin().disable();
            }
        }