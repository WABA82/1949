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
import admin.vo.EeInfoVO;
import admin.vo.EeListVO;
import admin.vo.ErInfoVO;
import admin.vo.ErListVO;
import admin.vo.ErModifyVO;
import admin.vo.UserInfoVO;
import admin.vo.UserListVO;
import admin.vo.UserModifyVO;
import user.ee.vo.EeModifyVO;

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
		
		con.setAutoCommit(true);
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
			
			rs2 = pstmt2.executeQuery();
			
			if(rs2.next()) {
				eivo = new ErInfoVO(rs2.getString("img1"), erNum, rs2.getString("er_id"),
						rs2.getString("name"), rs2.getString("email"), rs2.getString("tel"),
						rs2.getString("input_date"), rs2.getString("subject"), rs2.getString("co_name"),
						rs2.getString("education"), rs2.getString("rank"), rs2.getString("loc"),
						rs2.getString("hire_type"), rs2.getString("portfolio"), rs2.getString("er_desc"),
						rs2.getInt("sal"), listSkill);
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

	// updateEr 트랜잭션 용 
	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private Connection con;
	
	public boolean ueTransaction1(Connection con, ErModifyVO emvo) throws SQLException {
		boolean flag = false;
		System.out.println("t1시작");
		
		StringBuilder updateEr = new StringBuilder();
		updateEr
		.append(" update er_info ")
		.append(" set subject=?, education=?, rank=?, loc=?, hire_type=?, portfolio=?, er_desc=?, sal=? ")
		.append(" where er_num = ? ");
		
		pstmt1 = con.prepareStatement(updateEr.toString());
		pstmt1.setString(1, emvo.getSubject());
		pstmt1.setString(2, emvo.getEducation());
		pstmt1.setString(3, emvo.getRank());
		pstmt1.setString(4, emvo.getLoc());
		pstmt1.setString(5, emvo.getHireType());
		System.out.println("--ht"+emvo.getHireType());
		pstmt1.setString(6, emvo.getPortfolio());
		pstmt1.setString(7, emvo.getErDesc());
		pstmt1.setInt(8, emvo.getSal());
		pstmt1.setString(9, emvo.getErNum());
		
		int cnt1 = pstmt1.executeUpdate();
		
		if(cnt1 == 1) {
			flag = true;
		}
		System.out.println("t1반환");
		return flag;
	}
	
	public boolean ueTransaction2(Connection con, ErModifyVO emvo, int preSkillNum) throws SQLException {
		boolean flag = false;
		System.out.println("t2시작");
		
		StringBuilder deleteSkill = new StringBuilder();
		deleteSkill
		.append(" delete from selected_skill ")
		.append(" where er_num = ? ");
		
		pstmt2 = con.prepareStatement(deleteSkill.toString());
		pstmt2.setString(1, emvo.getErNum());
		
		int deleteCnt = pstmt2.executeUpdate();
		
		if(deleteCnt == preSkillNum) { // 기존 선택한 스킬 수와 삭제된 스킬 수가 같다면 true
			flag = true;
		}
		
		System.out.println("t2반환");
		return flag;
	}
	
	public void ueTransaction3(Connection con, ErModifyVO emvo) throws SQLException {
		System.out.println("t3시작");
		
		StringBuilder insertSkill = new StringBuilder();
		insertSkill
		.append(" insert into selected_skill(er_num, skill_num) ")
		.append(" values (?,?) ");
		
		pstmt3 = con.prepareStatement(insertSkill.toString());
		
		List<String> listSkill = emvo.getListSkill();
		for(int i=0; i<emvo.getListSkill().size(); i++) {
			pstmt3.setString(1, emvo.getErNum());
			pstmt3.setString(2, listSkill.get(i));
			
			pstmt3.executeUpdate();
		}
		
		System.out.println("t3반환");
	}
	
	public boolean updateEr(ErModifyVO emvo, int preSkillNum) {
		boolean flag = false;

		try {
			con = getConn();
			con.setAutoCommit(false);
			
			try {
				boolean t1 = ueTransaction1(con, emvo); // 새로운 정보로 er_info update
				System.out.println("t1끝");
				boolean t2 = ueTransaction2(con, emvo, preSkillNum); // 기존 selected_skill 삭제
				System.out.println("t2끝");
				ueTransaction3(con, emvo); // 새롭게 선택된 Skill들 insert
				System.out.println("t3끝");
				
				if (t1 && t2) {
					flag = true;
					con.commit();
				} else {
					con.rollback();
				}
				
			} finally {
				closeUpdateEr();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public void closeUpdateEr() throws SQLException {
		if (pstmt3 != null) { pstmt3.close(); }
		if (pstmt2 != null) { pstmt2.close(); }
		if (pstmt1 != null) { pstmt1.close(); }
		if (con != null) { con.close(); }
	}
	
	public boolean dErTransaction1(String erNum) throws SQLException {
		boolean flag = false;
		
		StringBuilder deleteEr = new StringBuilder();
		deleteEr
		.append(" delete from er_info ")
		.append(" where er_num=? ");
		
		pstmt1 = con.prepareStatement(deleteEr.toString());
		pstmt1.setString(1, erNum);
		
		int cnt = pstmt1.executeUpdate();
		
		if(cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean dErTransaction2(String id) throws SQLException {
		boolean flag = false;
		
		StringBuilder updateEe = new StringBuilder();
		updateEe
		.append(" update user_table ")
		.append(" set activation = 'N' ")
		.append(" where id=? ");
		
		pstmt2 = con.prepareStatement(updateEe.toString());
		pstmt2.setString(1, id);
		
		int cnt = pstmt2.executeUpdate();
		
		if(cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean deleteEr(ErInfoVO eivo)  { 
		boolean flag = false;
		
		con = null;
		
		try {
			try {
				con = getConn();
				con.setAutoCommit(false);
				
				if(dErTransaction1(eivo.getErNum()) && dErTransaction2(eivo.getErId())){
					con.commit();
					flag = true;
				} else {
					con.rollback();
				}
				
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		} finally {
			try {
				closeDeleteEr();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public void closeDeleteEr() throws SQLException {
		if (pstmt2 != null) { pstmt2.close(); }
		if (pstmt1 != null) { pstmt1.close(); }
		if (con != null) { con.close(); }
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
	
	public EeInfoVO selectOneEe(String eeNum) throws SQLException {
		EeInfoVO eivo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			StringBuilder selectOneEe = new StringBuilder();
			selectOneEe
			.append(" select ee_num, img, id, ut.name, rank, loc, ")
			.append(" education, portfolio, gender, to_char(ei.input_date, 'yyyy-MM-dd') input_date, ")
			.append(" ext_resume, ut.age ")
			.append(" from ee_info ei, user_table ut ")
			.append(" where ei.ee_id = ut.id and ei.ee_num=? ");
			pstmt = con.prepareStatement(selectOneEe.toString());
			pstmt.setString(1, eeNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eivo = new EeInfoVO(rs.getString("ee_num"), rs.getString("img"),
						rs.getString("id"), rs.getString("name"), rs.getString("rank"),
						rs.getString("loc"), rs.getString("education"), rs.getString("portfolio"),
						rs.getString("gender"), rs.getString("input_date"), rs.getString("ext_resume"),
						rs.getInt("age"));
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return eivo;
	}
	
	public boolean updateEe(EeModifyVO emvo) throws SQLException {
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			
			StringBuilder updateEe = new StringBuilder();
			
			updateEe
			.append(" update ee_info ")
			.append(" set img=?, rank=?, loc=?, education=?, portfolio=?, ext_resume=? ")
			.append(" where ee_num=? ");
			
			pstmt = con.prepareStatement(updateEe.toString());
			pstmt.setString(1, emvo.getImg());
			pstmt.setString(2, emvo.getRank());
			pstmt.setString(3, emvo.getLoc());
			pstmt.setString(4, emvo.getEducation());
			pstmt.setString(5, emvo.getPortfolio());
			pstmt.setString(6, emvo.getExtResume());
			pstmt.setString(7, emvo.getEeNum());
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 1) {
				flag = true;
			}
			
		} finally {
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		System.out.println("--updateEe flag : " +flag);
		return flag;
	}
	
	public boolean dEeTransaction1(String eeNum) throws SQLException {
		boolean flag = false;
		
		StringBuilder deleteEe = new StringBuilder();
		deleteEe
		.append(" delete from ee_info ")
		.append(" where ee_num=? ");
		
		pstmt1 = con.prepareStatement(deleteEe.toString());
		pstmt1.setString(1, eeNum);
		
		int cnt = pstmt1.executeUpdate();
		
		if(cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean dEeTransaction2(String id) throws SQLException {
		boolean flag = false;
		
		StringBuilder updateEe = new StringBuilder();
		updateEe
		.append(" update user_table ")
		.append(" set activation = 'N' ")
		.append(" where id=? ");
		
		pstmt1 = con.prepareStatement(updateEe.toString());
		pstmt1.setString(1, id);
		
		int cnt = pstmt1.executeUpdate();
		
		if(cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean deleteEe(EeInfoVO eivo){
		boolean flag = false;
		
		con = null;
		
		try {
			
			try {
				con = getConn();
				con.setAutoCommit(false);
				
				if (dEeTransaction1(eivo.getEeNum()) && dEeTransaction2(eivo.getId())) {
					con.commit();
					flag = true;
				} else {
					con.rollback();
				}
			
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		} finally {
			try {
				closeDeleteEe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public void closeDeleteEe() throws SQLException {
		if (pstmt2 != null) { pstmt2.close(); }
		if (pstmt1 != null) { pstmt1.close(); }
		if (con != null) { con.close(); }
	}
}
