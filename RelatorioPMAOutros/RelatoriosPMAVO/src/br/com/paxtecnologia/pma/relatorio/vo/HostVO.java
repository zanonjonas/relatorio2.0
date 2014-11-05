package br.com.paxtecnologia.pma.relatorio.vo;

import java.util.List;

public class HostVO {
	
	private Integer id;
	private String host;
	private String descricao;
	private String hostName;
	private String tipoCPU;
	private Integer quantidadeCPU;
	private Integer quantidadeMemoria;
	private List<InstanciaVO> instanciaVO;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public List<InstanciaVO> getInstanciaVO() {
		return instanciaVO;
	}
	public void setInstanciaVO(List<InstanciaVO> instanciaVO) {
		this.instanciaVO = instanciaVO;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getTipoCPU() {
		return tipoCPU;
	}
	public void setTipoCPU(String tipoCPU) {
		this.tipoCPU = tipoCPU;
	}
	public Integer getQuantidadeCPU() {
		return quantidadeCPU;
	}
	public void setQuantidadeCPU(Integer quantidadeCPU) {
		this.quantidadeCPU = quantidadeCPU;
	}
	public Integer getQuantidadeMemoria() {
		return quantidadeMemoria;
	}
	public void setQuantidadeMemoria(Integer quantidadeMemoria) {
		this.quantidadeMemoria = quantidadeMemoria;
	}
}
