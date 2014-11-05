package br.com.paxtecnologia.pma.relatorio.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.paxtecnologia.pma.relatorio.vo.UsuarioVO;


public class UsuarioDAO {
	private DataSourcePMA connection;
	private UsuarioVO usuario;

	public UsuarioVO getUsuario(String username) {
		
		try{

			usuario = new UsuarioVO();
			
			usuario.setUsername(getPassword(username));
			usuario.setUserRole(getRole(username));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuario;
		
	}
	
	public String getPassword(String username) {
		String retorno = null;
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT password FROM pmp_users WHERE name = ?";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setString(1, username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		try {
			while (rs.next()) {

				retorno = rs.getString("password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}
	
	public String getRole(String username) {
		String retorno = null;
		connection = new DataSourcePMA();
		PreparedStatement pstmt;
		String sql = "SELECT role_name FROM pmp_user_roles WHERE user_name = ?";
		pstmt = connection.getPreparedStatement(sql);
		try {
			pstmt.setString(1, username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = connection.executaQuery(pstmt);
		try {
			while (rs.next()) {

				retorno = rs.getString("role_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.closeConnection(pstmt);
		return retorno;
	}

}
