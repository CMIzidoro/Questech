package com.fatec.questTech.service;

import java.util.List;
import java.util.Optional;

import com.fatec.questTech.model.Funcionalidade;
import com.fatec.questTech.model.FuncionalidadeRepository;
import com.fatec.questTech.util.PageableUtil;
import com.fatec.questTech.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FuncionalidadeService {

	@Autowired
	private FuncionalidadeRepository repository;

	public Funcionalidade find(String uuid) {
		return repository.findByUuid(uuid).orElse(new Funcionalidade());
	}

	public void delete(String uuid) {
		Funcionalidade entity = repository.findByUuid(uuid).orElseThrow();
		repository.delete(entity);
	}

	public void save(Funcionalidade func) {
		if (ServiceUtil.notEmpty(func.getUuid())) {
			Funcionalidade entity = repository.findByUuid(func.getUuid()).orElseThrow();
			entity.setDescricao(func.getDescricao());
			repository.save(entity);
		} else {
			func.setUuid(ServiceUtil.generateUuid());
			repository.save(func);
		}
	}

	public List<Funcionalidade> list(Optional<String> fieldName, Optional<String> fieldValue) {
		if (!fieldName.isPresent() || !fieldValue.isPresent()) {
			return repository.findAll(PageableUtil.getPageable("nome")).toList();
		} else if (fieldName.get().equals("nome")) {
			return repository.findByNomeContainingIgnoreCase(fieldValue.get().trim(), PageableUtil.getPageable());
		} else {
			return repository.findAll(PageableUtil.getPageable("nome")).toList();
		}
	}

}
