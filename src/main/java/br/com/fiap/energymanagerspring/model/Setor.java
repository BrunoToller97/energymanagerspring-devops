package br.com.fiap.energymanagerspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tbl_setores")
public class Setor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_SETORES_SEQ")
    @SequenceGenerator(
            name = "TBL_SETORES_SEQ",
            sequenceName = "TBL_SETORES_SEQ",
            allocationSize = 1)
    private Long id;

    private String nome;

    @Column(name = "meta_reducao_percentual")
    private Double metaReducaoPercentual;

    private String responsavel;


    @OneToMany(mappedBy = "setor")
    @JsonIgnore
    private List<Funcionario> funcionarios;


}
