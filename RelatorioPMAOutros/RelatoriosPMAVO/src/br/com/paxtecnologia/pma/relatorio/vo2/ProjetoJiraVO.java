package br.com.paxtecnologia.pma.relatorio.vo2;

public class ProjetoJiraVO {

	public Integer id;
	public String nome;
	public String projetoJiraDisplayName;
	public String logoStr;
	public String clienteDisplayName;
	
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
	public String getProjetoJiraDisplayName() {
		return projetoJiraDisplayName;
	}
	public void setProjetoJiraDisplayName(String projetoJiraDisplayName) {
		this.projetoJiraDisplayName = projetoJiraDisplayName;
	}
	public String getLogoStr() {
		return logoStr;
	}
	public void setLogoStr(String logo) {
		this.logoStr = logo;
	}
	public String getClienteDisplayName() {
		return clienteDisplayName;
	}
	public void setClienteDisplayName(String clienteDisplayName) {
		this.clienteDisplayName = clienteDisplayName;
	}
	
	
}
