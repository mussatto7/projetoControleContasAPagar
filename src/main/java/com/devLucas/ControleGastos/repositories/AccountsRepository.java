package com.devLucas.ControleGastos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devLucas.ControleGastos.entity.AccountsPayable;

public interface AccountsRepository extends JpaRepository<AccountsPayable, Long> {

}
