package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.er.dto.ErHiringCdtDTO;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;
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
	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private Connection con;
	private int insertSkillcnt;
	
	
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
	/**
	 *  ���� �ڽ��� �ø� ������α��� ��ȸ
	 * @param erId
	 * @return
	 * @throws SQLException
	 */
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
					.append(" from er_info ei, company c ").append(" where (ei.co_num = c.co_num)and(c.er_id=?) ")
					.append(" order by input_date desc ");

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

	/**
	 * ���� ���� ������ �߰�
	 * @param eivo
	 * @throws SQLException
	 */
	public void insertInterestEe(ErInterestVO eivo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String insertInterestEe = "insert into interest_ee(ee_num,er_id) values(?,?)";
			pstmt = con.prepareStatement(insertInterestEe);

			pstmt.setString(1, eivo.getEeNum());
			pstmt.setString(2, eivo.getErId());

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

	/**
	 * ���� ���� ������ ����
	 * @param eivo
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * ���� �������ڰ� �ø� ���α� �ڼ��� �������� ���� ��ȸ
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * ���� �Ϲݻ���� ���� �� ������ ���� ���� ��ȸ
	 * @param eeNum
	 * @param erId
	 * @return
	 * @throws SQLException
	 */
	public DetailEeInfoVO selectDetailEe(String eeNum, String erId) throws SQLException {
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
	
	//==============================������ ����=========================================//
	/**
	 * ���� ���α� �߰� Ʈ����� ���θ޼ҵ�
	 * @param eavo
	 * @throws SQLException
	 */
	/*public boolean insertErAdd(ErAddVO eavo) throws SQLException {
		String erNum = "";
		boolean insertFlag = false;
		boolean t2 =false;
		boolean t1 = false;
		try {
			con = getConn();
			con.setAutoCommit(false);
			
			try {
				insertErAdd2(eavo);
				//t1=true;
				//t1= irTransaction1(con, eavo);
				erNum = irTransaction3(con, eavo);
				if(eavo.getListSkill().size()!=0) {
					for(int i=0; i<eavo.getListSkill().size();i++) {
						System.out.println(eavo.getListSkill().get(i));
						t2 = irTransaction2(con, eavo,erNum,eavo.getListSkill().get(i));
					}
				}else {
					t2=true;
				}
				
				if (t1 && t2) {
					insertFlag = true;
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
		return insertFlag;
	}// insertErAdd
	*/	
	/**
	 * ���α� �߰� Ʈ�����1 ���α�(��ų����) ���
	 * @param con
	 * @param eavo
	 * @return
	 * @throws SQLException
	 */
	/*public boolean irTransaction1(Connection con,ErAddVO eavo) throws SQLException {
			boolean insertFlag = false;
			con = getConn();

			StringBuilder insertSkillAdd =new StringBuilder();
			
			insertSkillAdd.append(" insert into er_info(er_num,subject,education,rank,loc,sal,hire_type, portfolio, er_desc,co_num  ) ")
			.append(" values(er_code, ?,?,?,?,?,?,?,?, ")
			.append(" (select co_num ")
			.append(" from company ")
			.append(" where er_id=?)) ");
			pstmt1 = con.prepareStatement(insertSkillAdd.toString());
			pstmt1.setString(1, eavo.getSubject());
			pstmt1.setString(2, eavo.getEducation());
			pstmt1.setString(3, eavo.getRank());
			pstmt1.setString(4, eavo.getLoc());
			pstmt1.setInt(5, eavo.getSal());
			pstmt1.setString(6, eavo.getHireType());
			pstmt1.setString(7, eavo.getPortfolio());
			pstmt1.setString(8, eavo.getErDesc());
			pstmt1.setString(9, eavo.getErId());
			
			pstmt1.executeQuery();
			insertFlag = true;
	
			return insertFlag;
	}// insertErAdd
/**
	 * �߰��� erNum�� ��ȸ
	 * @param con
	 * @param eavo
	 * @return
	 * @throws SQLException
	 */
	/*public String irTransaction3(Connection con, ErAddVO eavo)throws SQLException {
		String erNum ="";
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectErDetail = new StringBuilder();
			
			selectErDetail.append(" select er_num ")
					.append(" from ( ")
					.append(" select rownum r,er_num ")
					.append(" from er_info ei, company c, user_table ut ")
					.append(" where (ei.co_num=c.co_num and ut.id= c.er_id)and ut.id=? ")
					.append(" order by ut.input_date desc ) ")
					.append(" where r=1 ");
			
			pstmt3 = con.prepareStatement(selectErDetail.toString());
			pstmt3.setString(1, eavo.getErId());
			rs = pstmt3.executeQuery();
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
			if(rs.next()) {
				erNum = rs.getString("er_num");
				System.out.println(rs.getString("er_num"));
			} // end if
		} finally {
			// 6.
			if (rs != null) {
				rs.close();
			}
		}
		return erNum;
	}*/
	
	/**
	 * ���α� �߰� Ʈ�����1 ���α� ��ų ���
	 * @param con
	 * @param eavo
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
	/*public boolean irTransaction2(Connection con,ErAddVO eavo,String erNum, String skill) throws SQLException {
		boolean insertSkillFlag = false;
		con = getConn();
		
		StringBuilder insertSkillAdd = new StringBuilder();
		insertSkillAdd
		.append(" insert into selected_skill(er_num,skill_num) ")
		.append(" values(?,?) ");
		
		pstmt1 = con.prepareStatement(insertSkillAdd.toString());
		pstmt1.setString(1,erNum );
		pstmt1.setString(2, skill);
		insertSkillcnt += pstmt1.executeUpdate();
		
		if (insertSkillcnt == eavo.getListSkill().size()) {
			insertSkillFlag = true;
		} // end if
		
		return insertSkillFlag;
	}// insertErAdd
*/	//==============================������ ��=========================================//

	
	/**
	 * ���� ���α� ��� ����
	 * @param eavo
	 * @return
	 * @throws SQLException
	 */
	public boolean insertErAdd(ErAddVO eavo) throws SQLException {
		String erNum = "";
		int cnt=0;
		boolean insertFlag = false;
		
		insertErAddInfo(eavo);
		erNum = selectAddErNum(eavo);
		for(int i=0; i<eavo.getListSkill().size();i++) {
			insertErSkill(eavo, erNum, eavo.getListSkill().get(i));
			cnt++;
		}
		if(cnt==eavo.getListSkill().size()) {
			insertFlag=true;
		}
		
		return insertFlag;
	}// insertErAdd
	
	/**
	 * ���� ���α� ��ų���� �� DB�߰�
	 * @param eavo
	 * @throws SQLException
	 */
	public void insertErAddInfo(ErAddVO eavo)throws SQLException{
		
		Connection con=null;
		PreparedStatement pstmt = null;
		StringBuilder insertSkillAdd =new StringBuilder();
		
		try {
				con = getConn();
				//3.
				insertSkillAdd.append(" insert into er_info(er_num,subject,education,rank,loc,sal,hire_type, portfolio, er_desc,co_num  ) ")
				.append(" values(er_code, ?,?,?,?,?,?,?,?, ")
				.append(" (select co_num ")
				.append(" from company ")
				.append(" where er_id=?)) ");
				
				pstmt = con.prepareStatement(insertSkillAdd.toString());
				pstmt.setString(1, eavo.getSubject());
				pstmt.setString(2, eavo.getEducation());
				pstmt.setString(3, eavo.getRank());
				pstmt.setString(4, eavo.getLoc());
				pstmt.setInt(5, eavo.getSal());
				pstmt.setString(6, eavo.getHireType());
				pstmt.setString(7, eavo.getPortfolio());
				pstmt.setString(8, eavo.getErDesc());
				pstmt.setString(9, eavo.getErId());
				
				pstmt.executeQuery();
				
			
		}finally {
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
	}
	
	/**
	 * ���� �߰��� ��ϱ��� erNum��ȸ
	 * @param eavo
	 * @return
	 * @throws SQLException
	 */
	public String selectAddErNum(ErAddVO eavo)throws SQLException {
		String erNum ="";
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectErDetail = new StringBuilder();
			
			selectErDetail.append(" select er_num ")
					.append(" from ( ")
					.append(" select rownum r,er_num ")
					.append(" from( ")
					.append(" select ei.er_num, ei.input_date input_date ")
					.append(" from er_info ei, company c, user_table ut ")
					.append(" where (ei.co_num=c.co_num and ut.id= c.er_id)and ut.id=? ")
					.append(" order by input_date desc)) ")
					.append(" where r=1 ");

			pstmt3 = con.prepareStatement(selectErDetail.toString());
			pstmt3.setString(1, eavo.getErId());
			rs = pstmt3.executeQuery();
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
			if(rs.next()) {
				erNum = rs.getString("er_num");
			} // end if
		} finally {
			// 6.
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
		return erNum;
	}
	
	/**
	 * ���� ���α� ��ų�߰�
	 * @param con
	 * @param eavo
	 * @param erNum
	 * @param skill
	 * @return
	 * @throws SQLException
	 */
	public void insertErSkill(ErAddVO eavo,String erNum, String skill) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt = null;
		StringBuilder insertSkillAdd =new StringBuilder();
		
		try {
			con=getConn();
			insertSkillAdd
			.append(" insert into selected_skill(er_num,skill_num) ")
			.append(" values(?,?) ");
			
			pstmt1 = con.prepareStatement(insertSkillAdd.toString());
			pstmt1.setString(1,erNum );
			pstmt1.setString(2, skill);
			insertSkillcnt += pstmt1.executeUpdate();
			
		}finally {
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
	}// insertErAdd
	
	/**
	 * ���� ���α� ������ �����ϴ� Ʈ����� ���θ޼ҵ�
	 * @param emvo
	 * @param preSkill
	 * @return
	 * @throws SQLException
	 */
	public boolean updateErModify(ErModifyVO emvo, int preSkill ) throws SQLException {
		
		boolean updateFlag = false;
		try {
			con = getConn();
			con.setAutoCommit(false);
			
			try {
				boolean t1 = urTransaction1(con, emvo);
				boolean t2 = urTransaction2(con, emvo,preSkill);
				urTransaction3(con,emvo);
				
				if (t1 && t2) {
					updateFlag = true;
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
		
		return updateFlag;
	}// updateErModify

	

	/**
	 * ���� ���α� ���� Ʈ�����1 .���õ� �ɼ����� ������ ����
	 * @param emvo
	 * @return
	 * @throws SQLException
	 */
	public boolean urTransaction1(Connection con,ErModifyVO emvo) throws SQLException {
		boolean updateFlag = false;
		
			StringBuilder insertErAdd = new StringBuilder();

			insertErAdd.append(" update er_info ")
					.append(" set subject=?,education=?,rank=?,loc=?,hire_type=?, portfolio=?, er_desc=?, sal=? ")
					.append(" where er_num=?  ");

			pstmt1 = con.prepareStatement(insertErAdd.toString());
			
			pstmt1.setString(1, emvo.getSubject());
			pstmt1.setString(2, emvo.getEducation());
			pstmt1.setString(3, emvo.getRank());
			pstmt1.setString(4, emvo.getLoc());
			pstmt1.setString(5, emvo.getHireType());
			pstmt1.setString(6, emvo.getPortfolio());
			pstmt1.setString(7, emvo.getErDesc());
			pstmt1.setInt(8, emvo.getSal());
			pstmt1.setString(9, emvo.getErNum());

			int cnt = pstmt1.executeUpdate();
			if (cnt == 1) {
				updateFlag = true;
			} // end if
			return updateFlag;
	}// urTransaction1
	
	
	/**
	 * ���� ���α� ���� Ʈ�����2 ���γѹ��� ���� ��ų�� ��� ����
	 * @param con
	 * @param emvo
	 * @param erNum
	 * @return
	 */
	public boolean urTransaction2(Connection con, ErModifyVO emvo, int preSkill)throws SQLException {
		boolean flag = false;
		
		StringBuilder deleteSkill = new StringBuilder();
		deleteSkill
		.append(" delete from selected_skill ")
		.append(" where er_num = ? ");
		
		pstmt2 = con.prepareStatement(deleteSkill.toString());
		pstmt2.setString(1, emvo.getErNum());
		
		int deleteCnt = pstmt2.executeUpdate();
		
		if(deleteCnt == preSkill) { 
			flag = true;
		}
		
		return flag;
	}
	
/**
 * ���� ���α� Ʈ�����3 ���õȽ�ų �ٽ� �߰�
 * @param con
 * @param emvo
 * @throws SQLException
 */
public void urTransaction3(Connection con, ErModifyVO emvo) throws SQLException {
		
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
	 * ���� ���α��� ����
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEr(String erNum) throws SQLException {
		boolean deleteFlag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();
			String deleteQuery = " delete from er_info where er_num=? ";
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

	/**
	 * ���� ��ų��ȸ
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * ���� ���α� ���� Ʈ������� closeó��
	 * @throws SQLException
	 */
	public void closeAll() throws SQLException {
		if (pstmt3 != null) { pstmt3.close(); }
		if (pstmt2 != null) { pstmt2.close(); }
		if (pstmt1 != null) { pstmt1.close(); }
		if (con != null) { con.close(); }
	}

	/**
	 * ���� ������ ��ȸ
	 * @param erhcdto
	 * @return
	 * @throws SQLException
	 */
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
					selectEeHiring.append("	order by ei.input_date desc ");
				} else if (erhcdto.getSort().equals("���޼�")) {
					selectEeHiring.append("	order by ei.rank desc, input_date desc");
				}
			} else {
				selectEeHiring.append("	order by ei.input_date desc, input_date desc");
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
	
	/**
	 * �̷¼� Ȯ���ڸ� ã�� �޼ҵ�
	 * @param erId
	 * @param eeNum
	 * @return
	 */
/*	public String selectExtension(String eeNum) throws SQLException{
		String extension="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			String selectExt = "";
			selectExt= "select ext_resume from ee_info where ee_num=?";
			
			pstmt = con.prepareStatement(selectExt);
			pstmt.setString(1, eeNum);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				extension = rs.getString("ext_resume");
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
		return extension;
	}*/

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

//////////////////////////////////////////����� ���� //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////

/**
* ȸ�� ���� ����ϴ� method
* @param civo
* @return
* @throws SQLException
*/
public boolean insertCoInfo( CoInsertVO civo)  throws SQLException{
boolean flag=false;
Connection con=null;
PreparedStatement pstmt=null;

try {
con=getConn();

//private String erId, img1, img2, img3, img4, coName, estDate, coDesc;
//private int memberNum;
//er_id, img1, img2, img3, img4, co_name, est_Date,co_desc
StringBuilder insertCo =new StringBuilder();
insertCo
.append(" 	insert into company(er_id, img1, img2, img3, img4, co_name, est_date, co_desc, member_num	")
.append(" 	values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 	");

pstmt=con.prepareStatement(insertCo.toString());

pstmt.setString(1, civo.getErId() );
pstmt.setString(2, civo.getImg1() );
pstmt.setString(3, civo.getImg2() );
pstmt.setString(4, civo.getImg3() );
pstmt.setString(5, civo.getImg4() );
pstmt.setString(6, civo.getCoName() );
pstmt.setString(7, civo.getEstDate() );
pstmt.setString(8, civo.getCoDesc() );
pstmt.setInt(9, civo.getMemberNum() );

int cnt=0;
cnt=pstmt.executeUpdate();
if(cnt !=1 ) {
flag=true;
}//end if

}finally {
if( pstmt != null ) { pstmt.close();}
if( con != null ) { con.close();}
}//end finally

return flag;
}//insertCoInfo

//ȸ�� �߰� ���� �׽�Ʈ
//public static void main(String[] args) {
//CoInsertVO civo=new CoInsertVO("song9912", "1", "2", "3,", "4", "���ȭȸ��", "19-01-01", "����ȸ��", 10);
//System.out.println(civo);
//}//main

/**
* 19.02.17 ȸ���� ������ �������� method
* @param erId
* @return
* @throws SQLException
*/
public CoInfoVO selectCoInfo(String erId) throws SQLException {
CoInfoVO civo=null;

Connection con=null;
PreparedStatement pstmt=null;
ResultSet rs=null;

//select co_num, co_name, img1, img2, img3,img4, est_date, co_desc, member_num
try {
con=getConn();
StringBuilder selectInfo=new StringBuilder();
selectInfo
.append(" 	select co_num, co_name, img1, img2, img3, img4, est_date, co_desc, member_num	 ")
.append(" 	from company 	")
.append(" 	where er_id = ? 	");
pstmt=con.prepareStatement(selectInfo.toString() );

pstmt.setString(1, erId);

rs=pstmt.executeQuery();

if(rs.next()) {
civo=new CoInfoVO(rs.getString("co_num"), rs.getString("co_name"), rs.getString("img1"), rs.getString("img2"), 
rs.getString("img3"), rs.getString("img4"),rs.getString("est_date"), rs.getString("co_desc"),rs.getInt("member_num"));
}//end if

}finally {
if( rs !=null ) { rs.close(); }
if( pstmt !=null ) { pstmt.close(); }
if( con !=null ) { con.close(); }
}//end finally

return civo;
}//selectCoInfo

//���� �׽�Ʈ ����
//public static void main(String[] args)  throws SQLException{
//System.out.println(ErDAO.getInstance().selectCoInfo("song9912"));
//}

public boolean updateCoInfo(CoInfoVO cvo) throws SQLException{
boolean flag=false;

Connection con=null;
PreparedStatement pstmt =null;

try {
con=getConn();
//co_num, co_name, img1, img2, img3,img4, est_date, co_desc, member_num
StringBuilder updateInfo=new StringBuilder();
updateInfo
.append(" update company ")
.append(" set co_num = ?, co_name = ?, img1 = ?, img2 = ?, img3 = ?, img4 = ?, est_date = to_char(?, 'yyyy-mm-dd'), co_desc = ?, member_num = ? ")
.append(" where co_num = ? ");

pstmt=con.prepareStatement(updateInfo.toString());

pstmt.setString(1, cvo.getCoNum());
pstmt.setString(2, cvo.getCoName());
pstmt.setString(3, cvo.getImg1());
pstmt.setString(4, cvo.getImg2());
pstmt.setString(5, cvo.getImg3());
pstmt.setString(6, cvo.getImg4());
pstmt.setString(7, cvo.getEstDate());
pstmt.setString(8, cvo.getCoDesc());
pstmt.setInt(9, cvo.getMemberNum());
pstmt.setString(10, cvo.getCoNum());

int cnt = pstmt.executeUpdate();

if(cnt !=0) {
flag=true;
}//end if

}finally {
if( pstmt != null ) { pstmt.close(); }
if( con != null ) { con.close(); }
}//end finally

return flag;
}//updateCoInfo


//////////////////////////////////////////����� �� ///////////////////////////
/////////////////////////////////////////////////////////////////////////////////
	
	
	
	
}// class
