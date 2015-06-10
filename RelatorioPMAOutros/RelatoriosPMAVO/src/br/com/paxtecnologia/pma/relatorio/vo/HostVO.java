package br.com.paxtecnologia.pma.relatorio.vo;

import java.util.List;

public class HostVO {
	
	private Integer id;
	private String nome;
	private String displayName;
	private String projetoJiraId;
	private List<InstanciaVO> instanciaVO;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getProjetoJiraId() {
		return projetoJiraId;
	}
	public void setProjetoJiraId(String projetoJiraId) {
		this.projetoJiraId = projetoJiraId;
	}
	public List<InstanciaVO> getInstanciaVO() {
		return instanciaVO;
	}
	public void setInstanciaVO(List<InstanciaVO> instanciaVO) {
		this.instanciaVO = instanciaVO;
	}
	

}
