package br.com.fiap.energymanagerspring.dto;

import jakarta.validation.constraints.*;

public record SetorCadastroDTO(

        @NotBlank(message = "O nome do setor é obrigatório!")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "A meta de redução é obrigatória!")
        @Positive(message = "A meta deve ser maior que zero!")
        Double metaReducaoPercentual,

        @NotBlank(message = "O responsável é obrigatório!")
        @Size(max = 100, message = "O responsável deve ter no máximo 100 caracteres")
        String responsavel

) {
}