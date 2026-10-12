package br.com.fiap.energymanagerspring.service;

import br.com.fiap.energymanagerspring.dto.SetorCadastroDTO;
import br.com.fiap.energymanagerspring.dto.SetorExibicaoDTO;
import br.com.fiap.energymanagerspring.exception.SetorNaoEncontradoException;
import br.com.fiap.energymanagerspring.model.Setor;
import br.com.fiap.energymanagerspring.repository.SetorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    public SetorExibicaoDTO salvarSetor(SetorCadastroDTO dto){

        Setor setor = new Setor();
        BeanUtils.copyProperties(dto, setor);

        Setor salvo = setorRepository.save(setor);

        return new SetorExibicaoDTO(salvo);
    }

    public SetorExibicaoDTO buscarPorId(Long id){
        Optional<Setor> setorOptional = setorRepository.findById(id);

        if (setorOptional.isPresent()){
            return new SetorExibicaoDTO(setorOptional.get());
        } else {
            throw new SetorNaoEncontradoException("Setor não existe no banco de dados!");
        }
    }

    public Page<SetorExibicaoDTO> listarTodos(Pageable paginacao){
        return setorRepository
                .findAll(paginacao)
                .map(SetorExibicaoDTO::new);
    }

    public void excluir(Long id){
        Optional<Setor> setorOptional = setorRepository.findById(id);

        if (setorOptional.isPresent()){
            setorRepository.delete(setorOptional.get());
        } else {
            throw new RuntimeException("Setor não encontrado!");
        }
    }

    public Setor atualizar(Setor setor){
        Optional<Setor> setorOptional = setorRepository.findById(setor.getId());

        if (setorOptional.isPresent()){
            return setorRepository.save(setor);
        } else {
            throw new RuntimeException("Setor não encontrado!");
        }
    }

    public SetorExibicaoDTO buscarPorNome(String nome){
        Optional<Setor> setorOptional = setorRepository.buscarPorNome(nome);

        if (setorOptional.isPresent()){
            return new SetorExibicaoDTO(setorOptional.get());
        } else {
            throw new RuntimeException("Setor procurado não existe!");
        }
    }

}