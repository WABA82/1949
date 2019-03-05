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
import admin.vo.EeDeleteVO;
import admin.vo.EeInfoVO;
import admin.vo.EeListVO;
import admin.vo.EeModifyVO;
import admin.vo.ErInfoVO;
import admin.vo.ErListVO;
import admin.vo.ErModifyVO;
import admin.vo.UserInfoVO;
import admin.vo.UserListVO;
import admin.vo.UserModifyVO;

/**
 * @author owner
 *
 */
public class AdminDAO {
	
	private static AdminDAO a_dao;

	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private Connection con;
	
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
		
		//String url = "jdbc:oracle:thin:@`host:1521:orcl"; // 집 개발용
		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl"; // 학원 개발용
		String user = "kanu";
		String password = "share";
		
		con = DriverManager.getConnection(url, user, password);
		
		con.setAutoCommit(true);
		return con;
	}
	
	/**
	 * 모든 유저 정보를 조회하는 메소드 
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 모든 구직자 기본정보를 조회하는 메소드 
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 기업 사용자의 모든 구인글 정보를 조회하는 메소드
	 * @return
	 * @throws SQLException
	 */
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
	
	
	/**
	 * 모든 기업 정보를 조회하는 메소드
	 * @return
	 * @throws SQLException
	 */
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
	
	
	/**
	 * 한 유저의 정보를 가져오는 메소드
	 * @param id
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 한 유저의 정보를 업데이트하는 메소드
	 * @param umvo
	 * @return
	 * @throws SQLException
	 */
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
			.append(" addr_detail=?, email=?, question_type=?, user_type=?, gender=?, age=? ")
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
			pstmt.setString(10, umvo.getGender());
			pstmt.setInt(11, umvo.getAge());
			pstmt.setString(12, umvo.getId());
			
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
	
	/**
	 * 한 유저의 이력서파일명을 조회하는 메소드
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String selectEeExt(String id) throws SQLException {
		String extResume = "";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			
			String selectEeExt = "select NVL(ext_resume, ' ') ext_resume from ee_info where ee_id=? ";
			
			pstmt = con.prepareStatement(selectEeExt);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				extResume = rs.getString("ext_resume");
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return extResume;
	}
	
	/**
	 * 입력받은 유저 아이디와 유저타입에 등록된 이미지명을 반환하는 메소드
	 * @param id
	 * @param userType
	 * @return
	 * @throws SQLException
	 */
	public List<String> selectUserImgs(String id, String userType) throws SQLException {
		List<String> list = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			
			StringBuilder selectUserImgs = new StringBuilder();
			
			if (userType.equals("일반")) {
				selectUserImgs.append(" select img from ee_info where ee_id=? ");
			} else if (userType.equals("기업")) {
				selectUserImgs
				.append(" select img1, img2, img3, img4 ")
				.append(" from company ")
				.append(" where er_id=? ");
			}
			
			pstmt = con.prepareStatement(selectUserImgs.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			String img1 = "";
			String img2 = "";
			String img3 = "";
			String img4 = "";
			while(rs.next()) {
				if (userType.equals("일반")) {
					img1 = rs.getString("img");
					
					if (!checkDefaultImg(img1)) { // no_img인지 체크
						list.add(img1);
					}
				} else if (userType.equals("기업")) {
					img1 = rs.getString("img1");
					img2 = rs.getString("img2");
					img3 = rs.getString("img3");
					img4 = rs.getString("img4");
					
					if (!checkDefaultImg(img1)) {
						list.add(img1);
					}
					if (!checkDefaultImg(img2)) {
						list.add(img2);
					}
					if (!checkDefaultImg(img3)) {
						list.add(img3);
					}
					if (!checkDefaultImg(img4)) {
						list.add(img4);
					}
				}
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return list;
	}
	
	/**
	 * no_img인지 체크하는 메소드
	 * @param imgName
	 * @return
	 */
	public boolean checkDefaultImg(String imgName) {
		boolean flag = false;
		
		if (imgName == null) {
			return flag;
		}

		String[] defaultImgName = { "no_co_img1.png", "no_co_img2.png",
				"no_co_img3.png", "no_co_img4.png", "no_ee_img.png" };
		
		for(String name : defaultImgName) {
			if(imgName.equals(name)) { // 입력된 이미지명이 no_img명이면 true반환
				flag = true;
			}
		}
		
		return flag;
	}
	
	/**
	 * 주소를 조회하는 메소드
	 * @param dong
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 한 구인글 정보를 가져오는 메소드
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
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

	
	/**
	 * 구인글 정보를 수정하는 메소드
	 * Transaction처리를 수행
	 * @param emvo
	 * @param preSkillNum
	 * @return
	 */
	public boolean updateEr(ErModifyVO emvo, int preSkillNum) {
		boolean flag = false;

		try {
			con = getConn();
			con.setAutoCommit(false);
			
			try {
				boolean t1 = ueTransaction1(con, emvo); // 새로운 정보로 er_info update
				boolean t2 = ueTransaction2(con, emvo.getErNum(), preSkillNum); // 기존 selected_skill 삭제
				ueTransaction3(con, emvo); // 새롭게 선택된 Skill들 insert
				
				if (t1 && t2) {
					flag = true;
					con.commit();
				} else {
					con.rollback();
				}
				
			} finally {
				closeAll();
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
	
	/**
	 * updateEr 트랜잭션 - 1
	 * - er_info 테이블 정보를 업데이트
	 * @param con
	 * @param emvo
	 * @return
	 * @throws SQLException
	 */
	public boolean ueTransaction1(Connection con, ErModifyVO emvo) throws SQLException {
		boolean flag = false;
		
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
		pstmt1.setString(6, emvo.getPortfolio());
		pstmt1.setString(7, emvo.getErDesc());
		pstmt1.setInt(8, emvo.getSal());
		pstmt1.setString(9, emvo.getErNum());
		
		int cnt1 = pstmt1.executeUpdate();
		
		if(cnt1 == 1) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * updateEr 트랜잭션 - 2
	 * - selected_skill 테이블에서 기존 정보를 삭제
	 * @param con
	 * @param emvo
	 * @param preSkillNum
	 * @return
	 * @throws SQLException
	 */
	public boolean ueTransaction2(Connection con, String erNum, int preSkillNum) throws SQLException {
		boolean flag = false;
		
		StringBuilder deleteSkill = new StringBuilder();
		deleteSkill
		.append(" delete from selected_skill ")
		.append(" where er_num = ? ");
		
		pstmt2 = con.prepareStatement(deleteSkill.toString());
		pstmt2.setString(1, erNum);
		
		int deleteCnt = pstmt2.executeUpdate();
		
		if(deleteCnt == preSkillNum) { // 기존 선택한 스킬 수와 삭제된 스킬 수가 같다면 true
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * updateEr 트랜잭션 - 3
	 * selected_skill 테이블에 새로운 스킬 정보를 추가
	 * @param con
	 * @param emvo
	 * @throws SQLException
	 */
	public void ueTransaction3(Connection con, ErModifyVO emvo) throws SQLException {
		
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
	}
	

	/**
	 * 구인글 정보를 삭제하는 메소드
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEr(String erNum) throws SQLException  { 
		boolean flag = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();

			StringBuilder deleteEr = new StringBuilder();
			deleteEr
			.append(" delete from er_info ")
			.append(" where er_num=? ");
			
			pstmt = con.prepareStatement(deleteEr.toString());
			pstmt.setString(1, erNum);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) {
				flag = true;
			}
			
		} finally {
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return flag;
	}
	
	
	/**
	 * 한 기업정보를 조회하는 메소드
	 * @param coNum
	 * @return
	 * @throws SQLException
	 */
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
	

	/**
	 * 기업 정보를 업데이트하는 메소드
	 * @param cmvo
	 * @return
	 * @throws SQLException
	 */
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
	
	
	/**
	 * 기업 정보 삭제하는 메소드
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteCo(String id) throws SQLException { 
		// co정보만 지워도 er정보, selected_skill정보도 cascade로 삭제됨
		// co 정보를 지우면 co를 등록한 유저 activation정보를 N으로 변경
		
		boolean flag = false;

		try {
			try {
				con = getConn();
				con.setAutoCommit(false);

				if (dCoTransaction1(id) && dCoTransaction2(id)) {
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
				closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}
	
	/**
	 * deleteCo의 트랜잭션 - 1
	 * 기업 정보를 삭제
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean dCoTransaction1(String id) throws SQLException{
		boolean flag = false;
		
		StringBuilder deleteCo = new StringBuilder();
		
		deleteCo
		.append(" delete from company ")
		.append(" where er_id = ? ");
	
		pstmt1 = con.prepareStatement(deleteCo.toString());
		pstmt1.setString(1, id);
		
		int cnt = pstmt1.executeUpdate();
		
		if (cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	
	/**
	 * deleteCo의 트랜잭션 - 2
	 * 유저 activation 정보를 Y->N으로 변경
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean dCoTransaction2(String id) throws SQLException {
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
	
	/**
	 * 한 일반 회원 기본정보를 조회하는 메소드
	 * @param input
	 * @param flag
	 * @return
	 * @throws SQLException
	 */
	public EeInfoVO selectOneEe(String input, String flag) throws SQLException {
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
			.append(" from ee_info ei, user_table ut ");
			
			// 동적쿼리, eeNum으로 조회, 또는 id로 조회
			if (flag.equals("eeNum")) {
				selectOneEe.append(" where ei.ee_id = ut.id and ei.ee_num=? ");
			} else if (flag.equals("id")) {
				selectOneEe.append(" where ut.id=? and ei.ee_id = ut.id ");
			}
			
			pstmt = con.prepareStatement(selectOneEe.toString());
			pstmt.setString(1, input);
			
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
	
	/**
	 * 일반 사용자 기본 정보를 업데이트하는 메소드 
	 * @param emvo
	 * @return
	 * @throws SQLException
	 */
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
		
		return flag;
	}
	

	
	/**
	 * 기본 정보 삭제를 위해 EeDeleteVO정보를 조회하는 메소드 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public EeDeleteVO selectEDVO(String id) throws SQLException {
		EeDeleteVO edvo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			StringBuilder selectEDVO = new StringBuilder();
			selectEDVO
			.append(" select ee_num, ee_id from ee_info ei, user_table ut   ")
			.append("where ee_id = ut.id and ut.id = ? ");
			
			pstmt = con.prepareStatement(selectEDVO.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				edvo = new EeDeleteVO(rs.getString("ee_num"), rs.getString("ee_id"));
			}
			
		} finally {
			if (rs != null) { rs.close(); }
			if (pstmt != null) { pstmt.close(); }
			if (con != null) { con.close(); }
		}
		
		return edvo;
	}
	
	/**
	 * 일반 사용자 기본정보를 삭제하는 메소드
	 * @param edvo
	 * @return
	 */
	public boolean deleteEe(EeDeleteVO edvo){
		boolean flag = false;
		
		con = null;
		
		try {
			
			try {
				con = getConn();
				con.setAutoCommit(false);
				
				if (dEeTransaction1(edvo.getEeNum()) && dEeTransaction2(edvo.getEe_id())) {
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
				closeAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	/**
	 * deleteEe 트랜잭션 - 1
	 * ee_info에서 데이터 삭제
	 * @param eeNum
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * deleteEe 트랜잭션 - 2
	 * 유저 activation 정보를 Y->N으로 변경
	 * @param id
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Connection과 PreparedStatment를 close처리하는 메소드 
	 * @throws SQLException
	 */
	public void closeAll() throws SQLException {
		if (pstmt3 != null) { pstmt3.close(); }
		if (pstmt2 != null) { pstmt2.close(); }
		if (pstmt1 != null) { pstmt1.close(); }
		if (con != null) { con.close(); }
	}
	
}
