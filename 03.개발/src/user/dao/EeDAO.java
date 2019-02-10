package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.ee.dto.EeHiringCdtDTO;
import user.ee.vo.EeHiringVO;

public class EeDAO {
	private static EeDAO Ee_dao;
	
	public EeDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}//EeDAO
	
	public static EeDAO getInstance() {
		if(Ee_dao==null) {
			Ee_dao= new EeDAO();
		}//end if
		return Ee_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		
		Connection con = null;
		
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String id ="kanu";
		String pass ="share";
		con = DriverManager.getConnection(url, id, pass);
		return con;
	}//getConns
	
	
	
	/////02.09 선의 소스
	public List<EeHiringVO> selectEeHiring(EeHiringCdtDTO eh_dto) throws SQLException{
		List<EeHiringVO> list = new ArrayList<EeHiringVO>();
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConn();
			
			StringBuilder selectEeHiring = new StringBuilder();
			selectEeHiring.append("select ei.er_num, ei.subject, ei.education, ei.rank, ei.loc, ei.hire_type,")
			.append("to_char(ei.input_date,'yyyy-mm-dd-hh-mi') input_date, ei.sal, c.co_name	 ")
			.append("	from er_info ei ,company c	 ")
			.append("	where (ei.co_num=c.co_num) ");
			
			
			if(!(eh_dto.getCoName().trim()==null || eh_dto.getCoName().trim().equals(""))) {
				selectEeHiring.append("and (c.co_name like '%" )
				.append(eh_dto.getCoName())
				.append("%' ) ");
			}
			if(!(eh_dto.getCdt()==null || eh_dto.getCdt().equals(""))) {
				selectEeHiring.append(eh_dto.getCdt());
			}
			
			if(!(eh_dto.getSort().trim()==null || eh_dto.getSort().trim().equals(""))) {
				if(eh_dto.getSort().equals("등록일순")) {
					selectEeHiring.append("	order by ei.input_date	");
				}else if(eh_dto.getSort().equals("직급순")) {
					selectEeHiring.append("	order by ei.rank	 ");
				}else if(eh_dto.getSort().equals("급여순")) {
					selectEeHiring.append("	order by ei.sal	 ");
				}
			}else {
				selectEeHiring.append("	order by ei.input_date	");
			}
			System.out.println(selectEeHiring.toString());
			
			pstmt = con.prepareStatement(selectEeHiring.toString());
			
			rs=pstmt.executeQuery();
			EeHiringVO eh_vo= null;
			while(rs.next()) {

				eh_vo = new EeHiringVO(rs.getString("er_num"),rs.getString("subject"),rs.getString("co_name"), rs.getString("rank"),
						rs.getString("loc"), rs.getString("education"), rs.getString("hire_type"), rs.getString("input_date"),
						rs.getInt("sal"));
				list.add(eh_vo);
			}
		}finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
				
		
		return list;
	}
	
	
	//02.09선의테스트
/*	public static void main(String[] args) {
		EeDAO ee_dao = new EeDAO();
		EeHiringCdtDTO eh_dto = new EeHiringCdtDTO("직급순","","키스템프");
		try {
			ee_dao.selectEeHiring(eh_dto);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
}//class
