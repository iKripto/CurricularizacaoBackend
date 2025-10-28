package com.example.sospets.services.impl;

import com.example.sospets.entities.Animal;
import com.example.sospets.entities.Atendimento;
import com.example.sospets.entities.Clinica;
import com.example.sospets.entities.Funcionario;
import com.example.sospets.repositories.AnimalRepo;
import com.example.sospets.repositories.AtendimentoRepo;
import com.example.sospets.repositories.ClinicaRepo;
import com.example.sospets.repositories.FuncionarioRepo;
import com.example.sospets.services.AtendimentoService;
import com.example.sospets.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    @Autowired
    private AtendimentoRepo repository;

    @Autowired
    private AnimalRepo animalRepo;

    @Autowired
    private ClinicaRepo clinicaRepo;

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Atendimento create(Atendimento atendimento) {
        Animal animal = animalRepo.findById(atendimento.getAnimal().getId())
                .orElseThrow(()-> new ObjectNotFoundException("Animal não encontrado"));
        atendimento.setAnimal(animal);

        Clinica clinica = clinicaRepo.findById(atendimento.getClinica().getId())
                .orElseThrow(() -> new ObjectNotFoundException("Clínica não encontrada"));
        atendimento.setClinica(clinica);

        Funcionario funcionario = funcionarioRepo.findByCpf(atendimento.getFuncionario().getCpf())
                .orElseThrow(()-> new RuntimeException("Funcionário não encontrado"));
        atendimento.setFuncionario(funcionario);
        return repository.save(mapper.map(atendimento, Atendimento.class));
    }

    public List<Atendimento> findAll() {
        return repository.findAll();
    }

    @Override
    public Atendimento findById(Integer id) {
        Optional<Atendimento> atendimento = repository.findById(id);
        return atendimento.orElseThrow(()-> new ObjectNotFoundException("Atendimento não encontrado"));
    }

    @Override
    public Atendimento update(Atendimento atendimento) {
        return repository.save(mapper.map(atendimento, Atendimento.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
