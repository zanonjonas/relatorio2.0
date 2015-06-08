package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.paxtecnologia.pma.relatorio.vo2.TipoRelatorioVO;

public class TipoRelatorioDAO {

private DataSourcePMA connection;
	
	public TipoRelatorioVO getTipoRelatorioById(Integer id){
		connection = new DataSourcePMA();

		PreparedStatement pstmt;
		
		String sql = "select tipo_relatorio_id" +
				           ",tipo_relatorio " +
				       "from rel_tipo_relatorio " +
				      "where tipo_relatorio_id = ?";
		
		pstmt = connection.getPreparedStatement(sql);
		
		try {
			pstmt.setInt(1, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = connection.executaQuery(pstmt);
		
		TipoRelatorioVO retorno = new TipoRelatorioVO();
		
		try {
			while (rs.next()) {
				
				retorno.setId(rs.getInt("tipo_relatorio_id"));
				retorno.setTipoRelatorio(rs.getString("tipo_relatorio"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		connection.closeConnection(pstmt);
		
		return retorno;
		
		
	}
	
}
