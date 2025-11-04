package com.example.sospets.repositories;

import com.example.sospets.entities.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepo extends JpaRepository<Atendimento, Integer> {
}
