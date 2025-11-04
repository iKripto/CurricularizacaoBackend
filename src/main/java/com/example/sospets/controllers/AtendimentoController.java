package com.example.sospets.controllers;

import com.example.sospets.entities.Atendimento;
import com.example.sospets.services.AtendimentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/atendimentos")
public class AtendimentoController {

    public static final String ID = "/{id}";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AtendimentoService service;

    @PostMapping
    public ResponseEntity<Atendimento> create(@RequestBody Atendimento atendimento) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(service.create(atendimento).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> findAll(){
        return ResponseEntity.ok().body(service.findAll().stream()
                .map(a -> mapper.map(a, Atendimento.class)).collect(Collectors.toList()));
    }

    @GetMapping(value = ID)
    public ResponseEntity<Atendimento> findById (@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), Atendimento.class));
    }

    @PutMapping(value = ID)
    public ResponseEntity<Atendimento> update(@PathVariable Integer id, @RequestBody Atendimento atendimento) {
        atendimento.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(atendimento), Atendimento.class));
    }

    public ResponseEntity<Atendimento> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
