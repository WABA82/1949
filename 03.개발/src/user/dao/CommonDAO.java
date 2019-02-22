package user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import user.common.vo.AddrVO;
import user.common.vo.EeMainVO;
import user.common.vo.ErMainVO;
import user.common.vo.FindIdVO;
import user.common.vo.FindPassVO;
import user.common.vo.SetPassVO;
import user.common.vo.UserInfoVO;
import user.common.vo.UserInsertVO;
import user.common.vo.UserModifyVO;
import user.common.vo.UserModifyWithoutPassVO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

public class CommonDAO {
    private static CommonDAO C_dao;
    
    private CommonDAO() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CommonDAO getInstance() {
        if (C_dao == null) {
            C_dao = new CommonDAO();
        }
        return C_dao;
    }

    private Connection getConn() throws SQLException {
        Connection con = null;

        String url = "jdbc:oracle:thin:@211.63.89.144:1521:orcl";// 학원에서 바꿀것!!
        String id = "kanu";
        String pass = "share";
        con = DriverManager.getConnection(url, id, pass);
        return con;
    }

    /**
     * 박정미 로그인 기능 
     * @param id
     * @param pass
     * @return
     * @throws SQLException
     */
    public String login(String id, String pass) throws SQLException {
       String userType = "";

       Connection con = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          con = getConn();

          String match = "SELECT USER_TYPE FROM USER_TABLE WHERE ID=? AND PASS=?";
          pstmt = con.prepareStatement(match);
          pstmt.setString(1, id);
          pstmt.setString(2, pass);

          rs = pstmt.executeQuery();

          if (rs.next()) {
             userType = rs.getString("USER_TYPE");
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
       }
       return userType;
    }// login

