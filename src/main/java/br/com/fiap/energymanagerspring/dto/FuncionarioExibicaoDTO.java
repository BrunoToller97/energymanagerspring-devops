package br.com.fiap.energymanagerspring.dto;

import br.com.fiap.energymanagerspring.model.Funcionario;

public record FuncionarioExibicaoDTO(

        Long id,
        String nome,
        String cargo,
        Double salario,
        String ativo,
        Long setorId,
        String nomeSetor

) {

    public FuncionarioExibicaoDTO (Funcionario funcionario){
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getAtivo(),
                funcionario.getSetor().getId(),
                funcionario.getSetor().getNome()
        );
    }

}