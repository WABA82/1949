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
		
		String url = "jdbc:oracle:thin:@211.63.89.144:1522:orcl";//학원에서 바꿀것!!
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}
	
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

	}
	

	/**
	 * 	김건하 아이디 받기
	 * @return
	 * @param eeId
	 * @throws SQLException 
	 */
	public EeMainVO selectEeMain(String ee_id) throws SQLException {
		EeMainVO emvo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//드라이버 로딩
		try {
		con=getConn();
		//쿼리문 생성
//		private String eeId, name, img, activation;
		StringBuilder  selectMyInfo= new StringBuilder();
		selectMyInfo
		.append("		select ee_id, name, img, activation	") 
		.append("		from user_table ut, ee_info ei ")
		.append("		where (ut.id=ei.ee_id) and ut.id=? ");
		
		pstmt=con.prepareStatement(selectMyInfo.toString());
		
		pstmt.setString(1, ee_id);
		
		rs=pstmt.executeQuery();
		if(rs.next()) {
//			private String eeId, name, img, activation;
			emvo=new EeMainVO(rs.getString("eeId"),rs.getString("name"), rs.getString("img"), rs.getString("activation") );
		}
		}finally {
			if( rs != null) { rs.close(); }
			if( pstmt != null) { pstmt.close(); }
			if( con != null) { con.close(); }
		}//end finally
		return emvo;
	};
	
	public static void main(String[] args) {
		
	}
	
	
	public boolean selectFindPass(FindPassVO fpvo) throws SQLException {
		String searchPass ="";
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String selectPass = "select pass from user_table where id = ? and question_type = ? and answer = ? ";
			pstmt = con.prepareStatement(selectPass);
			
			pstmt.setString(1, fpvo.getId());
			pstmt.setString(2, fpvo.getqType());
			pstmt.setString(3, fpvo.getAnswer());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				searchPass = rs.getString("pass");
			}//end while
		}finally {
			//6.
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		if(searchPass.equals("")) {
			flag = true;
		}
		return flag;
	}
}