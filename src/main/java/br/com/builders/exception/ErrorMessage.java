package br.com.builders.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -40089374255050241L;

	private String code;

	private String message;

	private String link;

	public ErrorMessage(final String code, final String msg, final String identification) {
		this.code = code;
		message = msg;
		link = identification;
	}

	public ErrorMessage(final String code, final String msg) {
		this.code = code;
		message = msg;
	}
	
	public ErrorMessage(final String msg) {
		message = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = StringUtils.trimAllWhitespace(code);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = StringUtils.trimAllWhitespace(message);
	}

	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = StringUtils.trimAllWhitespace(link);
	}
}
