package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.vo.EeHiringVO;
import user.er.vo.ErListVO;

public class ErDAO {
	private static ErDAO Er_dao;
	public ErDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}//EeDAO
	
	public static ErDAO getInstance() {
		if(Er_dao==null) {
			Er_dao= new ErDAO();
		}//end if
		return Er_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		
		Connection con = null;
		
		String url="jdbc:oracle:thin:@211.63.89.144:1521:orcl";
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}//getConns
	
	public List<ErListVO> selectErList(String erId) throws SQLException {
		List<ErListVO> list = new ArrayList<ErListVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectErList = new StringBuilder();
			selectErList
			.append(" select ei.er_num,ei.subject,ei.rank,ei.loc,ei.education,ei.hire_type,to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date ")
			.append(" from er_info ei, company c ")
			.append(" where (ei.co_num = c.co_num)and(c.er_id=?) ");
			
			pstmt = con.prepareStatement(selectErList.toString());
			pstmt.setString(1, erId);
			rs = pstmt.executeQuery();
			ErListVO elvo = null;
			while (rs.next()) {
				elvo = new ErListVO(rs.getString("er_num"),rs.getString("subject"), 
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), 
						rs.getString("hire_type"),rs.getString("input_date"));
				list.add(elvo);
				System.out.println(list);
				
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
		return list;
	}
	
	public static void main(String[] args) {
		ErDAO er_dao = new ErDAO();
		try {
			er_dao.selectErList("lucky012");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
