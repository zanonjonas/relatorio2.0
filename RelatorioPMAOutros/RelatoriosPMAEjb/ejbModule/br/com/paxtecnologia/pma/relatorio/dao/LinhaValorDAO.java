package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.util.FormataDataUtil;
import br.com.paxtecnologia.pma.relatorio.vo.DBSizeTabelaVO;
import br.com.paxtecnologia.pma.relatorio.vo2.LinhaValorVO;

public class LinhaValorDAO {

	private DataSourcePMA connection;
	
	
	public List<LinhaValorVO> getLinhaValorTimeFrameHrMensal(Integer linhaId, Integer graficoId, String mesRelatorio, List<Integer> listaHoras) {
		List<LinhaValorVO> listaLinhaValor = new ArrayList<LinhaValorVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		List<Integer> possibleValues = listaHoras;
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < possibleValues.size(); i++ ) {
		    builder.append("?,");
		}
		
		String sql = "select to_char(data,'dd/mm/yyyy') data " +
				           ",round(avg(valor_consolidado)) valor " +
				       "from rel_grafico_dados_dia " +
				      "where linha_id = ? " +
				        "and grafico_id = ? " +
				        "and data between ? and ? " +
				        "AND hora in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ") " +
				        "group by to_char(data,'dd/mm/yyyy')";
		
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, linhaId);
			pstmt.setInt(2, graficoId);
			pstmt.setDate(3, FormataDataUtil.formataDataInicio(mesRelatorio));
			pstmt.setDate(4, FormataDataUtil.formataDataFim_dia(mesRelatorio));
			
			int index = 5;
			for( Integer o : possibleValues ) {
			   pstmt.setInt(  index++, o ); // or whatever it applies 
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		LinhaValorVO temp;
		try {
			while (rs.next()) {
				temp = new LinhaValorVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				listaLinhaValor.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaLinhaValor;
	}
	
	public List<LinhaValorVO> getLinhaValorTimeFrameHrAnual(Integer linhaId, Integer graficoId, String mesRelatorio, List<Integer> listaHoras) {
		List<LinhaValorVO> listaLinhaValor = new ArrayList<LinhaValorVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		List<Integer> possibleValues = listaHoras;
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < possibleValues.size(); i++ ) {
		    builder.append("?,");
		}
		
		String sql = "select to_char(data,'mm/yyyy') data " +
				           ",round(avg(valor_consolidado)) valor " +
				       "from rel_grafico_dados_dia " +
				      "where linha_id = ? " +
				        "and grafico_id = ? " +
				        "and trunc(data,'MM') between trunc(?,'MM') and trunc(?,'MM') " +
				        "AND hora in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ") " +
				        "group by to_char(data,'mm/yyyy')";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, linhaId);
			pstmt.setInt(2, graficoId);
			pstmt.setDate(3, FormataDataUtil.formataAnoInicio(mesRelatorio));
			pstmt.setDate(4, FormataDataUtil.formataDataFim(mesRelatorio));
			
			int index = 5;
			for( Integer o : possibleValues ) {
			   pstmt.setInt(  index++, o ); // or whatever it applies 
			   
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		LinhaValorVO temp;
		try {
			while (rs.next()) {
				temp = new LinhaValorVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				listaLinhaValor.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaLinhaValor;
	}
		
	public List<LinhaValorVO> getLinhaValorDiaMensal(Integer linhaId, Integer graficoId, String mesRelatorio) {
		List<LinhaValorVO> listaLinhaValor = new ArrayList<LinhaValorVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		
		String sql = "select to_char(data,'dd/mm/yyyy') data " +
				           ",round(avg(valor_consolidado)) valor " +
				       "from rel_grafico_dados_mes " +
				      "where linha_id = ? " +
				        "and grafico_id = ? " +
				        "and data between ? and ? " +
				        "group by to_char(data,'dd/mm/yyyy')";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, linhaId);
			pstmt.setInt(2, graficoId);
			pstmt.setDate(3, FormataDataUtil.formataDataInicio(mesRelatorio));
			pstmt.setDate(4, FormataDataUtil.formataDataFim_dia(mesRelatorio));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		LinhaValorVO temp;
		try {
			while (rs.next()) {
				temp = new LinhaValorVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				listaLinhaValor.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaLinhaValor;
	}
	
	public List<LinhaValorVO> getLinhaValorMesAnual(Integer linhaId, Integer graficoId, String mesRelatorio) {
		List<LinhaValorVO> listaLinhaValor = new ArrayList<LinhaValorVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		
		String sql = "select to_char(data,'mm/yyyy') data " +
				           ",round(avg(valor_consolidado)) valor " +
				       "from rel_grafico_dados_ano " +
				      "where linha_id = ? " +
				        "and grafico_id = ? " +
				        "and trunc(data,'MM') between trunc(?,'MM') and trunc(?,'MM') " +
				        "group by to_char(data,'mm/yyyy')";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, linhaId);
			pstmt.setInt(2, graficoId);
			pstmt.setDate(3, FormataDataUtil.formataAnoInicio(mesRelatorio));
			pstmt.setDate(4, FormataDataUtil.formataDataFim(mesRelatorio));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		LinhaValorVO temp;
		try {
			while (rs.next()) {
				temp = new LinhaValorVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				listaLinhaValor.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaLinhaValor;
	}
	
	public List<LinhaValorVO> getLinhaValorUdmAnual(Integer linhaId, Integer graficoId, String mesRelatorio) {
		List<LinhaValorVO> listaLinhaValor = new ArrayList<LinhaValorVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		
		String sql = "select to_char(data,'mm/yyyy') data " +
				           ",round(avg(valor_consolidado)) valor " +
				       "from rel_grafico_dados_udm " +
				      "where linha_id = ? " +
				        "and grafico_id = ? " +
				        "and trunc(data,'MM') between trunc(?,'MM') and trunc(?,'MM') " +
				        "group by to_char(data,'mm/yyyy')";
		
		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, linhaId);
			pstmt.setInt(2, graficoId);
			pstmt.setDate(3, FormataDataUtil.formataAnoInicio(mesRelatorio));
			pstmt.setDate(4, FormataDataUtil.formataDataFim(mesRelatorio));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		LinhaValorVO temp;
		try {
			while (rs.next()) {
				temp = new LinhaValorVO();
				temp.setData(rs.getString("data"));
				temp.setValor(rs.getDouble("valor"));
				listaLinhaValor.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaLinhaValor;
	}

	
	public List<DBSizeTabelaVO> getTabelaDBsize(Integer graficoId, String mesRelatorio) {
		List<DBSizeTabelaVO> listaDbSizeTabela = new ArrayList<DBSizeTabelaVO>();
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		
		String sql = "select antes.data antes_data " +
				"      ,antes.valor antes_valor " +
				"      ,depois.data depois_data " +
				"      ,depois.valor depois_valor " +
				"      ,depois.valor - antes.valor as variacaoabs " +
				"      ,round((depois.valor - antes.valor)*100 /antes.valor,1) varicaopct " +
				"      ,round(depois.valor*100/totaldisp.valor,1)  usadopct " +
				"      ,totaldisp.valor totaldisp_valor " +
				"from " +
				"(select to_char(a.data,'mm/yyyy') data   " +
				",a.valor_consolidado valor   " +
				"from rel_grafico_dados_udm a, " +
				"      rel_grafico_linha b " +
				"where a.grafico_id= ? " +
				"      and a.linha_id=b.linha_id " +
				"      and b.ordem = 1 " +
				"and trunc(a.data,'MM') = add_months(trunc(to_date(?,'yyyy-mm-dd'),'MM'),-1)) antes, " +
				"(select to_char(a.data,'mm/yyyy') data   " +
				",a.valor_consolidado valor   " +
				"from rel_grafico_dados_udm a, " +
				"      rel_grafico_linha b " +
				"where a.grafico_id= ? " +
				"      and a.linha_id=b.linha_id " +
				"      and b.ordem = 1 " +
				"and trunc(a.data,'MM') = trunc(to_date(?,'yyyy-mm-dd'),'MM')) depois, " +
				"(select to_char(a.data,'mm/yyyy') data   " +
				",a.valor_consolidado valor   " +
				"from rel_grafico_dados_udm a, " +
				"      rel_grafico_linha b " +
				"where a.grafico_id= ? " +
				"      and a.linha_id=b.linha_id " +
				"      and b.ordem=2 " +
				"and trunc(a.data,'MM') = trunc(to_date(?,'yyyy-mm-dd'),'MM')) totaldisp";

		pstmt = connection.getPreparedStatement(sql);
		try {
			
			pstmt.setInt(1, graficoId);
			pstmt.setString(2, mesRelatorio);
			pstmt.setInt(3, graficoId);
			pstmt.setString(4, mesRelatorio);
			pstmt.setInt(5, graficoId);
			pstmt.setString(6, mesRelatorio);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		DBSizeTabelaVO temp;
		try {
			while (rs.next()) {
				temp = new DBSizeTabelaVO();
				temp.setMesA(rs.getString("antes_data"));
				temp.setValorA(rs.getString("antes_valor"));
				temp.setMesD(rs.getString("depois_data"));
				temp.setValorD(rs.getString("depois_valor"));
				temp.setVarAbs(rs.getString("variacaoabs"));
				temp.setVarPct(rs.getDouble("varicaopct"));
				temp.setUtlTotalPct(rs.getDouble("usadopct"));
				temp.setEspTotal(rs.getString("totaldisp_valor"));
				
				listaDbSizeTabela.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return listaDbSizeTabela;
	}

	
}
