package br.com.paxtecnologia.pma.relatorio;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import br.com.paxtecnologia.pma.relatorio.ejb.RelatorioEjb;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;
import br.com.paxtecnologia.pma.relatorio.vo2.RelatorioVO;

@ManagedBean(name = "relatorioBean")
@SessionScoped

public class RelatorioBean {

		@EJB 
		private RelatorioEjb relatorioEjb;
		
		private Integer relatorioId;
		private Integer projetoJiraId;
		private String mesRelatorio;
		private List <RelatorioVO> listaRelatorios;
		private List<MesRelatorioVO> listaMes;
		private Boolean update = false;
		
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

		private List<MesRelatorioVO> generateListaMes(Integer projetoJiraId) {
			setUpdate(false);
			listaMes = relatorioEjb.getListaMes(projetoJiraId);
			setUpdate(true);
			return listaMes;
		}
		
		public List<MesRelatorioVO> getListaMes() {
			return listaMes;
		}

		
		public void setListaMes(List<MesRelatorioVO> listaMes) {
			this.listaMes = listaMes;
		}
	
		public Integer getRelatorioId() {
			return relatorioId;
		}

		public void setRelatorioId(Integer relatorioId) {
			this.relatorioId = relatorioId;
		}

		public String getMesRelatorio() {
			return mesRelatorio;
		}

		public void setMesRelatorio(String mesRelatorio) {
			this.mesRelatorio = mesRelatorio;
		}

		public Integer getProjetoJiraId() {
			return projetoJiraId;
		}

		public void setProjetoJiraId(Integer projetoJiraId) {
			this.projetoJiraId = projetoJiraId;
		}
		
}
