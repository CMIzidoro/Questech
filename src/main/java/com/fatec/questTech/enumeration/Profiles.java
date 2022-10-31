package com.fatec.questTech.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profiles {

	ADMIN( "admin", "ADMIN", new Roles[] {}, true);

	private String name;
	private String description;
	private Roles[] roles;
	private boolean allRoles;

}
