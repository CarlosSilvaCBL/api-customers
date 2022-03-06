package br.com.builders.service.impl;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.builders.controller.CustomerController;
import br.com.builders.entity.CustomerEntity;
import br.com.builders.exception.BusinessException;
import br.com.builders.repository.CustomerRepository;
import br.com.builders.service.CustomerService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	final Logger log = LoggerFactory.getLogger(CustomerController.class);

	final CustomerRepository repository;

	@Override
	public Optional<CustomerEntity> findById(final Long id) {
		log.info("Buscando cliente por id: {}", id);
		return repository.findById(id);
	}

	@Override
	public CustomerEntity save(final CustomerEntity customer) {
		log.info("Gravando cliente {}", customer.toString());
		return repository.save(customer);
	}

	@Override
	public Page<CustomerEntity> findAllByPageable(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void delete(final Long id) throws Exception{
		if(!repository.existsById(id)) {
			log.info("Cliente de id: {} nao existe para delecao!", id);
			throw new BusinessException("Cliente nao encontrado!");
		}
		repository.deleteById(id);
		log.info("Cliente de id: {} foi deletado!", id);
	}

	@Override
	public void update(Long id, CustomerEntity customer) throws Exception{
		if(!repository.existsById(id)) {
			log.info("Cliente de id: {} nao existe para atualizacao!", id);
			throw new BusinessException("Cliente nao encontrado!");
		}
		customer.setId(id);
		log.info("Atualizando cliente {}", customer.toString());
		repository.save(customer);
	}

	@Override
	public Optional<CustomerEntity> findByDocumentNumber(String documentNumber) {
		log.info("Buscando cliente por numero de documento: {}", documentNumber);
		return repository.findByDocumentNumber(documentNumber);
	}

	@Override
	public Optional<CustomerEntity> findByDocumentNumberAndName(String documentNumber, String name) {
		log.info("Buscando cliente por numero de documento: {} e nome: {}", documentNumber, name);
		return repository.findByDocumentNumberAndName(documentNumber, name);
	}

	@Override
	public Optional<CustomerEntity> findByName(String name) {
		log.info("Buscando cliente por nome: {}", name);
		return repository.findByName(name);
	}
}
