package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.MesRelatorioVO;
import br.com.paxtecnologia.pma.relatorio.vo2.RelatorioVO;

public class RelatorioDAO {

	private DataSourcePMA connection;
	
	public RelatorioVO getRelatorioById(Integer relatorioId, String mesRelatorio){
		RelatorioVO retorno = new RelatorioVO();
		
		connection = new DataSourcePMA();
		CapituloDAO capituloDAO = new CapituloDAO();
		
		PreparedStatement pstmt;
		
		String sql = "select a.relatorio_id " +
				           ",a.nome " +
				           ",a.display_name " +
				           ",a.projeto_jira_id " +
				           ",a.tipo_relatorio_id " +
				           ",a.titulo_capa " +
				           ",b.logo_str " +
				           ",b.cliente_display_name " +
				       "from rel_relatorios a " +
				           ",rel_projeto_jira b " +
				       "where a.relatorio_id = ? " +
				         "and a.projeto_jira_id = b.projeto_jira_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);

		try {
			while (rs.next()) {
				retorno.setRelatorioId(rs.getInt("relatorio_id"));
				retorno.setNome(rs.getString("nome"));
				retorno.setDisplayName(rs.getString("display_name"));
				retorno.setProjetoJiraId(rs.getInt("projeto_jira_id"));
				retorno.setTipoRelatorioId(rs.getInt("tipo_relatorio_id"));
				retorno.setTituloCapa(rs.getString("titulo_capa"));
				retorno.setLogoStr(rs.getString("logo_str"));
				retorno.setClienteDisplayName("cliente_display_name");
				retorno.setCapituloVO(capituloDAO.getListaCapitulosByRelId(retorno.getRelatorioId(), mesRelatorio));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
	public List<RelatorioVO> getListaRelatorioMenu(){
		List<RelatorioVO> retorno = new ArrayList<RelatorioVO>();
		connection = new DataSourcePMA();

		PreparedStatement pstmt;
		
		String sql = "select pj.cliente_display_name || ' / ' || r.display_name menu_entry " +
				           ",r.relatorio_id " +
				           ",pj.projeto_jira_id " +
				           ",r.nome " +
				       "from rel_relatorios r " +
				           ",rel_projeto_jira pj " +
				      "where r.projeto_jira_id=pj.projeto_jira_id " +
				   "order by 1";		
		pstmt = connection.getPreparedStatement(sql);
		ResultSet rs = connection.executaQuery(pstmt);
		
		RelatorioVO temp;
		
		try {
			while (rs.next()) {
				temp = new RelatorioVO();
				temp.setRelatorioId(rs.getInt("relatorio_id"));
				temp.setMenuEntry(rs.getString("menu_entry"));
				temp.setProjetoJiraId(rs.getInt("projeto_jira_id"));
				temp.setNome(rs.getString("nome"));
				
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		
		connection.closeConnection(pstmt);
		
		return retorno;
		
		
	}
	
	
	public List<MesRelatorioVO> getListaMes(Integer relatorioId) {
		List<MesRelatorioVO> retorno = new ArrayList<MesRelatorioVO>();
		connection = new DataSourcePMA();
		Date data = null;
		PreparedStatement pstmt;
		String sql = "SELECT DISTINCT a.data_insercao " +
				                "FROM pmp_task a, " +
				                     "rel_relatorios b " +
				              " WHERE a.cliente_id = b.projeto_jira_id " +
				                 "and b.relatorio_id = ? " +
				            "order by 1 desc";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
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
	
	
	public String getLogoStr(Integer relatorioId){
		
		String retorno = null;
		
		connection = new DataSourcePMA();
		
		PreparedStatement pstmt;
		
		String sql = "select  b.logo_str " +
				       "from rel_relatorios a " +
				           ",rel_projeto_jira b " +
				       "where a.relatorio_id = ? " +
				         "and a.projeto_jira_id = b.projeto_jira_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);

		try {
			while (rs.next()) {
				
				retorno = rs.getString("logo_str");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
	public String getClienteDisplayName(Integer relatorioId){
		
		String retorno = null;
		
		connection = new DataSourcePMA();
		
		PreparedStatement pstmt;
		
		String sql = "select  b.cliente_display_name " +
				       "from rel_relatorios a " +
				           ",rel_projeto_jira b " +
				       "where a.relatorio_id = ? " +
				         "and a.projeto_jira_id = b.projeto_jira_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);

		try {
			while (rs.next()) {
				
				retorno = rs.getString("cliente_display_name");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
	public String getTituloRelatorio(Integer relatorioId){
		
		String retorno = null;
		
		connection = new DataSourcePMA();
		
		PreparedStatement pstmt;
		
		String sql = "select  a.titulo_capa " +
				       "from rel_relatorios a " +
				       "where a.relatorio_id = ?";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);

		try {
			while (rs.next()) {
				
				retorno = rs.getString("titulo_capa");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
}
