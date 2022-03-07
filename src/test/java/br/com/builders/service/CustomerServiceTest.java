package br.com.builders.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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
public class CustomerServiceTest {

	@Autowired
	CustomerService service;
	
	@Test
	void testFindById() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());

		try {
			CustomerEntity entityThatWasSave = service.save(customerAnn);
			
			assertNotNull(entityThatWasSave.getId());
			
			service.delete(entityThatWasSave.getId());
		
			assertFalse(service.findById(entityThatWasSave.getId()).isPresent());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindAllByPageable() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		CustomerEntity customerJonny = new CustomerEntity(null, "Jonny", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		CustomerEntity customerPaul = new CustomerEntity(null, "Paul", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		
		try {
			service.save(customerAnn);
			service.save(customerJonny);
			service.save(customerPaul);
			
			Pageable pagingPageZeroAndSizeFive = PageRequest.of(0, 5);
			Page<CustomerEntity> customerPageZeroAndSizeFive = service.findAllByPageable(pagingPageZeroAndSizeFive);
			assertEquals(3, customerPageZeroAndSizeFive.get().count());
			
			Pageable pagingPageZeroAndSizeTwo = PageRequest.of(0, 2);
			Page<CustomerEntity> customerPageZeroAndSizeTwo = service.findAllByPageable(pagingPageZeroAndSizeTwo);
			assertEquals(2, customerPageZeroAndSizeTwo.get().count());
			
			Pageable pagingPageOneAndSizeTwo = PageRequest.of(1, 2);
			Page<CustomerEntity> customerPageOneAndSizeTwo = service.findAllByPageable(pagingPageOneAndSizeTwo);
			assertEquals(1, customerPageOneAndSizeTwo.get().count());
			
			Pageable pagingPageTwoAndSizeTwo = PageRequest.of(2, 2);
			Page<CustomerEntity> customerPageTwoAndSizeTwo = service.findAllByPageable(pagingPageTwoAndSizeTwo);
			assertEquals(0, customerPageTwoAndSizeTwo.get().count());
			
			service.delete(customerAnn.getId());
			service.delete(customerJonny.getId());
			service.delete(customerPaul.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testDeleteCustomerThatNotExists() {
		try {
			service.delete(Long.MAX_VALUE);
		} catch (Exception e) {
			assertEquals("Cliente nao encontrado!", e.getMessage());
		}
	}
	
	@Test
	void testUpdate() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		
		try {
			CustomerEntity entityThatWasSave = service.save(customerAnn);
			CustomerEntity newEntity = new CustomerEntity(entityThatWasSave.getId(), "Ann Li", entityThatWasSave.getBirthDate(), DocumentTypeEnum.PASSPORT, "1234567890");

			service.update(entityThatWasSave.getId(), newEntity);

			Optional<CustomerEntity> customerFindById = service.findById(entityThatWasSave.getId());
			
			assertEquals(customerFindById.get().getName(), newEntity.getName());
			assertEquals(customerFindById.get().getDocumentNumber(), newEntity.getDocumentNumber());
			
			service.delete(newEntity.getId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testUpdateCustomerThatNotExists() {
		try {
			CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
			
			service.update(Long.MAX_VALUE, customerAnn);
		} catch (Exception e) {
			assertEquals("Cliente nao encontrado!", e.getMessage());
		}
	}
	
	@Test
	void testFindByDocumentNumber() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			CustomerEntity entityThatWasSave = service.save(customerAnn);

			Optional<CustomerEntity> entityFindByDocumentNumber = service.findByDocumentNumber(entityThatWasSave.getDocumentNumber());
			
			assertEquals(customerAnn.getDocumentNumber(), entityFindByDocumentNumber.get().getDocumentNumber());
			
			service.delete(entityThatWasSave.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByDocumentNumberAndName() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			CustomerEntity entityThatWasSave = service.save(customerAnn);
			
			Optional<CustomerEntity> entityFindByDocumentNumberAndName = service.findByDocumentNumberAndName(entityThatWasSave.getDocumentNumber(), entityThatWasSave.getName());
			
			assertEquals(customerAnn.getDocumentNumber(), entityFindByDocumentNumberAndName.get().getDocumentNumber());
			assertEquals(customerAnn.getName(), entityFindByDocumentNumberAndName.get().getName());
			
			service.delete(entityThatWasSave.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByName() {
		CustomerEntity customerAnn = new CustomerEntity(null, "Ann", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			CustomerEntity entityThatWasSave = service.save(customerAnn);
			
			Optional<CustomerEntity> customerFindByName = service.findByName(entityThatWasSave.getName());
			
			assertEquals(customerAnn.getName(), customerFindByName.get().getName());
			
			service.delete(entityThatWasSave.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
}
