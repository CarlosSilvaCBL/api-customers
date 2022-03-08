package br.com.builders.controller;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.builders.model.CustomerModel;
import br.com.builders.request.CustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface IApiCustomer {
	
	@Operation(summary = "Busca clientes!")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerModel.class)) }),
			@ApiResponse(responseCode = "206", description = "Conteudo parcial!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerModel.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro nos parametros!", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Nao encontrado!", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do sistema!", content = @Content)})
	public ResponseEntity<List<CustomerModel>> find(
			@Parameter(description = "Numero do documento do cliente!") String documentNumber,
			@Parameter(description = "Nome do cliente!") String name,
			@Parameter(description = "Numero da pagina a ser buscada!") int page,
			@Parameter(description = "Quantidade maxima de clientes a ser listada por pagina!") int size) 
					throws Exception;
	
	@Operation(summary = "Busca cliente por id!")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerModel.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro nos parametros!", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Nao encontrado!", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do sistema!", content = @Content)})
	public ResponseEntity<CustomerModel> findById(
			@Parameter(description = "Id do cliente!") final long id);
	
	@Operation(summary = "Grava cliente!")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Criado!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Link.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro nos parametros!", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Nao encontrado!", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do sistema!", content = @Content)})
	public ResponseEntity<Link> save(
			@Parameter(description = "Payload de entrada!" )
			final CustomerRequest request) throws Exception;
	
	@Operation(summary = "Atualiza cliente!")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Criado!", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Link.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro nos parametros!", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Nao encontrado!", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do sistema!", content = @Content)})
	public ResponseEntity<Link> put(
			@Parameter(description = "Id do cliente!") final long id,
			@Parameter(description = "Payload de entrada!") CustomerRequest request);
	
	@Operation(summary = "Delete cliente!")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Sem conteudo!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Erro nos parametros!", content = @Content),
			@ApiResponse(responseCode = "401", description = "Não autorizado!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Nao encontrado!", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do sistema!", content = @Content)})
	public ResponseEntity<Link> delete(
			@Parameter(description = "Id do cliente!")
			@PathVariable("id") final long id);
	
}
