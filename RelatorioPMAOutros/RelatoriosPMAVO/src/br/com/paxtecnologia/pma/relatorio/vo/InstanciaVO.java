package br.com.paxtecnologia.pma.relatorio.vo;

import java.util.List;

public class InstanciaVO {
	private Integer id;
	private String instancia;
	private String descricao;
	private List<GraficoVO> graficoVO;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInstancia() {
		return instancia;
	}
	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}
	public List<GraficoVO> getGraficoVO() {
		return graficoVO;
	}
	public void setGraficoVO(List<GraficoVO> graficoVO) {
		this.graficoVO = graficoVO;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
