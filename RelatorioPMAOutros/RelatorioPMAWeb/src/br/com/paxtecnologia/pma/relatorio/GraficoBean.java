package br.com.paxtecnologia.pma.relatorio;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.paxtecnologia.pma.relatorio.ejb.GraficoEjb;
import br.com.paxtecnologia.pma.relatorio.ejb.GraficoLinhaEjb;
import br.com.paxtecnologia.pma.relatorio.ejb.LinhaValorEjb;
import br.com.paxtecnologia.pma.relatorio.util.FormataValorUtil;
import br.com.paxtecnologia.pma.relatorio.vo.DBSizeTabelaVO;


@ViewScoped
@ManagedBean(name = "graficoBean")
public class GraficoBean implements Serializable {
	
	@ManagedProperty(value = "#{relatorioBean.mesRelatorio}")
	private String mesRelatorio;

	@EJB 
	private LinhaValorEjb linhaValorEjb;

	@EJB
	private GraficoLinhaEjb graficoLinhaEjb;
	
	@EJB
	private GraficoEjb graficoEjb;
	
	private Integer diasNoMes;
	
	private static final long serialVersionUID = 1L;
	
	public String getLinhaValorJS(Integer linhaId, Integer graficoId, Integer tipoPeriodoId, Integer tipoConsolidacaoDadoId, Boolean isByte){
		
		String retorno = null;
		
		retorno = linhaValorEjb.getLinhaValorJS(linhaId, graficoId, mesRelatorio, tipoPeriodoId, tipoConsolidacaoDadoId, isByte);
	
		return retorno;
		
	}
	
	public String parseLegenda(Integer graficoId, Integer linhaId, String legenda){
		
		String retorno = null;
		
		retorno = graficoLinhaEjb.parseLegenda(graficoId, linhaId, legenda);
		
		return retorno;
		
	}
	
	public String getMesRelatorio() {
		return mesRelatorio;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}
	
	public Integer getDiasNoMes() {
		
			diasNoMes = graficoEjb.getDiasNoMes(mesRelatorio);
		
			return diasNoMes;
	}
	
	public List<DBSizeTabelaVO> getTabelaDBsize(Integer graficoId) {
		
		return linhaValorEjb.getTabelaDBsize(graficoId,mesRelatorio);
		
	}
		
	
}
