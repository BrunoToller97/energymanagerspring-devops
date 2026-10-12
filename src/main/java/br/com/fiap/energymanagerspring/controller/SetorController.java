package br.com.fiap.energymanagerspring.controller;

import br.com.fiap.energymanagerspring.dto.SetorCadastroDTO;
import br.com.fiap.energymanagerspring.dto.SetorExibicaoDTO;
import br.com.fiap.energymanagerspring.model.Setor;
import br.com.fiap.energymanagerspring.service.SetorService;
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
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping("/setores")
    @ResponseStatus(HttpStatus.CREATED)
    public SetorExibicaoDTO salvar(@Valid @RequestBody SetorCadastroDTO setor){
        return setorService.salvarSetor(setor);
    }

    @GetMapping("/setores")
    @ResponseStatus(HttpStatus.OK)
    public Page<SetorExibicaoDTO> listarTodos(
        @PageableDefault(size = 10,page = 0) Pageable paginacao){
        return setorService.listarTodos(paginacao);
    }

    @GetMapping("/setores/{id}")
    public ResponseEntity<SetorExibicaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(setorService.buscarPorId(id));
    }


    @DeleteMapping("/setores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        setorService.excluir(id);
    }

    @PutMapping("/setores")
    @ResponseStatus(HttpStatus.OK)
    public Setor atualizar(@Valid @RequestBody Setor setor){
        return setorService.atualizar(setor);
    }


    // http:localhost:8080/api/setores?nome=limpeza
    @GetMapping(value = "/setores", params = "nome")
    public ResponseEntity<SetorExibicaoDTO> buscarPorNome(@RequestParam String nome){

        try {
            return ResponseEntity
                    .ok(setorService.buscarPorNome(nome));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}







