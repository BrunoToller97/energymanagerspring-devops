package br.com.fiap.energymanagerspring.service;

import br.com.fiap.energymanagerspring.dto.FuncionarioCadastroDTO;
import br.com.fiap.energymanagerspring.dto.FuncionarioExibicaoDTO;
import br.com.fiap.energymanagerspring.dto.SetorExibicaoDTO;
import br.com.fiap.energymanagerspring.exception.FuncionarioNaoEncontradoException;
import br.com.fiap.energymanagerspring.model.Funcionario;
import br.com.fiap.energymanagerspring.model.Setor;
import br.com.fiap.energymanagerspring.repository.FuncionarioRepository;
import br.com.fiap.energymanagerspring.repository.SetorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioExibicaoDTO salvarFuncionario(FuncionarioCadastroDTO dto){

        Funcionario funcionario = new Funcionario();
        BeanUtils.copyProperties(dto, funcionario);

        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));

        funcionario.setSetor(setor);

        Funcionario salvo = funcionarioRepository.save(funcionario);

        return new FuncionarioExibicaoDTO(salvo);
    }

    public FuncionarioExibicaoDTO buscarPorID(Long id){
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()){
            return new FuncionarioExibicaoDTO(funcionarioOptional.get());
        } else {
            throw new FuncionarioNaoEncontradoException("Funcionário não existe no banco de dados!");
        }
    }

    public Page<FuncionarioExibicaoDTO> listarTodos(Pageable paginacao){
        return funcionarioRepository
                .findAll(paginacao)
                .map(FuncionarioExibicaoDTO::new);
    }

    public void excluir(Long id){
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()){
            funcionarioRepository.delete(funcionarioOptional.get());
        } else {
            throw new  RuntimeException("Funcionário não encontrado!");
        }
    }

    public Funcionario atualizar(Funcionario funcionario){
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(funcionario.getId());

        if (funcionarioOptional.isPresent()){
            return funcionarioRepository.save(funcionario);
        } else {
            throw new  RuntimeException("Funcionário não encontrado!");
        }

    }

    public FuncionarioExibicaoDTO buscarPorNome(String nome){
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.buscarPorNome(nome);

        if (funcionarioOptional.isPresent()){
            return new FuncionarioExibicaoDTO(funcionarioOptional.get());
        } else {
            throw new RuntimeException("Funcionario procurado não existe!");
        }
    }

}
