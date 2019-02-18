package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import user.common.view.SearchAddrView;
import user.common.view.SignUpView;

public class SignUpController extends WindowAdapter implements ActionListener {
     private SignUpView suv;
     private String addrSeq;
    
     public SignUpController(SignUpView suv) {
          this.suv=suv;
     }//생성자

     @Override
     public void windowClosing(WindowEvent e) {
          suv.dispose();
     }//closing
    
     @Override
     public void actionPerformed(ActionEvent ae) {
          if(ae.getSource()==suv.getJbSignUp()) {//가입 버튼이 눌려졌을 때 다 입력받기
          String id=suv.getJtfId().getText().trim();
          String pass1 = new String(suv.getJpfPass1().getPassword());
          String pass2 = new String(suv.getJpfPass2().getPassword());
          String name = suv.getJtfName().getText().trim();
          String ssn1 = suv.getJtfSsn1().getText().trim();
          String ssn2 = new String(suv.getJpfSsn2().getPassword());
          String tel = suv.getJtfTel().getText().trim();
          String email = suv.getJtfEmail().getText().trim();
          String addr = suv.getJtfAddr1().getText().trim();
          String detailAddr = suv.getJtfAddr2().getText().trim();
          String answer = suv.getJtfAnswer().getText().trim();
               if(id.isEmpty()) {JOptionPane.showMessageDialog(suv, "아이디를 입력해주세요");
                    return;
               };
               if(id.length()>15) {
                    JOptionPane.showMessageDialog(suv, "아이디는 최대 15자까지 가능합니다." );
                    suv.getJtfId().requestFocus();
                    return;
               };//if id 15자 검증
               if( pass1==null || pass1.equals("")){
                    JOptionPane.showMessageDialog(suv, "비밀번호를 입력하세요.");
                    suv.getJpfPass1().requestFocus();
                    return;
               };//pass1 입력 받기
               if( pass2==null || pass2.equals("")){
                    JOptionPane.showMessageDialog(suv, "비밀번호를 확인해주세요.");
                    suv.getJpfPass2().requestFocus();
                    return;
               };//pass2 입력 받기
               if(name==null || name.equals("")) {
                    JOptionPane.showMessageDialog(suv, "이름을 입력해주세요");
                    suv.getJtfName().requestFocus();
                    return;
               };//이름 받기
               if(ssn1==null || ssn1.equals("")) {JOptionPane.showMessageDialog(suv, "주민번호를 입력해주세요");
                    suv.getJtfSsn1().requestFocus();return;};
               if(ssn2==null || ssn2.equals("")) {JOptionPane.showMessageDialog(suv, "주민번호를 입력해주세요");
                    suv.getJpfSsn2().requestFocus();return;};//주민번호 입력받기
               if(tel==null||tel.equals("")) {JOptionPane.showMessageDialog(suv, "연락처를 입력해주세요");
                    suv.getJtfTel().requestFocus(); return;};//연락처 받기
               if(email==null||email.equals("")) {JOptionPane.showMessageDialog(suv, "이메일을 입력해주세요");
                    suv.getJtfEmail().requestFocus(); return;};//이메일 받기
// 테스트위해 임시로 막아둠               //if(addr==null||addr.equals("")) {JOptionPane.showMessageDialog(suv, "주소를 입력해주세요");
//추후 풀어주기                    //suv.getJtfAddr1().requestFocus(); return;};//주소 받기
               if(detailAddr==null||detailAddr.equals("")) {JOptionPane.showMessageDialog(suv, "상세주소를 입력해주세요");
                    suv.getJtfAddr2().requestFocus(); return;};//상세주소 받기
               if(answer==null||answer.equals("")) {JOptionPane.showMessageDialog(suv, "질문의 답을 입력해주세요");
                    suv.getJtfAnswer().requestFocus(); return;};//질문의 답
               ///////////////////////
               if(!pass2.equals(pass1)) {JOptionPane.showMessageDialog(suv, "비밀번호가 일치하지 않습니다");
               /*     System.out.println(pass1+"/"+pass2);
                    System.out.println(!pass2.equals(pass1));
                    System.out.println(pass2!=pass1);*/
                    suv.getJpfPass1().setText("");
                    suv.getJpfPass2().setText("");
                    suv.getJpfPass1().requestFocus();
                    return;
               };//비밀번호 일치확인
               signUp();
          }else if(ae.getSource()==suv.getJbAddr()) {
               new SearchAddrView(suv, this, null); //null은 혜원이 부분 changeuser info 부분에 
          }else if(ae.getSource()==suv.getJbCancel()) {
               suv.dispose();
          }//end if

     }//버튼처리

     public void signUp() {
          System.out.println("검증완료");
     }//signUp

	public String getAddrSeq() {
		return addrSeq;
	}

	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}

	public SignUpView getSuv() {
		return suv;
	}
}//class