package br.com.paxtecnologia.pma.relatorio;

import java.util.ArrayList;
import java.util.List;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.paxtecnologia.pma.relatorio.ejb.AtendimentoEjb;
import br.com.paxtecnologia.pma.relatorio.util.FormataValorUtil;
import br.com.paxtecnologia.pma.relatorio.vo.ChamadoVO;
import br.com.paxtecnologia.pma.relatorio.vo.IndicacoresQtdVO;
import br.com.paxtecnologia.pma.relatorio.vo.ChamadoQuantidadeVO;

@ViewScoped
@ManagedBean(name = "atendimentoBean")
public class AtendimentoBean {

	@EJB
	private AtendimentoEjb atendimentoEjb;

	@ManagedProperty(value = "#{clientesBean.idCliente}")
	private Integer idCliente;

	@ManagedProperty(value = "#{clientesBean.mesRelatorio}")
	private String mesRelatorio;

	private List<ChamadoQuantidadeVO> listaSolicitante;
	private List<ChamadoQuantidadeVO> listaTipoChamado;
	private List<ChamadoVO> listaChamadoFechado;
	private List<ChamadoVO> listaChamadoEmAberto;
	private Integer qtdeChamadosAbertos;
	private Integer qtdeChamadosEmAbertos;
	private Integer qtdeChamadosFechados;
	private Integer qtdeChamadosAbertosSolicitante;
	private Integer qtdeChamadosFechadosSolicitante;
	private Integer qtdeChamadosAbertosTipo;
	private Integer qtdeChamadosFechadosTipo;
	private Double porcentoAbertosSolicitante;
	private Double porcentoFechadosSolicitante;
	private Double porcentoAbertos;
	private String porcentoEmAbertos;
	private String porcentoFechados;
	private Double porcentagemAbertoTipo;
	private Double porcentagemFechadoTipo;
	private String tempoMedio;

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	// 2.1

	public Integer getQtdeChamadosAbertos() {
		if (qtdeChamadosAbertos == null) {
			qtdeChamadosAbertos = atendimentoEjb.getQtdeChamadosAbertos(idCliente, mesRelatorio);
		}
		return qtdeChamadosAbertos;
	}

	public Integer getQtdeChamadosFechados() {
		if (qtdeChamadosFechados == null) {
			qtdeChamadosFechados = atendimentoEjb.getQtdeChamadosFechados(idCliente, mesRelatorio);
		}
		return qtdeChamadosFechados;
	}

	public String getPorcentoFechados() {
		if (porcentoFechados == null) {
			Double porcento = atendimentoEjb.getPorcentagemChamadosFechados(idCliente, mesRelatorio) * 100;
			porcentoFechados = FormataValorUtil.converterDoubleString(porcento) + '%';
		}
		return porcentoFechados;
	}

	private Object getQtdeChamadosEmAberto() {
		if (qtdeChamadosEmAbertos == null) {
			qtdeChamadosEmAbertos = atendimentoEjb.getQtdeChamadosEmAberto(idCliente, mesRelatorio);
		}
		return qtdeChamadosEmAbertos;
	}

	public String getPorcentoEmAbertos() {
		if (porcentoEmAbertos == null) {
			Double porcento = atendimentoEjb.getPorcentagemChamadosEmAbertos(idCliente, mesRelatorio) * 100;
			porcentoEmAbertos = FormataValorUtil.converterDoubleString(porcento) + '%';
		}
		return porcentoEmAbertos;
	}
	
	public String getTempoMedio(){
		if (tempoMedio == null){
			Double tempo = atendimentoEjb.getTempoMedio(idCliente, mesRelatorio);
			tempoMedio = FormataValorUtil.converterDoubleString(tempo) + " h";
		}
		return tempoMedio;
	}

	public List<IndicacoresQtdVO> getListaGeral() {
		List<IndicacoresQtdVO> listaGeral = new ArrayList<IndicacoresQtdVO>();

		IndicacoresQtdVO a = new IndicacoresQtdVO();
		a.setTexto("Número de Chamados Abertos");
		a.setValor(getQtdeChamadosAbertos());
		listaGeral.add(a);

		IndicacoresQtdVO b = new IndicacoresQtdVO();
		b.setTexto("Número de Chamados Solucionados");
		b.setValor(getQtdeChamadosFechados());
		listaGeral.add(b);

		IndicacoresQtdVO c = new IndicacoresQtdVO();
		c.setTexto("% de Chamados Solucionados");
		c.setValor(getPorcentoFechados());
		listaGeral.add(c);

		IndicacoresQtdVO d = new IndicacoresQtdVO();
		d.setTexto("Tempo Médio para solucionar (em Horas)");
		d.setValor(getTempoMedio());
		listaGeral.add(d);

		IndicacoresQtdVO e = new IndicacoresQtdVO();
		e.setTexto("Número de Chamados em Aberto");
		e.setValor(getQtdeChamadosEmAberto());
		listaGeral.add(e);

		IndicacoresQtdVO f = new IndicacoresQtdVO();
		f.setTexto("% de Chamados em Aberto");
		f.setValor(getPorcentoEmAbertos());
		listaGeral.add(f);

		return listaGeral;
	}

