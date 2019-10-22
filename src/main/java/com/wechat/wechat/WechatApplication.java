package com.wechat.wechat;

import com.wechat.wechat.controller.DomainFilter;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class WechatApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WechatApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		DomainFilter domainFilter = new DomainFilter();
		registrationBean.setFilter(domainFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.addUrlPatterns();
		//registrationBean.addInitParameter("paramName", "paramValue");
		//registrationBean.setName("sessionFilter");
		return registrationBean;
	}

	@Bean
	public CookieLocaleResolver cookieLocaleResolver(){
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		Locale locale = new Locale("zh_CN");
		cookieLocaleResolver.setDefaultLocale(locale);
		return cookieLocaleResolver;
	}

}
