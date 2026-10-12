package br.com.fiap.energymanagerspring.dto;

import br.com.fiap.energymanagerspring.model.Setor;

public record SetorExibicaoDTO(

        Long id,
        String nome,
        Double metaReducaoPercentual,
        String responsavel

) {

    public SetorExibicaoDTO(Setor setor){
        this(
                setor.getId(),
                setor.getNome(),
                setor.getMetaReducaoPercentual(),
                setor.getResponsavel()
        );
    }

}