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

			listaRelatorios = relatorioDAO.getListaRelatorioMenu();

		return listaRelatorios;
	}
	
	public List<MesRelatorioVO> getListaMes(Integer relatorioId) {

		listaMesRelatorio = relatorioDAO.getListaMes(relatorioId);
		
		return listaMesRelatorio;

	}
	
	public RelatorioVO getRelatorioById(Integer relatorioId, String mesRelatorio){
		
		return relatorioDAO.getRelatorioById(relatorioId,mesRelatorio);
	}
	
	public String getLogoStr(Integer relatorioId){
		
		return relatorioDAO.getLogoStr(relatorioId);
		
	}
	
	public String getClienteDisplayName(Integer relatorioId){
		
		return relatorioDAO.getClienteDisplayName(relatorioId);
		
	}
	
	public String getTituloRelatorio(Integer relatorioId){
		
		return relatorioDAO.getTituloRelatorio(relatorioId);
		
	}
	
	
	
}
