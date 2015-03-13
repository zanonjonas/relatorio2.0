package br.com.paxtecnologia.pma.relatorio.vo;

import java.util.Comparator;

public class DBSizeTabelaVO implements Comparator<DBSizeTabelaVO>{
	private String valor;
	private String mes;
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getData() {
		return mes;
	}
	
	public void setData(String mes) {
		this.mes = mes;
	}

	@Override
	public int compare(DBSizeTabelaVO positive, DBSizeTabelaVO negative) {
		
		String [] partsp = positive.getData().split("/");
		String [] partsn = negative.getData().split("/");
		
		Integer pos = Integer.parseInt(partsp[1]+partsp[0].replaceAll("/", "")); 
		Integer neg = Integer.parseInt(partsn[1]+partsn[0].replaceAll("/", ""));
		
		return pos - neg;
		
	}

}
