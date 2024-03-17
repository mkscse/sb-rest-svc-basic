package com.mks.springboot.rest.example.student;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AccountResource {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> retrieveAllStudents() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account retrieveStudent(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isEmpty())
            throw new AccountNotFoundException("id-" + id);

        return account.get();
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteStudent(@PathVariable long id) {
        accountRepository.deleteById(id);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Object> createStudent(@RequestBody Account account) {
        Account savedStudent = accountRepository.save(account);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();

    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Account account, @PathVariable long id) {

        Optional<Account> studentOptional = accountRepository.findById(id);

        if (studentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        account.setId(id);

        accountRepository.save(account);

        return ResponseEntity.noContent()
                .build();
    }
}
