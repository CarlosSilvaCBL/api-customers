package br.com.builders.controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.builders.enums.DocumentTypeEnum;
import br.com.builders.request.CustomerRequest;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	CustomerController controller;

	HttpHeaders headers = new HttpHeaders();

	@Test
	void testDelete() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			HttpEntity<CustomerRequest> entityDelete = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseDelete = restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityDelete, String.class);
			
			assertThat(responseDelete.getStatusCode().value()).isEqualTo(HttpStatus.NO_CONTENT.value());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testDeleteCustomerNotFound() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {

			HttpEntity<CustomerRequest> entityDelete = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseDelete = restTemplate.exchange(
					criaUrl("/api-customers/v1/customers/999999999"),
					HttpMethod.DELETE, entityDelete, String.class);
			
			assertThat(responseDelete.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFind() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);
			
			HttpEntity<CustomerRequest> entityFind = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseFind = restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.GET, entityFind, String.class);
			
			assertThat(responseFind.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityFind, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testPut() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			CustomerRequest requestFrankUpdate = new CustomerRequest("Frank da Silva", LocalDate.now(), DocumentTypeEnum.PASSPORT, "101010101010");
			HttpEntity<CustomerRequest> entityPut = new HttpEntity<CustomerRequest>(requestFrankUpdate, headers);
			
			ResponseEntity<String> responsePut = restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.PUT, entityPut, String.class);
			
			assertThat(responsePut.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityPut, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByName() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			HttpEntity<CustomerRequest> entityFind = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseGet = restTemplate.exchange(
					criaUrl("/api-customers/v1/customers?name=Frank"),
					HttpMethod.GET, entityFind, String.class);
			
			assertThat(responseGet.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityFind, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByDocumentNumberAndName() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, "1234567890");
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			HttpEntity<CustomerRequest> entityFind = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseGet = restTemplate.exchange(
					criaUrl("/api-customers/v1/customers?documentNumber=1234567890&name=Frank"),
					HttpMethod.GET, entityFind, String.class);
			
			assertThat(responseGet.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityFind, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByDocumentNumber() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, "1234567890");
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			HttpEntity<CustomerRequest> entityFind = new HttpEntity<CustomerRequest>(requestFrank, headers);
			
			ResponseEntity<String> responseGet = restTemplate.exchange(
					criaUrl("/api-customers/v1/customers?documentNumber=1234567890"),
					HttpMethod.GET, entityFind, String.class);
			
			assertThat(responseGet.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityFind, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void testFindByPagination() {
		CustomerRequest requestFrank = new CustomerRequest("Frank", LocalDate.now(), DocumentTypeEnum.PASSPORT, UUID.randomUUID().toString());
		try {
			ResponseEntity<Link> linkRequestSaveFrank = controller.save(requestFrank);

			HttpEntity<CustomerRequest> entityFind = new HttpEntity<CustomerRequest>(null, headers);
			
			ResponseEntity<String> responseGet = restTemplate.exchange(
					criaUrl("/api-customers/v1/customers?page=0&size=2"),
					HttpMethod.GET, entityFind, String.class);
			
			assertThat(responseGet.getStatusCode().value()).isEqualTo(HttpStatus.PARTIAL_CONTENT.value());
			
			restTemplate.exchange(
					criaUrl(linkRequestSaveFrank.getBody().getHref()),
					HttpMethod.DELETE, entityFind, String.class);
			
		} catch (Exception e) {
			fail();
		}
	}
	
	private String criaUrl(String url) {
		return String.format("http://localhost:%d%s", port, url);
	}
	
}
