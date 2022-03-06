package br.com.builders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.builders.entity.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findById(final Long id);

    @Transactional
    void deleteById(final Long id);

	Optional<CustomerEntity> findByDocumentNumber(String documentNumber);
	
	Optional<CustomerEntity> findByDocumentNumberAndName(String documentNumber, String name);

	Optional<CustomerEntity> findByName(String name);
	
	Page<CustomerEntity> findAll(Pageable pageable);

}
