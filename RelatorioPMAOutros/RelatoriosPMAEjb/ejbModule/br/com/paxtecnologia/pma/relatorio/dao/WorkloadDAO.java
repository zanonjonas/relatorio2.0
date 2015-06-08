package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.vo.DBSizeTabelaVO;
import br.com.paxtecnologia.pma.relatorio.vo.GraficoMetricaVO;
import br.com.paxtecnologia.pma.relatorio.vo.GraficoVO;
import br.com.paxtecnologia.pma.relatorio.vo.HostVO;
import br.com.paxtecnologia.pma.relatorio.vo.InstanciaVO;
import br.com.paxtecnologia.pma.relatorio.vo.TimeFrameVO;
import br.com.paxtecnologia.pma.relatorio.util.FormataDataUtil;

public class WorkloadDAO {
	private DataSourcePMA connection;
	
	public List<TimeFrameVO> getTimeFrameAno24horasDBSize(Integer metrica, String mesRelatorio) {
		List<TimeFrameVO> timeFrame = new ArrayList<TimeFrameVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select to_char(f.data,'MM/YYYY') data " +
		        ",max(f.valor_medio) KEEP (DENSE_RANK LAST ORDER BY f.data ASC) valor " +
		        "from pmp_workload_mes f " +
		        "where metrica_link_id = ? "+
		        "and data >= trunc(add_months(to_date(?,'yyyy/mm/dd') ,-12) , 'month') "+
		        "and data < trunc(add_months( to_date(?,'yyyy/mm/dd') ,1) , 'month') "+
		        "group by to_char(f.data,'MM/YYYY') "+
		        "order by data";
				
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, metrica);
			pstmt.setString(2,mesRelatorio);
			pstmt.setString(3,mesRelatorio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		TimeFrameVO temp;
		try {
			while (rs.next()) {
				temp = new TimeFrameVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				timeFrame.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return timeFrame;
	}
	
	public List<DBSizeTabelaVO> getDBSizeTabela(String mesRelatorio, Integer graficoID){
		List <DBSizeTabelaVO> retorno = new ArrayList<DBSizeTabelaVO>();
		
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select to_char(f.data,'MM/YYYY') data_mes " +
			    "  ,max(f.valor_medio*1024*1024*1024) KEEP (DENSE_RANK LAST ORDER BY f.data ASC) valor " +
			    "  from pmp_workload_mes f " +
				"where metrica_link_id in " +
				"( select metrica_link_id " +
				"from PAX.PMP_TIME_FRAME " +
				"where grafico_id=?) " +
				"and f.data >= add_months(trunc(to_date(?,'yyyy/mm/dd'),'mm'),-1) " +
				"and f.data < trunc(add_months(trunc(to_date(?,'yyyy/mm/dd'),'mm'),1),'mm') " +
				"group by to_char(f.data,'MM/YYYY') "+
				"order by data_mes";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, graficoID);
			pstmt.setString(2, mesRelatorio);
			pstmt.setString(3, mesRelatorio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
		}
		ResultSet rs = connection.executaQuery(pstmt);
		DBSizeTabelaVO temp;
		try {
			while (rs.next()) {
				temp = new DBSizeTabelaVO();
				temp.setData(rs.getString("data_mes"));
				temp.setValor(rs.getString("valor"));
				retorno.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		
		return retorno;
	}
	
	public GraficoMetricaVO getMetrica(Integer idInstancia, Integer idGraficoControle, Integer idTf) {
		GraficoMetricaVO retorno = new GraficoMetricaVO();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select t.metrica_link_id, "+
				 	 "       t.tipo_horario_id "+
				 	 "  from pmp_grafico g, pmp_time_frame t "+
				 	 " where g.instancia_id = ? "+
				 	 "   and g.grafico_controle_id = ? "+
				 	 "   and t.time_frame_controle_id = ? "+
				 	 "   and g.grafico_id = t.grafico_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idInstancia);
			pstmt.setInt(2, idGraficoControle);
			pstmt.setInt(3, idTf);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		try {
			while (rs.next()) {
				retorno.setMetrica(rs.getInt("metrica_link_id"));
				retorno.setTipoHorario(rs.getInt("tipo_horario_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}

	public List<TimeFrameVO> getTimeFrame24horas(Integer metrica, String mesRelatorio) {
		List<TimeFrameVO> timeFrame = new ArrayList<TimeFrameVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT to_char(data, 'dd/mm/yyyy') data, avg(valor_medio) valor "
				+ "FROM pmp_workload_dia "
				+ "WHERE data between ? and ? "
				+ "AND metrica_link_id = ? "
				+ "GROUP BY to_char(data, 'dd/mm/yyyy') " 
				+ "ORDER BY data";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setDate(1, FormataDataUtil.formataDataInicio(mesRelatorio));
			pstmt.setDate(2, FormataDataUtil.formataDataFim_dia(mesRelatorio));
			pstmt.setInt(3, metrica);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		TimeFrameVO temp;
		try {
			while (rs.next()) {
				temp = new TimeFrameVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				timeFrame.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return timeFrame;
	}
		
	public List<TimeFrameVO> getTimeFrameAno24horas(Integer metrica, String mesRelatorio) {
		List<TimeFrameVO> timeFrame = new ArrayList<TimeFrameVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT to_char(data, 'mm/yyyy') data, avg(valor_medio) valor "
				+ "FROM pmp_workload_mes "
				+ "WHERE data between ? and ? "
				+ "AND metrica_link_id = ? "
				+ "GROUP BY to_char(data, 'mm/yyyy') "
				+ "ORDER BY to_date(data, 'mm/yyyy')";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setDate(1, FormataDataUtil.formataAnoInicio(mesRelatorio));
			pstmt.setDate(2, FormataDataUtil.formataDataFim(mesRelatorio));
			pstmt.setInt(3, metrica);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		TimeFrameVO temp;
		try {
			while (rs.next()) {
				temp = new TimeFrameVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				timeFrame.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return timeFrame;
	}	

	public List<TimeFrameVO> getTimeFrame(Integer metrica, String mesRelatorio, List<String> periodo) {
		List<TimeFrameVO> timeFrame = new ArrayList<TimeFrameVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		List<String> possibleValues = periodo;
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < possibleValues.size(); i++ ) {
		    builder.append("?,");
		}
		String sql = "SELECT to_char(data, 'dd/mm/yyyy') data, avg(valor_medio) valor "
				     + "FROM pmp_workload_dia "
				     + "WHERE data between ? and ? "
				     + "AND metrica_link_id = ? "
				     + "AND hora in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ") "
				     + "GROUP BY to_char(data, 'dd/mm/yyyy') ORDER BY data";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setDate(1, FormataDataUtil.formataDataInicio(mesRelatorio));
			pstmt.setDate(2, FormataDataUtil.formataDataFim_dia(mesRelatorio));
			pstmt.setInt(3, metrica);
			int index = 4;
			for( String o : possibleValues ) {
			   pstmt.setString(  index++, o ); // or whatever it applies 
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		TimeFrameVO temp;
		try {
			while (rs.next()) {
				temp = new TimeFrameVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				timeFrame.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return timeFrame;
	}

	public List<TimeFrameVO> getTimeFrameAno(Integer metrica, String mesRelatorio, List<String> periodo) {
		List<TimeFrameVO> timeFrame = new ArrayList<TimeFrameVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		List<String> possibleValues = periodo;
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < possibleValues.size(); i++ ) {
		    builder.append("?,");
		}
		String sql = "SELECT to_char(data, 'mm/yyyy') data, avg(valor_medio) valor "
				+ "FROM pmp_workload_mes "
				+ "WHERE data between ? and ? "
				+ "AND metrica_link_id = ? "
				+ "AND hora in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ") "
				+ "GROUP BY to_char(data, 'mm/yyyy') ORDER BY to_date(data, 'mm/yyyy')";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setDate(1, FormataDataUtil.formataAnoInicio(mesRelatorio));
			pstmt.setDate(2, FormataDataUtil.formataDataFim(mesRelatorio));
			pstmt.setInt(3, metrica);
			int index = 4;
			for( String o : possibleValues ) {
			   pstmt.setString(  index++, o ); // or whatever it applies 
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		TimeFrameVO temp;
		try {
			while (rs.next()) {
				temp = new TimeFrameVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				timeFrame.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return timeFrame;
	}		

	public String getLegenda(Integer idInstancia, Integer idGraficoControle, Integer idTf) {
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select t.legenda "+
				     "  from pmp_grafico g, pmp_time_frame t "+
				     " where g.instancia_id = ? "+
				     "   and g.grafico_controle_id = ? "+
				     "   and t.time_frame_controle_id = ? "+
				     "   and g.grafico_id = t.grafico_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idInstancia);
			pstmt.setInt(2, idGraficoControle);
			pstmt.setInt(3, idTf);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		String legenda = null;
		try {
			while (rs.next()) {
				legenda = rs.getString("legenda");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return legenda;
	}

	private List<GraficoVO> getGraficos(Integer idInstancia) {
		List<GraficoVO> grafico = new ArrayList<GraficoVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select titulo, "+
					 "       descricao, "+
					 "       mes_ano, "+
					 "       tipo_calculo_id, "+
					 "       grafico_controle_id, "+
					 "		 tipo_grafico_id, "+
					 "		 capitulo "+
					 "  from pmp_grafico "+
					 " where instancia_id = ? "+
					 " order by grafico_controle_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idInstancia);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		GraficoVO temp;
		try {
			while (rs.next()) {
				temp = new GraficoVO();
				temp.setTitulo(rs.getString("titulo"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setMesAno(rs.getInt("mes_ano"));
				temp.setTipoCalculo(rs.getInt("tipo_calculo_id"));
				temp.setControleId(rs.getInt("grafico_controle_id"));
				temp.setTipoGraficoId(rs.getInt("tipo_grafico_id"));
				temp.setCapitulo(rs.getInt("capitulo"));
				grafico.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return grafico;
	}

	private List<GraficoVO> getGraficos(Integer idInstancia, Integer capitulo) {
		List<GraficoVO> grafico = new ArrayList<GraficoVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select grafico_id," +
					 "		 titulo, "+
					 "       descricao, "+
					 "       mes_ano, "+
					 "       tipo_calculo_id, "+
					 "       grafico_controle_id, "+
					 "		 tipo_grafico_id, "+
					 "		 capitulo "+
					 "  from pmp_grafico "+
					 " where instancia_id = ? "+
					 "       and capitulo = ? "+
					 " order by grafico_controle_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idInstancia);
			pstmt.setInt(2, capitulo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		GraficoVO temp;
		try {
			while (rs.next()) {
				temp = new GraficoVO();
				temp.setTitulo(rs.getString("titulo"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setMesAno(rs.getInt("mes_ano"));
				temp.setTipoCalculo(rs.getInt("tipo_calculo_id"));
				temp.setControleId(rs.getInt("grafico_controle_id"));
				temp.setTipoGraficoId(rs.getInt("tipo_grafico_id"));
				temp.setCapitulo(rs.getInt("capitulo"));
				temp.setGraficoId(rs.getInt("grafico_id"));
				grafico.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return grafico;
	}
	
	private List<InstanciaVO> getInstancias(Integer idCliente, Integer idHost) {
		List<InstanciaVO> instancia = new ArrayList<InstanciaVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select i.instancia, "+
					 "       i.instancia_id, "+
					 "		 i.descricao " +
					 "  from pmp_host_ambiente a, "+
					 "       pmp_host h, "+
					 "       pmp_instancia i "+
					 "where h.cliente_id = ? "+
					 "  and h.host_id = ? "+
					 "  and a.host_id=h.host_id "+
					 "  and i.host_ambiente_id = a.host_ambiente_id "+
					 "  and exists (select 1 from pmp_grafico g where g.instancia_id = i.instancia_id) " +
					 "order by instancia_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
			pstmt.setInt(2, idHost);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		InstanciaVO temp;
		try {
			while (rs.next()) {
				temp = new InstanciaVO();
				temp.setInstancia(rs.getString("instancia"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setId(rs.getInt("instancia_id"));
				temp.setGraficoVO(getGraficos(temp.getId()));
				instancia.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return instancia;
	}	
	
	private List<InstanciaVO> getInstancias(Integer idCliente, Integer idHost, Integer capitulo) {
		List<InstanciaVO> instancia = new ArrayList<InstanciaVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select i.instancia, "+
					 "       i.instancia_id, "+
					 "		 i.descricao " +
					 "  from pmp_host_ambiente a, "+
					 "       pmp_host h, "+
					 "       pmp_instancia i "+
					 "where h.cliente_id = ? "+
					 "  and h.host_id = ? "+
					 "  and a.host_id=h.host_id "+
					 "  and i.host_ambiente_id = a.host_ambiente_id "+
					 "  and exists (select 1 from pmp_grafico g where g.instancia_id = i.instancia_id) " +
					 "order by instancia_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
			pstmt.setInt(2, idHost);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		InstanciaVO temp;
		try {
			while (rs.next()) {
				temp = new InstanciaVO();
				temp.setInstancia(rs.getString("instancia"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setId(rs.getInt("instancia_id"));
				temp.setGraficoVO(getGraficos(temp.getId(),capitulo));
				instancia.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return instancia;
	}

	public List<HostVO> getHosts(Integer idCliente, Integer capitulo) {
		List<HostVO> host = new ArrayList<HostVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select h.nome_fantasia, "+
					 "       h.host_id, " +
					 "		 h.hostname, " +
					 "		 h.tipo_cpu, " +
					 "		 h.num_cpu, " +
					 "		 h.qtd_memoria, " +
					 "		 h.descricao "+
					 "  from pmp_host h "+
					 " where h.cliente_id= ? "+
					 " and exists (select 1 from pmp_host_ambiente a, "+
					 "                           pmp_instancia i, "+
					 "                           pmp_grafico g "+
					 "                     where a.host_id = h.host_id "+
					 "                       and i.host_ambiente_id = a.host_ambiente_id "+
					 "                       and i.instancia_id = g.instancia_id) " +
					 " order by host_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		HostVO temp;
		try {
			while (rs.next()) {
				temp = new HostVO();
				temp.setHost(rs.getString("nome_fantasia"));
				temp.setId(rs.getInt("host_id"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setHostName(rs.getString("hostname"));
				temp.setInstanciaVO(getInstancias(idCliente,temp.getId(),capitulo));
				host.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return host;
	}
	
	public List<HostVO> getHosts(Integer idCliente) {
		List<HostVO> host = new ArrayList<HostVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "select h.nome_fantasia, "+
					 "       h.host_id, " +
					 "		 h.hostname, " +
					 "		 h.tipo_cpu, " +
					 "		 h.num_cpu, " +
					 "		 h.qtd_memoria, " +
					 "		 h.descricao "+
					 "  from pmp_host h "+
					 " where h.cliente_id= ? "+
					 " and exists (select 1 from pmp_host_ambiente a, "+
					 "                           pmp_instancia i, "+
					 "                           pmp_grafico g "+
					 "                     where a.host_id = h.host_id "+
					 "                       and i.host_ambiente_id = a.host_ambiente_id "+
					 "                       and i.instancia_id = g.instancia_id) " +
					 " order by host_id";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setInt(1, idCliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		HostVO temp;
		try {
			while (rs.next()) {
				temp = new HostVO();
				temp.setHost(rs.getString("nome_fantasia"));
				temp.setId(rs.getInt("host_id"));
				temp.setDescricao(rs.getString("descricao"));
				temp.setHostName(rs.getString("hostname"));
				temp.setQuantidadeCPU(rs.getInt("num_cpu"));
				temp.setQuantidadeMemoria(rs.getInt("qtd_memoria"));
				temp.setTipoCPU(rs.getString("tipo_cpu"));
				temp.setInstanciaVO(getInstancias(idCliente,temp.getId()));
				host.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return host;
	}	
}

