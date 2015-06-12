package br.com.paxtecnologia.pma.relatorio.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.paxtecnologia.pma.relatorio.dao.AtendimentoDAO;
import br.com.paxtecnologia.pma.relatorio.vo.ChamadoVO;
import br.com.paxtecnologia.pma.relatorio.vo.ChamadoQuantidadeVO;

@Stateless
public class AtendimentoEjb {

	private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();

	private Double porcentagemChamadosAbertos;
	private Double porcentagemChamadosEmAbertos;
	private Double porcentagemChamadosFechados;
	private Double porcentagemChamadosAbertosTipo;
	private Double porcentagemChamadosFechadosTipo;
	private Double porcentagemChamadosAbertosSolicitante;
	private Double porcentagemChamadosFechadosSolicitante;
	private Double porcentagemChamadosAbertosHost;
	private Double porcentagemChamadosFechadosHost;
	private Integer qtdeChamadosAbertos;
	private Integer qtdeChamadosFechados;
	private Integer qtdeChamadosEmAberto;
	private Integer qtdeChamadosFechadosHost;
	private Integer qtdeChamadosAbertosHost;
	private Integer qtdeChamadosAbertosSolicitante;
	private Integer qtdeChamadosFechadosSolicitante;
	private Integer qtdeChamadosAbertosTipo;
	private Integer qtdeChamadosFechadosTipo;
	private Double tempoMedioChamadoFechado;
	private List<ChamadoVO> listaChamadoAberto;
	private List<ChamadoVO> listaChamadoFechado;
	private List<ChamadoVO> listaChamadoEmAberto;
	private List<ChamadoQuantidadeVO> listaChamadoSolicitante;
	private List<ChamadoQuantidadeVO> listaChamadoTipo;
	private List<String> listaAbertosHost;
	private List<String> listaFechadosHost;
	private List<ChamadoQuantidadeVO> listaChamadosHost;
	private Map<String, Integer> controleIdCliente = new HashMap<String, Integer>();
	private Map<String, String> controleMesCliente = new HashMap<String, String>();
	private List<String> listaGraficoAbertosHost;
	private List<String> listaGraficoFechadosHost;
	
	// Conta Porcentagem
	private Double getPorcentagem(Integer valor, Integer total) {
		Double porcentagem = valor.doubleValue() / total.doubleValue();
		return porcentagem;
	}

