package com.devLucas.ControleGastos.resources;

import com.devLucas.ControleGastos.entity.AccountsPayable;
import com.devLucas.ControleGastos.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountsResource {

    @Autowired
    private AccountsService service;

    // FindAll
    @GetMapping
    public ResponseEntity<List<AccountsPayable>> findAll() {
        List<AccountsPayable> list = service.findAll();
        return ResponseEntity.ok().body(list);

    }

    // FindById
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountsPayable> findById(@PathVariable Long id) {
        AccountsPayable obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // Insert accounts
    @PostMapping
    public ResponseEntity<AccountsPayable> insert(@RequestBody AccountsPayable obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    // Delete accounts
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Update accounts
    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountsPayable> update(@PathVariable Long id, @RequestBody AccountsPayable obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
