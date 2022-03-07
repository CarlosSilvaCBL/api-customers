package br.com.builders.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.builders.entity.CustomerEntity;
import br.com.builders.enums.DocumentTypeEnum;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRepositoryTest {
	
	@Autowired
	CustomerRepository repository;

	CustomerEntity customerAnn;
	CustomerEntity customerJonny;
	CustomerEntity customerPaul;
	
	@BeforeAll
	void initialize() {
		customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		customerJonny = new CustomerEntity(null, "Jonny", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		customerPaul = new CustomerEntity(null, "Paul", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		
		repository.save(customerAnn);
		repository.save(customerJonny);
		repository.save(customerPaul);
	}
	
	@AfterAll
	protected void finalize() {
		repository.deleteById(customerAnn.getId());
		repository.deleteById(customerJonny.getId());
		repository.deleteById(customerPaul.getId());
	}
	
	@Test
	void testFindById() {
		Optional<CustomerEntity> idByFindNameAna = repository.findByName("Ann");
		Optional<CustomerEntity> idByFindId = repository.findById(idByFindNameAna.get().getId());
		
		assertEquals(idByFindId.get().getId(), idByFindNameAna.get().getId());
	}
	
	@Test
	void testFindAllByPageable() {
		Pageable pagingPageZeroAndSizeFive = PageRequest.of(0, 5);
		Page<CustomerEntity> customerPageZeroAndSizeFive = repository.findAll(pagingPageZeroAndSizeFive);
		assertEquals(3, customerPageZeroAndSizeFive.get().count());
		
		Pageable pagingPageZeroAndSizeTwo = PageRequest.of(0, 2);
		Page<CustomerEntity> customerPageZeroAndSizeTwo = repository.findAll(pagingPageZeroAndSizeTwo);
		assertEquals(2, customerPageZeroAndSizeTwo.get().count());
		
		Pageable pagingPageOneAndSizeTwo = PageRequest.of(1, 2);
		Page<CustomerEntity> customerPageOneAndSizeTwo = repository.findAll(pagingPageOneAndSizeTwo);
		assertEquals(1, customerPageOneAndSizeTwo.get().count());
		
		Pageable pagingPageTwoAndSizeTwo = PageRequest.of(2, 2);
		Page<CustomerEntity> customerPageTwoAndSizeTwo = repository.findAll(pagingPageTwoAndSizeTwo);
		assertEquals(0, customerPageTwoAndSizeTwo.get().count());
	}
	
	@Test
	void testFindByDocumentNumber() {
		Optional<CustomerEntity> customer = repository.findByDocumentNumber(customerJonny.getDocumentNumber());
		
		assertEquals(customer.get().getDocumentNumber(), customerJonny.getDocumentNumber());
	}
	
	@Test
	void testFindByDocumentNumberAndName() {
		Optional<CustomerEntity> customer = repository.findByDocumentNumberAndName(customerPaul.getDocumentNumber(), customerPaul.getName());
		
		assertEquals(customer.get().getDocumentNumber(), customerPaul.getDocumentNumber());
		assertEquals(customer.get().getName(), customerPaul.getName());
	}
	
	@Test
	void testFindByName() {
		Optional<CustomerEntity> customer = repository.findByName(customerAnn.getName());
		
		assertEquals(customer.get().getName(), customerAnn.getName());
	}
	
}