	// 2.2

	public List<ChamadoQuantidadeVO> getListaSolicitante() {
		if (listaSolicitante == null) {
			listaSolicitante = atendimentoEjb.getListaSolicitantes(idCliente,mesRelatorio);
		}
		return listaSolicitante;
	}

	public Integer getQtdeChamadosAbertosSolicitante() {
		if (qtdeChamadosAbertosSolicitante == null) {
			qtdeChamadosAbertosSolicitante = atendimentoEjb.getQtdeChamadosAbertosSolicitante(idCliente, mesRelatorio);
		}
		return qtdeChamadosAbertosSolicitante;
	}

	public Integer getQtdeChamadosFechadosSolicitante() {
		if (qtdeChamadosFechadosSolicitante == null) {
			qtdeChamadosFechadosSolicitante = atendimentoEjb.getQtdeChamadosFechadosSolicitante(idCliente, mesRelatorio);
		}
		return qtdeChamadosFechadosSolicitante;
	}

	public Double getPorcentoAbertosSolicitante() {
		if (porcentoAbertosSolicitante == null) {
			porcentoAbertosSolicitante = atendimentoEjb.getPorcentoAbertosSolicitante(idCliente, mesRelatorio);
		}
		return porcentoAbertosSolicitante;
	}

	public Double getPorcentoFechadosSolicitante() {
		if (porcentoFechadosSolicitante == null) {
			porcentoFechadosSolicitante = atendimentoEjb.getPorcentoFechadosSolicitante(idCliente, mesRelatorio);
		}
		return porcentoFechadosSolicitante;
	}

	// 2.3

	public List<ChamadoQuantidadeVO> getListaTipoChamado() {
		if (listaTipoChamado == null) {
			listaTipoChamado = atendimentoEjb.getListaChamadoTipo(idCliente, mesRelatorio);
		}
		return listaTipoChamado;
	}

	public List<ChamadoVO> getListaChamadosEmAbertos() {
		if (listaChamadoEmAberto == null) {
			listaChamadoEmAberto = atendimentoEjb.getListaChamadosEmAbertos(idCliente, mesRelatorio);
		}
		return listaChamadoEmAberto;
	}

	public List<ChamadoVO> getListaChamadosFechados() {
		if (listaChamadoFechado == null) {
			listaChamadoFechado = atendimentoEjb.getListaChamadosFechados(idCliente, mesRelatorio);
		}
		return listaChamadoFechado;
	}

	public Integer getQtdeChamadosAbertosTipo() {
		if (qtdeChamadosAbertosTipo == null) {
			qtdeChamadosAbertosTipo = atendimentoEjb.getQtdeChamadosAbertosTipo(idCliente, mesRelatorio);
		}
		return qtdeChamadosAbertosTipo;
	}

	public Integer getQtdeChamadosFechadosTipo() {
		if (qtdeChamadosFechadosTipo == null) {
			qtdeChamadosFechadosTipo = atendimentoEjb.getQtdeChamadosFechadosTipo(idCliente, mesRelatorio);
		}
		return qtdeChamadosFechadosTipo;
	}

	public Double getPorcentagemAbertoTipo() {
		if (porcentagemAbertoTipo == null) {
			porcentagemAbertoTipo = atendimentoEjb.getPorcentagemChamadosAbertosTipo(idCliente, mesRelatorio);
		}
		return porcentagemAbertoTipo;
	}

	public Double getPorcentagemFechadoTipo() {
		if (porcentagemFechadoTipo == null) {
			porcentagemFechadoTipo = atendimentoEjb.getPorcentagemChamadosFechadosTipo(idCliente, mesRelatorio);
		}
		return porcentagemFechadoTipo;
	}

	//

	public Integer getQtdeChamadosEmAbertos() {
		if(qtdeChamadosEmAbertos == null){
			qtdeChamadosEmAbertos = atendimentoEjb.getQtdeChamadosEmAberto(idCliente, mesRelatorio);
		}
		return qtdeChamadosEmAbertos;
	}

	public Double getPorcentoAbertos() {
		if (porcentoAbertos == null) {
			porcentoAbertos = atendimentoEjb.getPorcentagemChamadosAbertos(idCliente, mesRelatorio);
		}
		return porcentoAbertos;
	}

}
