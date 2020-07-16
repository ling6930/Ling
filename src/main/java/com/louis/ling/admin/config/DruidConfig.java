package com.louis.ling.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        Map<String,String> initParam = new HashMap<>();
        //允许登录的用户名
        initParam.put("loginUsername","admin");
        //允许登录的密码
        initParam.put("loginPassword","123456");
        //允许哪些ip地址访问，不写就是默认允许所有访问
        initParam.put("allow","");
        //拒绝访问ip名单
        initParam.put("deny","192.169.1.1");
        //添加初始化参数
        bean.setInitParameters(initParam);
        return bean;
    }

    @Bean
    public FilterRegistrationBean druidWebStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        //拦截哪些请求
        bean.setUrlPatterns(Arrays.asList("/*"));

        Map<String,String> initParam = new HashMap<>();
        //排除拦截哪些请求
        initParam.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParam);
        return bean;
    }
}
