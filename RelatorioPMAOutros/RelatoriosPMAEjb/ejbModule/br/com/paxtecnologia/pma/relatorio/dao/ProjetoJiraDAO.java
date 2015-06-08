package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.paxtecnologia.pma.relatorio.vo2.ProjetoJiraVO;


public class ProjetoJiraDAO {

	private DataSourcePMA connection;
	
	public ProjetoJiraVO getProjetoJiraById(Integer id){
		connection = new DataSourcePMA();

		PreparedStatement pstmt;
		
		String sql = "select projeto_jira_id" +
				           ",display_name" +
				           ",nome" +
				           ",logo_str" +
				           ",cliente_display_name " +
				       "from rel_projeto_jira " +
				      "where projeto_jira_id = ?";
		
		pstmt = connection.getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = connection.executaQuery(pstmt);
		
		ProjetoJiraVO retorno = new ProjetoJiraVO();
		
		try {
			while (rs.next()) {
				
				retorno.setId(rs.getInt("projeto_jira_id"));
				retorno.setNome(rs.getString("nome"));
				retorno.setProjetoJiraDisplayName(rs.getString("display_name"));
				retorno.setLogoStr(rs.getString("logo_str"));
				retorno.setClienteDisplayName(rs.getString("cliente_display_name"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
		
		
	}
	
}
