package br.com.paxtecnologia.pma.relatorio.ejb;

import javax.ejb.Stateless;

import br.com.paxtecnologia.pma.relatorio.util.FormataDataUtil;

@Stateless
public class GraficoEjb {
	
	private Integer diasNoMes;

	public Integer getDiasNoMes(String mesRelatorio) {
		
			diasNoMes = FormataDataUtil.diasNoMes(mesRelatorio);

		return diasNoMes;
	}
	
	
}
