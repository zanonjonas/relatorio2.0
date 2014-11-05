package br.com.paxtecnologia.pma.relatorio.ejb;

import javax.ejb.Stateless;

import br.com.paxtecnologia.pma.relatorio.dao.UsuarioDAO;
import br.com.paxtecnologia.pma.relatorio.vo.UsuarioVO;

@Stateless
public class UsuarioEjb {
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private UsuarioVO usuarioVO;

	public UsuarioVO getUsuario(String username){
		usuarioVO = usuarioDAO.getUsuario(username);

		return usuarioVO;

	}	
	
}