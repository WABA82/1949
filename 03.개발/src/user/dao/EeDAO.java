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


		
		String url="jdbc:oracle:thin:@211.63.89.144:1521:orcl";
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}//getConns
	
	
	
	//////////////////////////////////////////���� �ҽ�//////////////////////////////////////////////////////////


	///// 02.09 ���� Hiring�ҽ�
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
				if (eh_dto.getSort().equals("����ϼ�")) {
					selectEeHiring.append("	order by ei.input_date	");
				} else if (eh_dto.getSort().equals("���޼�")) {
					selectEeHiring.append("	order by ei.rank	 ");
				} else if (eh_dto.getSort().equals("�޿���")) {
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

	// 0210 ���� �߰�
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
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
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

	// 0210 ���� detailErInfo �˻�
	public DetailErInfoVO selectDetail(String erNum,String eeId) throws SQLException {


		DetailErInfoVO deivo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectDetail = new StringBuilder();
			selectDetail.append(" select ei.er_num, ei.subject, ut.name, ut.tel, ut.email, ")
					.append(" to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date, c.img1, c.co_name, ")
					.append("  ei.education, ei.rank, ei.loc, ei.hire_type, ei.portfolio, ei.er_desc, ei.sal, ")
					.append("  (select COUNT(*) from interest_er ")
					.append("  where ee_id = ? and er_num=?) interest ")
					.append(" from er_info ei, company c, user_table ut ")
					.append(" where (ei.co_num= c.co_num)and(ut.id=c.er_id) ").append(" and (ei.er_num= ? )");
			
	/*		select ei.er_num, ei.subject, ut.name, ut.tel, ut.email,
			 to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date, c.img1, c.co_name,
			 ei.education, ei.rank, ei.loc, ei.hire_type, ei.portfolio, ei.er_desc, ei.sal,
			 (select COUNT(*) from interest_er
			   where ee_id = 'gong1' and er_num='er_000031') interest
			  from er_info ei, company c, user_table ut
			  where (ei.co_num= c.co_num)
			     and (ut.id=c.er_id)
			     and (ei.er_num= 'er_000031' );*/
			pstmt = con.prepareStatement(selectDetail.toString());
			// 4.
			pstmt.setString(1, eeId);
			pstmt.setString(2, erNum);
			pstmt.setString(3, erNum);
			// 5.
			rs = pstmt.executeQuery();
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
			if (rs.next()) {
				deivo = new DetailErInfoVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("name"),
						rs.getString("tel"), rs.getString("email"), rs.getString("input_date"), rs.getString("img1"),
						rs.getString("co_name"), rs.getString("education"), rs.getString("rank"), rs.getString("loc"),
						rs.getString("hire_type"), rs.getString("portfolio"), rs.getString("er_desc"), rs.getString("interest"),
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
	}//DetailErInfoVO
	
	public List<EeHiringVO> selectInterestEr(String erNum) {
		List<EeHiringVO> list =null;
		
		
		
		return list;
	}
	
	
	// 0210 ���� ���ɱ��ΰ��� �߰�
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

	// 0210 ���� ���ɱ��ΰ��� ����
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
	//////////////////////////////////////////���� �ҽ� ��//////////////////////////////////////////////////////////

	/**
	 * 19.02.10����� ȸ������ �Է�
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
					.append("values('ee_num', ?, ?, ?, ?, ?, ?, ?, ? 	)");

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
		}
	}// end finally

	//////////// �����ڵ� ////////////
	/**
	 * selectInterestErInfo : �Ϲݻ���ڰ� ��Ʈ�� ���� ���������� DB���� ��ȸ.
	 * 
	 * @return
	 *
	 * @throws SQLException
	 */
	public List<EeHiringVO> selectInterestErInfo() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB���� ��ȸ�ϱ�

			con = getConn(); // Ŀ�ؼ� ���.

			StringBuilder selectInterestErInfo = new StringBuilder(); // sql��ȸ �ϱ�
			selectInterestErInfo.append(" select ie.er_num, ei.subject, ei.co_name, ei.education, ei.rank, ei.loc, ");
			selectInterestErInfo.append(" ei.hire_type, ei.sal, to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date");
			selectInterestErInfo.append(" from interest_er ie LEFT OUTER JOIN er_info ei ON ie.er_num=ei.er_num");
			selectInterestErInfo.append(" where (ei.co_num=c.co_num) and ");

		} finally { // fianlly : �������.
			if (rs != null) {
				rs.close();
			} // end if
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

		return null;
	}// end selectInterestErInfo

}// class
