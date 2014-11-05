package br.com.paxtecnologia.pma.relatorio;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.ejb.WorkloadEjb;
import br.com.paxtecnologia.pma.relatorio.vo.DBSizeTabelaVO;
import br.com.paxtecnologia.pma.relatorio.vo.HostVO;

@ViewScoped
@ManagedBean(name = "workloadBean")
public class WorkloadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private WorkloadEjb workloadEjb;

	@ManagedProperty(value = "#{clientesBean.idCliente}")
	private Integer idCliente;

	@ManagedProperty(value = "#{clientesBean.mesRelatorio}")
	private String mesRelatorio;
	
	private Integer diasNoMes;
	private List<HostVO> hosts;
	private List<DBSizeTabelaVO> dbSizeTabelaVO;

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}

	public String getTf(Integer idInstancia, Integer idGraficoControle, Integer idTf) {
		return workloadEjb.getTf(idInstancia, mesRelatorio, idGraficoControle, idTf);
	}

	public String getLegenda(Integer idInstancia, Integer idGraficoControle, Integer idTf) {
		return workloadEjb.getLegenda(idInstancia, idGraficoControle, idTf);
	}
	
	public Integer getDiasNoMes() {
		if (diasNoMes == null) {
			diasNoMes = workloadEjb.getDiasNoMes(mesRelatorio);
		}
		return diasNoMes;
	}
	
	public List<HostVO> getCapHosts(Integer capitulo) {
		hosts = null;
		if (hosts == null) {
			hosts = workloadEjb.getHosts(idCliente,capitulo);
		}
		return hosts;
	}
	
	public List<HostVO> getHosts() {
		hosts = null;
		if (hosts == null) {
			hosts = workloadEjb.getHosts(idCliente);
		}
		return hosts;
	}
	
	public List<DBSizeTabelaVO> getDBSizeTabela(Integer metricaLinkId){
		
		dbSizeTabelaVO = workloadEjb.getDBSizeTabela(mesRelatorio, metricaLinkId);
		
		return dbSizeTabelaVO;
	}
	
	
}
