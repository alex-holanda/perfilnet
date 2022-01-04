package br.com.perfilnet.common.web;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.perfilnet.common.web.converter.YearMonthConverter;

public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new YearMonthConverter());
	}
}
