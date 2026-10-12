package br.com.fiap.energymanagerspring.dto;

import br.com.fiap.energymanagerspring.model.Usuario;

public record UsuarioExibicaoDTO(

        Long usuarioId,
        String nome,
        String email,
        String role

) {

    public UsuarioExibicaoDTO(Usuario usuario) {
        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }

}