// src/main/java/br/fiap/gs/repository/CidadeRepository.java
package br.fiap.gs.repository;

import br.fiap.gs.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
