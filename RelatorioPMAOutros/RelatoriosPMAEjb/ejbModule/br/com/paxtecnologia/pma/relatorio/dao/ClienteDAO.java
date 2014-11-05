package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.ClienteVO;
import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;

public class ClienteDAO {
	private DataSourcePMA connection;

	public List<ClienteVO> getListaClientes() {
		List<ClienteVO> retorno = new ArrayList<ClienteVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT c.cliente_id, "+
					 "       c.cliente "+
				     "  FROM pmp_cliente c "+
					 " where exists (select 1 from pmp_task WHERE cliente_id = c.cliente_id) "+
					 " order by cliente";
		pstmt = connection.getPreparedStatement(sql);
		ResultSet rs = connection.executaQuery(pstmt);
		ClienteVO temp;
		try {
			while (rs.next()) {
				temp = new ClienteVO();
				temp.setId(rs.getInt("cliente_id"));
				temp.setNome(rs.getString("cliente"));
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;

	}

	public List<MesRelatorioVO> getListaMes(Integer idCliente) {
		List<MesRelatorioVO> retorno = new ArrayList<MesRelatorioVO>();
		connection = new DataSourcePMA();
		Date data = null;
		PreparedStatement pstmt;
		String sql = "SELECT DISTINCT data_insercao FROM pmp_task WHERE cliente_id = ? order by 1 desc";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		MesRelatorioVO temp;
		try {
			while (rs.next()) {
				temp = new MesRelatorioVO();
				data = rs.getDate("data_insercao");
				String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(data);
				String formattedDateTexto = new SimpleDateFormat("MMM/yyyy").format(data);
				temp.setLabelMes(formattedDateTexto);
				temp.setMesString(formattedDate);
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}

	public String getLogoCliente(Integer idCliente) {
		String retorno = null;
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT logo FROM pmp_cliente WHERE cliente_id = ?";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		try {
			while (rs.next()) {

				retorno = rs.getString("logo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}

	public String getNomeCliente(Integer idCliente) {
		String retorno = null;
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT cliente FROM pmp_cliente WHERE cliente_id = ?";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		try {
			while (rs.next()) {

				retorno = rs.getString("cliente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}

}
