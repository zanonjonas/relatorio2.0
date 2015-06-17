package br.com.paxtecnologia.pma.relatorio.vo2;

import java.util.Comparator;

public class LinhaValorVO implements Comparator<LinhaValorVO>{
	private String data;
	private Double valor;
	private Integer mes;
	private Integer dia;
	private Integer hora;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	
	@Override
	public int compare(LinhaValorVO positive, LinhaValorVO negative) {
		
		String [] partsp = positive.getData().split("/");
		String [] partsn = negative.getData().split("/");
		
		Integer pos = Integer.parseInt(partsp[1]+partsp[0].replaceAll("/", "")); 
		Integer neg = Integer.parseInt(partsn[1]+partsn[0].replaceAll("/", ""));

		return pos - neg;
		
	}
	
}
