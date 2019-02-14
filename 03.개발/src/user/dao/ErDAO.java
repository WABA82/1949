package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInterestVO;
import user.er.dto.ErHiringCdtDTO;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErAddVO;
import user.er.vo.ErDefaultVO;
import user.er.vo.ErDetailVO;
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
	
	//////////////////////////////���� //////////////////////////////////////////
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
	}//selectErList
	
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
	}//insertErAdd
	
	public boolean insertSkill(String erId) {
		boolean insertSkillFlag = false;
		
		return insertSkillFlag;
	}//insertSkill
	
	
	public boolean updateErModify(ErModifyVO emvo)throws SQLException {
		boolean updateFlag =false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			StringBuilder insertErAdd = new StringBuilder();
			
			//��� ���̺� ����Ǿ���Ѵ�. ������ ����
			//erNum, subject, education, rank, loc, hireType, portfolio, erDesc;
			//sal;
			insertErAdd
			.append(" update er_info ")
			.append(" set subject=?,education=?,rank=?,loc=?,hire_type=?, portfolio=?, er_desc=?, sal=? ")
			.append(" where er_num=?  ");
			
			pstmt = con.prepareStatement(insertErAdd.toString());

			System.out.println(emvo);
			pstmt.setString(1, emvo.getSubject());
			pstmt.setString(2, emvo.getEducation());
			pstmt.setString(3, emvo.getRank());
			pstmt.setString(4, emvo.getLoc());
			pstmt.setString(5, emvo.getHireType());
			pstmt.setString(6, emvo.getPortfolio());
			pstmt.setString(7, emvo.getErDesc());
			pstmt.setInt(8, emvo.getSal());
			pstmt.setString(9, emvo.getErNum());

			int cnt = pstmt.executeUpdate();
			if(cnt==1) {
				updateFlag=true;
			}//end if

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return updateFlag;
	}//updateErModify
	
	public boolean modifySkill(ErDetailVO emvo, String erNum) {
		boolean updateSkillFlag = false;
		
		return updateSkillFlag;
	}//updateSkill
	
	public boolean deleteEr(String erNum) throws SQLException {
		boolean deleteFlag= false;

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
	}//deleteEr
	
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
	
	
	public DetailEeInfoVO selectDetailEe(String eeNum)throws SQLException {
		DetailEeInfoVO devo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		
		try {
			con= getConn();
			StringBuilder selectErDetail = new StringBuilder();
			selectErDetail
			.append(" select ei.img, ut.name, ei.rank, ei.loc, ei.education, ei.portfolio, ut.gender,ut.age,ut.tel ,ut.email, ei.ext_resume")
			.append(" from ee_info ei, user_table ut ")
			.append(" where (ei.ee_id=ut.id) and ee_num=? ");
			
			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1,eeNum );
			//�Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�
			rs= pstmt.executeQuery();
			if(rs.next()) {
				devo = new DetailEeInfoVO(eeNum, rs.getString("img"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"),rs.getString("rank"), rs.getString("loc"), rs.getString("education"), 
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("ext_resume")," ", rs.getInt("age"));
			}//end if
			
		//String eeNum, String img, String name, String tel, String email, String rank, String loc,
		//String education, String portfolio, String gender, String extResume, String interest, int age
		}finally {
			//6.
			if(con!=null) { con.close();}
			if(pstmt!=null) {pstmt.close();}
			if(rs!=null) {rs.close();}
			
		}
		
		return devo;
	}

	public List<ErInterestVO> selectInterestEEInfoList(String er_id)throws SQLException {
		List<ErInterestVO> list = new ArrayList<>();
		
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
			pstmt.setString(1, er_id);

			rs = pstmt.executeQuery();
			EeInterestVO eivo = null;
			// ��ȸ�� ������
			while (rs.next()) {
				eivo = new EeInterestVO(rs.getString("er_num"), rs.getString("SUBJECT"), rs.getString("CO_NAME"),
						rs.getString("RANK"), rs.getString("LOC"), rs.getString("EDUCATION"), rs.getString("HIRE_TYPE"),
						rs.getString("INPUT_DATE"), rs.getInt("SAL"));
//				list.add(eivo);
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
		
	}//selectInterestEEInfoList
	
	public ErDefaultVO selectErDefault(String erId) throws SQLException{
		ErDefaultVO edfvo= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		
		try {
			con= getConn();
			StringBuilder selectErDetail = new StringBuilder();
			
			selectErDetail
			.append(" select c.img1, ut.name, ut.tel, ut.email, c.co_name ")
			.append(" from company c, user_table ut ")
			.append(" where (c.er_id=ut.id) and (c.er_id=?) ");
			
			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1,erId );
			rs= pstmt.executeQuery();
			//�Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� �� �߰�

			if(rs.next()) {
				edfvo = new ErDefaultVO(erId, rs.getString("img1"), rs.getString("name"),rs.getString("tel"),rs.getString("email"),rs.getString("co_name"));
			}//end if
		}finally {
			//6.
			if(rs!=null) { rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(rs!=null) {rs.close();}
			
		}
		
		return edfvo;
	}
	
	public List<ErHiringVO> selectErHiring(ErHiringCdtDTO erhcdto) throws SQLException{
		List<ErHiringVO> list =new ArrayList<ErHiringVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			StringBuilder selectEeHiring = new StringBuilder();

			selectEeHiring.append(" select ei.ee_num, ei.img, ut.name, ei.rank, ei.loc, ")
					.append(" ei.education, ut.age, ei.portfolio, ut.gender, to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date ")
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
			ErHiringVO erhvo =null;
			while (rs.next()) {
				erhvo = new ErHiringVO(rs.getString("ee_num"), rs.getString("img"), rs.getString("name"), 
						rs.getString("rank"), rs.getString("loc"),rs.getString("education"), 
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("input_date"), rs.getInt("age"));
				list.add(erhvo);
			}
		} finally {
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}
		
		return list;
	}
	
	//////////////////////////////////////////���ǳ�///////////////////////////////////////////////
	/*public static void main(String[] args) {
		ErDAO er_dao = new ErDAO();
		try {
			er_dao.selectErList("lucky012");
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	
	
	//////////////////////////////////////////���� ����///////////////////////////////////////////////
	
	
	
}// class


