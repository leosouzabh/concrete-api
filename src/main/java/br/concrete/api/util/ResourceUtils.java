package br.concrete.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.concrete.api.controller.resource.TelefoneResource;
import br.concrete.api.controller.resource.UsuarioResource;
import br.concrete.api.model.Telefone;
import br.concrete.api.model.Usuario;

public class ResourceUtils {

	public static UsuarioResource toUsuarioResource(Usuario usuario) {
		UsuarioResource resource = new UsuarioResource()
				.setId(usuario.getId())
				.setEmail(usuario.getEmail())
				.setName(usuario.getName())
				.setToken(usuario.getToken())
				.setCreated(usuario.getCreated())
				.setModified(usuario.getModified())
				.setUltimoLogin(usuario.getUltimoLogin())
				.setName(usuario.getName())
				.setPhones( toListTelefoneResource(usuario.getPhones()) );
		return resource;
	}

	public static List<TelefoneResource> toListTelefoneResource(Set<Telefone> telefones){
		List<TelefoneResource> retorno = new ArrayList<>();
		if ( telefones != null ){
			for ( Telefone resource : telefones ){
				retorno.add(new TelefoneResource().setDdd(resource.getDdd()).setNumber(resource.getNumber()) );
			}
		}
		return retorno;
	}


}
