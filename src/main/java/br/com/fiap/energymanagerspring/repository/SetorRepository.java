package br.com.fiap.energymanagerspring.repository;

import br.com.fiap.energymanagerspring.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    @Query("SELECT a FROM Setor a WHERE a.nome = :nome")
    Optional<Setor> buscarPorNome(@Param("nome") String nome);

}
