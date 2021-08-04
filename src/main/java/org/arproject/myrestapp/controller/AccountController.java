package org.arproject.myrestapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.arproject.myrestapp.model.Account;
import org.arproject.myrestapp.service.AccountService;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class AccountController {
	@Autowired
	AccountService accountService;
	
//	@PostMapping(value="/login")
//	public ResponseEntity<?> userLogin(@RequestParam String username, @RequestParam String password){
//		Account account = accountService.getAccountByUsername(username);
//		
//		if(accountService.logUser(username, password)) {
//			return ResponseEntity.ok(account);
//		}
//		return ResponseEntity.badRequest().body("Invalid credentials");
//	}
	
	@GetMapping(value="/user/{id}")
	public ResponseEntity<?> welcome (@PathVariable Long id){
		Optional<Account> user = accountService.getAccountById(id);
		return user.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<?> search (@RequestParam Long id){
		Optional<Account> user = accountService.getAccountById(id);
		return user.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	@GetMapping(value="/getaccounts")
	public ResponseEntity<?> getAccounts(){
		return ResponseEntity.ok().body(accountService.getAccounts());
	}
	
	@PostMapping(value="/addaccount")
	public ResponseEntity<?> addAccount(@RequestBody Account account){
		if(accountService.existAccountByUsername(account.getUsername())) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok().body(accountService.addAccount(account));
	}
	
	@DeleteMapping(value="/deleteaccount/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable Long id){
		Optional<Account> account = accountService.getAccountById(id);
		
		if(account == null) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}
		accountService.deleteAccount(id);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}	
	
}
