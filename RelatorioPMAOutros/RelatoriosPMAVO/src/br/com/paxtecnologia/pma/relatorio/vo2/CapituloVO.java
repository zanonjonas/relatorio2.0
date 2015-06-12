package br.com.paxtecnologia.pma.relatorio.vo2;

import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.HostVO;

public class CapituloVO {

	private String capitulo;
	private String display_name;
	private String nome;
	private Integer hostInstancia;
	private List<HostVO> hostVO;
	private Integer nivel;
	
	
	public String getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(String capitulo) {
		this.capitulo = capitulo;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getHostInstancia() {
		return hostInstancia;
	}
	public void setHostInstancia(Integer hostInstancia) {
		this.hostInstancia = hostInstancia;
	}
	public List<HostVO> getHostVO() {
		return hostVO;
	}
	public void setHostVO(List<HostVO> hostVO) {
		this.hostVO = hostVO;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	
	
	
}
