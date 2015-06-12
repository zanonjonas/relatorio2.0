package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.HostVO;

public class HostDAO {

	private DataSourcePMA connection;
	
	public List<HostVO> getListaHostsByRelId(Integer relatorioId,String mesRelatorio){
		List<HostVO> retorno = new ArrayList<HostVO>();
		InstanciaDAO instanciaDAO = new InstanciaDAO();
		
		connection = new DataSourcePMA();
		
		PreparedStatement pstmt;
		
		String sql = "select a.host_id, " +
				            "a.nome, " +
				            "a.display_name, " +
				            "a.projeto_jira_id " +
				       "from rel_hosts a " +
				           ",rel_relatorios b " +
				           ",rel_instancias c " +
				          "where a.projeto_jira_id=b.projeto_jira_id " +
				            "and c.host_id = a.host_id " +
				            "and b.relatorio_id = ? " +
				            "and c.instancia_id in " +
				            "(select instancia_id " +
				               "from rel_grafico_dados_ano " +
				              "where trunc(data,'MM')=trunc(to_date(?,'yyyy/mm/dd'),'MM'))";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
			pstmt.setString(2, mesRelatorio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		
		HostVO temp;
		
		try {
			while (rs.next()) {
				temp = new HostVO();
							
				temp.setId(rs.getInt("host_id"));
				temp.setNome(rs.getString("nome"));
				temp.setDisplayName(rs.getString("display_name"));
				temp.setProjetoJiraId(rs.getString("projeto_jira_id"));
				temp.setInstanciaVO(instanciaDAO.getListaInstanciaByHostId(relatorioId,temp.getId(), mesRelatorio));

				retorno.add(temp);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
}
