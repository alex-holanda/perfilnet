package br.com.perfilnet.common.api.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ApiUtils {

	public static URI uri(Object id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
