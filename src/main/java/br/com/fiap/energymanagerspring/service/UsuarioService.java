
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

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UsuarioExibicaoDTO salvarUsuario(UsuarioCadastroDTO dto) {

        String senhaCriptografada =
                passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario();

        BeanUtils.copyProperties(dto, usuario);

        usuario.setSenha(senhaCriptografada);
        usuario.setRole(UsuarioRole.valueOf(dto.role()));

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioExibicaoDTO(salvo);
    }

    public UsuarioExibicaoDTO buscarPorId(Long id) {

        Optional<Usuario> usuarioOptional =
                usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        }

        throw new UsuarioNaoEncontradoException(
                "Usuário não existe no banco de dados!"
        );
    }

    public Page<UsuarioExibicaoDTO> listarTodos(Pageable paginacao) {

        return usuarioRepository
                .findAll(paginacao)
                .map(UsuarioExibicaoDTO::new);
    }

    public void excluir(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado!"
                ));

        usuarioRepository.delete(usuario);
    }

    public UsuarioExibicaoDTO atualizar(Usuario usuario) {

        Usuario existente = usuarioRepository
                .findById(usuario.getUsuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado!"
                ));

        if (usuario.getNome() != null) {
            existente.setNome(usuario.getNome());
        }

        if (usuario.getEmail() != null) {
            existente.setEmail(usuario.getEmail());
        }

        if (usuario.getRole() != null) {
            existente.setRole(usuario.getRole());
        }

        if (usuario.getSenha() != null
                && !usuario.getSenha().isBlank()) {
            existente.setSenha(
                    passwordEncoder.encode(usuario.getSenha())
            );
        }

        Usuario atualizado = usuarioRepository.save(existente);

        return new UsuarioExibicaoDTO(atualizado);
    }

    public UsuarioExibicaoDTO buscarPorNome(String nome) {

        Optional<Usuario> usuarioOptional =
                usuarioRepository.buscarPorNome(nome);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        }

        throw new UsuarioNaoEncontradoException(
                "Usuário procurado não existe!"
        );
    }
}
