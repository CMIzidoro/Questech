package com.fatec.questTech.dto;

import java.util.List;

import com.fatec.questTech.model.Parametro;

import lombok.Data;

@Data
public class SessionDto {
	private String nomeUser;
	private Long idUser;
	private List<Parametro> parametros;
}
