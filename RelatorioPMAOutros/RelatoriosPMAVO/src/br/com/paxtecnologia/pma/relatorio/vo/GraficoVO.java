package br.com.paxtecnologia.pma.relatorio.vo;

import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo2.GraficoLinhaVO;

public class GraficoVO {
	
	private Integer graficoId;
	private String tituloDisplayName;
	private String descricaoAntes;
	private String descricaoDepois;
	private Integer ordemPlot;
	private String descricaoCustomizada;
	private String nickName;
	
	private List<GraficoLinhaVO> graficoLinhaVO;

	public Integer getGraficoId() {
		return graficoId;
	}

	public void setGraficoId(Integer graficoId) {
		this.graficoId = graficoId;
	}

	public String getTituloDisplayName() {
		return tituloDisplayName;
	}

	public void setTituloDisplayName(String tituloDisplayName) {
		this.tituloDisplayName = tituloDisplayName;
	}

	public String getDescricaoAntes() {
		return descricaoAntes;
	}

	public void setDescricaoAntes(String descricaoAntes) {
		this.descricaoAntes = descricaoAntes;
	}

	public String getDescricaoDepois() {
		return descricaoDepois;
	}

	public void setDescricaoDepois(String descricaoDepois) {
		this.descricaoDepois = descricaoDepois;
	}

	public Integer getOrdemPlot() {
		return ordemPlot;
	}

	public void setOrdemPlot(Integer ordemPlot) {
		this.ordemPlot = ordemPlot;
	}

	public String getDescricaoCustomizada() {
		return descricaoCustomizada;
	}

	public void setDescricaoCustomizada(String descricaoCustomizada) {
		this.descricaoCustomizada = descricaoCustomizada;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<GraficoLinhaVO> getGraficoLinhaVO() {
		return graficoLinhaVO;
	}

	public void setGraficoLinhaVO(List<GraficoLinhaVO> graficoLinhaVO) {
		this.graficoLinhaVO = graficoLinhaVO;
	}
	
	

}
