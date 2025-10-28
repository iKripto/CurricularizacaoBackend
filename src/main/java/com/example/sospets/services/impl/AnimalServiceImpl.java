package com.example.sospets.services.impl;

import com.example.sospets.entities.Animal;
import com.example.sospets.entities.Cor;
import com.example.sospets.enums.Especie;
import com.example.sospets.repositories.AnimalRepo;
import com.example.sospets.repositories.CorRepo;
import com.example.sospets.services.AnimalService;
import com.example.sospets.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepo repository;

    @Autowired
    private CorRepo corRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Animal create(Animal animal) {
        Cor cor = corRepo.findById(animal.getCor().getId())
                .orElseThrow(() -> new ObjectNotFoundException("Cor não encontrada"));
        animal.setCor(cor);
        return repository.save(mapper.map(animal, Animal.class));
    }

    public List<Animal> findAll() {
        return repository.findAll();
    }

    @Override
    public Animal findById(Integer id) {
        Optional<Animal> animal = repository.findById(id);
        return animal.orElseThrow(()-> new ObjectNotFoundException("Animal não encontrado"));
    }

    @Override
    public List<Animal> findByNomeContainingIgnoreCase(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Animal> findByNomeContainingIgnoreCaseAndEspecie(String nome, Especie especie) {
        return repository.findByNomeContainingIgnoreCaseAndEspecie(nome, especie);
    }

    @Override
    public List<Animal> findByEspecieOrderByNomeDesc(Especie especie) {
        return repository.findByEspecieOrderByNomeDesc(especie);
    }

    @Override
    public Animal update(Animal animal) {
        return repository.save(mapper.map(animal, Animal.class));
    }

    public void delete(Integer id){
        findById(id);
        repository.deleteById(id);
    }
}
