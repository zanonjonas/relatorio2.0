package br.com.paxtecnologia.pma.relatorio;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.paxtecnologia.pma.relatorio.ejb.AtendimentoEjb;
import br.com.paxtecnologia.pma.relatorio.vo.ChamadoQuantidadeVO;

@ViewScoped
@ManagedBean(name = "atendimentoHostBean")
public class AtendimentoHostBean {

	@EJB
	private AtendimentoEjb atendimentoEjb;

	@ManagedProperty(value = "#{relatorioBean.projetoJiraId}")
	private Integer projetoJiraId;

	@ManagedProperty(value = "#{relatorioBean.mesRelatorio}")
	private String mesRelatorio;

	private List<ChamadoQuantidadeVO> listaHost;
	private Integer qtdeChamadosAbertosComHost;
	private Double porcentoAbertosComHost;
	private Double porcentoFechadosComHost;
	private Integer qtdeChamadosFechadosComHost;
	private String graficoAbertos;
	private String graficoFechados;

	public void setIdCliente(Integer idCliente) {
		this.projetoJiraId = idCliente;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}

	public Integer getQtdeChamadosAbertosComHost() {
		if (qtdeChamadosAbertosComHost == null) {
			qtdeChamadosAbertosComHost = atendimentoEjb.getQtdeChamadosAbertosHost(projetoJiraId, mesRelatorio);
		}
		return qtdeChamadosAbertosComHost;
	}

	public Double getPorcentoAbertosComHost() {
		if (porcentoAbertosComHost == null) {
			porcentoAbertosComHost = atendimentoEjb.getPorcentoAbertosComHost(projetoJiraId, mesRelatorio);
		}
		return porcentoAbertosComHost;
	}

	public Double getPorcentoFechadosComHost() {
		if (porcentoFechadosComHost == null) {
			porcentoFechadosComHost = atendimentoEjb.getPorcentoFechadosComHost(projetoJiraId, mesRelatorio);
		}
		return porcentoFechadosComHost;
	}

	public Integer getQtdeChamadosFechadosComHost() {
		if (qtdeChamadosFechadosComHost == null) {
			qtdeChamadosFechadosComHost = atendimentoEjb.getQtdeChamadosFechadosHost(projetoJiraId, mesRelatorio);
		}
		return qtdeChamadosFechadosComHost;
	}

	public List<ChamadoQuantidadeVO> getListaHost() {
		if (listaHost == null) {
			listaHost = atendimentoEjb.getListaChamadoHost(projetoJiraId, mesRelatorio);
		}
		return listaHost;
	}

	public String getGraficoAbertos() {
		if (graficoAbertos == null) {
			graficoAbertos = atendimentoEjb.getGraficoAbertosHost(projetoJiraId, mesRelatorio);
		}
		return graficoAbertos;
	}

	public String getGraficoFechados() {
		if (graficoFechados == null) {
			graficoFechados = atendimentoEjb.getGraficoFechadosHost(projetoJiraId, mesRelatorio);
		}
		return graficoFechados;
	}

	public Integer getProjetoJiraId() {
		return projetoJiraId;
	}

	public void setProjetoJiraId(Integer projetoJiraId) {
		this.projetoJiraId = projetoJiraId;
	}

	
	
}
