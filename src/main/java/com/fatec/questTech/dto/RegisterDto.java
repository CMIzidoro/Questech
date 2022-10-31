package com.fatec.questTech.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fatec.questTech.validator.Register;
import com.fatec.questTech.validator.ValidStrenghtPassword;

import lombok.Data;

@Data
@Register
public class RegisterDto {

	@NotBlank
	private String nome;

	@NotBlank
	private String tel;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@ValidStrenghtPassword
	private String password;

	@NotBlank
	private String passwordConfirm;

	private String uuid;

}
