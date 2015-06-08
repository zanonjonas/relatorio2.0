package br.com.paxtecnologia.pma.relatorio.vo2;

public class RelatorioVO {

	private Integer id;
	private String nome;
	private String displayName;
	private Integer projetoJiraId;
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
	public Integer getProjetoJiraId() {
		return projetoJiraId;
	}
	public void setProjetoJiraId(Integer projetoJiraId) {
		this.projetoJiraId = projetoJiraId;
	}
	
	
	
}
