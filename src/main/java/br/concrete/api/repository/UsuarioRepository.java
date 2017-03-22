package br.concrete.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import br.concrete.api.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, QueryByExampleExecutor<Usuario> {

	Usuario findByEmail(String email);
	Usuario findById(String id);
	
}
