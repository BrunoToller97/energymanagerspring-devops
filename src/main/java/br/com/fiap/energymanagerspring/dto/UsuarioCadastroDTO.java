package br.com.fiap.energymanagerspring.dto;

import jakarta.validation.constraints.*;

public record UsuarioCadastroDTO(

        @NotBlank(message = "O nome é obrigatório!")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "O email deve ser válido!")
        @Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "A senha é obrigatória!")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
        String senha,

        @NotBlank(message = "A role é obrigatória!")
        @Size(max = 50, message = "A role deve ter no máximo 50 caracteres")
        String role

) {
}