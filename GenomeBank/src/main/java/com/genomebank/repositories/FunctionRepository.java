package com.genomebank.repositories;

import com.genomebank.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FunctionRepository extends JpaRepository<Function, Long> {
    List<Function> findByCode(String code);
    List<Function> findByCategory(String category);
}
