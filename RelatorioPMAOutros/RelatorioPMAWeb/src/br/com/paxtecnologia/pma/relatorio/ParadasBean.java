package br.com.paxtecnologia.pma.relatorio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.paxtecnologia.pma.relatorio.ejb.ParadasEjb;
import br.com.paxtecnologia.pma.relatorio.vo.ParadasPorTipoVO;
import br.com.paxtecnologia.pma.relatorio.vo.ParadasVO;
import br.com.paxtecnologia.pma.relatorio.vo.UltimoAnoVO;

@ViewScoped
@ManagedBean(name = "paradasBean")
public class ParadasBean {

	@EJB
	private ParadasEjb paradasEjb;

	@ManagedProperty(value = "#{relatorioBean.projetoJiraId}")
	private Integer projetoJiraId;

	@ManagedProperty(value = "#{relatorioBean.mesRelatorio}")
	private String mesRelatorio;

	private List<ParadasVO> listaItem;
	private List<UltimoAnoVO> listaUltimosAnosHoras;
	private List<ParadasPorTipoVO> listaParadasEvitadasMes;
	private List<ParadasPorTipoVO> listaParadasNaoProgramadasMes;
	private List<ParadasPorTipoVO> listaParadasProgramadasEstrategicasMes;
	private List<ParadasPorTipoVO> listaParadasProgramadasMes;
	private Integer diasTrabalhados;
	private Integer qtdeParadasEvitadasTotal;

	private static String PARADAS_EVITADAS = "PE";
	private static String PARADAS_NAO_PROGRAMADAS = "PNP";
	private static String PARADAS_PROGRAMADAS_ESTRATEGICAS = "PPE";
	private static String PARADAS_PROGRAMADAS = "PP";
	
	public Integer getDiasTrabalhados() {
		
		if (diasTrabalhados == null) {
			diasTrabalhados = paradasEjb.getDiasTrabalhados(projetoJiraId, mesRelatorio);
		}
		return diasTrabalhados;
	}

	public Integer getQtdeParadasEvitadasTotal() {
		if (qtdeParadasEvitadasTotal == null) {
			qtdeParadasEvitadasTotal = paradasEjb.getQtdeParadaEvitadasTotal(projetoJiraId, mesRelatorio);
		}
		return qtdeParadasEvitadasTotal;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}



	public List<UltimoAnoVO> getListaUltimosAnosHoras(String tipo) {
		if (listaUltimosAnosHoras == null) {
			listaUltimosAnosHoras = paradasEjb.getListaUltimosAnosHoras(projetoJiraId, tipo, mesRelatorio);
		}
		return listaUltimosAnosHoras;
	}

	public List<ParadasVO> getListaResumo() {
		if (listaItem == null) {

			listaItem = new ArrayList<ParadasVO>();

			ParadasVO a = new ParadasVO();
			a.setTipo("Paradas Evitadas");
			a.setSigla(PARADAS_EVITADAS);
			a.setQtde(paradasEjb.getQtdeParadaEvitadasMes(projetoJiraId, mesRelatorio, PARADAS_EVITADAS));
			listaItem.add(a);

			ParadasVO b = new ParadasVO();
			b.setTipo("Paradas Não Programadas");
			b.setSigla(PARADAS_NAO_PROGRAMADAS);
			b.setQtde(paradasEjb.getQtdeParadaNaoProgramadasMes(projetoJiraId,	mesRelatorio, PARADAS_NAO_PROGRAMADAS));
			listaItem.add(b);

			ParadasVO c = new ParadasVO();
			c.setTipo("Paradas Programadas Estratégicas");
			c.setSigla(PARADAS_PROGRAMADAS_ESTRATEGICAS);
			c.setQtde(paradasEjb.getQtdeProgramadasEstrategicasMes(projetoJiraId, mesRelatorio, PARADAS_PROGRAMADAS_ESTRATEGICAS));
			listaItem.add(c);

			ParadasVO d = new ParadasVO();
			d.setTipo("Paradas Programadas");
			d.setSigla(PARADAS_PROGRAMADAS);
			d.setQtde(paradasEjb.getQtdeParadaProgramadasMes(projetoJiraId, mesRelatorio, PARADAS_PROGRAMADAS));
			listaItem.add(d);
		}
		return listaItem;
	}

	public List<ParadasPorTipoVO> getListaParadasEvitadasMes() {
		if (listaParadasEvitadasMes == null) {
			listaParadasEvitadasMes = paradasEjb.getListaParadasEvitadasMes(projetoJiraId, mesRelatorio, PARADAS_EVITADAS);
		}
		return listaParadasEvitadasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasNaoProgramadasMes() {
		if (listaParadasNaoProgramadasMes == null) {
			listaParadasNaoProgramadasMes = paradasEjb.getListaParadasNaoProgramadasMes(projetoJiraId, mesRelatorio, PARADAS_NAO_PROGRAMADAS);
		}
		return listaParadasNaoProgramadasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasProgramadasEstrategicasMes() {
		if (listaParadasProgramadasEstrategicasMes == null) {
			listaParadasProgramadasEstrategicasMes = paradasEjb.getListaParadasProgramadasEstrategicasMes(projetoJiraId, mesRelatorio, PARADAS_PROGRAMADAS_ESTRATEGICAS);
		}
		return listaParadasProgramadasEstrategicasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasProgramadasMes() {
		if (listaParadasProgramadasMes == null) {
			listaParadasProgramadasMes = paradasEjb.getListaParadasProgramadasMes(projetoJiraId, mesRelatorio, PARADAS_PROGRAMADAS);
		}
		return listaParadasProgramadasMes;
	}
	
	public String getParadas(String tipo) {
		return paradasEjb.getParadas(tipo,mesRelatorio);
	}
	
	public String getParadasAcumulado(String tipo) {
		return paradasEjb.getParadasAcumulado(tipo,mesRelatorio);
	}

	public Double getTempoParadasMes(String tipo) {
		return paradasEjb.getTempoParadasMes(tipo,mesRelatorio);
	}

	public Integer getRelatorioId() {
		return projetoJiraId;
	}

	public void setRelatorioId(Integer relatorioId) {
		this.projetoJiraId = relatorioId;
	}

	public String getMesRelatorio() {
		return mesRelatorio;
	}

	public Integer getProjetoJiraId() {
		return projetoJiraId;
	}

	public void setProjetoJiraId(Integer projetoJiraId) {
		this.projetoJiraId = projetoJiraId;
	}
	
	
}