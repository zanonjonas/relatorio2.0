package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo2.GraficoLinhaVO;

public class GraficoLinhaDAO {

	private DataSourcePMA connection;
	
	public List<GraficoLinhaVO> getListaGraficoLinhaByGrfId(Integer graficoId){
		List<GraficoLinhaVO> retorno = new ArrayList<GraficoLinhaVO>();
		
		connection = new DataSourcePMA();
		
		PreparedStatement pstmt;
		
		String sql = "select a.linha_id, " +
				            "a.legenda, " +
				            "a.tipo_grafico_id, " +
				            "a.tipo_consolidacao_dado_id, " +
				            "a.ordem, " +
				            "a.nome, " +
				            "c.codigocor " +
				       "from rel_grafico_linha a, " +
				            "rel_graficos b, " +
				            "rel_cores_linha c " +
				      "where a.tipo_grafico_id=b.tipo_grafico_id " +
				        "and grafico_id=? " +
				        "and a.cor=c.cor ";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, graficoId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		
		GraficoLinhaVO temp;
		
		try {
			while (rs.next()) {
				temp = new GraficoLinhaVO();
			
				temp.setLinhaId(rs.getInt("linha_id"));
				temp.setTipoGraficoId(rs.getInt("tipo_grafico_id"));
				temp.setTipoConsolidacaoDadoId(rs.getInt("tipo_consolidacao_dado_id"));
				temp.setLegenda(rs.getString("legenda"));
				temp.setOrdem(rs.getInt("ordem"));
				temp.setNome(rs.getString("nome"));
				temp.setCor(rs.getString("codigocor"));
				
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
