package br.com.paxtecnologia.pma.relatorio.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.paxtecnologia.pma.relatorio.dao.ParadasDAO;
import br.com.paxtecnologia.pma.relatorio.util.FormataDataUtil;
import br.com.paxtecnologia.pma.relatorio.util.FormataValorUtil;
import br.com.paxtecnologia.pma.relatorio.vo.ParadasPorTipoVO;
import br.com.paxtecnologia.pma.relatorio.vo.UltimoAnoVO;

@Stateless
public class ParadasEjb {

	private ParadasDAO paradasDao = new ParadasDAO();
	private List<UltimoAnoVO> listaUltimosAnosHoras;
	private List<ParadasPorTipoVO> listaParadasEvitadas;
	private List<ParadasPorTipoVO> listaParadasEvitadasMes;
	private List<ParadasPorTipoVO> listaParadasNaoProgramadas;
	private List<ParadasPorTipoVO> listaParadasNaoProgramadasMes;
	private List<ParadasPorTipoVO> listaParadasProgramadasEstrategicas;
	private List<ParadasPorTipoVO> listaParadasProgramadasEstrategicasMes;
	private List<ParadasPorTipoVO> listaParadasProgramadas;
	private List<ParadasPorTipoVO> listaParadasProgramadasMes;
	private Integer qtdeParadaProgramadas;
	private Integer qtdeParadaProgramadasMes;
	private Integer qtdeProgramadasEstrategicas;
	private Integer qtdeProgramadasEstrategicasMes;
	private Integer qtdeParadaNaoProgramadas;
	private Integer qtdeParadaNaoProgramadasMes;
	private Integer qtdeParadaEvitadas;
	private Integer qtdeParadaEvitadasMes;
	private Integer diasTrabalhados;

	private static String PARADAS_EVITADAS = "PE";
	private static String PARADAS_NAO_PROGRAMADAS = "PNP";
	private static String PARADAS_PROGRAMADAS_ESTRATEGICAS = "PPE";
	private static String PARADAS_PROGRAMADAS = "PP";

	Map<String, Integer> controleIdCliente = new HashMap<String, Integer>();
	private Map<String, String> controleMesCliente = new HashMap<String, String>();

