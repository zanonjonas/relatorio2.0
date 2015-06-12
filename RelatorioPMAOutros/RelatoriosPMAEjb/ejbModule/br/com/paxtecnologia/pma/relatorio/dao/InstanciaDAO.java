package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.InstanciaVO;

public class InstanciaDAO {

	private DataSourcePMA connection;
	
	public List<InstanciaVO> getListaInstanciaByHostId(Integer relatorioId, Integer hostId,String mesRelatorio){
		List<InstanciaVO> retorno = new ArrayList<InstanciaVO>();
		
		connection = new DataSourcePMA();
		GraficoDAO graficoDAO = new GraficoDAO();
		
		PreparedStatement pstmt;
		
		String sql = "select distinct b.instancia_id, " +
							"b.nome, " +
							"b.display_name, " +
							"b.descricao " +
			      	   "from rel_hosts a, " +
			      	        "rel_instancias b, " +
			      	        "rel_graficos c, " +
			      	        "rel_relatorios d " +
			      	  "where a.host_id=b.host_id " +
			      	    "and c.tipo_relatorio_id = d.tipo_relatorio_id " +
			      	    "and b.instancia_id = c.instancia_id " +
			      	    "and d.relatorio_id = ? " +
			      	    "and a.host_id = ? " +
			      	    "and c.instancia_id in (select  instancia_id " +
			      	    "from rel_grafico_dados_ano " +
			      	    "where trunc(data,'MM')= trunc(to_date(?,'yyyy/mm/dd'),'MM'))";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
			pstmt.setInt(2, hostId);
			pstmt.setString(3, mesRelatorio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		
		InstanciaVO temp;
		
		try {
			while (rs.next()) {
				temp = new InstanciaVO();
							
				temp.setId(rs.getInt("instancia_id"));
				temp.setNome(rs.getString("nome"));
				temp.setDisplayName(rs.getString("display_name"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setGraficoVO(graficoDAO.getListaGraficoByInstanciaId(relatorioId, temp.getId(), mesRelatorio));

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
