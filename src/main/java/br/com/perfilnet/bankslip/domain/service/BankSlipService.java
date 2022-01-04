package br.com.perfilnet.bankslip.domain.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import br.com.perfilnet.bankslip.domain.model.BankSlip;
import br.com.perfilnet.bankslip.domain.repository.BankSlipRepository;
import br.com.perfilnet.bankslip.infrastructure.repository.spec.BankSlipSpec;
import br.com.perfilnet.common.domain.BusinessException;
import br.com.perfilnet.common.domain.EntityNotFoundException;
import br.com.perfilnet.product.domain.model.Product;
import br.com.perfilnet.product.domain.model.ProductType;
import br.com.perfilnet.product.domain.service.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BankSlipService {

	private final BankSlipRepository bankSlipRepository;

	private final ProductService productService;

	public Page<BankSlip> getAll(Pageable pageable) {
		return bankSlipRepository.findAll(BankSlipSpec.filter(), pageable);
	}

	@Transactional
	public void add(Long productId, Set<MultipartFile> files) {
		try {
			var product = productService.findById(productId);

			if (!product.getType().equals(ProductType.BANK_SLIP)) {
				throw new BusinessException("Empresa não possui o produto de boleto");
			}

			addBankSlips(product, files);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Async
	private void addBankSlips(Product product, Set<MultipartFile> files) {
		files.forEach(file -> {
			var quantidadePaginas = 0;

			try (var pdfReader = new PdfReader(file.getInputStream())) {
				var pdfDoc = new PdfDocument(pdfReader);

				quantidadePaginas = pdfDoc.getNumberOfPages();

				pdfDoc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			var bankSlip = BankSlip.builder().company(product.getCompany()).amount(quantidadePaginas)
					.price(product.getPrice()).total(new BigDecimal(quantidadePaginas).multiply(product.getPrice()))
					.fileName(file.getOriginalFilename()).year(String.valueOf(OffsetDateTime.now().getYear()))
					.month(OffsetDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())).build();

			bankSlipRepository.save(bankSlip);
		});
	}
}
