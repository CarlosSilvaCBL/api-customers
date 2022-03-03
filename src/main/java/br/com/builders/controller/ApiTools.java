package br.com.builders.controller;

import org.springframework.http.HttpHeaders;

import br.com.builders.util.Constants;

public class ApiTools {

	public static HttpHeaders createHeadersPaginacao(long totalElementos, Integer page, Integer size,
			int tamanhoLista) {
		int elementoInicial = (page * size) + 1;
		int elementoFinal = (page * size) + tamanhoLista;

		String contentRange = Integer.toString(elementoInicial).concat("-").concat(Integer.toString(elementoFinal))
				.concat("/").concat(Long.toString(totalElementos));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Range", contentRange);
		headers.add("Accept-Ranges", Constants.DEFAULT_PAGE_SIZE);

		return headers;
	}

}
