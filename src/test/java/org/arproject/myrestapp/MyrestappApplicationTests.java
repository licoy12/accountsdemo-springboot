package org.arproject.myrestapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.arproject.myrestapp.model.Account;
import org.arproject.myrestapp.repository.AccountRepository;
import org.arproject.myrestapp.service.AccountService;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class MyrestappApplicationTests {
	
	@Autowired
	private AccountService accountService;
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@MockBean
	private AccountRepository accountRepository;
	
	private static Logger log = LoggerFactory.getLogger(MyrestappApplicationTests.class);
	
	
	@Test
	public void getAccountsTests() {
		when(accountRepository.findAll()).thenReturn(Stream
				.of(new Account(100L, "Angelico", "Rodriguez", "aruser", "pass", "blue" ),
					new Account(101L, "Deo", "Rodriguez", "druser","pass","cyan")).collect(Collectors.toList()));
		
		assertEquals(2, accountService.getAccounts().size());
		log.info("Mocked objects: " + accountRepository.findAll());
	}
	
	@Test
	public void getAccountByIdTest() {
		Long id = 1L;
		when(accountRepository.findById(id)).thenReturn(Optional.of(new Account(103L, "Angelico", "Rodriguez", "aruser", "pass", "blue" )));
		assertThat(accountService.getAccountById(id) != null);
		log.info("null check: " + accountService.getAccountById(id));
	}
	
	@Test
	public void addAccountTest() {
		Account account = new Account(109L, "Angelico", "Rodriguez", "aruser", "pass", "blue");
		when(accountRepository.save(account)).thenReturn(account);
		assertEquals(account, accountService.addAccount(account));
		log.info("Actual: "+ account);
		log.info("Expected: "+ accountService.addAccount(account));
		
	}
	
	@Test
	public void deleteAccountTest() {
		Account account = new Account(104L, "Angelico", "Rodriguez", "aruser", "pass", "blue");
		accountService.deleteAccount(account.getId());
		verify(accountRepository, times(1)).deleteById(account.getId());
		//log.info("Checking if id 104 still exist: "+ (accountService.getAccountById(104L).isEmpty() ? "DELETED":"EXIST" ));
	}
	
	@Test
	public void existAccountByUsernameTest() {
		String username = "aruser";
		when(accountRepository.findByUsername(username)).thenReturn(new Account(104L, "Angelico", "Rodriguez", "aruser", "pass", "blue"));
		assertEquals(accountService.existAccountByUsername(username), true);
	}


}
