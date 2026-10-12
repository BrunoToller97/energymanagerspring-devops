package br.com.fiap.energymanagerspring.controller;


import br.com.fiap.energymanagerspring.dto.UsuarioCadastroDTO;
import br.com.fiap.energymanagerspring.dto.UsuarioExibicaoDTO;
import br.com.fiap.energymanagerspring.model.Usuario;
import br.com.fiap.energymanagerspring.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoDTO salvar(@Valid @RequestBody UsuarioCadastroDTO usuario){
        return usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioExibicaoDTO> listarTodos(
            @PageableDefault(size = 10,page = 0) Pageable paginacao){
        return usuarioService.listarTodos(paginacao);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioExibicaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        usuarioService.excluir(id);
    }

    @PutMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public Usuario atualizar(@Valid @RequestBody Usuario usuario){
        return usuarioService.atualizar(usuario);
    }


    // http:localhost:8080/api/usuarios?nome=Lino
    @GetMapping(value = "/usuarios", params = "nome")
    public ResponseEntity<UsuarioExibicaoDTO> buscarPorNome(@RequestParam String nome){

        try {
            return ResponseEntity
                    .ok(usuarioService.buscarPorNome(nome));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}