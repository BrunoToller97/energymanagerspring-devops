package br.com.fiap.energymanagerspring.controller;

import br.com.fiap.energymanagerspring.config.security.TokenService;
import br.com.fiap.energymanagerspring.dto.LoginDTO;
import br.com.fiap.energymanagerspring.dto.TokenDTO;
import br.com.fiap.energymanagerspring.dto.UsuarioCadastroDTO;
import br.com.fiap.energymanagerspring.dto.UsuarioExibicaoDTO;
import br.com.fiap.energymanagerspring.model.Usuario;
import br.com.fiap.energymanagerspring.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody
            @Valid
            LoginDTO usuarioDTO
    ){

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(usuarioDTO.email(), usuarioDTO.senha());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity registrar(

            @RequestBody
            @Valid
            UsuarioCadastroDTO usuarioCadastroDTO
    ){

        UsuarioExibicaoDTO usuarioSalvo =
                usuarioService.salvarUsuario(usuarioCadastroDTO);

        return ResponseEntity.ok(usuarioSalvo);

    }

}
