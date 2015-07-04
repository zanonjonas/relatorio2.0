package br.com.paxtecnologia.pma.relatorio;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import br.com.paxtecnologia.pma.relatorio.ejb.RelatorioEjb;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;
import br.com.paxtecnologia.pma.relatorio.vo2.RelatorioVO;


@SessionScoped
@ManagedBean(name = "relatorioBean")
public class RelatorioBean {
		
		private static final long serialVersionUID = 1L;	
	
		@EJB 
		private RelatorioEjb relatorioEjb;
		
		private Integer relatorioId;
		private Integer projetoJiraId;
		private String mesRelatorio;
		private List <RelatorioVO> listaRelatorios;
		private List<MesRelatorioVO> listaMes;
		private Boolean update = false;
		private String nome;
		private String tituloCapa;
		private String logoStr;
		private String clienteDisplayName;
		private Integer tipoRelatorioId;
		
		
		
		public Boolean getUpdate() {
			return update;
		}

		public void setUpdate(Boolean update) {
			this.update = update;
		}
		
		public List<RelatorioVO> getListaRelatoriosMenu() {
			if (listaRelatorios == null) {
				listaRelatorios = relatorioEjb.getListaRelatorioMenu();
			}
			return listaRelatorios;
		}
		
		public void updateListaMes(ValueChangeEvent e) {
			// This will return you the newly selected
			// value as an object. You'll have to cast it.
			Object newValue = e.getNewValue();

			// The rest of your processing logic goes here...
			setListaMes(generateListaMes((Integer) newValue));
		}

		private List<MesRelatorioVO> generateListaMes(Integer relatorioId) {
			setUpdate(false);
			listaMes = relatorioEjb.getListaMes(relatorioId);
			setUpdate(true);
			return listaMes;
		}
		
		public List<MesRelatorioVO> getListaMes() {
			return listaMes;
		}

		
		public void setListaMes(List<MesRelatorioVO> listaMes) {
			this.listaMes = listaMes;
		}

		public String getMesRelatorio() {
			return mesRelatorio;
		}

		public void setMesRelatorio(String mesRelatorio) {
			this.mesRelatorio = mesRelatorio;
		}

		public Integer getProjetoJiraId() {
			
			projetoJiraId = relatorioEjb.getProjetoJiraIdByRelatorioId(getRelatorioId());
			
			return projetoJiraId;
		}
		
		public Integer getTipoRelatorioId() {
			
			tipoRelatorioId = relatorioEjb.getTipoRelatorioIdByRelatorioId(getRelatorioId());
			
			return tipoRelatorioId;
		}

		public void setProjetoJiraId(Integer projetoJiraId) {
			this.projetoJiraId = projetoJiraId;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public RelatorioVO getRelatorioById(Integer id, String mesRelatorio){
			
			return relatorioEjb.getRelatorioById(id,mesRelatorio);
			
		}

		public String getTituloCapa() {
			return tituloCapa;
		}

		public void setTituloCapa(String tituloCapa) {
			this.tituloCapa = tituloCapa;
		}

		public String getLogoStr() {
			return logoStr;
		}

		public void setLogoStr(String logoStr) {
			this.logoStr = logoStr;
		}

		public String getClienteDisplayName() {
			return clienteDisplayName;
		}

		public void setClienteDisplayName(String clienteDisplayName) {
			this.clienteDisplayName = clienteDisplayName;
		}

		public Integer getRelatorioId() {
			return relatorioId;
		}

		public void setRelatorioId(Integer relatorioId) {
			this.relatorioId = relatorioId;
		}
		
		
		public void getTipoRelatorioId(String nome) {
			this.nome = nome;
		}
		
		
		
}
