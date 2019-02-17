package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.dto.EeHiringCdtDTO;
import user.ee.vo.CoDetailVO;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeAppVO;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeInterestVO;
import user.ee.vo.EeRegVO;

public class EeDAO {
	private static EeDAO Ee_dao;

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

		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "kanu";
		String pass = "share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConns

	////////////////////////////////////////// ����
	////////////////////////////////////////// �ҽ�//////////////////////////////////////////////////////////
	/**
	 * ���� �����ϱ� ������ application���̺� ������ �ֱ�
	 */
	public void insertApplication(EeInterestAndAppVO eiaavo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String insertApplication = "insert into application(app_num, er_num,ee_id,app_status) values(app_code,?,?,?)";
			pstmt = con.prepareStatement(insertApplication);

			pstmt.setString(1, eiaavo.getErNum());
			pstmt.setString(2, eiaavo.getEeId());
			pstmt.setString(3, "U");

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

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
	public DetailErInfoVO selectDetail(String erNum, String eeId) throws SQLException {

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
					.append("  (select COUNT(*) from interest_er ").append("  where ee_id = ? and er_num=?) interest ")
					.append(" from er_info ei, company c, user_table ut ")
					.append(" where (ei.co_num= c.co_num)and(ut.id=c.er_id) ").append(" and (ei.er_num= ? )");

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
						rs.getString("hire_type"), rs.getString("portfolio"), rs.getString("er_desc"),
						rs.getString("interest"), rs.getInt("sal"), selectSkill(erNum));
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
			String deleteQuery = "delete from interest_er where er_num=? and ee_Id=?";
			pstmt = con.prepareStatement(deleteQuery);
			// 4.
			pstmt.setString(1, eiaavo.getErNum());
			pstmt.setString(2, eiaavo.getEeId());
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

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////// �ӽ÷� �߰��� �޼ҵ�(�����ϱ� ��ư���ֱ�
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ��ƴٱ���!!!!!!!!!!!!!!)///////////////////////////////////////////
//	public boolean selectApplication(String eeId,String eeNum ){
//		boolean flag =false;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {// try : DB���� ��ȸ�ϱ�
//
//			con = getConn(); // Ŀ�ؼ� ���.
//			StringBuilder selectAppList = new StringBuilder(); // ������Ȳ���̺� list.
//			selectAppList.append("dfdsfds");��������
//
//			pstmt = con.prepareStatement(selectAppList.toString());
//			// ���ε� ����
//			pstmt.setString(1, );
//			pstmt.setString(2, );
//			// ResultSet ������.
//			rs = pstmt.executeQuery();
//			// VO���� null;
//			EeAppVO eavo = null;
//			// VO���� �� list�� ���
//
//		} finally { // finally : �������.
//			if (rs != null) {rs.close();} // end if
//			if (pstmt != null) {pstmt.close();} // end if
//			if (con != null) {con.close();} // end if
//		} // end finally
//		
//		return flag;
//	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////////////////// ���� �ҽ�
	////////////////////////////////////////// ��//////////////////////////////////////////////////////////

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

			StringBuilder insertInfo = new StringBuilder();
			insertInfo.append(
					"		insert into ee_info(ee_num, img, rank, loc, education, portfolio, ext_resume, ee_id)	")
					.append("		values( ee_code, ?, ?, ?, ?, ?, ?, ? 	)	");
			pstmt = con.prepareStatement(insertInfo.toString());

