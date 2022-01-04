package br.com.perfilnet.bankslip.domain.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface BankSlipJoinService {

	public byte[] join(List<MultipartFile> files);
}
