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

	////////////////////////////// 선의시작 //////////////////////////////////////////
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
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
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
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가

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

	////////////////////////////////////////// 선의끝///////////////////////////////////////////////

	/***************************** 이하 재현 *****************************/

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
			ErHiringForInterestVO ehfivo = null;

			// 조회된 데이터
			while (rs.next()) {
				ehfivo = new ErHiringForInterestVO(rs.getString("ee_num"), rs.getString("img"), rs.getString("name"),
						rs.getString("rank"), rs.getString("loc"), rs.getString("education"), rs.getInt("age"),
						rs.getString("portfolio"), rs.getString("gender"), rs.getString("input_date"));
				// 리스트에 담기.
				list.add(ehfivo);
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
	 * 재현 0214 : 상세 지원 현황 창의 테이블을 채울 데이터를 조회하는 메서드.
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

			// 반인드 변수 값 할당.
			pstmt.setString(1, er_num);

			// rs받아오기
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
	 * 재현 : 지원 현황 - 상세 지원 현황 - 지원자 상세 정보 창의 텍스트필드를 채울 정보 조회.
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
	 * DB application 지원상태를 수락으로 변경하는 메서드.
	 */
	public boolean updateAppSatus(ErAppStatusVO easvo) throws SQLException {

		boolean flag = false;

		// * DEFAULT 'U'
		// * 'U' - unread 응답대기
		// * 'R' - read 열람
		// * 'A' - approved 지원수락
		// * 'D' - denied 지원거절

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
				// 1이 아닌 경우 문제가 발생한 것.
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

	/* 재현 : 단위 테스트용 main메서드. */
	public static void main(String[] args) {
		try {
			System.out.println(ErDAO.getInstance().updateAppSatus(new ErAppStatusVO("app_000001", "u")));
//			System.out.println(ErDAO.getInstance().selectDetailAppEe("app_000001"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// main

	/***************************** 재현 끝 *****************************/

//////////////////////////////////////////김건하 시작 //////////////////////////////////////
////////////////////////////////////////////////////////////////////////////

/**
* 회사 값을 등록하는 method
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

//회사 추가 단위 테스트
//public static void main(String[] args) {
//CoInsertVO civo=new CoInsertVO("song9912", "1", "2", "3,", "4", "김건화회사", "19-01-01", "좋은회사", 10);
//System.out.println(civo);
//}//main

/**
* 19.02.17 회사의 정보를 가져오는 method
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

//단위 테스트 성공
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
.append(" 	update company 	")
.append(" 	co_num =?, co_name =?, img1=? ,img2= ? ,img3= ?, img4= ? ")
.append("   est_date=?, co_desc =?, member_num=? 	")
.append(" 	where er_id= ?  ");

pstmt=con.prepareStatement(updateInfo.toString());

pstmt.setString(1, cvo.getCoNum());
pstmt.setString(2, cvo.getCoName() );
pstmt.setString(3, cvo.getImg1());
pstmt.setString(4, cvo.getImg2());
pstmt.setString(5, cvo.getImg3());
pstmt.setString(6, cvo.getImg4());
pstmt.setString(7, cvo.getEstDate());
pstmt.setString(8, cvo.getCoDesc());
pstmt.setInt(9, cvo.getMemberNum());

int cnt=pstmt.executeUpdate();

if(cnt !=0) {
flag=true;
}//end if

}finally {
if( pstmt != null ) { pstmt.close(); }
if( con != null ) { con.close(); }
}//end finally

return flag;
}//updateCoInfo


//////////////////////////////////////////김건하 끝 ///////////////////////////
/////////////////////////////////////////////////////////////////////////////////
	
	
	
	
}// class
