package br.com.builders.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.builders.assembler.CustomerModelAssembler;
import br.com.builders.entity.CustomerEntity;
import br.com.builders.enums.DocumentTypeEnum;
import br.com.builders.model.CustomerModel;
import br.com.builders.request.CustomerRequest;
import br.com.builders.service.CustomerService;
import br.com.builders.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.BASE_PATH_CUSTOMER, produces = APPLICATION_JSON_VALUE)
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerModelAssembler customerModelAssembler;
	
	@GetMapping
	public ResponseEntity<List<CustomerModel>> find(
			@RequestParam(value = "documentNumber", required = false) String documentNumber,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(defaultValue = "0", required = false) int page,
	        @RequestParam(defaultValue = "5", required = false) int size) throws Exception {
		
		log.info("Requisicao de busca de cliente por numero de documento!");
		
		List<CustomerModel> customers = new ArrayList<>();
		if (Objects.nonNull(documentNumber) && Objects.nonNull(name)) {
			customers.add(customerService.findByDocumentNumberAndName(documentNumber, name)
					.map(customerModelAssembler::toModel).get());

		} else if (Objects.nonNull(documentNumber) && Objects.isNull(name)) {
			customers.add(customerService.findByDocumentNumber(documentNumber)
					.map(customerModelAssembler::toModel).get()); 
	
		} else if (Objects.isNull(documentNumber) && Objects.nonNull(name)) {
			customers.add(customerService.findByName(name)
					.map(customerModelAssembler::toModel).get()); 
			
		} else {
			Pageable paging = PageRequest.of(page, size);
			Page<CustomerEntity> pageEntity = customerService.findAllByPageable(paging);
			
			return new ResponseEntity<List<CustomerModel>>(
					pageEntity.get().map(customerModelAssembler::toModel).collect(Collectors.toList()), HttpStatus.PARTIAL_CONTENT);
		}

		return new ResponseEntity<List<CustomerModel>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<CustomerModel> findById(@PathVariable final long id) {
		return customerService.findById(id)
		        .map(customerModelAssembler::toModel) 
		        .map(ResponseEntity::ok) 
		        .orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
	public ResponseEntity<Link> save(@Valid @RequestBody final CustomerRequest request) throws Exception {

		log.info("Requisicao para gravar cliente!");
		CustomerEntity customer = new CustomerEntity(null, request.getName(), request.getBirthDate(),
				DocumentTypeEnum.PASSPORT, request.getDocumentNumber());
	
		CustomerEntity customerManaged = customerService.save(customer);

		Link link = customerModelAssembler.linkFindById(customerManaged.getId());
		
		return new ResponseEntity<Link>(link, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Link> put(@PathVariable("id") final long id, @RequestBody CustomerRequest request) {

		log.info("Requisicao para atualizar cliente com id: {}", id);

		final CustomerEntity entity = new CustomerEntity(id, request.getName(), request.getBirthDate(),
				DocumentTypeEnum.PASSPORT, request.getDocumentNumber());

		try {
			customerService.update(id, entity);
		} catch (Exception e) {
			log.error("Erro ao atualizar cliente com id: {}", id);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Link link = customerModelAssembler.linkFindById(id);
		return new ResponseEntity<Link>(link, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Link> delete(@PathVariable("id") final long id) {
		
		log.info("Requisicao para deletar cliente!");
		
		try {
			customerService.delete(id);
			return ResponseEntity.noContent().build();

		} catch (Exception e) {
			log.error("Erro ao deletar cliente com id: {}", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
