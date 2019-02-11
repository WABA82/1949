package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.common.view.FindIdView;
import user.common.view.LoginView;
import user.common.vo.FindIdVO;
import user.common.vo.UserInsertVO;

public class CommonDAO {
	private static CommonDAO C_dao;
	LoginView lv=new LoginView();
	
	private CommonDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static CommonDAO getInstance() {
		if(C_dao==null) {
			C_dao = new CommonDAO();
		}
		return C_dao;
	}
	
	private Connection getConn() throws SQLException{
		Connection con =null;
		
		String url = "jdbc:oracle:thin:@localhost:1522:orcl";
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}

	
	
	
	
	
	
	public String selectFindId(FindIdVO fivo)throws SQLException {
		FindIdView fiv=new FindIdView(lv);
		String userId="";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=getConn();
			String login="select name from user_table where name=? and tel=?";

			pstmt=con.prepareStatement(login);
			
			pstmt.setString(1, fivo.getName());
			pstmt.setString(2, fivo.getTel());

			rs=pstmt.executeQuery();
			if(rs.next()) {
				userId=rs.getString("id");
			}

		}finally {
		//6.
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		return userId;

	}
	

	
	public boolean searchId(String id) throws SQLException {
		String searchId ="";
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String selectId = "select id from simple_login where id = ?	";
			pstmt = con.prepareStatement(selectId);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				searchId = rs.getString("id");
			}//end while
		}finally {
			//6.
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		if(searchId.equals("")) {
			flag = true;
		}
		return flag;
	}
}