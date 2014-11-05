package br.com.paxtecnologia.pma.relatorio.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.paxtecnologia.pma.relatorio.dao.ClienteDAO;
import br.com.paxtecnologia.pma.relatorio.vo.ClienteVO;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;

@Stateless
public class ClientesEjb {
	private ClienteDAO clienteDAO = new ClienteDAO();
	private List<ClienteVO> listaClientes;
	private List<MesRelatorioVO> listaMesRelatorio;
	private String logoCliente;
	private String nomeCliente;
	private Map<String, Integer> controleIdCliente = new HashMap<String, Integer>();

	public List<ClienteVO> getListaClientes() {
		if (listaClientes == null) {
			listaClientes = clienteDAO.getListaClientes();
		}
		return listaClientes;
	}

	public List<MesRelatorioVO> getListaMes(Integer idCliente) {

		if (listaMesRelatorio == null
				|| controleIdCliente.get("getListaMes") != idCliente) {
			controleIdCliente.put("getListaMes", idCliente);
			listaMesRelatorio = clienteDAO.getListaMes(idCliente);
		}
		return listaMesRelatorio;

	}

	public String getLogoCliente(Integer idCliente) {
		if (logoCliente == null
				|| controleIdCliente.get("getLogoCliente") != idCliente) {
			controleIdCliente.put("getLogoCliente", idCliente);
			logoCliente = clienteDAO.getLogoCliente(idCliente);
		}
		return logoCliente;
	}

	public String getNomeCliente(Integer idCliente) {
		if (nomeCliente == null
				|| controleIdCliente.get("getNomeCliente") != idCliente) {
			controleIdCliente.put("getNomeCliente", idCliente);
			nomeCliente = clienteDAO.getNomeCliente(idCliente);
		}
		return nomeCliente;
	}

}
