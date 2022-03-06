package br.com.builders.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.builders.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerService {
	
    Optional<CustomerEntity> findById(Long id);

    CustomerEntity save(CustomerEntity sample);

    Page<CustomerEntity> findAllByPageable(Pageable pageable);

    void delete(Long id) throws Exception;

    void update(Long id, CustomerEntity sample) throws Exception;
    
    Optional<CustomerEntity> findByDocumentNumber(String documentNumber);
    
    Optional<CustomerEntity> findByDocumentNumberAndName(String documentNumber, String name);

	Optional<CustomerEntity> findByName(String name);

}
