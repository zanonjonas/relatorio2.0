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
	
	public List<RelatorioVO> getListaRelatorioMenu(){
		List<RelatorioVO> retorno = new ArrayList<RelatorioVO>();
		connection = new DataSourcePMA();

		PreparedStatement pstmt;
		
		String sql = "select pj.cliente_display_name || ' / ' || r.display_name menu_entry " +
				           ",r.relatorio_id " +
				           ",pj.projeto_jira_id " +
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
				temp.setId(rs.getInt("relatorio_id"));
				temp.setMenuEntry(rs.getString("menu_entry"));
				temp.setProjetoJiraId(rs.getInt("projeto_jira_id"));
				
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		
		connection.closeConnection(pstmt);
		
		return retorno;
		
		
	}
	
	
	public List<MesRelatorioVO> getListaMes(Integer projetoJiraId) {
		List<MesRelatorioVO> retorno = new ArrayList<MesRelatorioVO>();
		connection = new DataSourcePMA();
		Date data = null;
		PreparedStatement pstmt;
		String sql = "SELECT DISTINCT data_insercao FROM pmp_task WHERE cliente_id = ? order by 1 desc";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, projetoJiraId);
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
	
	
	/*public List<RelatorioVO> getListaRelatorio(){
		List<RelatorioVO> retorno = new ArrayList<RelatorioVO>();
		connection = new DataSourcePMA();

		PreparedStatement pstmt;
		
		String sql = "select r.relatorio_id " +
				    ",r.nome" +
				    ",r.display_name" +
				    ",r.projeto_jira_id" +
				    ",r.tipo_relatorio_id " +
			    "from rel_relatorios r";
		
		pstmt = connection.getPreparedStatement(sql);
		ResultSet rs = connection.executaQuery(pstmt);
		
		RelatorioVO temp;
		
		try {
			while (rs.next()) {
				temp = new RelatorioVO();
				temp.setId(rs.getInt("relatorio_id"));
				temp.setNome(rs.getString("nome"));
				temp.setDisplayName(rs.getString("display_name"));
				temp.setProjetoJiraVO(new ProjetoJiraDAO().getProjetoJiraById(rs.getInt("projeto_jira_id")));
				temp.setTipoRelatorioVO(new TipoRelatorioDAO().getTipoRelatorioById(rs.getInt("tipo_relatorio_id")));
				
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		
		connection.closeConnection(pstmt);
		
		return retorno;
		
		
	}
	*/	
}
