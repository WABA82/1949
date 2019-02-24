package user.common.controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.FindIdView;
import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SignUpView;
import user.common.vo.EeMainVO;
import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

public class LoginController extends WindowAdapter implements ActionListener, MouseListener {
   private LoginView lv;
   private EeMainVO emvo;
   private ErMainVO ermvo;
   private CommonDAO C_dao;

   public LoginController(LoginView lv) {
      this.lv = lv;
      C_dao=CommonDAO.getInstance();

   }// 생성자

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
      if (ae.getSource() == lv.getJbLogin()) {
         try {
            login();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      } // end if
   }// 버튼

   @Override
   public void windowClosing(WindowEvent we) {
      lv.dispose();
   }

   public void login() throws SQLException {
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
         
         String userType="";
         CommonDAO c_dao = CommonDAO.getInstance();
         
         String act = C_dao.selectActivation(id);
         userType=c_dao.login(id, pass);
         String act = C_dao.selectActivation(id);  //er도 act 필요하기 때문에 86번줄을 여기로 옮겨줌 
         if(userType.equals("E")) {
            emvo = C_dao.selectEeMain(id, act);
            new EeMainView(emvo);
            lv.dispose();
         }else if(userType.equals("R")){
            ermvo = C_dao.selectErMain(id, act);
            new ErMainView(ermvo);
            lv.dispose();
         }else{
          JOptionPane.showMessageDialog(lv, "아이디와 비밀번호를 확인해주세요");
           lv.getJtfId().setText("");
           lv.getJpfPass().setText("");
           lv.getJtfId().requestFocus();
         }
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
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }
}

