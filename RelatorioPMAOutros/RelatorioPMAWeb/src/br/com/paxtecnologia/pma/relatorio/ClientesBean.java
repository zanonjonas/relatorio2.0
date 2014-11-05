package br.com.paxtecnologia.pma.relatorio;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import br.com.paxtecnologia.pma.relatorio.ejb.ClientesEjb;
import br.com.paxtecnologia.pma.relatorio.vo.ClienteVO;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;

@ManagedBean(name = "clientesBean")
@SessionScoped
public class ClientesBean {

	@EJB
	private ClientesEjb clientesEjb;

	private Integer idCliente;
	private String mesRelatorio;
	private List<ClienteVO> listaClientes;
	private List<MesRelatorioVO> listaMes;
	private Boolean update = false;

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public List<ClienteVO> getListaClientes() {
		if (listaClientes == null) {
			listaClientes = clientesEjb.getListaClientes();
		}
		return listaClientes;
	}

	public void updateListaMes(ValueChangeEvent e) {
		// This will return you the newly selected
		// value as an object. You'll have to cast it.
		Object newValue = e.getNewValue();

		// The rest of your processing logic goes here...
		setListaMes(generateListaMes((Integer) newValue));
	}

	private List<MesRelatorioVO> generateListaMes(Integer idCliente) {
		setUpdate(false);
		listaMes = clientesEjb.getListaMes(idCliente);
		setUpdate(true);
		return listaMes;
	}

	public List<MesRelatorioVO> getListaMes() {
		return listaMes;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getMesRelatorio() {
		return mesRelatorio;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}

	public void setListaClientes(List<ClienteVO> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public void setListaMes(List<MesRelatorioVO> listaMes) {
		this.listaMes = listaMes;
	}
	
	public void mesChanged(ValueChangeEvent e){
		
	}

}
