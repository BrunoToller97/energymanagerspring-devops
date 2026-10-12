package br.com.fiap.energymanagerspring.service;

import br.com.fiap.energymanagerspring.dto.UsuarioCadastroDTO;
import br.com.fiap.energymanagerspring.dto.UsuarioExibicaoDTO;
import br.com.fiap.energymanagerspring.exception.UsuarioNaoEncontradoException;
import br.com.fiap.energymanagerspring.model.Usuario;
import br.com.fiap.energymanagerspring.model.UsuarioRole;
import br.com.fiap.energymanagerspring.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDTO salvarUsuario(UsuarioCadastroDTO dto){


        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.senha());

        Usuario usuario = new Usuario();

        BeanUtils.copyProperties(dto, usuario);

        usuario.setSenha(senhaCriptografada);
        usuario.setRole(UsuarioRole.valueOf(dto.role()));

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioExibicaoDTO(salvo);
    }

    public UsuarioExibicaoDTO buscarPorId(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()){
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não existe no banco de dados!");
        }
    }

    public Page<UsuarioExibicaoDTO> listarTodos(Pageable paginacao){
        return usuarioRepository
                .findAll(paginacao)
                .map(UsuarioExibicaoDTO::new);
    }

    public void excluir(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()){
            usuarioRepository.delete(usuarioOptional.get());
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    public Usuario atualizar(Usuario usuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getUsuarioId());

        if (usuarioOptional.isPresent()){
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    public UsuarioExibicaoDTO buscarPorNome(String nome){
        Optional<Usuario> usuarioOptional = usuarioRepository.buscarPorNome(nome);

        if (usuarioOptional.isPresent()){
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        } else {
            throw new RuntimeException("Usuário procurado não existe!");
        }
    }

}