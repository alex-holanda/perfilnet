package br.com.perfilnet.bankslip.api.model.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceInput {

	private List<MultipartFile> files = new ArrayList<>();
}
