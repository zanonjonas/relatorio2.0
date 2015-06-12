package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.GraficoVO;

public class GraficoDAO {
	private DataSourcePMA connection;
	
	public List<GraficoVO> getListaGraficoByInstanciaId(Integer relatorioId, Integer instanciaId,String mesRelatorio){
		List<GraficoVO> retorno = new ArrayList<GraficoVO>();
		
		connection = new DataSourcePMA();
		GraficoLinhaDAO graficoLinhaDAO = new GraficoLinhaDAO();
		
		PreparedStatement pstmt;
		
		String sql = "select c.grafico_id, " +
				            "c.ordem_plot, " +
				            "c.titulo_display_name, " +
				            "c.descricao_antes, " +
				            "c.descricao_depois, " +
				            "c.descricao_customizada, " +
				            "c.nickname, " +
				            "c.capitulo, " +
				            "c.tipo_grafico_id, " +
				            "f.tipo_periodo_id " +
				       "from rel_instancias b, " +
				            "rel_graficos c, " +
				            "rel_relatorios d, " +
				            "rel_tipo_grafico f " +
				      "where c.tipo_relatorio_id = d.tipo_relatorio_id " +
				        "and b.instancia_id = c.instancia_id " +
				        "and d.relatorio_id = ? " +
				        "and c.instancia_id = ? " +
				        "and c.tipo_grafico_id=f.tipo_grafico_id " +
				        "and c.instancia_id in (select instancia_id " +
				          "from rel_grafico_dados_ano " +
				          "where trunc(data,'MM')= trunc(to_date(?,'yyyy/mm/dd'),'MM')) " +
				          "order by ordem_plot";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, relatorioId);
			pstmt.setInt(2, instanciaId);
			pstmt.setString(3, mesRelatorio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		
		GraficoVO temp;
		
		try {
			while (rs.next()) {
				temp = new GraficoVO();
							
				temp.setGraficoId(rs.getInt("grafico_id"));
				temp.setOrdemPlot(rs.getInt("ordem_plot"));
				temp.setTituloDisplayName(rs.getString("titulo_display_name"));
				temp.setDescricaoAntes(rs.getString("descricao_antes"));
				temp.setDescricaoDepois(rs.getString("descricao_depois"));
				temp.setDescricaoCustomizada(rs.getString("descricao_customizada"));
				temp.setNickName(rs.getString("nickname"));
				temp.setCapitulo(rs.getString("capitulo"));
				temp.setGraficoLinhaVO(graficoLinhaDAO.getListaGraficoLinhaByGrfId(temp.getGraficoId()));
				temp.setTipoPeriodoId(rs.getInt("tipo_periodo_id"));
				

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
