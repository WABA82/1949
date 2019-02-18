package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.er.dto.ErHiringCdtDTO;
import user.er.vo.DetailAppEeVO;
import user.er.vo.DetailAppListVO;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErAddVO;
import user.er.vo.ErAppStatusVO;
import user.er.vo.ErDefaultVO;
import user.er.vo.ErDetailVO;
import user.er.vo.ErHiringForInterestVO;
import user.er.vo.ErHiringVO;
import user.er.vo.ErInterestVO;
import user.er.vo.ErListVO;
import user.er.vo.ErModifyVO;

public class ErDAO {

	private static ErDAO Er_dao;

	public ErDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}// EeDAO

	public static ErDAO getInstance() {
		if (Er_dao == null) {
			Er_dao = new ErDAO();
		} // end if
		return Er_dao;
	}// getInstance

	private Connection getConn() throws SQLException {

		Connection con = null;

		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";
		String id = "kanu";
		String pass = "share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConns

	////////////////////////////// ���ǽ��� //////////////////////////////////////////
	public List<ErListVO> selectErList(String erId) throws SQLException {
		List<ErListVO> list = new ArrayList<ErListVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectErList = new StringBuilder();
			selectErList.append(
					" select ei.er_num,ei.subject,ei.rank,ei.loc,ei.education,ei.hire_type,to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date ")
					.append(" from er_info ei, company c ").append(" where (ei.co_num = c.co_num)and(c.er_id=?) ");

			pstmt = con.prepareStatement(selectErList.toString());
			pstmt.setString(1, erId);
			rs = pstmt.executeQuery();
			ErListVO elvo = null;
			while (rs.next()) {
				elvo = new ErListVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("rank"),
						rs.getString("loc"), rs.getString("education"), rs.getString("hire_type"),
						rs.getString("input_date"));
				list.add(elvo);
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

	}// selectErList

	public void insertInterestEe(ErInterestVO eivo) throws SQLException {
		System.out.println("----1");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String insertInterestEe = "insert into interest_ee(ee_num,er_id) values(?,?)";
			pstmt = con.prepareStatement(insertInterestEe);

			System.out.println("----2");
			pstmt.setString(1, eivo.getEeNum());
			pstmt.setString(2, eivo.getErId());

			System.out.println("----3");
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

	public boolean deleteInterestEe(ErInterestVO eivo) throws SQLException {
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			String deleteQuery = "delete from interest_ee where ee_num=? and er_id=?";
			pstmt = con.prepareStatement(deleteQuery);
			// 4.
			pstmt.setString(1, eivo.getEeNum());
			pstmt.setString(2, eivo.getErId());
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

	public ErDetailVO selectErDetail(String erNum) throws SQLException {
		ErDetailVO edvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectErDetail = new StringBuilder();
			selectErDetail.append(
					" select c.img1, ut.name, ut.tel, ut.email, ei.subject, c.co_name, ei.rank, ei.loc, ei.education,  ")
					.append(" ei.hire_type, ei.portfolio, ei.er_desc, ei.sal  ")
					.append(" from er_info ei, user_table ut,company c  ")
					.append(" where (ei.co_num=c.co_num) and (c.er_id= ut.id) and (er_num=?)  ");

			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1, erNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				edvo = new ErDetailVO(erNum, rs.getString("img1"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"), rs.getString("subject"), rs.getString("co_name"),
						rs.getString("education"), rs.getString("rank"), rs.getString("loc"), rs.getString("hire_type"),
						rs.getString("portfolio"), rs.getString("er_desc"), rs.getInt("sal"), selectSkill(erNum));
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

		return edvo;
	}// selectErDetail

	public DetailEeInfoVO selectDeatilEe(String eeNum, String erId) throws SQLException {
		DetailEeInfoVO devo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectDetail = new StringBuilder();
			selectDetail.append(" select ei.img, ut.name,ut.tel,ut.email,ei.rank,ei.loc, ")
					.append(" ei.education,ei.portfolio, ut.gender, ei.ext_resume, ut.age, ")
					.append("  (select COUNT(*) from interest_ee where er_id = ? and ee_num=? ) interest ")
					.append(" from ee_info ei, company c, user_table ut  ")
					.append("  where ut.id=ei.ee_id and ei.ee_num= ? ");

			pstmt = con.prepareStatement(selectDetail.toString());
			// 4.
			pstmt.setString(1, erId);
			pstmt.setString(2, eeNum);
			pstmt.setString(3, eeNum);
			// 5.
			rs = pstmt.executeQuery();
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
			if (rs.next()) {

				devo = new DetailEeInfoVO(eeNum, rs.getString("img"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"), rs.getString("rank"), rs.getString("loc"), rs.getString("education"),
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("ext_resume"),
						rs.getString("interest"), rs.getInt("age"));
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
		return devo;
	}// selectDeatilEe

	public ErDefaultVO selectErDefault(String erId) throws SQLException {
		ErDefaultVO edfvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectErDetail = new StringBuilder();

			selectErDetail.append(" select c.img1, ut.name, ut.tel, ut.email, c.co_name ")
					.append(" from company c, user_table ut ").append(" where (c.er_id=ut.id) and (c.er_id=?) ");

			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1, erId);
			rs = pstmt.executeQuery();
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�

			if (rs.next()) {
				edfvo = new ErDefaultVO(erId, rs.getString("img1"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"), rs.getString("co_name"));
			} // end if
		} finally {
			// 6.
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}

		}

		return edfvo;
	}// selectErDefault

	public void insertErAdd(ErAddVO eavo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String insertErAdd = "insert into er_info(er_id,subject,education,rank,loc,hire_type, portfolio, er_desc) values(?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(insertErAdd);

			pstmt.setString(1, eavo.getErId());
			pstmt.setString(2, eavo.getSubject());
			pstmt.setString(3, eavo.getEducation());
			pstmt.setString(4, eavo.getRank());
			pstmt.setString(5, eavo.getLoc());
			pstmt.setString(6, eavo.getHireType());
			pstmt.setString(7, eavo.getPortfolio());
			pstmt.setString(8, eavo.getErDesc());

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}// insertErAdd

	public boolean updateErModify(ErModifyVO emvo) throws SQLException {
		boolean updateFlag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			StringBuilder insertErAdd = new StringBuilder();

			insertErAdd.append(" update er_info ")
					.append(" set subject=?,education=?,rank=?,loc=?,hire_type=?, portfolio=?, er_desc=?, sal=? ")
					.append(" where er_num=?  ");

			pstmt = con.prepareStatement(insertErAdd.toString());

			pstmt.setString(1, emvo.getSubject());
			pstmt.setString(2, emvo.getEducation());
			pstmt.setString(3, emvo.getRank());
			pstmt.setString(4, emvo.getLoc());
			pstmt.setString(5, emvo.getHireType());
			pstmt.setString(6, emvo.getPortfolio());
			pstmt.setString(7, emvo.getErDesc());
			pstmt.setString(8, emvo.getErNum());

			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				updateFlag = true;
			} // end if

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return updateFlag;
	}// updateErModify

	public boolean deleteEr(String erNum) throws SQLException {
		boolean deleteFlag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();
			String deleteQuery = "delete from er_info where er_num=?";
			pstmt = con.prepareStatement(deleteQuery);
			pstmt.setString(1, erNum);
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				deleteFlag = true;
			} // end if

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return deleteFlag;
	}// deleteEr

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
	}// selectSkill

	public List<ErHiringVO> selectErHiring(ErHiringCdtDTO erhcdto) throws SQLException {
		List<ErHiringVO> list = new ArrayList<ErHiringVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			StringBuilder selectEeHiring = new StringBuilder();

			selectEeHiring.append(" select ei.ee_num, ei.img, ut.name, ei.rank, ei.loc, ").append(
					" ei.education, ut.age, ei.portfolio, ut.gender, to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date ")
					.append(" from   ee_info ei, user_table ut ").append(" where ut.id= ei.ee_id ");

			if (!(erhcdto.getCdt() == null || erhcdto.getCdt().equals(""))) {
				selectEeHiring.append(erhcdto.getCdt());
			}

			if (!(erhcdto.getSort().trim() == null || erhcdto.getSort().trim().equals(""))) {
				if (erhcdto.getSort().equals("����ϼ�")) {
					selectEeHiring.append("	order by ei.input_date	");
				} else if (erhcdto.getSort().equals("���޼�")) {
					selectEeHiring.append("	order by ei.rank	 ");
				}
			} else {
				selectEeHiring.append("	order by ei.input_date	");
			}

			pstmt = con.prepareStatement(selectEeHiring.toString());

			rs = pstmt.executeQuery();
			ErHiringVO erhvo = null;
			while (rs.next()) {
				erhvo = new ErHiringVO(rs.getString("ee_num"), rs.getString("img"), rs.getString("name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getString("portfolio"),
						rs.getString("gender"), rs.getString("input_date"), rs.getInt("age"));
				list.add(erhvo);
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

	////////////////////////////////////////// ���ǳ�///////////////////////////////////////////////

	/***************************** ���� ���� *****************************/

	/**
	 * 
	 * @param er_id
	 * @return
	 * @throws SQLException
	 */
	public List<ErHiringForInterestVO> selectInterestEEInfoList(String er_id) throws SQLException {
		List<ErHiringForInterestVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB���� ��ȸ�ϱ�

			con = getConn(); // Ŀ�ؼ� ���.

			StringBuilder slcInterestEeInfo = new StringBuilder(); // ���ɱ���������ȸ �ϱ�
			slcInterestEeInfo.append(
					" select ie.ee_num, ei.img, ut.name, ei.rank, ei.loc, ei.education, ut.age, ei.portfolio, ut.gender, to_char(ie.input_date,'yyyy-mm-dd') input_date ");
			slcInterestEeInfo.append(" from interest_ee ie, ee_info ei, user_table ut");
			slcInterestEeInfo.append(" where (ie.ee_num = ei.ee_num) and (ei.ee_id = ut.id) and (ie.er_id=?)");
			pstmt = con.prepareStatement(slcInterestEeInfo.toString());

			// ���ε庯�� �� �ֱ�
			pstmt.setString(1, er_id);

			rs = pstmt.executeQuery();
			ErHiringForInterestVO ehfivo = null;

			// ��ȸ�� ������
			while (rs.next()) {
				ehfivo = new ErHiringForInterestVO(rs.getString("ee_num"), rs.getString("img"), rs.getString("name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getInt("age"),
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("input_date"));
				// ����Ʈ�� ���.
				list.add(ehfivo);
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
	}// selectInterestEEInfoList

	/**
	 * ���� 0214 : �� ���� ��Ȳ â�� ���̺��� ä�� �����͸� ��ȸ�ϴ� �޼���.
	 * 
	 * @param er_num
	 * @return
	 */
	public List<DetailAppListVO> selectDetailApplist(String er_num) throws SQLException {
		List<DetailAppListVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			StringBuilder selectDetailApplist = new StringBuilder();
			selectDetailApplist.append(
					" select a.app_num, eei.img, ut.name, eei.rank, eei.loc, eei.education, ut.age, eei.portfolio, ut.gender, to_char(a.app_date,'yyyy-mm-dd') app_date, a.app_status ");
			selectDetailApplist.append(" from application a, user_table ut, ee_info eei ");
			selectDetailApplist.append(" where (a.ee_id = ut.id) and (ut.id = eei.ee_id) and er_num = ? ");
			pstmt = con.prepareStatement(selectDetailApplist.toString());

			// ���ε� ���� �� �Ҵ�.
			pstmt.setString(1, er_num);

			// rs�޾ƿ���
			rs = pstmt.executeQuery();
			DetailAppListVO elvo = null;
			while (rs.next()) {
				elvo = new DetailAppListVO(rs.getString("app_num"), rs.getString("img"), rs.getString("name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getString("portfolio"),
						rs.getString("gender"), rs.getString("app_date"), rs.getString("app_status"), rs.getInt("age"));

				list.add(elvo);
			} // end while

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
		} // end catch

		return list;

	}// selectDetailApplist

	public DetailEeInfoVO selectDetailEEInfo(String er_id, String ee_num) {
		return null;
	}// selectDetailEEInfo

	/**
	 * ���� : ���� ��Ȳ - �� ���� ��Ȳ - ������ �� ���� â�� �ؽ�Ʈ�ʵ带 ä�� ���� ��ȸ.
	 * 
	 * @return DetailAppEeVO
	 */
	public DetailAppEeVO selectDetailAppEe(String app_num) throws SQLException {
		DetailAppEeVO daevo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			StringBuilder selectDetailAppEe = new StringBuilder();
			selectDetailAppEe.append(
					" select img, name, tel, email, rank, loc, education, age, portfolio, gender, ext_resume, upper(app_status) app_status");
			selectDetailAppEe.append(" from application a, user_table ut, ee_info eei ");
			selectDetailAppEe.append(" where (a.ee_id = ut.id) and (ut.id = eei.ee_id) and (a.app_num = ?) ");
			pstmt = con.prepareStatement(selectDetailAppEe.toString());
			pstmt.setString(1, app_num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				daevo = new DetailAppEeVO(rs.getString("img"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"), rs.getString("rank"), rs.getString("loc"), rs.getString("education"),
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("ext_resume"),
						rs.getString("app_status"), rs.getInt("age"));
			} // end while

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
		} // end catch

		return daevo;
	}// selectDetailAppEe()

	/**
	 * DB application �������¸� �������� �����ϴ� �޼���.
	 */
	public boolean updateAppSatus(ErAppStatusVO easvo) throws SQLException {

		boolean flag = false;

		// * DEFAULT 'U'
		// * 'U' - unread ������
		// * 'R' - read ����
		// * 'A' - approved ��������
		// * 'D' - denied ��������

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();

			String updateAppSatusAccept = new String();
			updateAppSatusAccept = "update application set app_status=UPPER(?) where app_num=?";
			pstmt = con.prepareStatement(updateAppSatusAccept.toString());

			pstmt.setString(1, easvo.getApp_status());
			pstmt.setString(2, easvo.getApp_num());

			if (pstmt.executeUpdate() != 1) {
				// 1�� �ƴ� ��� ������ �߻��� ��.
				flag = true;
			} // end if
		} finally {
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

		return flag;
	}// updateAppSatusAccept()

	/* ���� : ���� �׽�Ʈ�� main�޼���. */
	public static void main(String[] args) {
		try {
			System.out.println(ErDAO.getInstance().updateAppSatus(new ErAppStatusVO("app_000001", "u")));
//			System.out.println(ErDAO.getInstance().selectDetailAppEe("app_000001"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// main

	/***************************** ���� �� *****************************/

}// class