	// Quantidade de Chamados Abertos - OK
	public Integer getQtdeChamadosAbertos(Integer idCliente, String mesRelatorio) {
		if (qtdeChamadosAbertos == null
				|| controleIdCliente.get("getQtdeChamadosAbertos") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosAbertos") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoAberto == null
					|| controleIdCliente.get("getListaChamadosAbertos") != idCliente
					|| (controleIdCliente.get("getListaChamadosAbertos") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadosAbertos(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosAbertos", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			qtdeChamadosAbertos = listaChamadoAberto.size();
		}
		return qtdeChamadosAbertos;
	}

	// Quantidade de Chamdos Fechados - OK
	public Integer getQtdeChamadosFechados(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosFechados == null
				|| controleIdCliente.get("getQtdeChamadosFechados") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosFechados") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoFechado == null
					|| controleIdCliente.get("getListaChamadosFechados") != idCliente
				    || (controleIdCliente.get("getListaChamadosFechados") == idCliente
				       && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadosFechados(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosFechados", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			qtdeChamadosFechados = listaChamadoFechado.size();
		}
		
	
		return qtdeChamadosFechados;
	}

	// Quantidade de Chamados em Aberto - OK
	public Integer getQtdeChamadosEmAberto(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosEmAberto == null
				|| controleIdCliente.get("getQtdeChamadosEmAberto") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosEmAberto") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoEmAberto == null
					|| controleIdCliente.get("getListaChamadosEmAbertos") != idCliente
			    	|| (controleIdCliente.get("getListaChamadosEmAbertos") == idCliente
					       && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadosEmAbertos(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosEmAberto", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			qtdeChamadosEmAberto = listaChamadoEmAberto.size();
		}
		return qtdeChamadosEmAberto;
	}

	// Quantidade de Chamados Aberto Solicitante - OK
	public Integer getQtdeChamadosAbertosSolicitante(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosAbertosSolicitante == null
				|| controleIdCliente.get("getQtdeChamadosAbertosSolicitante") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosAbertosSolicitante") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaSolicitantes") != idCliente
					|| (controleIdCliente.get("getListaSolicitantes") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaSolicitantes(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosAbertosSolicitante",idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosAbertosSolicitante;
	}

	// Quantidade de Chamados Fechado Solicitante - OK
	public Integer getQtdeChamadosFechadosSolicitante(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosFechadosSolicitante == null
				|| controleIdCliente.get("getQtdeChamadosFechadosSolicitante") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosFechadosSolicitante") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaSolicitantes") != idCliente
	    			|| (controleIdCliente.get("getListaSolicitantes") == idCliente
					       && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaSolicitantes(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosFechadosSolicitante",idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosFechadosSolicitante;
	}

	public Integer getQtdeChamadosAbertosTipo(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosAbertosTipo == null
				|| controleIdCliente.get("getQtdeChamadosAbertosTipo") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosAbertosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoTipo == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosAbertosTipo", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosAbertosTipo;
	}

	public Integer getQtdeChamadosFechadosTipo(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosFechadosTipo == null
				|| controleIdCliente.get("getQtdeChamadosFechadosTipo") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosFechadosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoTipo == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosFechadosTipo", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosFechadosTipo;
	}

	public Integer getQtdeChamadosAbertosHost(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosAbertosHost == null
				|| controleIdCliente.get("getQtdeChamadosAbertosHost") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosAbertosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadosHost == null
					|| controleIdCliente.get("getListaChamadoHost") != idCliente
					|| (controleIdCliente.get("getListaChamadoHost") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoHost(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosAbertosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosAbertosHost;
	}

	public Integer getQtdeChamadosFechadosHost(Integer idCliente,
			String mesRelatorio) {
		if (qtdeChamadosFechadosHost == null
				|| controleIdCliente.get("getQtdeChamadosFechadosHost") != idCliente
				|| (controleIdCliente.get("getQtdeChamadosFechadosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadosHost == null
					|| controleIdCliente.get("getListaChamadoHost") != idCliente
					|| (controleIdCliente.get("getListaChamadoHost") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoHost(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getQtdeChamadosFechadosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return qtdeChamadosFechadosHost;
	}

	// Porcentagem em Aberto
	public Double getPorcentagemChamadosAbertos(Integer idCliente,
			String mesRelatorio) {
		porcentagemChamadosAbertos = 1.00;
		return porcentagemChamadosAbertos;
	}

	public Double getPorcentagemChamadosEmAbertos(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosEmAbertos == null
				|| controleIdCliente.get("getPorcentagemChamadosAbertos") != idCliente
				|| (controleIdCliente.get("getPorcentagemChamadosAbertos") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (qtdeChamadosAbertos == null
					|| controleIdCliente.get("getQtdeChamadosAbertos") != idCliente) {
				getQtdeChamadosAbertos(idCliente, mesRelatorio);
			}
			if (qtdeChamadosEmAberto == null
					|| controleIdCliente.get("getQtdeChamadosEmAberto") != idCliente) {
				getQtdeChamadosEmAberto(idCliente, mesRelatorio);
			}
			porcentagemChamadosEmAbertos = getPorcentagem(qtdeChamadosEmAberto,
					qtdeChamadosAbertos);
		}
		return porcentagemChamadosEmAbertos;
	}

	public Double getPorcentagemChamadosFechados(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosFechados == null
				|| controleIdCliente.get("getPorcentagemChamadosAbertos") != idCliente
				|| (controleIdCliente.get("getPorcentagemChamadosAbertos") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (qtdeChamadosAbertos == null
					|| controleIdCliente.get("getQtdeChamadosAbertos") != idCliente) {
				getQtdeChamadosAbertos(idCliente, mesRelatorio);
			}
			if (qtdeChamadosFechados == null
					|| controleIdCliente.get("getQtdeChamadosFechados") != idCliente) {
				getQtdeChamadosFechados(idCliente, mesRelatorio);
			}
			porcentagemChamadosFechados = getPorcentagem(qtdeChamadosFechados,
					qtdeChamadosAbertos);
		}
		return porcentagemChamadosFechados;
	}

	public Double getPorcentoAbertosSolicitante(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosAbertosSolicitante == null
				|| controleIdCliente.get("getPorcentoAbertosSolicitante") != idCliente
				|| (controleIdCliente.get("getPorcentoAbertosSolicitante") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaSolicitantes") != idCliente
					|| (controleIdCliente.get("getListaSolicitantes") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaSolicitantes(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoAbertosSolicitante", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosAbertosSolicitante;
	}

	public Double getPorcentoFechadosSolicitante(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosFechadosSolicitante == null
				|| controleIdCliente.get("getPorcentoFechadosSolicitante") != idCliente
				|| (controleIdCliente.get("getPorcentoFechadosSolicitante") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaSolicitantes") != idCliente
					|| (controleIdCliente.get("getListaSolicitantes") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaSolicitantes(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoFechadosSolicitante", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosFechadosSolicitante;
	}

	public Double getPorcentoAbertosTipo(Integer idCliente, String mesRelatorio) {
		if (porcentagemChamadosAbertosTipo == null
				|| controleIdCliente.get("getPorcentoAbertosTipo") != idCliente
				|| (controleIdCliente.get("getPorcentoAbertosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoAbertosTipo", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosAbertosTipo;
	}

	public Double getPorcentoFechadosTipo(Integer idCliente, String mesRelatorio) {
		if (porcentagemChamadosFechadosTipo == null
				|| controleIdCliente.get("getPorcentoFechadosTipo") != idCliente
				|| (controleIdCliente.get("getPorcentoFechadosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoFechadosTipo", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosFechadosTipo;
	}

	public Double getPorcentoFechadosComHost(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosFechadosHost == null
				|| controleIdCliente.get("getPorcentoFechadosComHost") != idCliente
				|| (controleIdCliente.get("getPorcentoFechadosComHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaChamadoHost") != idCliente
					|| (controleIdCliente.get("getListaChamadoHost") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoHost(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoFechadosComHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosFechadosHost;
	}

	public Double getPorcentoAbertosComHost(Integer idCliente,
			String mesRelatorio) {
		if (porcentagemChamadosAbertosHost == null
				|| controleIdCliente.get("getPorcentoAbertosComHost") != idCliente
				|| (controleIdCliente.get("getPorcentoAbertosComHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoSolicitante == null
					|| controleIdCliente.get("getListaChamadoHost") != idCliente
					|| (controleIdCliente.get("getListaChamadoHost") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoHost(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getPorcentoAbertosComHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return porcentagemChamadosAbertosHost;
	}

	public Double getTempoMedio(Integer idCliente, String mesRelatorio) {
		if (tempoMedioChamadoFechado == null
				|| controleIdCliente.get("getTempoMedio") != idCliente
				|| (controleIdCliente.get("getTempoMedio") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoFechado == null
					|| controleIdCliente.get("getListaChamadosFechados") != idCliente) {
				getListaChamadosFechados(idCliente, mesRelatorio);
			}
			if (qtdeChamadosFechados == null
					|| controleIdCliente.get("getListaChamadosFechados") != idCliente) {
				getQtdeChamadosFechados(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getTempoMedio", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			tempoMedioChamadoFechado=0.0;
			Iterator<ChamadoVO> itChamado;
			ChamadoVO chamado;

			itChamado = listaChamadoFechado.iterator();

			while (itChamado.hasNext()) {
				chamado = itChamado.next();
				tempoMedioChamadoFechado = tempoMedioChamadoFechado
						+ chamado.getSegundosTrabalhos();
			}
			// Converte para horas
			tempoMedioChamadoFechado = tempoMedioChamadoFechado
					/ qtdeChamadosFechados / 60 / 60;
		}

		return tempoMedioChamadoFechado;

	}

	// ////////////////////////////////

	// Lista de Chamados Abertos
	public List<ChamadoVO> getListaChamadosAbertos(Integer idCliente,
			String mesRelatorio) {
		if (listaChamadoAberto == null
				|| controleIdCliente.get("getListaChamadosAbertos") != idCliente
				|| (controleIdCliente.get("getListaChamadosAbertos") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getListaChamadosAbertos", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaChamadoAberto = atendimentoDAO.getChamadosAbertos(idCliente, mesRelatorio);
		}
		return listaChamadoAberto;
	}

	// Lista de Chamados Fechados
	public List<ChamadoVO> getListaChamadosFechados(Integer idCliente,
			String mesRelatorio) {
		if (listaChamadoFechado == null
				|| controleIdCliente.get("getListaChamadosFechados") != idCliente
				|| (controleIdCliente.get("getListaChamadosFechados") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getListaChamadosFechados", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaChamadoFechado = atendimentoDAO.getChamadosFechados(idCliente,	mesRelatorio);
		}
		return listaChamadoFechado;
	}

	// Lista de Chamados em Aberto
	public List<ChamadoVO> getListaChamadosEmAbertos(Integer idCliente,
			String mesRelatorio) {
		if (listaChamadoEmAberto == null
				|| controleIdCliente.get("getListaChamadosEmAbertos") != idCliente
				|| (controleIdCliente.get("getListaChamadosEmAbertos") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getListaChamadosEmAbertos", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaChamadoEmAberto = atendimentoDAO.getChamadosEmAbertos(idCliente, mesRelatorio);
		}
		return listaChamadoEmAberto;
	}

	public List<ChamadoQuantidadeVO> getListaSolicitantes(Integer idCliente,
			String mesRelatorio) {
		if (listaChamadoSolicitante == null
				|| controleIdCliente.get("getListaSolicitantes") != idCliente
				|| (controleIdCliente.get("getListaSolicitantes") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoAberto == null
					|| controleIdCliente.get("getListaChamadosAbertos") != idCliente) {
				getListaChamadosAbertos(idCliente, mesRelatorio);
			}
			if (listaChamadoFechado == null
					|| controleIdCliente.get("getListaChamadosFechados") != idCliente) {
				getListaChamadosFechados(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getListaSolicitantes", idCliente);
			controleIdCliente.put("controleMesCliente", idCliente);

			listaChamadoSolicitante = new ArrayList<ChamadoQuantidadeVO>();
			Iterator<ChamadoVO> itChamado;
			Iterator<ChamadoQuantidadeVO> itSolicitante;
			ChamadoVO chamado;
			Integer achou;

			itChamado = listaChamadoAberto.iterator();

			while (itChamado.hasNext()) {
				chamado = itChamado.next();
				itSolicitante = listaChamadoSolicitante.iterator();
				achou = 0;
				while (itSolicitante.hasNext()) {
					ChamadoQuantidadeVO solicitante = itSolicitante.next();
					if ((solicitante.getNome() != null && solicitante.getNome().equals(chamado.getSolicitante()))
					   ||(solicitante.getNome() == null && chamado.getSolicitante() == null)) {
						solicitante.setQtdeAberto(solicitante.getQtdeAberto() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO solicitante = new ChamadoQuantidadeVO();
					solicitante.setNome(chamado.getSolicitante());
					solicitante.setQtdeAberto(1);
					solicitante.setQtdeFechado(0);
					solicitante.setQtdeEmAberto(0);
					listaChamadoSolicitante.add(solicitante);
				}
			}

			itChamado = listaChamadoFechado.iterator();
			while (itChamado.hasNext()) {
				chamado = itChamado.next();
				itSolicitante = listaChamadoSolicitante.iterator();
				achou = 0;
				while (itSolicitante.hasNext()) {
					ChamadoQuantidadeVO solicitante = itSolicitante.next();
					if ((solicitante.getNome() != null && solicitante.getNome().equals(chamado.getSolicitante()))
					   ||(solicitante.getNome() == null && chamado.getSolicitante() == null)) {
						solicitante.setQtdeFechado(solicitante.getQtdeFechado() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO solicitante = new ChamadoQuantidadeVO();
					solicitante.setNome(chamado.getSolicitante());
					solicitante.setQtdeAberto(0);
					solicitante.setQtdeFechado(1);
					solicitante.setQtdeEmAberto(0);
					listaChamadoSolicitante.add(solicitante);
				}
			}
			Integer aberto = 0;
			Integer fechado = 0;
			itSolicitante = listaChamadoSolicitante.iterator();
			while (itSolicitante.hasNext()) {
				ChamadoQuantidadeVO solicitante = itSolicitante.next();
				aberto = aberto + solicitante.getQtdeAberto();
				fechado = fechado + solicitante.getQtdeFechado();
			}

			setQtdeChamadosAbertosSolicitante(aberto);
			setQtdeChamadosFechadosSolicitante(fechado);

			Double porcentagemAberto = 0.0;
			Double porcentagemFechado = 0.0;
			itSolicitante = listaChamadoSolicitante.iterator();
			while (itSolicitante.hasNext()) {
				ChamadoQuantidadeVO solicitante = itSolicitante.next();
				solicitante.setPorcentoAberto(getPorcentagem(
						solicitante.getQtdeAberto(), aberto));
				solicitante.setPorcentoFechado(getPorcentagem(
						solicitante.getQtdeFechado(), fechado));
				porcentagemAberto = porcentagemAberto
						+ solicitante.getPorcentoAberto();
				porcentagemFechado = porcentagemFechado
						+ solicitante.getPorcentoFechado();
			}

			setPorcentagemChamadosAbertosSolicitante(porcentagemAberto);
			setPorcentagemChamadosFechadosSolicitante(porcentagemFechado);
		}
		
		List<ChamadoQuantidadeVO> orderArrayList = listaChamadoSolicitante;
		
		Collections.sort(orderArrayList, new Comparator<ChamadoQuantidadeVO>() {
			@Override
			public int compare(ChamadoQuantidadeVO o1, ChamadoQuantidadeVO o2) {
 				return o2.getQtdeAberto().compareTo(o1.getQtdeAberto());
 			}
		});
		
		return listaChamadoSolicitante;
	}

	public List<ChamadoQuantidadeVO> getListaChamadoTipo(Integer idCliente,
			String mesRelatorio) {

		if (listaChamadoTipo == null
				|| controleIdCliente.get("getListaChamadoTipo") != idCliente
				|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaChamadoAberto == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente) {
				atendimentoDAO.getChamadosAbertos(idCliente, mesRelatorio);
			}
			if (listaChamadoFechado == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente) {
				atendimentoDAO.getChamadosFechados(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getListaChamadoTipo", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaChamadoTipo = new ArrayList<ChamadoQuantidadeVO>();
			Iterator<ChamadoVO> itChamado;
			Iterator<ChamadoQuantidadeVO> itTipoChamado;
			ChamadoVO chamado;
			Integer achou;

			itChamado = listaChamadoAberto.iterator();

			while (itChamado.hasNext()) {
				chamado = itChamado.next();
				itTipoChamado = listaChamadoTipo.iterator();
				achou = 0;
				while (itTipoChamado.hasNext()) {
					ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
					if (tipoChamados.getNome().equals(chamado.getTipoChamado())) {
						tipoChamados.setQtdeAberto(tipoChamados.getQtdeAberto() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO tipoChamados = new ChamadoQuantidadeVO();
					tipoChamados.setNome(chamado.getTipoChamado());
					tipoChamados.setQtdeAberto(1);
					tipoChamados.setQtdeFechado(0);
					tipoChamados.setQtdeEmAberto(0);
					listaChamadoTipo.add(tipoChamados);
				}
			}

			itChamado = listaChamadoFechado.iterator();
			while (itChamado.hasNext()) {
				chamado = itChamado.next();
				itTipoChamado = listaChamadoTipo.iterator();
				achou = 0;
				while (itTipoChamado.hasNext()) {
					ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
					if (tipoChamados.getNome().equals(chamado.getTipoChamado())) {
						tipoChamados.setQtdeFechado(tipoChamados.getQtdeFechado() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO tipoChamados = new ChamadoQuantidadeVO();
					tipoChamados.setNome(chamado.getTipoChamado());
					tipoChamados.setQtdeAberto(0);
					tipoChamados.setQtdeFechado(1);
					tipoChamados.setQtdeEmAberto(0);
					listaChamadoTipo.add(tipoChamados);
				}

			}
			Integer aberto = 0;
			Integer fechado = 0;
			itTipoChamado = listaChamadoTipo.iterator();
			while (itTipoChamado.hasNext()) {
				ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
				aberto = aberto + tipoChamados.getQtdeAberto();
				fechado = fechado + tipoChamados.getQtdeFechado();
			}

			setQtdeChamadosAbertosTipo(aberto);
			setQtdeChamadosFechadosTipo(fechado);

			Double porcentagemAberto = 0.0;
			Double porcentagemFechado = 0.0;
			itTipoChamado = listaChamadoTipo.iterator();
			while (itTipoChamado.hasNext()) {
				ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
				tipoChamados.setPorcentoAberto(getPorcentagem(
						tipoChamados.getQtdeAberto(), aberto));
				tipoChamados.setPorcentoFechado(getPorcentagem(
						tipoChamados.getQtdeFechado(), fechado));
				porcentagemAberto = porcentagemAberto
						+ tipoChamados.getPorcentoAberto();
				porcentagemFechado = porcentagemFechado
						+ tipoChamados.getPorcentoFechado();
			}
			setPorcentagemChamadosAbertosTipo(porcentagemAberto);
			setPorcentagemChamadosFechadosTipo(porcentagemFechado);
		}
		return listaChamadoTipo;
	}

	public List<String> getListaChamadoAbertosHost(Integer idCliente,
			String mesRelatorio) {
		if (listaAbertosHost == null
				|| controleIdCliente.get("getListaChamadoAbertosHost") != idCliente
				|| (controleIdCliente.get("getListaChamadoAbertosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getListaChamadoAbertosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaAbertosHost = atendimentoDAO.getListaAbertosComHosts(
					idCliente, mesRelatorio);
		}
		return listaAbertosHost;
	}

	public List<String> getListaChamadoFechadosHost(Integer idCliente,
			String mesRelatorio) {
		if (listaFechadosHost == null
				|| controleIdCliente.get("getListaChamadoFechadosHost") != idCliente
				|| (controleIdCliente.get("getListaChamadoAbertosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getListaChamadoFechadosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaFechadosHost = atendimentoDAO.getListaFechadosComHosts(
					idCliente, mesRelatorio);
		}
		return listaAbertosHost;
	}

	public List<ChamadoQuantidadeVO> getListaChamadoHost(Integer idCliente,
			String mesRelatorio) {
		if (listaChamadosHost == null
				|| controleIdCliente.get("getListaChamadoHost") != idCliente
				|| (controleIdCliente.get("getListaChamadoHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			if (listaAbertosHost == null
					|| controleIdCliente.get("getListaChamadoAbertosHost") != idCliente) {
				getListaChamadoAbertosHost(idCliente, mesRelatorio);
			}
			if (listaFechadosHost == null
					|| controleIdCliente.get("getListaChamadoFechadosHost") != idCliente) {
				getListaChamadoFechadosHost(idCliente, mesRelatorio);
			}
			controleIdCliente.put("getListaChamadoHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			listaChamadosHost = new ArrayList<ChamadoQuantidadeVO>();
			Iterator<String> itHost;
			Iterator<ChamadoQuantidadeVO> itHostList;
			String host;
			Integer achou;

			itHost = listaAbertosHost.iterator();

			while (itHost.hasNext()) {
				host = itHost.next();
				itHostList = listaChamadosHost.iterator();
				achou = 0;
				while (itHostList.hasNext()) {
					ChamadoQuantidadeVO hostVO = itHostList.next();
					if (hostVO.getNome().equals(host)) {
						hostVO.setQtdeAberto(hostVO.getQtdeAberto() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO hostVO = new ChamadoQuantidadeVO();
					hostVO.setNome(host);
					hostVO.setQtdeAberto(1);
					hostVO.setQtdeFechado(0);
					hostVO.setQtdeEmAberto(0);
					listaChamadosHost.add(hostVO);
				}
			}

			itHost = listaFechadosHost.iterator();
			while (itHost.hasNext()) {
				host = itHost.next();
				itHostList = listaChamadosHost.iterator();
				achou = 0;
				while (itHostList.hasNext()) {
					ChamadoQuantidadeVO hostVO = itHostList.next();
					if (hostVO.getNome().equals(host)) {
						hostVO.setQtdeFechado(hostVO.getQtdeFechado() + 1);
						achou = 1;
					}
				}
				if (achou == 0) {
					ChamadoQuantidadeVO hostVO = new ChamadoQuantidadeVO();
					hostVO.setNome(host);
					hostVO.setQtdeAberto(0);
					hostVO.setQtdeFechado(1);
					hostVO.setQtdeEmAberto(0);
					listaChamadosHost.add(hostVO);
				}

			}
			Integer aberto = 0;
			Integer fechado = 0;
			List<String> hostGraficoAberto = new ArrayList<String>();
			List<String> hostGraficoFechado = new ArrayList<String>();
			itHostList = listaChamadosHost.iterator();
			while (itHostList.hasNext()) {
				ChamadoQuantidadeVO hostVO = itHostList.next();
				aberto = aberto + hostVO.getQtdeAberto();
				fechado = fechado + hostVO.getQtdeFechado();
				hostGraficoAberto.add("{label: \"" + hostVO.getNome()
						+ "\",  data: " + hostVO.getQtdeAberto() + "}");
				hostGraficoFechado.add("{label: \"" + hostVO.getNome()
						+ "\",  data: " + hostVO.getQtdeFechado() + "}");
			}
			setListaGraficoAbertosHost(hostGraficoAberto);
			setListaGraficoFechadosHost(hostGraficoFechado);
			
			setQtdeChamadosAbertosHost(aberto);
			setQtdeChamadosFechadosHost(fechado);

			Double porcentagemAberto = 0.0;
			Double porcentagemFechado = 0.0;
			itHostList = listaChamadosHost.iterator();
			while (itHostList.hasNext()) {
				ChamadoQuantidadeVO hostVO = itHostList.next();
				hostVO.setPorcentoAberto(getPorcentagem(hostVO.getQtdeAberto(),
						aberto));
				hostVO.setPorcentoFechado(getPorcentagem(
						hostVO.getQtdeFechado(), fechado));
				porcentagemAberto = porcentagemAberto
						+ hostVO.getPorcentoAberto();
				porcentagemFechado = porcentagemFechado
						+ hostVO.getPorcentoFechado();
			}
			setPorcentagemChamadosAbertosHost(porcentagemAberto);
			setPorcentagemChamadosFechadosHost(porcentagemFechado);
		}
		
		List<ChamadoQuantidadeVO> orderArrayList = listaChamadosHost;
		
		Collections.sort(orderArrayList, new Comparator<ChamadoQuantidadeVO>() {
			@Override
			public int compare(ChamadoQuantidadeVO o1, ChamadoQuantidadeVO o2) {
 				return o2.getQtdeAberto().compareTo(o1.getQtdeAberto());
 			}
		});	
		
		return listaChamadosHost;
	}

	public String getGraficoFechadosHost(Integer idCliente, String mesRelatorio) {
		String saida = new String();
		if (listaGraficoFechadosHost == null
				|| controleIdCliente.get("getGraficoFechadosHost") != idCliente
				|| (controleIdCliente.get("getGraficoFechadosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			getListaChamadoHost(idCliente, mesRelatorio);
			controleIdCliente.put("getGraficoFechadosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}	
		Iterator<String> itHost = listaGraficoFechadosHost.iterator();
		saida = "[";
		while (itHost.hasNext()) {
			String host = itHost.next();
			saida = saida + host + ",";
		}
		saida = saida + "]";
		return saida;
	}

	public String getGraficoAbertosHost(Integer idCliente, String mesRelatorio) {
		String saida = new String();
		if (listaGraficoAbertosHost == null
				|| controleIdCliente.get("getGraficoAbertosHost") != idCliente
				|| (controleIdCliente.get("getGraficoAbertosHost") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			getListaChamadoHost(idCliente, mesRelatorio);
			controleIdCliente.put("getGraficoAbertosHost", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}	
		Iterator<String> itHost = listaGraficoAbertosHost.iterator();
		saida = "[";
		while (itHost.hasNext()) {
			String host = itHost.next();
			saida = saida + host + ",";
		}
		saida = saida + "]";
		return saida;
	}

	public Double getPorcentagemChamadosAbertosTipo(Integer idCliente,
			String mesRelatorio) {
		Double porcentagem = 0.0;
		Iterator<ChamadoQuantidadeVO> itTipoChamado;

		if (porcentagemChamadosAbertosTipo == null
				|| controleIdCliente.get("getPorcentagemChamadosAbertosTipo") != idCliente
				|| (controleIdCliente.get("getPorcentagemChamadosAbertosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getPorcentagemChamadosAbertosTipo",idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			if (listaChamadoTipo == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			itTipoChamado = listaChamadoTipo.iterator();

			while (itTipoChamado.hasNext()) {
				ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
				porcentagem = porcentagem + tipoChamados.getPorcentoAberto();
			}
		}
		return porcentagem;
	}

	public Double getPorcentagemChamadosFechadosTipo(Integer idCliente,
			String mesRelatorio) {
		Double porcentagem = 0.0;
		Iterator<ChamadoQuantidadeVO> itTipoChamado;

		if (porcentagemChamadosFechadosTipo == null
				|| controleIdCliente.get("getPorcentagemChamadosFechadosTipo") != idCliente
				|| (controleIdCliente.get("getPorcentagemChamadosFechadosTipo") == idCliente
				   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
			controleIdCliente.put("getPorcentagemChamadosFechadosTipo",idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
			if (listaChamadoTipo == null
					|| controleIdCliente.get("getListaChamadoTipo") != idCliente
					|| (controleIdCliente.get("getListaChamadoTipo") == idCliente
					   && controleMesCliente.get("mesCliente") != mesRelatorio)) {
				getListaChamadoTipo(idCliente, mesRelatorio);
			}
			itTipoChamado = listaChamadoTipo.iterator();

			while (itTipoChamado.hasNext()) {
				ChamadoQuantidadeVO tipoChamados = itTipoChamado.next();
				porcentagem = porcentagem + tipoChamados.getPorcentoFechado();
			}
		}
		return porcentagem;
	}

	private void setQtdeChamadosAbertosSolicitante(
			Integer qtdeChamadosAbertosSolicitante) {
		this.qtdeChamadosAbertosSolicitante = qtdeChamadosAbertosSolicitante;
	}

	private void setQtdeChamadosFechadosSolicitante(
			Integer qtdeChamadosFechadosSolicitante) {
		this.qtdeChamadosFechadosSolicitante = qtdeChamadosFechadosSolicitante;
	}

	private void setPorcentagemChamadosAbertosSolicitante(
			Double porcentagemChamadosAbertosSolicitante) {
		this.porcentagemChamadosAbertosSolicitante = porcentagemChamadosAbertosSolicitante;
	}

	private void setPorcentagemChamadosFechadosSolicitante(
			Double porcentagemChamadosFechadosSolicitante) {
		this.porcentagemChamadosFechadosSolicitante = porcentagemChamadosFechadosSolicitante;
	}

	private void setPorcentagemChamadosAbertosTipo(
			Double porcentagemChamadosAbertosTipo) {
		this.porcentagemChamadosAbertosTipo = porcentagemChamadosAbertosTipo;
	}

	private void setPorcentagemChamadosFechadosTipo(
			Double porcentagemChamadosFechadosTipo) {
		this.porcentagemChamadosFechadosTipo = porcentagemChamadosFechadosTipo;
	}

	private void setQtdeChamadosAbertosTipo(Integer qtdeChamadosAbertosTipo) {
		this.qtdeChamadosAbertosTipo = qtdeChamadosAbertosTipo;
	}

	private void setQtdeChamadosFechadosTipo(Integer qtdeChamadosFechadosTipo) {
		this.qtdeChamadosFechadosTipo = qtdeChamadosFechadosTipo;
	}

	private void setPorcentagemChamadosAbertosHost(
			Double porcentagemChamadosAbertosHost) {
		this.porcentagemChamadosAbertosHost = porcentagemChamadosAbertosHost;
	}

	private void setPorcentagemChamadosFechadosHost(
			Double porcentagemChamadosFechadosHost) {
		this.porcentagemChamadosFechadosHost = porcentagemChamadosFechadosHost;
	}

	private void setQtdeChamadosFechadosHost(Integer qtdeChamadosFechadosHost) {
		this.qtdeChamadosFechadosHost = qtdeChamadosFechadosHost;
	}

	private void setQtdeChamadosAbertosHost(Integer qtdeChamadosAbertosHost) {
		this.qtdeChamadosAbertosHost = qtdeChamadosAbertosHost;
	}

	private void setListaGraficoAbertosHost(List<String> listaGraficoAbertosHost) {
		this.listaGraficoAbertosHost = listaGraficoAbertosHost;
	}

	private void setListaGraficoFechadosHost(
			List<String> listaGraficoFechadosHost) {
		this.listaGraficoFechadosHost = listaGraficoFechadosHost;
	}
}
