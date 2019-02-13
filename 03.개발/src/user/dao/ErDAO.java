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
import user.er.vo.ErAddVO;
import user.er.vo.ErDefaultVO;
import user.er.vo.ErDetailVO;
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
	
	//////////////////////////////선의 //////////////////////////////////////////
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
			
			//모든 테이블에 저장되어야한다. 쿼리문 수정
			//erNum, subject, education, rank, loc, hireType, portfolio, erDesc;
			//sal;
			insertErAdd
			.append(" update er_info ")
			.append(" set subject=?,education=?,rank=?,loc=?,hire_type=?, portfolio=?, er_desc=?,sal=? ")
			.append(" where er_num=?;  ");
			
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

			int cnt= pstmt.executeUpdate();
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
	
	
	public ErDetailVO selectErDetail(String erNum)throws SQLException {
		ErDetailVO edtvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		
		try {
		//1.
		//2.
			con= getConn();
		//3.
			StringBuilder selectErDetail = new StringBuilder();
			
			selectErDetail
			.append(" select ei.er_num, c.img1 , ut.name, ut.tel, ")
			.append(" ut.email, ei.subject, c.co_name, ei.education, ei.rank, ei.sal,ei.er_desc, ei.loc, ei.hire_type,ei.portfolio ")
			.append(" from er_info ei, company c, user_table ut ")
			.append(" where (ei.co_num=c.co_num)and(c.er_id= ut.id)and(er_num=?) ");
			pstmt = con.prepareStatement(selectErDetail.toString());
		//4.
			pstmt.setString(1,erNum );
		//5.
			rs= pstmt.executeQuery();
			//입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가

			if(rs.next()) {
				edtvo = new ErDetailVO(erNum, rs.getString("img1"), rs.getString("name"), rs.getString("tel"), 
						rs.getString("email"), rs.getString("subject"), rs.getString("co_name"), rs.getString("education"), 
						rs.getString("rank"), rs.getString("loc"),rs.getString("hire_type"),rs.getString("portfolio"),
						rs.getString("er_desc"), rs.getInt("sal"), selectSkill(erNum));
			}//end if
		}finally {
			//6.
			if(rs!=null) { rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(rs!=null) {rs.close();}
			
		}
		
		return edtvo;
	}

	public List<ErInterestVO> selectInterestEEInfoList(String er_id) {
		List<ErInterestVO> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB에서 조회하기

			con = getConn(); // 커넥션 얻기.

			StringBuilder slcInterestErInfo = new StringBuilder(); // 관심구인정보조회 하기
			slcInterestErInfo
					.append(" select ei.ER_NUM, ei.SUBJECT, c.CO_NAME, ei.RANK, ei.LOC, ei.EDUCATION, ei.SAL,");
			slcInterestErInfo.append(
					" ei.HIRE_TYPE, ei.PORTFOLIO, ei.ER_DESC,  to_char(ei.INPUT_DATE, 'yyyy-dd-mm') INPUT_DATE");
			slcInterestErInfo.append(" from interest_er ie, er_info ei, company c");
			slcInterestErInfo.append(" where (ie.er_num = ei.er_num) and (c.co_num=ei.co_num) and ie.ee_id = ?");
			pstmt = con.prepareStatement(slcInterestErInfo.toString());

			// 바인드변수 값 넣기
			pstmt.setString(1, ee_id);

			rs = pstmt.executeQuery();
			EeInterestVO eivo = null;
			// 조회된 데이터
			while (rs.next()) {
				eivo = new EeInterestVO(rs.getString("er_num"), rs.getString("SUBJECT"), rs.getString("CO_NAME"),
						rs.getString("RANK"), rs.getString("LOC"), rs.getString("EDUCATION"), rs.getString("HIRE_TYPE"),
						rs.getString("INPUT_DATE"), rs.getInt("SAL"));
				list.add(eivo);
			} // end if

		} finally { // finally : 연결끊기.
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
		
		return null;
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
			//입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가

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
	
	//////////////////////////////////////////선의끝///////////////////////////////////////////////
	/*public static void main(String[] args) {
		ErDAO er_dao = new ErDAO();
		try {
			er_dao.selectErList("lucky012");
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	
	
	//////////////////////////////////////////재현 시작///////////////////////////////////////////////
	
	
	
}// class


