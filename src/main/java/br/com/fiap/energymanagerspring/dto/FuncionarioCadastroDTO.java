package br.com.fiap.energymanagerspring.dto;

import jakarta.validation.constraints.*;

public record FuncionarioCadastroDTO(

        @NotBlank(message = "O nome do funcionário é obrigatório!")
        String nome,

        @NotBlank(message = "O cargo do funcionário é obrigatório!")
        String cargo,

        @NotNull(message = "O salário é obrigatório!")
        @Positive(message = "O salário deve ser maior que zero!")
        Double salario,

        @NotBlank(message = "O campo ativo é obrigatório!")
        @Pattern(regexp = "S|N", message = "O campo ativo deve ser 'S' ou 'N'")
        String ativo,

        @NotNull(message = "O setor é obrigatório!")
        Long setorId

) {}