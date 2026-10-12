package br.com.fiap.energymanagerspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tbl_funcionarios")
public class Funcionario {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_FUNCIONARIOS_SEQ")
    @SequenceGenerator(
            name = "TBL_FUNCIONARIOS_SEQ",
            sequenceName = "TBL_FUNCIONARIOS_SEQ",
            allocationSize = 1
    )
    private Long id;

    private String nome;

    private String cargo;

    private Double salario;

    private String ativo;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;





}