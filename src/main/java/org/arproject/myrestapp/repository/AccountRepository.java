package org.arproject.myrestapp.repository;

import org.arproject.myrestapp.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
	public Account findByUsername(String username);
}
