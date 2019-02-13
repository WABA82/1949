package admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import admin.vo.AddrVO;
import admin.vo.CoInfoVO;
import admin.vo.CoListVO;
import admin.vo.CoModifyVO;
import admin.vo.EeListVO;
import admin.vo.ErInfoVO;
import admin.vo.ErListVO;
import admin.vo.UserInfoVO;
import admin.vo.UserListVO;
import admin.vo.UserModifyVO;

public class AdminDAO {
	
	private static AdminDAO a_dao;
	
	private AdminDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static AdminDAO getInstance() {
		
		if (a_dao == null) {
			a_dao = new AdminDAO();
		}
		
		return a_dao;
	}
	
	public Connection getConn() throws SQLException {
		Connection con = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // 집 개발용
//		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl"; // 학원 개발용
		String user = "kanu";
		String password = "share";
		
		con = DriverManager.getConnection(url, user, password);
		
		return con;
	}
	
	public List<UserListVO> selectAllUser() throws SQLException {
		List<UserListVO> list = new ArrayList<UserListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			con = getConn();
			
			StringBuilder selectAllUser = new StringBuilder();
			selectAllUser
			.append(" select id, name, ssn, tel, ")
			.append(" (select sido||gugun||dong||bunji from zipcode where seq = addr_seq) addr, ")
			.append(" email, user_type, TO_CHAR(input_date, 'yyyy-mm-dd') input_date from user_table order by input_date desc ");
			
			pstmt = con.prepareStatement(selectAllUser.toString());
			
			rs = pstmt.executeQuery();
			
			UserListVO ulvo = null;
			while(rs.next()) {
				ulvo = new UserListVO(rs.getString("id"), rs.getString("name"),
						rs.getString("ssn"), rs.getString("tel"), 
						rs.getString("addr"), rs.getString("email"), 
						rs.getString("user_type"), rs.getString("input_date"));
				list.add(ulvo);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	public List<EeListVO> selectAllEe() throws SQLException {
		List<EeListVO> list = new ArrayList<EeListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			con = getConn();
			
			StringBuilder selectAllEe = new StringBuilder();
			selectAllEe
			.append(" select ee_num, img, ee_id, name, rank, loc, education, age, portfolio, gender, ext_resume, TO_CHAR(ei.input_date, 'yyyy-mm-dd') input_date ")
			.append(" from ee_info ei, user_table u ")
			.append(" where ei.ee_id = u.id order by ee_num desc  ");
			
			pstmt = con.prepareStatement(selectAllEe.toString());
			
			rs = pstmt.executeQuery();
			
			EeListVO elvo = null;
			while(rs.next()) {
				elvo = new EeListVO(rs.getString("ee_num"), rs.getString("img"),
						rs.getString("ee_id"), rs.getString("name"), rs.getString("rank"),
						rs.getString("loc"), rs.getString("education"), rs.getString("portfolio"),
						rs.getString("gender"), rs.getString("ext_resume"), rs.getString("input_date"),
						rs.getInt("age"));
				list.add(elvo);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	public List<ErListVO> selectAllEr() throws SQLException {
		List<ErListVO> list = new ArrayList<ErListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			con = getConn();
			
			StringBuilder selectAllEr = new StringBuilder();
			selectAllEr
			.append(" select er_num, subject, co_name, er_id, name, tel, rank, loc, education, hire_type, sal, TO_CHAR(ei.input_date, 'yyyy-mm-dd') input_date ")
			.append(" from company c, er_info ei, user_table u ")
			.append(" where c.co_num = ei.co_num AND c.er_id = u.id order by er_num desc ");
			
			pstmt = con.prepareStatement(selectAllEr.toString());
			
			rs = pstmt.executeQuery();
			
			ErListVO elvo = null;
			while(rs.next()) {
				elvo = new ErListVO(rs.getString("er_num"), 
						rs.getString("subject"), rs.getString("co_name"),
						rs.getString("er_id"), rs.getString("name"), rs.getString("tel"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"),
						rs.getString("hire_type"), rs.getString("input_date"), rs.getInt("sal"));
				list.add(elvo);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	
	public List<CoListVO> selectAllCo() throws SQLException {
		List<CoListVO> list = new ArrayList<CoListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			con = getConn();
			
			StringBuilder selectAllCo = new StringBuilder();
			selectAllCo
			.append(" select co_num, img1, co_name, er_id, est_date, member_num, TO_CHAR(input_date, 'yyyy-mm-dd') input_date ")
			.append(" from company order by co_num desc ");
			
			pstmt = con.prepareStatement(selectAllCo.toString());
			
			rs = pstmt.executeQuery();
			
			CoListVO clvo = null;
			while(rs.next()) {
				clvo = new CoListVO(rs.getString("co_num"), rs.getString("img1"),
						rs.getString("co_name"), rs.getString("er_id"), rs.getString("est_date"), 
						rs.getString("input_date"), rs.getInt("member_num"));
				list.add(clvo);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	public UserInfoVO selectOneUser(String id) throws SQLException {
		UserInfoVO uivo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			
			StringBuilder selectOneUser = new StringBuilder();
			
			selectOneUser
			.append(" select id, pass, name, ssn, tel, addr_seq, z.zipcode, z.sido||z.gugun||z.dong||z.bunji addr1, ")
			.append(" addr_detail, email, question_type, answer, user_type, to_char(input_date,'yyyy-MM-dd') input_date ")
			.append(" from user_table ut, zipcode z ")
			.append(" where ut.addr_seq = z.seq AND id = ? ");
			
			pstmt = con.prepareStatement(selectOneUser.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				uivo = new UserInfoVO(rs.getString("id"), rs.getString("pass"), 
						rs.getString("name"), rs.getString("ssn"), rs.getString("tel"), 
						rs.getString("addr_seq"), rs.getString("zipcode"), rs.getString("addr1"),
						rs.getString("addr_detail"), rs.getString("email"),
						rs.getString("question_type"),
						rs.getString("answer"),
						rs.getString("user_type"),
						rs.getString("input_date"));
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return uivo;
	}
	
	public boolean updateUser(UserModifyVO umvo) throws SQLException {
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			StringBuilder updateUser = new StringBuilder();
			updateUser
			.append(" update user_table ")
			.append(" set pass=?, name=?, ssn=?, tel=?, addr_seq=?,  ")
			.append(" addr_detail=?, email=?, question_type=?, user_type=? ")
			.append(" where id = ? ");
			
			pstmt = con.prepareStatement(updateUser.toString());
			
			pstmt.setString(1, umvo.getPass());
			pstmt.setString(2, umvo.getName());
			pstmt.setString(3, umvo.getSsn());
			pstmt.setString(4, umvo.getTel());
			pstmt.setString(5, umvo.getAddrSeq());
			pstmt.setString(6, umvo.getAddrDetail());
			pstmt.setString(7, umvo.getEmail());
			pstmt.setString(8, umvo.getQuestionType());
			pstmt.setString(9, umvo.getUserType());
			pstmt.setString(10, umvo.getId());
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 1) {
				flag = true;
			}
			
			
		} finally {
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return flag;
	}
	
	public boolean deleteUser(String id) throws SQLException {
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			String deleteUser = "delete from user_table where id=?";
			pstmt = con.prepareStatement(deleteUser);
			pstmt.setString(1, id);
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 1) {
				flag = true;
			}
			
		} finally {
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		
		return flag;
	}
	
	public List<AddrVO> selectAddr(String dong) throws SQLException {
		List<AddrVO> list = new ArrayList<AddrVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			
			StringBuilder selectAddr = new StringBuilder();
			selectAddr.append("select seq, zipcode, sido, gugun, dong, NVL(bunji, ' ') bunji from zipcode where dong like '%'||?||'%'");
			pstmt = con.prepareStatement(selectAddr.toString());
			pstmt.setString(1, dong);
			
			rs = pstmt.executeQuery();
			
			AddrVO avo = null;
			while(rs.next()) {
				avo = new AddrVO(rs.getString("seq"),
						rs.getString("zipcode"), rs.getString("sido"),
						rs.getString("gugun"), rs.getString("dong"),
						rs.getString("bunji"));
						
				list.add(avo);
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	public ErInfoVO selectOneEr(String erNum) throws SQLException {
		ErInfoVO eivo = null;
		
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try {
			con = getConn();
			
			StringBuilder selectListSkill = new StringBuilder();
			selectListSkill
			.append(" select skill_num ")
			.append(" from selected_skill  ")
			.append(" where er_num=? ");
			
			pstmt1 = con.prepareStatement(selectListSkill.toString());
			pstmt1.setString(1, erNum);
			
			rs1 = pstmt1.executeQuery();
			List<String> listSkill = new ArrayList<String>();
			while(rs1.next()) {
				listSkill.add(rs1.getString("skill_num"));
			}
			System.out.println(listSkill);
			
			StringBuilder selectOneEr = new StringBuilder();
			selectOneEr
			.append(" select c.img1, ut.id er_id, ut.name, ut.email,  ")
			.append(" ut.tel, to_char(ei.input_date, 'yyyy-MM-dd') input_date,  ")
			.append(" ei.subject, c.co_name, ei.education, ei.rank, ei.loc, ei.hire_type,  ")
			.append(" ei.portfolio, ei.er_desc, ei.sal  ")
			.append(" from er_info ei, company c, user_table ut  ")
			.append(" where c.co_num = ei.co_num and c.er_id = ut.id and ei.er_num=? ");
			
			pstmt2 = con.prepareStatement(selectOneEr.toString());
			pstmt2.setString(1, erNum);
			
			rs2 = pstmt1.executeQuery();
			
			if(rs2.next()) { /////////////////////////////////////////////////////// 에러발생 수정 필요 0212
				eivo = new ErInfoVO(rs2.getString("img1"), erNum, rs2.getString("er_id"), rs2.getString("name"), 
						rs2.getString("email"), rs2.getString("tel"), rs2.getString("input_date"), rs2.getString("subject"), 
						rs2.getString("co_name"), rs2.getString("education"), rs2.getString("rank"), rs2.getString("loc"), 
						rs2.getString("hire_type"), rs2.getString("portfolio"), rs2.getString("er_desc"), rs2.getInt("sal"), 
						listSkill);
			}
			
		} finally {
			if (rs2 != null) { rs2.close(); }
			if (rs1 != null) { rs1.close(); }
			if (pstmt1 != null) { pstmt1.close(); }
			if (pstmt2 != null) { pstmt2.close(); }
			if (con != null) { con.close(); }
		}
		
		return eivo;
	}
	
	public CoInfoVO selectOneCo(String coNum) throws SQLException {
		CoInfoVO civo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			StringBuilder selectOneCo = new StringBuilder();
			selectOneCo
			.append(" select er_id, co_num, img1, img2, img3, img4, co_name, to_char(est_date, 'yyyy-MM-dd') est_date, co_desc, member_num ")
			.append(" from company ")
			.append(" where co_num=? ");
			pstmt = con.prepareStatement(selectOneCo.toString());
			pstmt.setString(1, coNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				civo = new CoInfoVO(rs.getString("er_id"), rs.getString("co_num"),
						rs.getString("img1"), rs.getString("img2"),
						rs.getString("img3"), rs.getString("img4"),
						rs.getString("co_name"), rs.getString("est_date"),
						rs.getString("co_desc"), rs.getInt("member_num"));
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return civo;
	}
	
	public boolean updateCo(CoModifyVO cmvo) throws SQLException {
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			StringBuilder updateCo = new StringBuilder();
			
			updateCo
			.append(" update company ")
			.append(" set co_name=?, est_date=?, co_desc=?, img1=?, img2=?, img3=?, img4=?, member_num=? ")
			.append(" where co_num=?  ");
			pstmt = con.prepareStatement(updateCo.toString());
			pstmt.setString(1, cmvo.getCoName());
			pstmt.setString(2, cmvo.getEstDate());
			pstmt.setString(3, cmvo.getCoDesc());
			pstmt.setString(4, cmvo.getImg1());
			pstmt.setString(5, cmvo.getImg2());
			pstmt.setString(6, cmvo.getImg3());
			pstmt.setString(7, cmvo.getImg4());
			pstmt.setInt(8, cmvo.getMemberNum());
			pstmt.setString(9, cmvo.getCoNum());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) {
				flag = true;
			}
			
		} finally {
			if(pstmt != null) { pstmt.close(); }
			if(con != null) { con.close(); }
		}
		
		return flag;
	}
}
