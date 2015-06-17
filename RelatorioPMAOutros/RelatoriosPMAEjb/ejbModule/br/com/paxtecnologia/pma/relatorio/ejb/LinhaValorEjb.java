package br.com.paxtecnologia.pma.relatorio.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.com.paxtecnologia.pma.relatorio.dao.LinhaValorDAO;
import br.com.paxtecnologia.pma.relatorio.vo.HostVO;
import br.com.paxtecnologia.pma.relatorio.vo.TimeFrameVO;
import br.com.paxtecnologia.pma.relatorio.vo2.LinhaValorVO;

@Stateless
public class LinhaValorEjb {

	private LinhaValorDAO linhaValorDAO = new LinhaValorDAO();
	private Map<String, Integer> controleIdCliente = new HashMap<String, Integer>();
	private Map<String, String> controleMesCliente = new HashMap<String, String>();
	private List<HostVO> hosts;
	private Integer diasNoMes;
	private Long diferenca;
	
	public String getLinhaValorJS(Integer linhaId, Integer graficoId, String mesRelatorio, Integer tipoPeriodoId, Integer tipoConsolidacaoDadoId) {
		List<LinhaValorVO> listaLinhaValorVO = null;
		List<Integer> listaHoras = getListaHorasTF(tipoConsolidacaoDadoId);
	
		switch (tipoConsolidacaoDadoId) {
		
		case 1: // Ano
			return "Erro: Tipo de dado nao implementado.";	
			
		case 2: // Mes
			
			if (tipoPeriodoId == 1) { // mensal 
				
				return "Erro: Tipo de dado nao implementado.";
				
			}if (tipoPeriodoId == 2) { // anual
				
				listaLinhaValorVO = linhaValorDAO.getLinhaValorMesAnual(linhaId, graficoId, mesRelatorio);
				
				return formataDsJSAno(listaLinhaValorVO);
				
			}
		
		case 3: // Dia
			
			if (tipoPeriodoId == 1) { // mensal 
				
				listaLinhaValorVO = linhaValorDAO.getLinhaValorDiaMensal(linhaId, graficoId, mesRelatorio);
				
				return formataDsJSMes(listaLinhaValorVO);
				
			}if (tipoPeriodoId == 2) { // anual
				
				listaLinhaValorVO = linhaValorDAO.getLinhaValorMesAnual(linhaId, graficoId, mesRelatorio);
				
				return formataDsJSAno(listaLinhaValorVO);
				
			}

		
		case 5: // UDM (ultimo dia do mes)	
			
			if (tipoPeriodoId == 1) { // mensal 
								
				return "Erro: Tipo de dado nao implementado.";
				
			}if (tipoPeriodoId == 2) { // anual
				listaLinhaValorVO = linhaValorDAO.getLinhaValorUdmAnual(linhaId, graficoId, mesRelatorio);
				
				return formataDsJSAno(listaLinhaValorVO);
			}
			
			
		case 6: case 7: // TimeFrames.
			
			if (tipoPeriodoId == 1) { // mensal 
				listaLinhaValorVO = linhaValorDAO.getLinhaValorTimeFrameHrMensal(linhaId, graficoId, mesRelatorio, listaHoras);
				
				return formataDsJSMes(listaLinhaValorVO);
				
			}if (tipoPeriodoId == 2) { // anual
				listaLinhaValorVO = linhaValorDAO.getLinhaValorTimeFrameHrAnual(linhaId, graficoId, mesRelatorio, listaHoras);
				
				return formataDsJSAno(listaLinhaValorVO);
			}
			
		default:
			return "Erro: Tipo de dado nao implementado.";
			
		}
				
	}
	
	private List<Integer> getListaHorasTF(Integer tipoConsolidacaoDadoId){
		
		List<Integer> listaHoras = new ArrayList<Integer>();
		
		switch (tipoConsolidacaoDadoId) {
		
		case 6: // 8:00 as 18:00

			
			listaHoras.add(8);
			listaHoras.add(9);
			listaHoras.add(10);
			listaHoras.add(11);
			listaHoras.add(12);
			listaHoras.add(13);
			listaHoras.add(14);
			listaHoras.add(15);
			listaHoras.add(16);
			listaHoras.add(17);
		
			break;
			
		case 7: // 0:00 as 8:00 e 18:00 as 24:00
			
			listaHoras.add(0);
			listaHoras.add(1);
			listaHoras.add(2);
			listaHoras.add(3);
			listaHoras.add(4);
			listaHoras.add(5);
			listaHoras.add(6);
			listaHoras.add(7);

			listaHoras.add(18);
			listaHoras.add(19);
			listaHoras.add(20);
			listaHoras.add(21);
			listaHoras.add(22);
			listaHoras.add(23);

			break;
			
		default:
			
			listaHoras = null;
			
			break;
			
		}
	
		return listaHoras;
		
	}
	
	private String formataDsJSMes(List<LinhaValorVO> listaLinhaValorVO) {
		String saida = "[";
		Iterator<LinhaValorVO> itTime = listaLinhaValorVO.iterator();
		
		Collections.sort(listaLinhaValorVO, new LinhaValorVO());
		
		SimpleDateFormat sdfIn = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd");
		//DecimalFormat df = new DecimalFormat("###");
		while (itTime.hasNext()) {
			LinhaValorVO linhaValorVO = itTime.next();
			try {
				saida = saida
						+ "["
						+ sdfOut.format(sdfIn.parse(linhaValorVO.getData()).getTime())
						+ "," + linhaValorVO.getValor() + "],";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		saida = saida.substring(0,saida.length()-1);
		saida = saida + "]";
		
		System.err.println(saida);
		
		return saida;
	}
	
	
	private String formataDsJSAno(List<LinhaValorVO> listaLinhaValorVO) {
		String saida = "[";
		Iterator<LinhaValorVO> itTime = listaLinhaValorVO.iterator();
		Collections.sort(listaLinhaValorVO, new LinhaValorVO());
		
		SimpleDateFormat sdfIn = new SimpleDateFormat("MM/yyyy");
		SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy,MM");

		while (itTime.hasNext()) {
			LinhaValorVO linhaValorVO = itTime.next();
			
			try {
				saida = saida
						+ "["
						+ "(new Date("+linhaValorVO.getData().substring(3, 7) +","+(Integer.parseInt(linhaValorVO.getData().substring(0, 2))-1)+")).getTime()"
						+ "," + linhaValorVO.getValor() + "],";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		saida = saida.substring(0,saida.length()-1);
		saida = saida + "]";
		
		return saida;
	}
	
	
}
