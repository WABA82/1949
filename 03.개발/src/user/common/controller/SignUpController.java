package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import user.common.view.SearchAddrView;
import user.common.view.SignUpView;
import user.common.vo.UserInsertVO;
import user.dao.CommonDAO;

public class SignUpController extends WindowAdapter implements ActionListener {
     private SignUpView suv;
     private String addrSeq;
     private CommonDAO c_dao;
    
     public SignUpController(SignUpView suv) {
          this.suv=suv;
          c_dao=CommonDAO.getInstance();
     }//������

     @Override
     public void windowClosing(WindowEvent e) {
          suv.dispose();
     }//closing
    
     @Override
     public void actionPerformed(ActionEvent ae) {
          if(ae.getSource()==suv.getJbSignUp()) {//���� ��ư�� �������� �� �� �Է¹ޱ�
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
               if(id.isEmpty()) {JOptionPane.showMessageDialog(suv, "���̵� �Է����ּ���");
                    return;
               };
               if(id.contains(" ")) {JOptionPane.showMessageDialog(suv, "���̵� ������ �Է��� �� �����ϴ�."); return;};
               if(id.length()>15) {
                    JOptionPane.showMessageDialog(suv, "���̵�� �ִ� 15�ڱ��� �����մϴ�." );
                    suv.getJtfId().requestFocus();
                    return;
               };//if id 15�� ����
               if( pass1==null || pass1.equals("")){
                    JOptionPane.showMessageDialog(suv, "��й�ȣ�� �Է��ϼ���.");
                    suv.getJpfPass1().requestFocus();
                    return;
               };//pass1 �Է� �ޱ�
               if( pass2==null || pass2.equals("")){
                    JOptionPane.showMessageDialog(suv, "��й�ȣ�� Ȯ�����ּ���.");
                    suv.getJpfPass2().requestFocus();
                    return;
               };//pass2 �Է� �ޱ�
               if(name==null || name.equals("")) {
                    JOptionPane.showMessageDialog(suv, "�̸��� �Է����ּ���");
                    suv.getJtfName().requestFocus();
                    return;
               };//�̸� �ޱ�
               if(ssn1==null || ssn1.equals("")) {JOptionPane.showMessageDialog(suv, "�ֹι�ȣ�� �Է����ּ���");
                    suv.getJtfSsn1().requestFocus();return;};
               if(ssn2==null || ssn2.equals("")) {JOptionPane.showMessageDialog(suv, "�ֹι�ȣ�� �Է����ּ���");
                    suv.getJpfSsn2().requestFocus();return;};//�ֹι�ȣ �Է¹ޱ�
               if(tel==null||tel.equals("")) {JOptionPane.showMessageDialog(suv, "����ó�� �Է����ּ���");
                    suv.getJtfTel().requestFocus(); return;};//����ó �ޱ�
               if(email==null||email.equals("")) {JOptionPane.showMessageDialog(suv, "�̸����� �Է����ּ���");
                    suv.getJtfEmail().requestFocus(); return;};//�̸��� �ޱ�
// �׽�Ʈ���� �ӽ÷� ���Ƶ�               //if(addr==null||addr.equals("")) {JOptionPane.showMessageDialog(suv, "�ּҸ� �Է����ּ���");
//���� Ǯ���ֱ�                    //suv.getJtfAddr1().requestFocus(); return;};//�ּ� �ޱ�
               if(detailAddr==null||detailAddr.equals("")) {JOptionPane.showMessageDialog(suv, "���ּҸ� �Է����ּ���");
                    suv.getJtfAddr2().requestFocus(); return;};//���ּ� �ޱ�
               if(answer==null||answer.equals("")) {JOptionPane.showMessageDialog(suv, "������ ���� �Է����ּ���");
                    suv.getJtfAnswer().requestFocus(); return;};//������ ��
               ///////////////////////
                    
               if( !(pass1.matches(".*[A-Z].*")) || !(pass1.matches(".*[a-z].*")) || !(pass1.matches(".*\\d.*")) || !(pass1.matches(".*[~!.......].*")) ) {
            	   JOptionPane.showMessageDialog(suv, "��й�ȣ�� �빮��, �ҹ���, Ư�����ڰ� �����մϴ�.");
            	   return;
               };
            	   
               if(!pass2.equals(pass1)) {JOptionPane.showMessageDialog(suv, "��й�ȣ�� ��ġ���� �ʽ��ϴ�");
               /*     System.out.println(pass1+"/"+pass2);
                    System.out.println(!pass2.equals(pass1));
                    System.out.println(pass2!=pass1);*/
                    suv.getJpfPass1().setText("");
                    suv.getJpfPass2().setText("");
                    suv.getJpfPass1().requestFocus();
                    return;
               };//��й�ȣ ��ġȮ��
               signUp();
          }else if(ae.getSource()==suv.getJbAddr()) {
               new SearchAddrView(suv, this, null); //null�� ������ �κ� changeuser info �κп� 
          }else if(ae.getSource()==suv.getJbCancel()) {
               suv.dispose();
          }//end if

     }//��ưó��

     public void signUp() {
          System.out.println("�����Ϸ�");
          UserInsertVO uivo = new UserInsertVO(
        		  
		  );
          c_dao.insertUser(uivo);
          
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