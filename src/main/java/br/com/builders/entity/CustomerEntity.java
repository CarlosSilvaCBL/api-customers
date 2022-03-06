package br.com.builders.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.builders.enums.DocumentTypeEnum;
import br.com.builders.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = Constants.TABLE_CUSTOMERS)
public class CustomerEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 70, nullable = false)
	private String name;

	@Column(name = "BIRTH_DATE", nullable = false)
	private LocalDate birthDate;

	@Column(name = "DOCUMENT_TYPE", nullable = false, length = 40)
	@Enumerated(EnumType.STRING)
	private DocumentTypeEnum documentType;

	@GeneratedValue(generator = "customer_sequence")
	@SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
	@Column(name = "DOCUMENT_NUMBER", unique = true, nullable = false, length = 40)
	private String documentNumber;

}
