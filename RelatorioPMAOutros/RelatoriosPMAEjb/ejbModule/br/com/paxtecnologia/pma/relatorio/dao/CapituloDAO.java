package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo2.CapituloVO;

public class CapituloDAO {

	private DataSourcePMA connection;
	
	public List<CapituloVO> getListaCapitulosByRelId(Integer relatorioId, String mesRelatorio){
		List<CapituloVO> retorno = new ArrayList<CapituloVO>();
		
		connection = new DataSourcePMA();
		HostDAO hostDAO = new HostDAO();
		
		PreparedStatement pstmt;
		
		String sql = "select a.capitulo " +
				           ",a.nome " +
				           ",a.display_name " +
				           ",a.host_instancia " +
				           ",a.nivel " +
				           "from rel_capitulos_relatorio a, " +
		                        "rel_relatorios b " +
		                  "where a.tipo_relatorio_id=b.tipo_relatorio_id " +
		                    "and b.relatorio_id = ? " +
		                   "order by a.capitulo";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		
		CapituloVO temp;
		
		try {
			while (rs.next()) {
				temp = new CapituloVO();
				
				temp.setHostInstancia(rs.getInt("host_instancia"));
				temp.setCapitulo(rs.getString("capitulo"));
				temp.setNome(rs.getString("nome"));
				temp.setDisplay_name(rs.getString("display_name"));
				temp.setNivel(rs.getInt("nivel"));
				temp.setHostVO(hostDAO.getListaHostsByRelId(relatorioId, mesRelatorio));
				
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
