package br.com.paxtecnologia.pma.relatorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.paxtecnologia.pma.relatorio.ejb.ClientesEjb;

@ViewScoped
@ManagedBean(name = "capaBean")
public class CapaBean {

	@EJB
	private ClientesEjb clientesEjb;

	@ManagedProperty(value = "#{clientesBean.idCliente}")
	private Integer idCliente;

	@ManagedProperty(value = "#{clientesBean.mesRelatorio}")
	private String mesRelatorio;

	private String tituloRelatorio = "SUPORTE";
	private String subtituloRelatorio;
	private String dataCriacao;
	private String textoVersao = "Versão 7.3";
	SimpleDateFormat dateFormat;
	private String nomeCliente;
	private String mesEAno;
	private String logoCliente;

	public String getLogoCliente() {
		if (logoCliente == null) {
			logoCliente = clientesEjb.getLogoCliente(idCliente);
		}
		return logoCliente;
	}

	public String getNomeCliente() {
		if (nomeCliente == null) {
			nomeCliente = clientesEjb.getNomeCliente(idCliente);
		}
		return nomeCliente;
	}

	public String getMesEAno() {
		if (mesEAno == null) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dataRelatorio;
			try {
			dataRelatorio = dateFormat.parse(mesRelatorio);
			dateFormat = new SimpleDateFormat("MMMM 'de' yyyy");
			mesEAno = dateFormat.format(dataRelatorio);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mesEAno;
	}

	public String getTituloRelatorio() {
		return tituloRelatorio;
	}

	public String getSubtituloRelatorio() {
		if (subtituloRelatorio == null) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dataRelatorio;
			try {
				dataRelatorio = dateFormat.parse(mesRelatorio);
				dateFormat = new SimpleDateFormat("MMMM / yyyy");
				subtituloRelatorio = "- " + dateFormat.format(dataRelatorio)
						+ " -";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return subtituloRelatorio;
	}

	public String getDataCriacao() {
		if (dataCriacao == null) {
			Date dataAtual = new Date();
			dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
			dataCriacao = "Data de criação: " + dateFormat.format(dataAtual);
		}
		return dataCriacao;
	}

	public String getTextoVersao() {
		return textoVersao;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public void setMesRelatorio(String mesRelatorio) {
		this.mesRelatorio = mesRelatorio;
	}

}
