package user.common.controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

   }// ������

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
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // end if
   }// ��ư

   @Override
   public void windowClosing(WindowEvent we) {
      lv.dispose();
   }

   public void login() throws SQLException {
	   	
         String id=lv.getJtfId().getText().trim();
         String pass=new String(lv.getJpfPass().getPassword());
         
         if(id==null||id.equals("")) {
            JOptionPane.showMessageDialog(lv,"���̵� �Է��ϼ���");
            lv.getJtfId().requestFocus();
            return;
         } // end if
         if (pass == null || pass.equals("")) {
            JOptionPane.showMessageDialog(lv, "��й�ȣ�� �Է��ϼ���");
            lv.getJpfPass().requestFocus();
            return;
         }
         
         
         String userType="";
         CommonDAO c_dao = CommonDAO.getInstance();
         
         userType=c_dao.login(id, pass);
         String act = C_dao.selectActivation(id);   //er�� act �ʿ��ϱ� ������ 86������ ����� �Ű��� 
         if(userType.equals("E")) {
            emvo = C_dao.selectEeMain(id, act);
            fail.clear();
            
            // ����â�� ��� �� �̹��������� ������ �̹����� �ȶ�
            // ���� imgName������ �̿��ؼ� FileServer�� ������ ��û, �ٿ�޾� user.img.ee ������ ����
            /////////////////////////////////////////////////////////////////////////////////////////
            String imgName = emvo.getImg();
            new EeMainView(emvo);
            lv.dispose();
         }else if(userType.equals("R")){
            ermvo = C_dao.selectErMain(id, act);
            fail.clear();
            
            //////////////////////////////////////////////////////////////////////////////////////////
            // ���� imgName������ �̿��ؼ� FileServer�� ������ ��û, �ٿ�޾� user.img.co ������ ����
            String imgName = ermvo.getImg1();
            
            new ErMainView(ermvo);
            lv.dispose();
         }else{
        	 //hack ++;
        	 if(fail.containsKey(id)) {
        		 fail.put(id, fail.get(id)+1);
        	 }else {
        		 fail.put(id, 1);
        	 }//end else
          JOptionPane.showMessageDialog(lv, "���̵�� ��й�ȣ�� Ȯ�����ּ��� 5�� �� "+fail.get(id)+"�� �α��� ����");
          if(fail.get(id)==5) {
        	  JOptionPane.showMessageDialog(lv, "5ȸ�̻� �α��ο� �����Ͽ����ϴ�. ���α׷��� �����մϴ�.");
        	  ul.sendLog(id, "������ ���� �õ� : �α����� 5ȸ�̻� �����߽��ϴ�.");
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

