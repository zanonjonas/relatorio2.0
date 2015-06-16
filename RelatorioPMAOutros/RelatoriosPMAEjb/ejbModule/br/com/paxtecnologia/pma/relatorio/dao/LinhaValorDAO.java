package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.paxtecnologia.pma.relatorio.util.FormataDataUtil;
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


	
}
