package org.arproject.myrestapp.service;

import java.util.List;
import java.util.Optional;

import org.arproject.myrestapp.model.Account;
import org.arproject.myrestapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	public Account addAccount(Account account) {
		return accountRepository.save(account);
	}
	
	public List<Account> getAccounts(){
		//List<Account> accounts = (List<Account>) accountRepository.findAll();
		return (List<Account>) accountRepository.findAll();
	}
	
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}
	
	public boolean existAccountByUsername(String username) {
		return accountRepository.findByUsername(username)!=null;
	}
	
	public Optional<Account> getAccountById(Long id) {
		return accountRepository.findById(id);
	}
	
}
