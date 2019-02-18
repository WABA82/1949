package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.common.vo.AddrVO;
import user.common.vo.EeMainVO;
import user.common.vo.ErMainVO;
import user.common.vo.FindIdVO;
import user.common.vo.FindPassVO;
import user.common.vo.SetPassVO;

public class CommonDAO {
	
	private static CommonDAO C_dao;

	private CommonDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static CommonDAO getInstance() {
		if (C_dao == null) {
			C_dao = new CommonDAO();
		}// end if
		return C_dao;
	}

	private Connection getConn() throws SQLException {
		Connection con = null;

		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";// 학원에서 바꿀것!!
		String id = "kanu";
		String pass = "share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}

	public String login(String id, String pass) throws SQLException {
		String userType = "";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			String match = "SELECT USER_TYPE FROM USER_TABLE WHERE ID=? AND PASS=?";
			pstmt = con.prepareStatement(match);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userType = rs.getString("USER_TYPE");
			} // end if
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return userType;
	}// login

	public List<AddrVO> selectAddr(String dong) throws SQLException {
		List<AddrVO> list = new ArrayList<AddrVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String selectAddr = "select seq,zipcode,sido,gugun,dong,nvl(bunji,' ') bunji from zipcode where dong like '%'||?||'%'  ";
			pstmt = con.prepareStatement(selectAddr);
			pstmt.setString(1, dong);
			rs = pstmt.executeQuery();
			AddrVO av = null;
			if (rs.next()) {
				av = new AddrVO(rs.getString("seq"), rs.getString("zipcode"), rs.getString("sido"),
						rs.getString("gugun"), rs.getString("dong"), rs.getString("bunji"));
				list.add(av);
			} // end if
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally
		return list;
	}

	public String selectFindId(FindIdVO fivo) throws SQLException {
		String searchId = "";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String selectId = "select id from user_table where name=? and tel=?";

			pstmt = con.prepareStatement(selectId);

			pstmt.setString(1, fivo.getName());
			pstmt.setString(2, fivo.getTel());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				searchId = rs.getString("id");
			}

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return searchId;

	}

	/////////// 에러떠서 임시로 막아둠- 내꺼 테스트해보고 주석지우기
	/**
	 * 김건하 아이디 받기
	 * 
	 * @return
	 * @param eeId
	 * @throws SQLException
	 */
	public EeMainVO selectEeMain(String eeId) throws SQLException {
		EeMainVO emvo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//드라이버 로딩
		try {
		con=getConn();
		//쿼리문 생성
		StringBuilder selectMyInfo= new StringBuilder();
		selectMyInfo
		.append("		select ei.ee_id, ut.name, ei.img, ut.activation		") 
		.append("		from ee_info ei, user_table ut	")
		.append("		where (ee_id = id) and ei.ee_id = ?	"	);
		pstmt=con.prepareStatement(selectMyInfo.toString());
		pstmt.setString(1,eeId );
		
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			emvo = new EeMainVO(rs.getString("EE_ID"), rs.getString("NAME"), rs.getString("IMG"), rs.getString("ACTIVATION"));
		}//end if
		
		}finally {
			if( rs != null) { rs.close(); }
			if( pstmt != null) { pstmt.close(); }
			if( con != null) { con.close(); }
		}//end finally
		
		return emvo;
	}// selectEeMain
	
	public static void main(String[] args) {
		try {
			System.out.println(CommonDAO.getInstance().selectEeMain("gong1"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// main
	

	public boolean selectFindPass(FindPassVO fpvo) throws SQLException {
		int searchPass = 0;
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			String count = "select count(*) login from user_table where id=? and question_type=? and answer=?";
			pstmt = con.prepareStatement(count);

			pstmt.setString(1, fpvo.getId());
			pstmt.setString(2, fpvo.getqType());
			pstmt.setString(3, fpvo.getAnswer());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				searchPass = rs.getInt("login");

				System.out.println(searchPass);

				if (searchPass == 1) {
					flag = true;
				}
				System.out.println(flag);
			}

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return flag;
	}// selectFindPass

	public boolean updatePass(SetPassVO spvo) throws SQLException {
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();

			String updatePass = "update user_table set pass=? where id=?";
			pstmt = con.prepareStatement(updatePass);

			pstmt.setString(1, spvo.getNewPass());
			pstmt.setString(2, spvo.getId());

			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			}
		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return flag;
	
	}//updatePass
	
//	public UserInfoVO selectUserInfo(String id) throws SQLException {
//		String userInfo="";
//		
//		Connection con=null;
//		PreparedStatement stmt=null;
//		ResultSet rs=null;
//		
//		try {
//			con=getConn();
//			String selectUserInfo="select name, tel, seq, zipcode, addr1, addr2, email from user_info where id=? ";
//		}finally {
//			
//		}
//		
//		
//		
//		return userInfo;
//	}

	
	
	
	
//	public EeMainVO selectEeMain(String id) throws SQLException {
//		EeMainVO emvo=null;
//		
//		Connection con =null;
//		PreparedStatement pstmt =null;
//		ResultSet rs = null;
//		
//		try {
//		con =getConn();
//		
//		StringBuilder selectEeInfo = new StringBuilder();
//		selectEeInfo.append("select ut.id, ut.name, ei.img, ut.activation").append("from ee_info ei,USER_TABLE ut")
//			.append("where ut.id=ei.ee_id").append("and ut.id=?");
//		pstmt = con.prepareStatement(selectEeInfo.toString());
//		
//		pstmt.setString(1, id);
//		
//		rs=pstmt.executeQuery();
//			if(rs.next()) {
//				emvo = new EeMainVO(rs.getString("id"), rs.getString("name"),rs.getString("img"), rs.getString("activation"));
//			}
//		}finally {
//		if(rs!=null) {	rs.close();	}
//		if(pstmt!=null) {pstmt.close();}
//		if(con!=null) {con.close();}
//		}
//		
//		return emvo;
//	}//selectEeMain
	

//	}// updatePass

//	public UserInfoVO selectUserInfo(String id) throws SQLException {
//		String userInfo="";
//		
//		Connection con=null;
//		PreparedStatement stmt=null;
//		ResultSet rs=null;
//		
//		try {
//			con=getConn();
//			String selectUserInfo="select name, tel, seq, zipcode, addr1, addr2, email from user_info where id=? ";
//		}finally {
//
//		}
//		
//		
//		
//		return userInfo;
//	}


	public ErMainVO selectErMain(String id) throws SQLException {
		ErMainVO emv = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			StringBuilder selectErInfo = new StringBuilder();
			selectErInfo.append("select ut.id, ut.name, co.img1, ut.activation")
					.append("from company co, user_table ut").append("where ut.id=co.er_id").append("and ut.id=?");
			pstmt = con.prepareStatement(selectErInfo.toString());

			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				emv = new ErMainVO(rs.getString("id"), rs.getString("name"), rs.getString("img1"),
						rs.getString("activation"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally
		return emv;
	}// selectErMain

}// class