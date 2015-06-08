package br.com.paxtecnologia.pma.relatorio.ejb;

import java.util.List;

import javax.ejb.Stateless;



import br.com.paxtecnologia.pma.relatorio.dao.RelatorioDAO;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;
import br.com.paxtecnologia.pma.relatorio.vo2.RelatorioVO;
@Stateless
public class RelatorioEjb {

	private RelatorioDAO relatorioDAO = new RelatorioDAO();
	private List<RelatorioVO> listaRelatorios;
	private List<MesRelatorioVO> listaMesRelatorio;
	
	public List<RelatorioVO> getListaRelatorioMenu(){
		if (listaRelatorios == null) {
			listaRelatorios = relatorioDAO.getListaRelatorioMenu();
		}			
		
		return listaRelatorios;
	}
	
	public List<MesRelatorioVO> getListaMes(Integer projetoJiraId) {

		listaMesRelatorio = relatorioDAO.getListaMes(projetoJiraId);
		
		return listaMesRelatorio;

	}
	
}
