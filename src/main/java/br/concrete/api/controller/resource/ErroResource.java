package br.concrete.api.controller.resource;

public class ErroResource {

	private String mensagem;

	public ErroResource(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
