package com.cryptoexchange.cryptoexchange.payloads.responses;


// Mensagem de resposta
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
        this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
