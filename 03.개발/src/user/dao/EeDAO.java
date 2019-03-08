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
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeInterestVO;
import user.ee.vo.EeModifyVO;
import user.ee.vo.EeRegVO;

public class EeDAO {
	private static EeDAO Ee_dao;
	private Connection con;
	private PreparedStatement pstmt1, pstmt2;
	
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

		// String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // 집 개발용
		String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";
		String id = "kanu";
		String pass = "share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConns

	////////////////////////////////////////// 선의
	////////////////////////////////////////// 소스//////////////////////////////////////////////////////////
	/**
	 * 선의 지원하기 누르면 application테이블에 데이터 넣기
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

	/**
	 * 기업정보를 모두 조회
	 * @param eh_dto
	 * @return
	 * @throws SQLException
	 */
	public List<EeHiringVO> selectEeHiring(EeHiringCdtDTO eh_dto) throws SQLException {
		List<EeHiringVO> list = new ArrayList<EeHiringVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();

			StringBuilder selectEeHiring = new StringBuilder();
			selectEeHiring.append("select ei.er_num, ei.subject, ei.education, ei.rank, ei.loc, ei.hire_type,")
					.append("to_char(ei.input_date,'yyyy-mm-dd hh:mi') input_date, ei.sal, c.co_name	 ")
					.append("	from er_info ei ,company c	 ").append("	where (ei.co_num=c.co_num) ");
			System.out.println(eh_dto.getCoName());
			if (!(eh_dto.getCoName().trim() == null || eh_dto.getCoName().trim().equals(""))) {
				selectEeHiring.append("and (c.co_name like '%").append(eh_dto.getCoName()).append("%' ) ");
			}
			if (!(eh_dto.getCdt() == null || eh_dto.getCdt().equals(""))) {
				selectEeHiring.append(eh_dto.getCdt());
			}

			if (!(eh_dto.getSort().trim() == null || eh_dto.getSort().trim().equals(""))) {
				if (eh_dto.getSort().equals("등록일순")) {
					selectEeHiring.append("	order by ei.input_date desc, input_date desc");
				} else if (eh_dto.getSort().equals("직급순")) {
					selectEeHiring.append("	order by ei.rank , input_date desc ");
				} else if (eh_dto.getSort().equals("급여순")) {
					selectEeHiring.append("	order by ei.sal	desc, input_date desc ");
				}
			} else {
				selectEeHiring.append("	order by ei.input_date desc	");
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
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값 추가
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


	/**
	 * 지원상태만 조회하는 클래스
	 * @param eeId
	 * @param erNum
	 * @return
	 * @throws SQLException
	 */
	public String selectApplication(String eeId,String erNum )throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String appStatus ="";
		try {
			con = getConn();
			StringBuilder selectAppStatus = new StringBuilder();
			
			selectAppStatus.append("select app_status from application where er_num=? and ee_id=?");

			pstmt = con.prepareStatement(selectAppStatus.toString());
			pstmt.setString(1,erNum );
			pstmt.setString(2, eeId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				appStatus= rs.getString("app_status");
			}

		} finally { // finally : 연결끊기.
			if (rs != null) {rs.close();} // end if
			if (pstmt != null) {pstmt.close();} // end if
			if (con != null) {con.close();} // end if
		} // end finally
		
		return appStatus;
	}

//	////////////////////////////////////////// 선의 소스
//	////////////////////////////////////////// 끝//////////////////////////////////////////////////////////


	//////////// 재현코드 ////////////

	/**
	 * 재현 : selectInterestErInfo : 일반사용자가 하트를 누른 구인정보를 DB에서 조회.
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

		try {// try : DB에서 조회하기

			con = getConn(); // 커넥션 얻기.

			StringBuilder slcInterestErInfo = new StringBuilder(); // 관심구인정보조회 하기
			slcInterestErInfo.append(
					" select ei.ER_NUM, ei.SUBJECT, c.CO_NAME, ei.RANK, ei.LOC, ei.EDUCATION, ei.SAL, ei.HIRE_TYPE, ei.PORTFOLIO, ei.ER_DESC,  to_char(ei.INPUT_DATE, 'yyyy-mm-dd') INPUT_DATE");
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
	}// end selectInterestErInfo

	/**
	 * 일반사용자의 지원현황 목록을 채울 값을 조회하는 메소드.
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

		try {// try : DB에서 조회하기

			con = getConn(); // 커넥션 얻기.
			StringBuilder selectAppList = new StringBuilder(); // 지원현황테이블 list.
			selectAppList.append(
					" select a.app_num, ei.er_num, ei.subject, c.co_name, ei.rank, ei.loc, ei.education, ei.hire_type, ei.sal, to_char(a.app_date,'yyyy-mm-dd') app_date, a.app_status");
			selectAppList.append(" from application a, er_info ei, company c");
			selectAppList.append(" where (a.er_num=ei.er_num) and (ei.co_num=c.co_num) and a.ee_id=?");
			pstmt = con.prepareStatement(selectAppList.toString());
			// 바인드 변수
			pstmt.setString(1, ee_id);
			// ResultSet 얻어오기.
			rs = pstmt.executeQuery();
			// VO선언 null;
			EeAppVO eavo = null;
			// VO생성 후 list에 담기
			while (rs.next()) {
				eavo = new EeAppVO(rs.getString("app_num"), rs.getString("er_num"), rs.getString("subject"),
						rs.getString("co_name"), rs.getString("rank"), rs.getString("loc"), rs.getString("education"),
						rs.getString("hire_type"), rs.getString("app_date"), rs.getString("app_status"),
						rs.getInt("sal"));
				list.add(eavo);
			} // end while

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
	}// selectAppList

	/**
	 * 재현 : 일반 사용자_지원 현황에서 자신이 지원한 회사의 er_num을 조회하는 메서드.
	 * 
	 * @param ee_id
	 * @return
	 * @throws SQLException
	 */
	public String selectErNumFromAppTb(String app_num) throws SQLException {

		String er_num = ""; // 반환할 값의 변수

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {// try : DB에서 조회하기

			con = getConn(); // 커넥션 얻기.
			String selectErNumFromAppTb = " SELECT EI.ER_NUM FROM APPLICATION A, ER_INFO EI WHERE (A.ER_NUM = EI.ER_NUM) AND APP_NUM = ? ";
			pstmt = con.prepareStatement(selectErNumFromAppTb);

			// 바인드 변수
			pstmt.setString(1, app_num);
			// ResultSet 얻어오기.
			rs = pstmt.executeQuery();

			// 값 얻기
			if (rs.next()) {
				er_num = rs.getString("er_num");
			} // end while

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

		return er_num;
	}// selectErNumFromAppTb(String ee_id)

	/**
	 * 회사 상세 정보 창을 채울 데이터를 조회하는 메서드.
	 * 
	 * @return
	 */
	public CoDetailVO selectCompany(String er_num) throws SQLException {
		// 회사 상세 정보 VO를 선언.
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

			// 바인드 변수.
			pstmt.setString(1, er_num);

			// 쿼리문 실행하고 ResultSet 받기.
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

	/* 단위 테스트용 main */
/*	public static void main(String[] args) {
		EeDAO ee_dao = EeDAO.getInstance();
		try {
			System.out.println(ee_dao.selectCompany("er_000033"));
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// main
	////////////////////////// 재현 끝 //////////////////////////

///////////////////////////////////////////////////////////////////////////////////////// 김건하 VO정리 시작///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	/**
	 * 19.02.10김건하 회원정보 입력
	 * 
	 * @param eivo
	 * @throws SQLException
	 */
	public boolean insertEeinfo(Connection con, EeInsertVO eivo) throws SQLException {
		boolean insertFlag = false; //

		StringBuilder insertInfo = new StringBuilder();
		insertInfo
		.append("  insert into ee_info(ee_num, ee_id, img, rank, loc, education, portfolio, ext_resume)  ")
		.append("  values( ee_code, ?, ?, ?, ?, ?, ?, ? )" );
		pstmt1 = con.prepareStatement(insertInfo.toString());

		pstmt1.setString(1, eivo.getEeId());
		pstmt1.setString(2, eivo.getImg());
		pstmt1.setString(3, eivo.getRank());
		pstmt1.setString(4, eivo.getLoc());
		pstmt1.setString(5, eivo.getEducation());
		pstmt1.setString(6, eivo.getPortfolio());
		pstmt1.setString(7, eivo.getExtResume());

		int cnt = pstmt1.executeUpdate();
		
		if (cnt == 1) {
			insertFlag = true;
		} // end if

		return insertFlag;
	}// insertEeinfo
	
//	public static void main(String[] args) throws SQLException {
//		Connection con=EeDAO.getInstance().getConn();
//		EeInsertVO eivo = new EeInsertVO("root", "123.jpg", "C", "서울", "고졸", "N", "test.txt");
//		System.out.println(Ee_dao.insertEeinfo(con, eivo));
//		System.out.println(eivo);
//	}// main

	//회원 번호
	public String selectUserNum(String eeId) throws SQLException {
		String userNum="";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		con=getConn();
		String userCo="select ee_num from ee_info where ee_id=? ";
		pstmt=con.prepareStatement(userCo);
		pstmt.setString(1, eeId);
		
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			userNum=rs.getString("ee_num");
		}//end if
		
		}finally {
			if( rs != null) { rs.close(); }
			if( pstmt != null) { pstmt.close(); }
			if( con != null) { con.close(); }
		}//end finally
		
		return userNum;
	}
	
	/**
	 * 	김건하 activation 처리 메소드
	 * 0302 ActivationVO 불필요해서 수정처리 - 영근
	 * @throws SQLException 
	 */
	public boolean updateActivation(Connection con, String id) throws SQLException {
		boolean updateFlag=false;
		
		StringBuilder updateActivation=new StringBuilder();
		
		updateActivation
		.append("  update user_table  ")
		.append("  set activation='Y'  ")
		.append("  where id=?  ");
		
		pstmt2=con.prepareStatement(updateActivation.toString());
		
		pstmt2.setString(1, id);
		
		int cnt=pstmt2.executeUpdate();
		
		if( cnt==1) {
			updateFlag=true;
		}//end if
		
		return updateFlag;
	}//updateActivation
	
	
	/**
	 *  트랜잭션 메소드
	 *  	김건하
	 *  기본정보 등록과 유저 activation Y로 변경하는 트랜잭션
	 * @param eeid
	 * @throws SQLException
	 */
	public boolean updateUserInfo(EeInsertVO eivo) throws SQLException {
	
		boolean updateFlag = false;
		
		try {
			con=getConn();
			con.setAutoCommit(false);
			
			try {
				boolean insert = insertEeinfo(con, eivo);
				boolean update = updateActivation(con, eivo.getEeId());
				
				if(insert && update) {
					updateFlag = true;
					con.commit();
				}else {
					con.rollback();
				}//end else
			
			}finally {
				closeAll();
			}//end finally
		
		}catch(SQLException e) {
			try {	
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return updateFlag;
	}//updateUserinfo

	/**
	 * 	연결끊어주는 메소드
	 * @throws SQLException
	 */
	public void closeAll() throws SQLException {
		if(pstmt2 != null) {
			pstmt2.close();
		}//end if
		
		if(pstmt1 != null) {
			pstmt1.close();
		}//end if
		
		if(con != null) {
			con.close();
		}//end if
		
	}//closeall
	
	
	
	/**
	 * 19.02.11 김건하 EeRegVO
	 * 
	 * @return
	 * @throws SQLException
	 */
	public EeRegVO selectEeReg(String eeid) throws SQLException {
		EeRegVO ervo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 커넥션 얻기.
			con = getConn();

			//쿼리문 수정해야 됨!!!!!!
			
			
			// 쿼리문생성
			StringBuilder selectMyInfo = new StringBuilder();
//			if() {//조건을 노이미지로 할까?
				selectMyInfo
				.append("		select id, name, gender, age ")
				.append("		from user_table ")
				.append("		where id = ? 	");
				
				
//				selectMyInfo
//				.append("		select ei.ee_id, ut.name, ut.gender, ut.age")
//				.append("		from ee_info ei, user_table ut")
//				.append("		where (ei.ee_id = ut.id) and ei.ee_id = ? 	");
				
//			}else {
//				
//			}
			pstmt = con.prepareStatement(selectMyInfo.toString());
			// 바인드변수 값 할당.
			pstmt.setString(1, eeid);

			// DB에서 조회하기s
			rs = pstmt.executeQuery(); // 쿼리실행
			if (rs.next()) {
				ervo = new EeRegVO(rs.getString("id"), rs.getString("name"), rs.getString("gender"),
						rs.getInt("age"));
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


	/**
	 * 19.02.16 모든 정보가져오는 VO
	 * 
	 * @param eeId
	 * @return
	 */
	public EeInfoVO selectEeInfo(String eeId) throws SQLException {
		EeInfoVO eivo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			StringBuilder selectInfo = new StringBuilder();
			selectInfo.append(" 	select ee_id, ei.ee_num, ei.img, ut.name, ei.rank, ei.loc, ei.education, ")
					.append("	ei.portfolio, ut.gender, ei.ext_resume, ut.age	")
					.append("	from ee_info ei, user_table ut		").append("	where (ee_id= id) and ei.ee_id= ? ");

			pstmt = con.prepareStatement(selectInfo.toString());
			pstmt.setString(1, eeId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				eivo = new EeInfoVO(rs.getString("EE_ID"), rs.getString("EE_NUM"), rs.getString("IMG"), rs.getString("NAME"),
						rs.getString("RANK"), rs.getString("LOC"), rs.getString("EDUCATION"), rs.getString("PORTFOLIO"),
						rs.getString("GENDER"), rs.getString("EXT_RESUME"), rs.getInt("AGE"));

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
		} // end finally

		return eivo;

	}// selectEeInfo

//	String eeNum, img, rank, loc, education, portfolio, extResume;
	public boolean updateEeInfo(EeModifyVO emvo) throws SQLException {
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();

			StringBuilder updateEeInfo = new StringBuilder();
			updateEeInfo
			.append("	  update ee_info	")
			.append("  set  ee_num =? , img = ? , rank = ?, loc = ? , education = ? , portfolio = ? ,	ext_resume = ? ")
			.append("	  where ee_num = ?	");

			pstmt = con.prepareStatement(updateEeInfo.toString());

			pstmt.setString(1, emvo.getEeNum());
			pstmt.setString(2, emvo.getImg());
			pstmt.setString(3, emvo.getRank());
			pstmt.setString(4, emvo.getLoc());
			pstmt.setString(5, emvo.getEducation());
			pstmt.setString(6, emvo.getPortfolio());
			pstmt.setString(7, emvo.getExtResume());
			pstmt.setString(8, emvo.getEeNum());

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
	}//UpdateEeinfo


	//////////////////////////////////////////////////////////////////////////////////////////////////////// 끝///////////////////////////////////////////////////////////////////////////////////////////////


}// class