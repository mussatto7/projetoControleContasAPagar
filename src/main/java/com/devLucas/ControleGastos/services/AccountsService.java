package com.devLucas.ControleGastos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.devLucas.ControleGastos.entity.AccountsPayable;
import com.devLucas.ControleGastos.repositories.AccountsRepository;
import com.devLucas.ControleGastos.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountsService {

	@Autowired
	private AccountsRepository repository;

	public List<AccountsPayable> findAll() {
		return repository.findAll();
	}

	public AccountsPayable findById(Long id) {
		Optional<AccountsPayable> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public AccountsPayable insert(AccountsPayable obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public AccountsPayable update(Long id, AccountsPayable obj) {
		try {
			AccountsPayable entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(AccountsPayable entity, AccountsPayable obj) {

		entity.setTitle(obj.getTitle());
		entity.setAmount(obj.getAmount());
		entity.setDueDate(obj.getDueDate());
		entity.setStore(obj.getStore());
		entity.setSituation(obj.getSituation());
	}
}
