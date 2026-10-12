package br.com.fiap.energymanagerspring.repository;

import br.com.fiap.energymanagerspring.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {


    @Query("SELECT a FROM Funcionario a WHERE a.nome = :nome")
    Optional<Funcionario> buscarPorNome(@Param("nome") String nome);

}
