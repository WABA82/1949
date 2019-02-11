package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.dto.EeHiringCdtDTO;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeRegVO;

public class EeDAO {
	private static EeDAO Ee_dao;
	private String interest;

	public EeDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}// EeDAO

	public static EeDAO getInstance() {
		if (Ee_dao == null) {
			Ee_dao = new EeDAO();
		} // end if
		return Ee_dao;
	}// getInstance

	private Connection getConn() throws SQLException {

		Connection con = null;

		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";
		String id = "kanu";
		String pass = "share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConns

	///// 02.09 선의 Hiring소스
	public List<EeHiringVO> selectEeHiring(EeHiringCdtDTO eh_dto) throws SQLException {
		List<EeHiringVO> list = new ArrayList<EeHiringVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectEeHiring = new StringBuilder();
			selectEeHiring.append("select ei.er_num, ei.subject, ei.education, ei.rank, ei.loc, ei.hire_type,")
					.append("to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date, ei.sal, c.co_name	 ")
					.append("	from er_info ei ,company c	 ").append("	where (ei.co_num=c.co_num) ");

			if (!(eh_dto.getCoName().trim() == null || eh_dto.getCoName().trim().equals(""))) {
				selectEeHiring.append("and (c.co_name like '%").append(eh_dto.getCoName()).append("%' ) ");
			}
			if (!(eh_dto.getCdt() == null || eh_dto.getCdt().equals(""))) {
				selectEeHiring.append(eh_dto.getCdt());
			}

			if (!(eh_dto.getSort().trim() == null || eh_dto.getSort().trim().equals(""))) {
				if (eh_dto.getSort().equals("등록일순")) {
					selectEeHiring.append("	order by ei.input_date	");
				} else if (eh_dto.getSort().equals("직급순")) {
					selectEeHiring.append("	order by ei.rank	 ");
				} else if (eh_dto.getSort().equals("급여순")) {
					selectEeHiring.append("	order by ei.sal	 ");
				}
			} else {
				selectEeHiring.append("	order by ei.input_date	");
			}

			pstmt = con.prepareStatement(selectEeHiring.toString());

			rs = pstmt.executeQuery();
			EeHiringVO eh_vo = null;
			while (rs.next()) {

				eh_vo = new EeHiringVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("co_name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getString("hire_type"),
						rs.getString("input_date"), rs.getInt("sal"));
				list.add(eh_vo);
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
	}// selectEeHiring

	// 0210 선의 추가
	public List<String> selectSkill(String erNum) throws SQLException {
		List<String> listSkill = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectDetail = new StringBuilder();
			selectDetail.append(" select sk.skill_name ").append(" from selected_skill ss, skill sk ")
					.append(" where (ss.skill_num=sk.skill_num) and (er_num=?) ");
			pstmt = con.prepareStatement(selectDetail.toString());

			// 4.
			pstmt.setString(1, erNum);
			// 5.
			rs = pstmt.executeQuery();
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
			while (rs.next()) {
				listSkill.add(rs.getString("skill_name"));
			} // end if
		} finally {
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		return listSkill;
	}

	// 0210 선의 detailErInfo 검색
	public DetailErInfoVO selectDetail(String erNum) throws SQLException {

		DetailErInfoVO deivo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectDetail = new StringBuilder();
			selectDetail.append(" select ei.er_num, ei.subject, ut.name, ut.tel, ut.email, ")
					.append(" to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date, c.img1, c.co_name, ")
					.append(" ei.education, ei.rank, ei.loc, ei.hire_type, ei.portfolio, ei.er_desc, ei.sal ")
					.append(" from er_info ei, company c, user_table ut ")
					.append(" where (ei.co_num= c.co_num)and(ut.id=c.er_id) ").append(" and (ei.er_num= ? )");
			pstmt = con.prepareStatement(selectDetail.toString());
			// 4.
			pstmt.setString(1, erNum);
			// 5.
			rs = pstmt.executeQuery();
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
			if (rs.next()) {
				deivo = new DetailErInfoVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("name"),
						rs.getString("tel"), rs.getString("email"), rs.getString("input_date"), rs.getString("img1"),
						rs.getString("co_name"), rs.getString("education"), rs.getString("rank"), rs.getString("loc"),
						rs.getString("hire_type"), rs.getString("portfolio"), rs.getString("er_desc"), "true",
						rs.getInt("sal"), selectSkill(erNum));
			} // end if
		} finally {
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return deivo;
	}// DetailErInfoVO

	// 0210 선의 관심구인공고 추가
	public void insertInterestEr(EeInterestAndAppVO eiaavo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String insertInterestEr = "insert into interest_er(er_num,ee_id) values(?,?)";
			pstmt = con.prepareStatement(insertInterestEr);

			pstmt.setString(1, eiaavo.getErNum());
			pstmt.setString(2, eiaavo.getEeId());

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}// insertInterestEr

	// 0210 선의 관심구인공고 제거
	public boolean deleteInterestEr(EeInterestAndAppVO eiaavo) throws SQLException {
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			String deleteQuery = "delete from interest_er where er_num=?";
			pstmt = con.prepareStatement(deleteQuery);
			// 4.
			pstmt.setString(1, eiaavo.getErNum());
			// 5.
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			} // end if

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return flag;
	}

	/**
	 * 19.02.10김건하 회원정보 입력
	 * 
	 * @param eivo
	 * @throws SQLException
	 */
	public void insertEeinfo(EeInsertVO eivo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();

			// String eeNum, img, rank, loc, education, portfolio, extResume, inputDate,
			// eeId;

			StringBuilder insertInfo = new StringBuilder();
			insertInfo.append("insert into ee_info").append(
					"(ee_num, img, rank, loc, education, portfolio, ext_resume, to_char( input_date,'YYYY-MM-DD') input_date, ee_id)")
					.append("values(ee_code, ?, ?, ?, ?, ?, ?, ?, ? 	)");

			pstmt = con.prepareStatement(insertInfo.toString());

			pstmt.setString(1, eivo.getImg());
			pstmt.setString(2, eivo.getRank());
			pstmt.setString(3, eivo.getLoc());
			pstmt.setString(4, eivo.getEducation());
			pstmt.setString(5, eivo.getPortfolio());
			pstmt.setString(6, eivo.getExtResume());
			pstmt.setString(7, eivo.getInputDate());
			pstmt.setString(8, eivo.getEeId());

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally

	}// insertEeinfo

//	public static void main(String[] args) {
//		EeDAO eeado=new EeDAO().insertEeinfo();
//	}//main

	/**
	 * 19.02.11 김건하 EeRegVO
	 * 
	 * @return
	 * @throws SQLException
	 */
	public EeRegVO selectEeReg(String ee_id) throws SQLException {
		EeRegVO ervo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 커넥션 얻기.
			con = getConn();

			// 쿼리문생성
			String selectMyInfo = "select name, gender, age from user_table where id=?";
			pstmt = con.prepareStatement(selectMyInfo);
			// 바인드변수 값 할당.
			pstmt.setString(1, ee_id);

			// DB에서 조회하기
			rs = pstmt.executeQuery(); // 쿼리실행
			if (rs.next()) {
				ervo = new EeRegVO(rs.getString("name"),rs.getString("gender"), rs.getInt("age"));
			} // end while

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

		return ervo;
	}// insertEeReg

}// class