			pstmt.setString(1, eivo.getEeId());
			pstmt.setString(2, eivo.getImg());
			pstmt.setString(3, eivo.getRank());
			pstmt.setString(4, eivo.getLoc());
			pstmt.setString(5, eivo.getEducation());
			pstmt.setString(6, eivo.getPortfolio());
			pstmt.setString(7, eivo.getExtResume());

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
//		EeInsertVO eivo= new EeInsertVO("ee1.jpg", "C", "��õ", "����", "Y", "�̷¼�", "choi7");
//		try {
//			EeDAO.getInstance().insertEeinfo(eivo);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}//main
	/**
	 * 19.02.11 ����� EeRegVO
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

			// Ŀ�ؼ� ���.
			con = getConn();

			// ����������
			String selectMyInfo = "select name, gender, age from user_table where id=?";
			pstmt = con.prepareStatement(selectMyInfo);
			// ���ε庯�� �� �Ҵ�.
			pstmt.setString(1, ee_id);

			// DB���� ��ȸ�ϱ�
			rs = pstmt.executeQuery(); // ��������
			if (rs.next()) {
				ervo = new EeRegVO(rs.getString("name"), rs.getString("gender"), rs.getInt("age"));
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
		} // selectEeReg
		return ervo;

	}//

	//////////// �����ڵ� ////////////

	/**
	 * selectInterestErInfo : �Ϲݻ���ڰ� ��Ʈ�� ���� ���������� DB���� ��ȸ.
	 * 
	 * @return
	 *
	 * @throws SQLException
	 */
	public List<EeInterestVO> selectInterestErInfoList(String ee_id) throws SQLException {
		List<EeInterestVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB���� ��ȸ�ϱ�

			con = getConn(); // Ŀ�ؼ� ���.

			StringBuilder slcInterestErInfo = new StringBuilder(); // ���ɱ���������ȸ �ϱ�
			slcInterestErInfo
					.append(" select ei.ER_NUM, ei.SUBJECT, c.CO_NAME, ei.RANK, ei.LOC, ei.EDUCATION, ei.SAL,");
			slcInterestErInfo.append(
					" ei.HIRE_TYPE, ei.PORTFOLIO, ei.ER_DESC,  to_char(ei.INPUT_DATE, 'yyyy-dd-mm') INPUT_DATE");
			slcInterestErInfo.append(" from interest_er ie, er_info ei, company c");
			slcInterestErInfo.append(" where (ie.er_num = ei.er_num) and (c.co_num=ei.co_num) and ie.ee_id = ?");
			pstmt = con.prepareStatement(slcInterestErInfo.toString());

			// ���ε庯�� �� �ֱ�
			pstmt.setString(1, ee_id);

			rs = pstmt.executeQuery();
			EeInterestVO eivo = null;
			// ��ȸ�� ������
			while (rs.next()) {
				eivo = new EeInterestVO(rs.getString("er_num"), rs.getString("SUBJECT"), rs.getString("CO_NAME"),
						rs.getString("RANK"), rs.getString("LOC"), rs.getString("EDUCATION"), rs.getString("HIRE_TYPE"),
						rs.getString("INPUT_DATE"), rs.getInt("SAL"));
				list.add(eivo);
			} // end if

		} finally { // finally : �������.
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

		return list;
	}// end selectInterestErInfo

	/**
	 * �Ϲݻ������ ������Ȳ ����� ä�� ���� ��ȸ�ϴ� �޼ҵ�.
	 * 
	 * @param ee_id
	 * @return
	 * @throws SQLException
	 */
	public List<EeAppVO> selectAppList(String ee_id) throws SQLException {
		List<EeAppVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB���� ��ȸ�ϱ�

			con = getConn(); // Ŀ�ؼ� ���.
			StringBuilder selectAppList = new StringBuilder(); // ������Ȳ���̺� list.
			selectAppList.append(
					" select a.app_num, ei.er_num, ei.subject, c.co_name, ei.rank, ei.loc, ei.education, ei.hire_type, ei.sal, to_char(a.app_date,'yyyy-mm-dd') app_date, a.app_status");
			selectAppList.append(" from application a, er_info ei, company c");
			selectAppList.append(" where (a.er_num=ei.er_num) and (ei.co_num=c.co_num) and a.ee_id=?");
			pstmt = con.prepareStatement(selectAppList.toString());
			// ���ε� ����
			pstmt.setString(1, ee_id);
			// ResultSet ������.
			rs = pstmt.executeQuery();
			// VO���� null;
			EeAppVO eavo = null;
			// VO���� �� list�� ���
			while (rs.next()) {
				eavo = new EeAppVO(rs.getString("app_num"), rs.getString("er_num"), rs.getString("subject"),
						rs.getString("co_name"), rs.getString("rank"), rs.getString("loc"), rs.getString("education"),
						rs.getString("hire_type"), rs.getString("app_date"), rs.getString("app_status"),
						rs.getInt("sal"));
				list.add(eavo);
			} // end while

		} finally { // finally : �������.
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

		return list;
	}// selectAppList

	/**
	 * ȸ�� �� ���� â�� ä�� �����͸� ��ȸ�ϴ� �޼���.
	 * 
	 * @return
	 */
	public CoDetailVO selectCompany(String er_num) throws SQLException {
		// ȸ�� �� ���� VO�� ����.
		CoDetailVO cdvo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = getConn();

			StringBuilder selectCompany = new StringBuilder();
			selectCompany.append(
					" select c.co_name, c.img1, c.img2, c.img3, c.img4, to_char(c.est_date,'yyyy-mm-dd')est_date, c.member_num, c.co_desc, to_char(c.input_date,'yyyy-mm-dd') input_date ");
			selectCompany.append(" from er_info ei, company c ");
			selectCompany.append(" where (ei.co_num = c.co_num) and ei.er_num = ? ");
			pstmt = con.prepareStatement(selectCompany.toString());

			// ���ε� ����.
			pstmt.setString(1, er_num);

			// ������ �����ϰ� ResultSet �ޱ�.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cdvo = new CoDetailVO(rs.getString("co_name"), rs.getString("est_date"), rs.getString("img1"),
						rs.getString("img2"), rs.getString("img3"), rs.getString("img4"), rs.getString("co_desc"),
						rs.getInt("member_num"));
			} // end if

		} finally {
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

		return cdvo;
	}// selectCompany()

	/* ���� �׽�Ʈ�� main */
	public static void main(String[] args) {
		EeDAO ee_dao = EeDAO.getInstance();
		try {
			System.out.println(ee_dao.selectCompany("er_000028"));
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// main

	////////////////////////// ���� �� //////////////////////////

}// class