    /**
     * 박정미 주소 검색 완성
     * @param dong
     * @return
     * @throws SQLException
     */
    public List<AddrVO> selectAddr(String dong) throws SQLException {
       List<AddrVO> list = new ArrayList<AddrVO>();

       Connection con = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          con = getConn();
          String selectAddr = " select seq, zipcode, sido, gugun, dong, nvl(bunji,' ') bunji from zipcode where dong like '%'||?||'%'   ";
          pstmt= con.prepareStatement(selectAddr);
          pstmt.setString(1, dong);
          rs = pstmt.executeQuery();
          AddrVO av = null;
          while (rs.next()) {
             av = new AddrVO(rs.getString("seq"), rs.getString("zipcode"), rs.getString("sido"),
                   rs.getString("gugun"), rs.getString("dong"), rs.getString("bunji"));
             list.add(av);
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
       return list;
    }//주소 검색 완성 여기까지 

    /**
     * 박정미 유저 정보 등록  ////구현중
     * @param uivo
     * @throws SQLException
     */
    public String insertUser(UserInsertVO uivo)throws SQLException{
       String resultMsg="";
       
       Connection con = null;
       CallableStatement cstmt = null;
       try {
       con = getConn();
       
       //3. 프로시저 실행 객체 얻기
       cstmt = con.prepareCall("{ call insert_sign_up_proc(?,?,?,?,?,?,?,?,?,?,?,?) }");
/*i_id IN VARCHAR2, i_pass IN VARCHAR2, i_name IN VARCHAR2,
       i_ssn IN CHAR, i_tel IN VARCHAR2, i_email IN VARCHAR2,
       i_seq IN NUMBER, i_addr_detail IN VARCHAR2, i_q_type IN CHAR,
       i_answer IN VARCHAR2, i_user_type IN CHAR,
       msg OUT VARCHAR2*/
       cstmt.setString(1,uivo.getId());
       cstmt.setString(2,uivo.getPass());
       cstmt.setString(3,uivo.getName());
       cstmt.setString(4, uivo.getSsn());
       cstmt.setString(5,uivo.getTel());
       cstmt.setString(6,uivo.getEmail());
       cstmt.setInt(7,Integer.parseInt(uivo.getAddrSeq().trim()));
       cstmt.setString(8,uivo.getAddrDetail());
       cstmt.setString(9,uivo.getQuestionType());
       cstmt.setString(10,uivo.getAnswer());
       cstmt.setString(11,uivo.getUserType());
       
       cstmt.registerOutParameter(12, Types.VARCHAR);
       
       cstmt.execute();
       resultMsg = cstmt.getString(12);
       System.out.println(resultMsg);
       }finally {
          if(cstmt!=null) {cstmt.close();}
          if(con!=null) {con.close();}
       }//end finally
       return resultMsg;

    }//insertUser

    /**
     * 최혜원 아이디 찾기
     * @param fivo
     * @return
     * @throws SQLException
     */
    public String selectFindId(FindIdVO fivo)throws SQLException {
        String searchId="";
        
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        try {
            con = getConn();
            String selectId = "select id from user_table where name=? and tel=?";

            pstmt = con.prepareStatement(selectId);

            pstmt.setString(1, fivo.getName());
            pstmt.setString(2, fivo.getTel());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                searchId = rs.getString("id");
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
        return searchId;

    }
    
    /**
     * 최혜원 비밀번호 변경전 회원정보받아 검증
     * @param fpvo
     * @return
     * @throws SQLException
     */
    public boolean selectFindPass(FindPassVO fpvo) throws SQLException {
        int searchPass = 0;
        boolean flag = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConn();

            String count = "select count(*) login from user_table where id=? and question_type=? and answer=?";
            pstmt = con.prepareStatement(count);

            pstmt.setString(1, fpvo.getId());
            pstmt.setString(2, fpvo.getqType());
            pstmt.setString(3, fpvo.getAnswer());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                searchPass = rs.getInt("login");
                
                
                if(searchPass==1) {
                    flag = true;
                }
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
        return flag;
    }//selectFindPass
    
    /**
     * 최혜원 비밀번호 변경하기
     * @param spvo
     * @return
     * @throws SQLException
     */
    public boolean updatePass(SetPassVO spvo) throws SQLException {
        boolean flag = false;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConn();

            String updatePass = "update user_table set pass=? where id=?";
            pstmt = con.prepareStatement(updatePass);

            pstmt.setString(1, spvo.getNewPass());
            pstmt.setString(2, spvo.getId());

            int cnt = pstmt.executeUpdate();
            if (cnt == 1) {
                flag = true;
            }
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return flag;
    
    }//updatePass
    
    /**
     * 최혜원 사용자 정보조회
     * @param id
     * @return
     * @throws SQLException
     */
    public UserInfoVO selectUserInfo(String id) throws SQLException {
        UserInfoVO uivo=null;
        
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        
        try {
            con=getConn();
            StringBuilder selectUserInfo=new StringBuilder();
            selectUserInfo.append("select u.id, u.name, u.tel, z.seq, z.zipcode,")
            .append("z.sido||' '||z.gugun||' '||z.dong||' '||z.bunji addr1 ,")
            .append("   u.addr_detail addr2, u.email")
            .append("      from user_table u, zipcode z ")
            .append("      where u.addr_seq=z.seq and u.id in ? ");
            //.append("      and u.id in ? ");
            
            pstmt=con.prepareStatement(selectUserInfo.toString());
            
            pstmt.setString(1, id);
            
            rs=pstmt.executeQuery();
            
            if(rs.next()) {
                uivo=new UserInfoVO(rs.getString("id"), rs.getString("name"), rs.getString("tel")
                                            ,rs.getString("seq"), rs.getString("zipcode"), rs.getString("addr1")
                                            ,rs.getString("addr2"), rs.getString("email"));
            }
            
        }finally {
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(pstmt!=null) {pstmt.close();}
        }
        return uivo;
    }
    
    /**
     * 최혜원 사용자 정보 수정
     * @param umvo
     * @return
     * @throws SQLException
     */
    public boolean updateUserInfo(UserModifyVO umvo) throws SQLException {
        boolean flag=false;
        
        Connection con=null;
        PreparedStatement pstmt=null;
        
        try {
            con=getConn();
            
            StringBuilder updateUserInfo=new StringBuilder();
            
            updateUserInfo.append("update user_table    ")
            .append("        set  name=?, pass=?, tel=?, addr_seq=?, addr_detail=?, email=? ")
            .append("        where id=? ");
            
            pstmt=con.prepareStatement(updateUserInfo.toString());
            
            pstmt.setString(1, umvo.getName());
            pstmt.setString(2, umvo.getPass());
            pstmt.setString(3, umvo.getTel());
            pstmt.setString(4, umvo.getSeq());
            pstmt.setString(5, umvo.getAddrDetail());
            pstmt.setString(6, umvo.getEmail());
            pstmt.setString(7, umvo.getId());
            
            int cnt=pstmt.executeUpdate();
             if(cnt==1) {
                 flag=true;
             }
        }finally {
            if(pstmt!=null) {pstmt.close();}
            if(con!=null) {con.close();}
        }
    
        return flag;
        
    }
    /**
     * 최혜원 사용자 정보 수정2(비밀번호 수정하지 않을 때)
     * @param umvo
     * @return
     * @throws SQLException
     */
    public boolean updateUserInfoWithoutPass(UserModifyWithoutPassVO umvo2) throws SQLException {
       boolean flag=false;
       
       Connection con=null;
       PreparedStatement pstmt=null;
       
       try {
          con=getConn();
          
          StringBuilder updateUserInfo=new StringBuilder();
          
          updateUserInfo.append("update user_table    ")
          .append("        set  name=?, tel=?, addr_seq=?, addr_detail=?, email=? ")
          .append("        where id=? ");
          
          pstmt=con.prepareStatement(updateUserInfo.toString());
          
          pstmt.setString(1, umvo2.getName());
          pstmt.setString(2, umvo2.getTel());
          pstmt.setString(3, umvo2.getSeq());
          pstmt.setString(4, umvo2.getAddrDetail());
          pstmt.setString(5, umvo2.getEmail());
          pstmt.setString(6, umvo2.getId());
          
          int cnt=pstmt.executeUpdate();
          if(cnt==1) {
             flag=true;
          }
       }finally {
          if(pstmt!=null) {pstmt.close();}
          if(con!=null) {con.close();}
       }
       
       return flag;
       
    }
    
    /**
     * 최혜원 회원 정보 삭제
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean deleteUserInfo(String id)throws SQLException {
        boolean flag=false;
        Connection con=null;
        PreparedStatement pstmt=null;
        
        try {
            con=getConn();
            
            String deleteUserInfo="delete from user_table where id=? ";
            
            pstmt=con.prepareStatement(deleteUserInfo);
            
            pstmt.setString(1, id);
            
            int cnt=pstmt.executeUpdate();
            
            if( cnt == 1) {
                flag=true;
                System.out.println(flag);
            }
        }finally {
            if(pstmt != null) { pstmt.close(); }
            if(con != null) { con.close(); }
        }
        
        return flag;
    }
    /**
     * 박정미 - eemainVO를 위해서 activation을 받아오는 메서드
     * @param id
     * @return
     * @throws SQLException
     */
    public String selectActivation(String id) throws SQLException {
       String act="";
       
       Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        //드라이버 로딩
        try {
           con=getConn();
           //쿼리문 생성
           StringBuilder selectAct= new StringBuilder();
           selectAct
           .append("      select activation      ")
           .append("      from user_table    ")
           .append("      where id = ?   "   );
           
           pstmt=con.prepareStatement(selectAct.toString());
           pstmt.setString(1,id );
           
           rs=pstmt.executeQuery();
           
           if(rs.next()) {
              act =  rs.getString("activation");
           }//end if
           
        }finally {
           if( rs != null) { rs.close(); }
           if( pstmt != null) { pstmt.close(); }
           if( con != null) { con.close(); }
        }//end finally
        return act;
    }//selectActivation
    
    
    /**
     *    김건하 아이디 받기        *****회원가입 한 아이디 비번이 로그인 안되는 경우를 해결 - 메서드를 추가해줬음 02-21 
     * @return
     * @param eeId
     * @throws SQLException
     */
    public EeMainVO selectEeMain(String eeid, String act) throws SQLException {
       EeMainVO emvo=null;
       
       Connection con=null;
       PreparedStatement pstmt=null;
       ResultSet rs=null;
       
       //드라이버 로딩
       try {
          con=getConn();
          //쿼리문 생성
          
          StringBuilder selectMyInfo= new StringBuilder();
          if(act.equals("Y")) {
             selectMyInfo
             .append("      select id, name, img, activation      ")
             .append("      from ee_info ei, user_table ut   ")
             .append("      where (ee_id = id) and ei.ee_id = ?   "   );
          }else{
             selectMyInfo
             .append("      select id, name,  activation      ")
             .append("      from user_table  ")
             .append("      where id = ?   "   );
          }
          
          pstmt=con.prepareStatement(selectMyInfo.toString());
          pstmt.setString(1,eeid );
          
          rs=pstmt.executeQuery();
          
          if(rs.next()) {
             if(act.equals("Y")) {
                emvo = new EeMainVO(rs.getString("id"),rs.getString("name"), rs.getString("img"), rs.getString("activation"));
             }else {
                emvo = new EeMainVO(rs.getString("id"),rs.getString("name"), "no_ee_img.png", rs.getString("activation"));
             }
          }//end if
          
       }finally {
          if( rs != null) { rs.close(); }
          if( pstmt != null) { pstmt.close(); }
          if( con != null) { con.close(); }
       }//end finally
       
       return emvo;
    }// selectEeMain
    
       /**
        *박정미 er 아이디 받아오기 (출력됨)
     * @param id
     * @return
     * @throws SQLException
     */
    public ErMainVO selectErMain(String id) throws SQLException {
          ErMainVO emv=null;
          
          Connection con =null;
          PreparedStatement pstmt =null;
          ResultSet rs = null;
          
          try {
             con =getConn();
             
             StringBuilder selectErInfo = new StringBuilder();
             selectErInfo.append(" select ut.id, ut.name, co.img1, ut.activation ")
             .append(" from company co, user_table ut ").append(" where (ut.id=co.er_id) ")
             .append(" and ut.id=? ");
             pstmt =con.prepareStatement(selectErInfo.toString());
             
             pstmt.setString(1, id );
             rs=pstmt.executeQuery();
             
             if(rs.next()){
                emv= new ErMainVO(rs.getString("id"), rs.getString("name"),
                      rs.getString("img1"), rs.getString("activation"));
                //System.out.println(emv); 값 받았는지 확인
             }
          }finally {
             if(rs!=null) {   rs.close();   }
             if(pstmt!=null) {pstmt.close();}
             if(con!=null) {con.close();}
          }//end finally
          return emv;
       }//selectErMain
    }