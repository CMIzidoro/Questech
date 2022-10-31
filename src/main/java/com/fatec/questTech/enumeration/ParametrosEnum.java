package com.fatec.questTech.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParametrosEnum {

	ENDPOINT_CEP("https://buscacepinter.correios.com.br/app/endereco/carrega-cep-endereco.php", FuncionalidadeEnum.CORREIOS.getNome()),
	ERROR_EMAIL("senderros@email.com", FuncionalidadeEnum.SYSTEM.getNome());

	private String valor;
	private String funcionalidade;


}
