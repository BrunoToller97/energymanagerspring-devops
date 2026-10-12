package br.com.fiap.energymanagerspring.repository;

import br.com.fiap.energymanagerspring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    @Query("SELECT a FROM Usuario a WHERE a.nome = :nome")
    Optional<Usuario> buscarPorNome(@Param("nome") String nome);

}