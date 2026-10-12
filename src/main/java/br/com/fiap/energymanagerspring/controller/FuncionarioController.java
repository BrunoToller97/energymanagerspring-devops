package br.com.fiap.energymanagerspring.controller;

import br.com.fiap.energymanagerspring.dto.FuncionarioCadastroDTO;
import br.com.fiap.energymanagerspring.dto.FuncionarioExibicaoDTO;
import br.com.fiap.energymanagerspring.model.Funcionario;
import br.com.fiap.energymanagerspring.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/funcionarios")
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioExibicaoDTO salvar(@RequestBody @jakarta.validation.Valid FuncionarioCadastroDTO funcionario){
        return funcionarioService.salvarFuncionario(funcionario);
    }

    @GetMapping("/funcionarios")
    @ResponseStatus(HttpStatus.OK)
    public Page<FuncionarioExibicaoDTO> listarTodos(
            @PageableDefault(size = 10,page = 0) Pageable paginacao){
        return funcionarioService.listarTodos(paginacao);
    }

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioExibicaoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(funcionarioService.buscarPorID(id));
    }

    @DeleteMapping("/funcionarios/{funcionarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long funcionarioId){
        funcionarioService.excluir(funcionarioId);
    }

    @PutMapping("/funcionarios")
    @ResponseStatus(HttpStatus.OK)
    public Funcionario atualizar(@Valid @RequestBody Funcionario funcionario){
        return funcionarioService.atualizar(funcionario);
    }

    // http:localhost:8080/api/funcionarios?nome=Antonio
    @GetMapping(value = "/funcionarios", params = "nome")
    public ResponseEntity<FuncionarioExibicaoDTO> buscarPorNome(@RequestParam String nome){

        try {
            return ResponseEntity
                    .ok(funcionarioService.buscarPorNome(nome));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }


}
