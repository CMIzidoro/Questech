package com.fatec.questTech.dto;

import javax.validation.constraints.NotBlank;

import com.fatec.questTech.validator.ConfirmPassword;
import com.fatec.questTech.validator.ChangePwd;
import com.fatec.questTech.validator.ValidStrenghtPassword;

import lombok.Data;

@Data
@ChangePwd
public class ChagePwdDto {

	private String uuid;

	@ConfirmPassword
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	@ValidStrenghtPassword
	private String newPassword;

	@NotBlank
	private String newPasswordConfirm;

}
