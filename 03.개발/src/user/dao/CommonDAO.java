package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.common.view.LoginView;
import user.common.vo.EeMainVO;
import user.common.vo.FindIdVO;
import user.common.vo.FindPassVO;
import user.common.vo.SetPassVO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

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
		
		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";//학원에서 바꿀것!!
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}
	
	public String login(String id, String pass)throws SQLException{
		String userType ="";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		
		con=getConn();
		
		String match = "SELECT ID, USER_TYPE FROM USER_TABLE WHERE ID=? AND PASS=?";
		pstmt = con.prepareStatement(match);
		pstmt.setString(1, id);
		pstmt.setString(2, pass);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			userType = rs.getString("USER_TYPE");
		}//end if
		return userType;
	}//login
	
	public String selectFindId(FindIdVO fivo)throws SQLException {
		String searchId="";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=getConn();
			String selectId="select id from user_table where name=? and tel=?";

			pstmt=con.prepareStatement(selectId);
			
			pstmt.setString(1, fivo.getName());
			pstmt.setString(2, fivo.getTel());

			rs=pstmt.executeQuery();
			if(rs.next()) {
				searchId=rs.getString("id");
			}

		}finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		return searchId;

	}//selectFindId

	public boolean selectFindPass(FindPassVO fpvo) throws SQLException {
		String searchPass ="";
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String count = "select count(*) from user_table where id = ? and question_type = ? and answer = ? ";
			pstmt = con.prepareStatement(count);
			
			pstmt.setString(1, fpvo.getId());
			pstmt.setString(2, fpvo.getqType());
			pstmt.setString(3, fpvo.getAnswer());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				searchPass = rs.getString("count(*)");
			}//end while
		}finally {
			//6.
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		if(searchPass.equals("0")) {
			flag = true;
		}
		return flag;
	}//selectFindPass
	
	public boolean updatePass(SetPassVO spvo) throws SQLException {
		boolean flag=false;
	
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=getConn();
			
			String updatePass="update user_table set pass=? where id=?";
			pstmt=con.prepareStatement(updatePass);
			
			pstmt.setString(1, spvo.getNewPass());
			pstmt.setString(2, spvo.getId());
			
			int cnt=pstmt.executeUpdate();
		}finally {
			
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		
		
		
		
		
		return flag;
	}
	
	public EeMainVO selectEeMain(String id) throws SQLException {
		EeMainVO emvo=null;
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		con =getConn();
		
		String selectEeInfo = "SELECT EE_ID, NAME, IMG, ACTIVATION FROM EE_INFO WHERE ID=?";
		pstmt = con.prepareStatement(selectEeInfo);
		
		pstmt.setString(1, id);
		
		rs=pstmt.executeQuery();
		//String eeId, name, img, activation;
		if(rs.next()) {
			emvo = new EeMainVO(id, rs.getString("name"),rs.getString() img, activation)
		}
		
		return emvo;
	}//selectEeMain
	
	
}