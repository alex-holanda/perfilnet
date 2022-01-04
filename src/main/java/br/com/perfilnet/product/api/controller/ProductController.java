package br.com.perfilnet.product.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.perfilnet.common.api.utils.ApiUtils;
import br.com.perfilnet.product.api.assembler.ProductModelAssembler;
import br.com.perfilnet.product.api.disassembler.ProductInputDisassembler;
import br.com.perfilnet.product.api.model.ProductModel;
import br.com.perfilnet.product.api.model.input.ProductInput;
import br.com.perfilnet.product.domain.model.Product;
import br.com.perfilnet.product.domain.model.ProductType;
import br.com.perfilnet.product.domain.service.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	private final ProductService productService;

	private final PagedResourcesAssembler<Product> pagedResourceAssembler;

	private final ProductModelAssembler productModelAssembler;

	private final ProductInputDisassembler productInputDisassembler;

	@GetMapping
	public ResponseEntity<PagedModel<ProductModel>> getAll(Pageable pageable) {
		var pagedProducts = productService.findAll(pageable);
		var productsModel = pagedResourceAssembler.toModel(pagedProducts, productModelAssembler);
		
		return ResponseEntity.ok(productsModel);
	}

	@GetMapping("/bank-slips")
	public ResponseEntity<CollectionModel<ProductModel>> getAllBankSlips() {
		var products = productService.findAll(ProductType.BANK_SLIP);
		var productsModel = productModelAssembler.toCollectionModel(products);

		return ResponseEntity.ok(productsModel);
	}

	@GetMapping("/paychecks")
	public ResponseEntity<CollectionModel<ProductModel>> getAllPaychecks() {
		var products = productService.findAll(ProductType.PAYCHECK);
		var productsModel = productModelAssembler.toCollectionModel(products);

		return ResponseEntity.ok(productsModel);
	}

	@GetMapping("/income-reports")
	public ResponseEntity<CollectionModel<ProductModel>> getAllIncomeReports() {
		var products = productService.findAll(ProductType.INCOME_REPORT);
		var productsModel = productModelAssembler.toCollectionModel(products);

		return ResponseEntity.ok(productsModel);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductModel> findById(@PathVariable Long productId) {
		var product = productService.findById(productId);
		var productModel = productModelAssembler.toModel(product);

		return ResponseEntity.ok(productModel);
	}

	@PostMapping
	public ResponseEntity<ProductModel> add(@Valid @RequestBody ProductInput productInput) {
		var product = productInputDisassembler.toDomainObject(productInput);
		product = productService.save(product);

		var productModel = productModelAssembler.toModel(product);

		return ResponseEntity.created(ApiUtils.uri(productModel.getId())).body(productModel);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductModel> update(@PathVariable Long productId,
			@Valid @RequestBody ProductInput productInput) {
		var product = productInputDisassembler.toDomainObject(productInput);
		product = productService.update(productId, product);

		var productModel = productModelAssembler.toModel(product);

		return ResponseEntity.ok(productModel);
	}
}
