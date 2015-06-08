package br.com.paxtecnologia.pma.relatorio.vo2;

public class RelatorioVO {

	private Integer id;
	private String nome;
	private String displayName;
	private ProjetoJiraVO projetoJiraVO;
	private TipoRelatorioVO tipoRelatorioVO;
	private String menuEntry;
	
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
	public ProjetoJiraVO getProjetoJiraVO() {
		return projetoJiraVO;
	}
	public void setProjetoJiraVO(ProjetoJiraVO projetoJiraVO) {
		this.projetoJiraVO = projetoJiraVO;
	}
	public TipoRelatorioVO getTipoRelatorioVO() {
		return tipoRelatorioVO;
	}
	public void setTipoRelatorioVO(TipoRelatorioVO tipoRelatorioVO) {
		this.tipoRelatorioVO = tipoRelatorioVO;
	}
	public String getMenuEntry() {
		return menuEntry;
	}
	public void setMenuEntry(String menuEntry) {
		this.menuEntry = menuEntry;
	}
	
	
	
}
