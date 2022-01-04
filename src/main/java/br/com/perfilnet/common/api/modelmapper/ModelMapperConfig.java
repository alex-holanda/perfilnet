package br.com.perfilnet.common.api.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		return modelMapper;
	}
}
