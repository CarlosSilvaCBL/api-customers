package br.com.builders.request;

import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.builders.enums.DocumentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import org.springframework.hateoas.server.core.Relation;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "cliente")
@Relation(collectionRelation = "clientes")
@Schema(description = "Dados do cliente")
@Validated
public class CustomerRequest {
	
	@Schema(description = "Nome do cliente")
    private String name;

	@Schema(description = "Data de nascimento")
	private LocalDate birthDate;
	
	@Schema(description = "Tipo do documento")
	private DocumentTypeEnum documentoType;
	
	@Schema(description = "Numero documento")
	private String documentNumber;
	
}

