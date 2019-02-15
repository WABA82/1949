package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeInterestVO;
import user.er.dto.ErHiringCdtDTO;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErAddVO;
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

	////////////////////////////// 선의 //////////////////////////////////////////
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
		
	}//selectErList
	
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
	
	
	public ErDetailVO selectErDetail(String erNum)throws SQLException {
		ErDetailVO edvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectErDetail = new StringBuilder();
			selectErDetail
			.append(" select c.img1, ut.name, ut.tel, ut.email, ei.subject, c.co_name, ei.rank, ei.loc, ei.education,  ")
			.append(" ei.hire_type, ei.portfolio, ei.er_desc, ei.sal  ")
			.append(" from er_info ei, user_table ut,company c  ")
			.append(" where (ei.co_num=c.co_num) and (c.er_id= ut.id) and (er_num=?)  ");

			
			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1, erNum);
			rs = pstmt.executeQuery();
			ErListVO elvo = null;
			if(rs.next()) {
				edvo = new ErDetailVO(erNum, rs.getString("img1"), rs.getString("name"), 
						rs.getString("tel"), rs.getString("email"), rs.getString("subject"), 
						rs.getString("co_name"), rs.getString("education"), 
						rs.getString("rank"), rs.getString("loc"), rs.getString("hire_type"), 
						rs.getString("portfolio"), rs.getString("er_desc"), rs.getInt("sal"),
						selectSkill(erNum));
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
	}
	
	public DetailEeInfoVO selectDeatilEe(String eeNum, String erId) throws SQLException{
		DetailEeInfoVO devo= null;
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
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
			if (rs.next()) {
				
				devo = new DetailEeInfoVO(eeNum, rs.getString("img"), rs.getString("name"), 
						rs.getString("tel"), rs.getString("email"), rs.getString("rank"), 
						rs.getString("loc"), rs.getString("education"), rs.getString("portfolio"), 
						rs.getString("gender"), rs.getString("ext_resume"), rs.getString("interest"), rs.getInt("age"));
				
				
/*				devo = new DetailErInfoVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("name"),
						rs.getString("tel"), rs.getString("email"), rs.getString("input_date"), rs.getString("img1"),
						rs.getString("co_name"), rs.getString("education"), rs.getString("rank"), rs.getString("loc"),
						rs.getString("hire_type"), rs.getString("portfolio"), rs.getString("er_desc"),
						rs.getString("interest"), rs.getInt("sal"), selectSkill(erNum));*/
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
	}
	
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
			
			//모든 테이블에 저장되어야한다. 쿼리문 수정
			//erNum, subject, education, rank, loc, hireType, portfolio, erDesc;
			//sal;
			insertErAdd
			.append(" update er_info ")
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
	
	
	
	/*public DetailEeInfoVO selectDetailEe(String eeNum)throws SQLException {
		DetailEeInfoVO devo = null;

	public ErDetailVO selectErDetail(String erNum) throws SQLException {
		ErDetailVO edtvo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con= getConn();
			StringBuilder selectErDetail = new StringBuilder();
			selectErDetail
			.append(" select ei.img, ut.name, ei.rank, ei.loc, ei.education, ei.portfolio, ut.gender,ut.age,ut.tel ,ut.email, ei.ext_resume")
			.append(" from ee_info ei, user_table ut ")
			.append(" where (ei.ee_id=ut.id) and ee_num=? ");
			
			pstmt = con.prepareStatement(selectErDetail.toString());
			pstmt.setString(1,eeNum );
			//입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
			rs= pstmt.executeQuery();
			if(rs.next()) {
				devo = new DetailEeInfoVO(eeNum, rs.getString("img"), rs.getString("name"), rs.getString("tel"),
						rs.getString("email"),rs.getString("rank"), rs.getString("loc"), rs.getString("education"), 
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("ext_resume"),rs.getString("interest"), rs.getInt("age"));
			}//end if
			
		}finally {
			//6.
			if(con!=null) { con.close();}
			if(pstmt!=null) {pstmt.close();}
			if(rs!=null) {rs.close();}
			
		}
		
		return devo;
	}*/
	////////////////////////////////////////// 선의끝///////////////////////////////////////////////

	////////////////////////////// 재현 //////////////////////////////

	public List<ErInterestVO> selectInterestEEInfoList(String er_id) throws SQLException {
		List<ErInterestVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB에서 조회하기

			con = getConn(); // 커넥션 얻기.

			StringBuilder slcInterestEeInfo = new StringBuilder(); // 관심구인정보조회 하기
			slcInterestEeInfo.append(
					" select ie.ee_num, ei.img, ut.name, ei.rank, ei.loc, ei.education, ut.age, ei.portfolio, ut.gender, to_char(ie.input_date,'yyyy-mm-dd') input_date ");
			slcInterestEeInfo.append(" from interest_ee ie, ee_info ei, user_table ut");
			slcInterestEeInfo.append(" where (ie.ee_num = ei.ee_num) and (ei.ee_id = ut.id) and (ie.er_id=?)");
			pstmt = con.prepareStatement(slcInterestEeInfo.toString());

			// 바인드변수 값 넣기
			pstmt.setString(1, er_id);

			rs = pstmt.executeQuery();
			ErInterestVO erivo = null;

			// 조회된 데이터
			while (rs.next()) {
				erivo = new ErInterestVO(rs.getString("ee_num"), rs.getString("img"), rs.getString("name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getInt("age"),
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("input_date"));
				// 리스트에 담기.
				list.add(erivo);
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
	}// selectInterestEEInfoList

	/**
	 * 관심구직자 - 구직자 상세 정보 : 기업이 선택한 관심 구직자의 상세 정보를 조회하는 메소드.
	 * 
	 * @param er_id
	 * @param ee_num
	 * @return
	 */
	public DetailEeInfoVO selectDetailEEInfo(String er_id, String ee_num) {
		return null;
	}// selectDetailEEInfo

	public List<ErListVO> selectErInfoList(String erId) throws SQLException {
		List<ErListVO> list = new ArrayList<ErListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

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
				if (erhcdto.getSort().equals("등록일순")) {
					selectEeHiring.append("	order by ei.input_date	");
				} else if (erhcdto.getSort().equals("직급순")) {
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
	
	//////////////////////////////////////////선의끝///////////////////////////////////////////////
	/*public static void main(String[] args) {
		ErDAO er_dao = new ErDAO();
			con = getConn();

			StringBuilder selectErList = new StringBuilder();
			selectErList.append(
					" select ei.er_num,ei.subject,ei.rank,ei.loc,ei.education,ei.hire_type,to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date ");
			selectErList.append(" from er_info ei, company c ");
			selectErList.append(" where (ei.co_num = c.co_num) and (c.er_id=?) ");
			pstmt = con.prepareStatement(selectErList.toString());

			pstmt.setString(1, erId);
			rs = pstmt.executeQuery();

			ErListVO elvo = null;
			while (rs.next()) {
				elvo = new ErListVO(rs.getString("er_num"), rs.getString("subject"), rs.getString("rank"),
						rs.getString("loc"), rs.getString("education"), rs.getString("hire_type"),
						rs.getString("input_date"));
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
	}// selectErList

	/**
	 * 재현 0214 : 상세 지원 현황 창의 테이블을 채울 데이터를 조회하는 메서드.
	 * 
	 * @param er_num
	 * @return
	 */
	public List<DetailAppVO> selectDetailApplist(String er_num) throws SQLException {
		List<DetailAppVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

//			select a.app_num, eei.img, ut.name, eei.rank, eei.loc, eei.education, ut.age, eei.portfolio, ut.gender, a.app_date, a.app_status
//			from application a, user_table ut, ee_info eei
//			where (a.ee_id = ut.id) and (ut.id = eei.ee_id) and (er_num = 'er_000028');

			StringBuilder selectDetailApplist = new StringBuilder();
			selectDetailApplist.append(
					" select a.app_num, eei.img, ut.name, eei.rank, eei.loc, eei.education, ut.age, eei.portfolio, ut.gender, to_char(a.app_date,'yyyy-mm-dd') app_date, a.app_status ");
			selectDetailApplist.append(" from application a, user_table ut, ee_info eei ");
			selectDetailApplist.append(" where (a.ee_id = ut.id) and (ut.id = eei.ee_id) and er_num = ? ");
			pstmt = con.prepareStatement(selectDetailApplist.toString());

			// 반인드 변수 값 할당.
			pstmt.setString(1, er_num);

			// rs받아오기
			rs = pstmt.executeQuery();
			DetailAppVO elvo = null;
			while (rs.next()) {
				elvo = new DetailAppVO(rs.getString("app_num"), rs.getString("img"), rs.getString("name"),
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

	public static void main(String[] args) {
		try {
			System.out.println(ErDAO.getInstance().selectErInfoList("lucky012"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// main

}// class
