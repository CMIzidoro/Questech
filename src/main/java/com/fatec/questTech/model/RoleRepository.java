package com.fatec.questTech.model;

import com.fatec.questTech.enumeration.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(Roles roleName);

}
