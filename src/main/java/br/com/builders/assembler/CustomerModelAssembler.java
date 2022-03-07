package br.com.builders.assembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.builders.controller.CustomerController;
import br.com.builders.entity.CustomerEntity;
import br.com.builders.model.CustomerModel;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<CustomerEntity, CustomerModel> {
	
	public CustomerModelAssembler() {
		super(CustomerController.class, CustomerModel.class);
	}
	
	@Override
	public CustomerModel toModel(CustomerEntity entity) {
		
		CustomerModel customerModel = instantiateModel(entity);
	     
		try {
			customerModel.add(linkTo(
						methodOn(CustomerController.class)
						.findById(entity.getId()))
						.withSelfRel());

			customerModel.setBirthDate(entity.getBirthDate());
			customerModel.setDocumentNumber(entity.getDocumentNumber());
			customerModel.setDocumentoType(entity.getDocumentType());
			customerModel.setName(entity.getName());
		
		} catch (Exception e) {
			log.error("Erro ao construir objeto de modelo", e);
		}
		
		return customerModel;
	}
	
	public Link linkFindById(Long id) {
		return linkTo(methodOn(CustomerController.class).findById(id)).withSelfRel();
	}

}
