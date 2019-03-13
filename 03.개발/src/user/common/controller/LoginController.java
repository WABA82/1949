package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import user.common.view.FindIdView;
import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SignUpView;
import user.common.vo.EeMainVO;
import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;
import user.util.UserLog;
import user.util.UserUtil;

public class LoginController extends WindowAdapter implements ActionListener, MouseListener, KeyListener {
   private LoginView lv;
   private EeMainVO emvo;
   private ErMainVO ermvo;
   private CommonDAO C_dao;
   private UserLog ul;
   private Map<String, Integer> fail; 
   private UserUtil uu;

   public LoginController(LoginView lv) {
      this.lv = lv;
      ul=new UserLog();
      fail = new HashMap<String, Integer>();
      uu = new UserUtil();
      C_dao=CommonDAO.getInstance();
   }

   @Override
   public void keyPressed(KeyEvent ke) {
	   if(ke.getKeyCode()==KeyEvent.VK_TAB) {
		   lv.getJpfPass().requestFocus();
	   }//end if
   }//key press

   
   
   @Override
   public void mouseClicked(MouseEvent me) {
      if (me.getSource() == lv.getJlSignUp()) {
         signUp();
      } else if (me.getSource() == lv.getJlFindID()) {
         findId();
      } else if (me.getSource() == lv.getJlFindPass()) {
         findPass();
      } // end else
   }// mouseClicked

   @Override
   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == lv.getJbLogin() || ae.getSource()==lv.getJpfPass()) {
         try {
            login();
         } catch (SQLException | IOException e) {
            e.printStackTrace();
         }
      } // end if
   }

   @Override
   public void windowClosing(WindowEvent we) {
      lv.dispose();
   }

   public void login() throws SQLException, IOException {
	   	
         String id=lv.getJtfId().getText().trim();
         String pass=new String(lv.getJpfPass().getPassword());
         
         if(id==null||id.equals("")) {
            JOptionPane.showMessageDialog(lv,"아이디를 입력하세요");
            lv.getJtfId().requestFocus();
            return;
         } // end if
         if (pass == null || pass.equals("")) {
            JOptionPane.showMessageDialog(lv, "비밀번호를 입력하세요");
            lv.getJpfPass().requestFocus();
            return;
         }
         
         pass = uu.shaEncoding(pass);
         
         String userType="";
         CommonDAO c_dao = CommonDAO.getInstance();
         
         userType=c_dao.login(id, pass);
         String act = C_dao.selectActivation(id);   
         if(userType.equals("E")) {
            emvo = C_dao.selectEeMain(id, act);
            fail.clear();
            
            // 메인창을 띄울 때 이미지파일이 없으면 이미지가 안뜸
            // 따라서 imgName변수를 이용해서 FileServer에 파일을 요청, 다운받아 user.img.ee 폴더에 저장
            String imgName = emvo.getImg();

            
            /////////////////// 정미가 기존에 구현한 파일 유무 판단 수정 // 영근0313 //////////////////////////////////////
            File dir = new File("C:/dev/1949/03.개발/src/user/img/ee/");
            
            boolean noFileFlag = false;
            String[] filesIHad = dir.list();
            
            for (int i=0; i<filesIHad.length; i++) {
            	
            	if (filesIHad[i].equals(imgName)) {
            		noFileFlag = true;
            	}
            }
            	
            if(!noFileFlag) { // true면 파일 존재
            	Socket client = null;
            	DataOutputStream dos = null;
            	DataInputStream dis =null;
            	FileOutputStream fos =null;
            	
            	uu.reqFile(imgName, "ee", client, dos, dis, fos);
            }//end if
            

            new EeMainView(emvo);
            lv.dispose();
         }else if(userType.equals("R")){
            ermvo = C_dao.selectErMain(id, act);
            fail.clear();
            

			// 따라서 imgName변수를 이용해서 FileServer에 파일을 요청, 다운받아 user.img.co 폴더에 저장
			String imgName = ermvo.getImg1();

			/////////////////// 정미가 기존에 구현한 파일 유무 판단 수정 // 영근0313
			/////////////////// //////////////////////////////////////
			File dir = new File("C:/dev/1949/03.개발/src/user/img/ee/");

			boolean noFileFlag = false;
			String[] filesIHad = dir.list();

			for (int i = 0; i < filesIHad.length; i++) {

				if (filesIHad[i].equals(imgName)) {
					noFileFlag = true;
				}
			}

			if (!noFileFlag) { // true면 파일 존재
				Socket client = null;
				DataOutputStream dos = null;
				DataInputStream dis = null;
				FileOutputStream fos = null;

				uu.reqFile(imgName, "co", client, dos, dis, fos);
			} // end if

            new ErMainView(ermvo);
            lv.dispose();
         }else{
        	 //hack ++;
        	 if(fail.containsKey(id)) {
        		 fail.put(id, fail.get(id)+1);
        	 }else {
        		 fail.put(id, 1);
        	 }//end else
          JOptionPane.showMessageDialog(lv, "아이디와 비밀번호를 확인해주세요 5번 중 "+fail.get(id)+"번 로그인 실패");
          if(fail.get(id)==5) {
        	  JOptionPane.showMessageDialog(lv, "5회이상 로그인에 실패하였습니다. 프로그램을 종료합니다.");
        	  ul.sendLog(id, "["+id+"]"+"비정상 접속 시도");
        	  System.exit(-1);
        	  return;
          }//hack
           lv.getJtfId().setText("");
           lv.getJpfPass().setText("");
           lv.getJtfId().requestFocus();
         }//end else
         
      }// login

   public void signUp() {
      new SignUpView(lv); 
   }// signUp

   public void findId() {
      new FindIdView(lv);
   }// findId

   public void findPass() {
      new FindPassView(lv);
   }// findPass

   
   
   
   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}

@Override
public void keyTyped(KeyEvent e) {}

@Override
public void keyReleased(KeyEvent e) {}
}