	public Integer getDiasTrabalhados(Integer idCliente, String mesRelatorio) {
		if (diasTrabalhados == null
				|| controleIdCliente.get("getDiasTrabalhados") != idCliente
				|| (controleIdCliente.get("getDiasTrabalhados") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			Calendar gc = paradasDao.getDataUltimoPNP(idCliente, mesRelatorio);

			Calendar calendar = Calendar.getInstance();
			calendar.set(
					FormataDataUtil.getCampoMesRelatorio("ano", mesRelatorio),
					FormataDataUtil.getCampoMesRelatorio("mes", mesRelatorio)-1,
					FormataDataUtil.getCampoMesRelatorio("dia", mesRelatorio),
					0, 0, 0);
			int lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, lastDate);

			DateTime start = new DateTime(gc.getTime());
			DateTime end = new DateTime(calendar);

			int days = Days.daysBetween(start, end).getDays();
			diasTrabalhados = days;

			controleIdCliente.put("getDiasTrabalhados", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return diasTrabalhados;
	}

	public Integer getQtdeParadaEvitadasTotal(Integer idCliente,
			String mesRelatorio) {
		return paradasDao.getQtdeParadaEvitadasTotal(idCliente, mesRelatorio);
	}

	public List<UltimoAnoVO> getListaUltimosAnosHoras(Integer idCliente,
			String tipo, String mesRelatorio) {
		if (listaUltimosAnosHoras == null
				|| controleIdCliente.get("getListaUltimosAnosHoras") != idCliente
				|| (controleIdCliente.get("getListaUltimosAnosHoras") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			listaUltimosAnosHoras = paradasDao.getListaUltimosAnosHoras(
					idCliente, tipo, mesRelatorio);
			controleIdCliente.put("getListaUltimosAnosHoras", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaUltimosAnosHoras;
	}

	public Integer getQtdeParadaProgramadas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaProgramadas == null
				|| controleIdCliente.get("getQtdeParadaProgramadas") != idCliente
				|| (controleIdCliente.get("getQtdeParadaProgramadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadas == null
					|| controleIdCliente.get("getQtdeParadaProgramadas") != idCliente
					|| (controleIdCliente.get("getQtdeParadaProgramadas") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasProgramadas(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaProgramadas", idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaProgramadas = listaParadasProgramadas.size();
		}
		return qtdeParadaProgramadas;
	}

	public Integer getQtdeParadaProgramadasMes(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaProgramadasMes == null
				|| controleIdCliente.get("getQtdeParadaProgramadasMes") != idCliente
				|| (controleIdCliente.get("getQtdeParadaProgramadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadasMes == null
					|| controleIdCliente.get("getQtdeParadaProgramadasMes") != idCliente
					|| (controleIdCliente.get("getQtdeParadaProgramadasMes") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasProgramadasMes(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaProgramadasMes", idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaProgramadasMes = listaParadasProgramadasMes.size();
		}
		return qtdeParadaProgramadasMes;
	}

	public Integer getQtdeProgramadasEstrategicas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeProgramadasEstrategicas == null
				|| controleIdCliente.get("getQtdeProgramadasEstrategicas") != idCliente
				|| (controleIdCliente.get("getQtdeProgramadasEstrategicas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadas == null
					|| controleIdCliente.get("getQtdeProgramadasEstrategicas") != idCliente
					|| (controleIdCliente.get("getQtdeProgramadasEstrategicas") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasProgramadasEstrategicas(idCliente, mesRelatorio,
						tipo);
				controleIdCliente.put("getQtdeProgramadasEstrategicas",
						idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeProgramadasEstrategicas = listaParadasProgramadasEstrategicas
					.size();
		}
		return qtdeProgramadasEstrategicas;
	}

	public Integer getQtdeProgramadasEstrategicasMes(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeProgramadasEstrategicasMes == null
				|| controleIdCliente.get("getQtdeProgramadasEstrategicasMes") != idCliente
				|| (controleIdCliente.get("getQtdeProgramadasEstrategicasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadasMes == null
					|| controleIdCliente
							.get("getQtdeProgramadasEstrategicasMes") != idCliente
					|| (controleIdCliente
							.get("getQtdeProgramadasEstrategicasMes") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasProgramadasEstrategicasMes(idCliente,
						mesRelatorio, tipo);
				controleIdCliente.put("getQtdeProgramadasEstrategicasMes",
						idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeProgramadasEstrategicasMes = listaParadasProgramadasEstrategicasMes
					.size();
		}
		return qtdeProgramadasEstrategicasMes;
	}

	public Integer getQtdeParadaNaoProgramadas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaNaoProgramadas == null
				|| controleIdCliente.get("getQtdeParadaNaoProgramadas") != idCliente
				|| (controleIdCliente.get("getQtdeParadaNaoProgramadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadas == null
					|| controleIdCliente.get("getQtdeParadaNaoProgramadas") != idCliente
					|| (controleIdCliente.get("getQtdeParadaNaoProgramadas") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasNaoProgramadas(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaNaoProgramadas", idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaNaoProgramadas = listaParadasNaoProgramadas.size();
		}
		return qtdeParadaNaoProgramadas;
	}

	public Integer getQtdeParadaNaoProgramadasMes(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaNaoProgramadasMes == null
				|| controleIdCliente.get("getQtdeParadaNaoProgramadasMes") != idCliente
				|| (controleIdCliente.get("getQtdeParadaNaoProgramadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasProgramadasMes == null
					|| controleIdCliente.get("getQtdeParadaNaoProgramadasMes") != idCliente
					|| (controleIdCliente.get("getQtdeParadaNaoProgramadasMes") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasNaoProgramadasMes(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaNaoProgramadasMes",
						idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaNaoProgramadasMes = listaParadasNaoProgramadasMes.size();
		}
		return qtdeParadaNaoProgramadasMes;
	}

	public Integer getQtdeParadaEvitadas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaEvitadas == null
				|| controleIdCliente.get("getQtdeParadaEvitadas") != idCliente
				|| (controleIdCliente.get("getQtdeParadaEvitadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasEvitadas == null
					|| controleIdCliente.get("getQtdeParadaEvitadas") != idCliente
					|| (controleIdCliente.get("getQtdeParadaEvitadas") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasEvitadas(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaEvitadas", idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaEvitadas = listaParadasEvitadas.size();
		}
		return qtdeParadaEvitadas;
	}

	public Integer getQtdeParadaEvitadasMes(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (qtdeParadaEvitadasMes == null
				|| controleIdCliente.get("getQtdeParadaEvitadasMes") != idCliente
				|| (controleIdCliente.get("getQtdeParadaEvitadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			if (listaParadasEvitadasMes == null
					|| controleIdCliente.get("getQtdeParadaEvitadasMes") != idCliente
					|| (controleIdCliente.get("getQtdeParadaEvitadasMes") == idCliente && controleMesCliente
							.get("mesCliente") != mesRelatorio)) {
				getListaParadasEvitadasMes(idCliente, mesRelatorio, tipo);
				controleIdCliente.put("getQtdeParadaEvitadasMes", idCliente);
				controleMesCliente.put("controleMesCliente", mesRelatorio);
			}
			qtdeParadaEvitadasMes = listaParadasEvitadasMes.size();
		}
		return qtdeParadaEvitadasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasEvitadas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (listaParadasEvitadas == null
				|| controleIdCliente.get("getListaParadasEvitadas") != idCliente
				|| (controleIdCliente.get("getListaParadasEvitadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			listaParadasEvitadas = paradasDao.getListaParadasPorTipo(idCliente,
					mesRelatorio, tipo);
			controleIdCliente.put("getListaParadasEvitadas", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasEvitadas;
	}

	public List<ParadasPorTipoVO> getListaParadasEvitadasMes(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (listaParadasEvitadasMes == null
				|| listaParadasEvitadasMes.size() == 0
				|| controleIdCliente.get("getListaParadasEvitadasMes") != idCliente
				|| (controleIdCliente.get("getListaParadasEvitadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {

			DateTime data = new DateTime(FormataDataUtil.getCampoMesRelatorio(
					"ano", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"mes", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"dia", mesRelatorio), 0, 0, 0);

			listaParadasEvitadasMes = new ArrayList<ParadasPorTipoVO>();
			for (ParadasPorTipoVO paradasPorTipoVO : getListaParadasEvitadas(
					idCliente, mesRelatorio, tipo)) {
				// Verifica se a data pertence ao mes do parametro mesRelatorio
				if (Integer.parseInt(paradasPorTipoVO.getDataParada()
						.substring(3, 5)) == (data.monthOfYear().get())
						&& Integer.parseInt(paradasPorTipoVO.getDataParada()
								.substring(6, 10)) == (data.year().get())) {
					listaParadasEvitadasMes.add(paradasPorTipoVO);
				}
			}
			controleIdCliente.put("getListaParadasEvitadasMes", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasEvitadasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasNaoProgramadas(
			Integer idCliente, String mesRelatorio, String tipo) {
		if (listaParadasNaoProgramadas == null
				|| controleIdCliente.get("getListaParadasNaoProgramadas") != idCliente
				|| (controleIdCliente.get("getListaParadasNaoProgramadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			listaParadasNaoProgramadas = paradasDao.getListaParadasPorTipo(
					idCliente, mesRelatorio, tipo);
			controleIdCliente.put("getListaParadasNaoProgramadas", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasNaoProgramadas;

	}

	public List<ParadasPorTipoVO> getListaParadasNaoProgramadasMes(
			Integer idCliente, String mesRelatorio, String tipo) {
		if (listaParadasNaoProgramadasMes == null
				|| listaParadasNaoProgramadasMes.size() == 0
				|| controleIdCliente.get("getListaParadasNaoProgramadasMes") != idCliente
				|| (controleIdCliente.get("getListaParadasNaoProgramadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {

			DateTime data = new DateTime(FormataDataUtil.getCampoMesRelatorio(
					"ano", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"mes", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"dia", mesRelatorio), 0, 0, 0);

			listaParadasNaoProgramadasMes = new ArrayList<ParadasPorTipoVO>();
			for (ParadasPorTipoVO paradasPorTipoVO : getListaParadasNaoProgramadas(
					idCliente, mesRelatorio, tipo)) {
				// Verifica se a data pertence ao mes do parametro mesRelatorio
				if (Integer.parseInt(paradasPorTipoVO.getDataParada()
						.substring(3, 5)) == (data.monthOfYear().get())
						&& Integer.parseInt(paradasPorTipoVO.getDataParada()
								.substring(6, 10)) == (data.year().get())) {
					listaParadasNaoProgramadasMes.add(paradasPorTipoVO);
				}
			}
			controleIdCliente
					.put("getListaParadasNaoProgramadasMes", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasNaoProgramadasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasProgramadasEstrategicas(
			Integer idCliente, String mesRelatorio, String tipo) {
		if (listaParadasProgramadasEstrategicas == null
				|| controleIdCliente
						.get("getListaParadasProgramadasEstrategicas") != idCliente
				|| (controleIdCliente
						.get("getListaParadasProgramadasEstrategicas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			listaParadasProgramadasEstrategicas = paradasDao
					.getListaParadasPorTipo(idCliente, mesRelatorio, tipo);
			controleIdCliente.put("getListaParadasProgramadasEstrategicas",
					idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasProgramadasEstrategicas;

	}

	public List<ParadasPorTipoVO> getListaParadasProgramadasEstrategicasMes(
			Integer idCliente, String mesRelatorio, String tipo) {
		if (listaParadasProgramadasEstrategicasMes == null
				|| listaParadasProgramadasEstrategicasMes.size() == 0
				|| controleIdCliente
						.get("getListaParadasProgramadasEstrategicasMes") != idCliente
				|| (controleIdCliente
						.get("getListaParadasProgramadasEstrategicasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {

			DateTime data = new DateTime(FormataDataUtil.getCampoMesRelatorio(
					"ano", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"mes", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"dia", mesRelatorio), 0, 0, 0);

			listaParadasProgramadasEstrategicasMes = new ArrayList<ParadasPorTipoVO>();
			for (ParadasPorTipoVO paradasPorTipoVO : getListaParadasProgramadasEstrategicas(
					idCliente, mesRelatorio, tipo)) {
				// Verifica se a data pertence ao mes do parametro mesRelatorio
				if (Integer.parseInt(paradasPorTipoVO.getDataParada()
						.substring(3, 5)) == (data.monthOfYear().get())
						&& Integer.parseInt(paradasPorTipoVO.getDataParada()
								.substring(6, 10)) == (data.year().get())) {
					listaParadasProgramadasEstrategicasMes
							.add(paradasPorTipoVO);
				}
			}
			controleIdCliente.put("getListaParadasProgramadasEstrategicasMes",
					idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasProgramadasEstrategicasMes;
	}

	public List<ParadasPorTipoVO> getListaParadasProgramadas(Integer idCliente,
			String mesRelatorio, String tipo) {
		if (listaParadasProgramadas == null
				|| controleIdCliente.get("getListaParadasProgramadas") != idCliente
				|| (controleIdCliente.get("getListaParadasProgramadas") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {
			listaParadasProgramadas = paradasDao.getListaParadasPorTipo(
					idCliente, mesRelatorio, tipo);
			controleIdCliente.put("getListaParadasProgramadas", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasProgramadas;
	}

	public List<ParadasPorTipoVO> getListaParadasProgramadasMes(
			Integer idCliente, String mesRelatorio, String tipo) {
		if (listaParadasProgramadasMes == null
				|| listaParadasProgramadasMes.size() == 0
				|| controleIdCliente.get("getListaParadasProgramadasMes") != idCliente
				|| (controleIdCliente.get("getListaParadasProgramadasMes") == idCliente && controleMesCliente
						.get("mesCliente") != mesRelatorio)) {

			DateTime data = new DateTime(FormataDataUtil.getCampoMesRelatorio(
					"ano", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"mes", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
					"dia", mesRelatorio), 0, 0, 0);

			listaParadasProgramadasMes = new ArrayList<ParadasPorTipoVO>();
			for (ParadasPorTipoVO paradasPorTipoVO : getListaParadasProgramadas(
					idCliente, mesRelatorio, tipo)) {
				// Verifica se a data pertence ao mes do parametro mesRelatorio
				if (Integer.parseInt(paradasPorTipoVO.getDataParada()
						.substring(3, 5)) == (data.monthOfYear().get())
						&& Integer.parseInt(paradasPorTipoVO.getDataParada()
								.substring(6, 10)) == (data.year().get())) {
					listaParadasProgramadasMes.add(paradasPorTipoVO);
				}
			}
			controleIdCliente.put("getListaParadasProgramadasMes", idCliente);
			controleMesCliente.put("controleMesCliente", mesRelatorio);
		}
		return listaParadasProgramadasMes;
	}

	public String getParadas(String tipo, String mesRelatorio) {
		List<ParadasPorTipoVO> listaParadas = null;

		if (tipo.equals(PARADAS_EVITADAS)) {
			listaParadas = listaParadasEvitadas;
		} else if (tipo.equals(PARADAS_NAO_PROGRAMADAS)) {
			listaParadas = listaParadasNaoProgramadas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS_ESTRATEGICAS)) {
			listaParadas = listaParadasProgramadasEstrategicas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS)) {
			listaParadas = listaParadasProgramadas;
		}

		String saida = "[";
		double[][] meses = new double[2][12];
		for (ParadasPorTipoVO paradasPorTipoVO : listaParadas) {
			for (Integer j = 0; j <= 1; j++) {
				Double somaHoras = 0.0;
				for (Integer i = 0; i < 12; i++) {
					somaHoras = 0.0;
					// Verifica se a data pertence ao mesmo mes
					if (Integer.parseInt(paradasPorTipoVO.getDataParada()
							.substring(3, 5)) == (i + 1)
							&& (Integer.parseInt(paradasPorTipoVO
									.getDataParada().substring(6, 10))) == (Integer
									.parseInt(mesRelatorio.substring(0, 4)) - j)) {
						somaHoras = somaHoras + paradasPorTipoVO.getHoras();
					}
					meses[j][i] = meses[j][i] + somaHoras;
				}
			}
		}
		for (Integer j = 1; j >= 0; j--) {
			Integer mesInicial = 0;
			Integer mesFinal = 0;
			if (j == 1) {

				mesInicial = FormataDataUtil.getCampoMesRelatorio("mes",
						mesRelatorio);
				mesFinal = 12;
			} else {
				mesInicial = 0;
				mesFinal = FormataDataUtil.getCampoMesRelatorio("mes",
						mesRelatorio);
			}
			for (Integer i = mesInicial; i < mesFinal; i++) {
				// if (i <= Integer.parseInt(mesRelatorio.substring(6,7))) {
				if (i <= mesFinal) {
					if (tipo.equals(PARADAS_NAO_PROGRAMADAS)
							|| tipo.equals(PARADAS_PROGRAMADAS)) {
						saida = saida
								+ "[(new Date("
								+ (Integer.parseInt(mesRelatorio
										.substring(0, 4)) - j) + "," + i
								+ ").getTime())," + (-meses[j][i]) + "],";
					} else {
						saida = saida
								+ "[(new Date("
								+ (Integer.parseInt(mesRelatorio
										.substring(0, 4)) - j) + "," + i
								+ ").getTime())," + meses[j][i] + "],";
					}
				} else {
					saida = saida
							+ "[(new Date("
							+ (Integer.parseInt(mesRelatorio.substring(0, 4)) - j)
							+ "," + i + ").getTime())," + 0.0 + "],";
				}
			}
		}

		saida = saida.substring(0, saida.length() - 1);
		saida = saida + "]";
		return saida;
	}

	public String getParadasAcumulado(String tipo, String mesRelatorio) {
		List<ParadasPorTipoVO> listaParadas = null;

		if (tipo.equals(PARADAS_EVITADAS)) {
			listaParadas = listaParadasEvitadas;
		} else if (tipo.equals(PARADAS_NAO_PROGRAMADAS)) {
			listaParadas = listaParadasNaoProgramadas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS_ESTRATEGICAS)) {
			listaParadas = listaParadasProgramadasEstrategicas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS)) {
			listaParadas = listaParadasProgramadas;
		}

		String saida = "[";
		double[] meses = new double[12];
		for (ParadasPorTipoVO paradasPorTipoVO : listaParadas) {
			for (Integer i = 0; i < 12; i++) {
				Double somaHoras = 0.0;
				// Verifica se a data pertence ao mes do parametro mesRelatorio

				if ((Integer.parseInt(paradasPorTipoVO.getDataParada()
						.substring(3, 5)) - 1) == i
						&& Integer.parseInt(paradasPorTipoVO.getDataParada()
								.substring(6, 10)) == (Integer
								.parseInt(mesRelatorio.substring(0, 4)))) {
					somaHoras = somaHoras + paradasPorTipoVO.getHoras();
				}
				meses[i] = meses[i] + somaHoras;
			}
		}
		double somaHorasAcumulado = 0.0;
		for (Integer i = 0; i < meses.length; i++) {
			somaHorasAcumulado = somaHorasAcumulado + meses[i];
			if (i < FormataDataUtil.getCampoMesRelatorio("mes", mesRelatorio)) {
				if (tipo.equals(PARADAS_NAO_PROGRAMADAS)
						|| tipo.equals(PARADAS_PROGRAMADAS)) {
					saida = saida + "[(new Date("
							+ Integer.parseInt(mesRelatorio.substring(0, 4))
							+ "," + i + ").getTime())," + (-somaHorasAcumulado)
							+ "],";
				} else {
					saida = saida + "[(new Date("
							+ Integer.parseInt(mesRelatorio.substring(0, 4))
							+ "," + i + ").getTime())," + somaHorasAcumulado
							+ "],";
				}
			} else {
				saida = saida + "[(new Date("
						+ Integer.parseInt(mesRelatorio.substring(0, 4)) + ","
						+ i + ").getTime())," + 0.0 + "],";
			}
		}

		saida = saida.substring(0, saida.length() - 1);
		saida = saida + "]";
		return saida;
	}

	public Double getTempoParadasMes(String tipo, String mesRelatorio) {
		List<ParadasPorTipoVO> listaParadas = null;

		if (tipo.equals(PARADAS_EVITADAS)) {
			listaParadas = listaParadasEvitadas;
		} else if (tipo.equals(PARADAS_NAO_PROGRAMADAS)) {
			listaParadas = listaParadasNaoProgramadas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS_ESTRATEGICAS)) {
			listaParadas = listaParadasProgramadasEstrategicas;
		} else if (tipo.equals(PARADAS_PROGRAMADAS)) {
			listaParadas = listaParadasProgramadas;
		}

		DateTime data = new DateTime(FormataDataUtil.getCampoMesRelatorio(
				"ano", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
				"mes", mesRelatorio), FormataDataUtil.getCampoMesRelatorio(
				"dia", mesRelatorio), 0, 0, 0);

		Double somaHorasMes = 0.0;
		for (ParadasPorTipoVO paradasPorTipoVO : listaParadas) {
			// Verifica se a data pertence ao mes do parametro mesRelatorio
			if (Integer.parseInt(paradasPorTipoVO.getDataParada().substring(3,
					5)) == (data.monthOfYear().get())
					&& Integer.parseInt(paradasPorTipoVO.getDataParada()
							.substring(6, 10)) == (data.year().get())) {
				somaHorasMes = somaHorasMes + paradasPorTipoVO.getHoras();
			}
		}

		return FormataValorUtil.converterDoubleDoisDecimais(somaHorasMes);
	}
}
