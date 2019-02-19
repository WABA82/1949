package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

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
    /*test    */     if(addr==null||addr.equals("")) {JOptionPane.showMessageDialog(suv, "�ּҸ� �Է����ּ���");
    /* test        */     suv.getJtfAddr1().requestFocus(); return;};//�ּ� �ޱ�
               if(detailAddr==null||detailAddr.equals("")) {JOptionPane.showMessageDialog(suv, "���ּҸ� �Է����ּ���");
                    suv.getJtfAddr2().requestFocus(); return;};//���ּ� �ޱ�
               if(answer==null||answer.equals("")) {JOptionPane.showMessageDialog(suv, "������ ���� �Է����ּ���");
                    suv.getJtfAnswer().requestFocus(); return;};//������ ��
               ///////////////////////
                    
               /*if( !(pass1.matches(".*[A-Z].*")) || !(pass1.matches(".*[a-z].*")) || !(pass1.matches(".*\\d.*")) || !(pass1.matches(".*[~!.......].*")) ) {
            	   JOptionPane.showMessageDialog(suv, "��й�ȣ�� �빮��, �ҹ���, Ư�����ڰ� �����մϴ�.");
            	   return;
               };*/
            	   
               if(!pass2.equals(pass1)) {JOptionPane.showMessageDialog(suv, "��й�ȣ�� ��ġ���� �ʽ��ϴ�");
               /*     System.out.println(pass1+"/"+pass2);
                    System.out.println(!pass2.equals(pass1));
                    System.out.println(pass2!=pass1);*/
                    suv.getJpfPass1().setText("");
                    suv.getJpfPass2().setText("");
                    suv.getJpfPass1().requestFocus();
                    return;
               };//��й�ȣ ��ġȮ��
               
               /////��й�ȣ Ư������/ �빮�� / �ҹ��� ����////////////////�ణ ��������
               char[] lowerCase = { 
       				'a','b','c','d','e','f','g',
       				'h','i','j','k','l','m','n','o','p','q','r',
       				's','t','u','v','w','x','y','z'};
       		
       		char[] upperCase = {
       				'A','B','C','D','E','F','G',
       				'H','I','J','K','L','M','N','O','P','Q','R',
       				'S','T','U','V','W','X','Y','Z'};
       		
       		char[] spSymbol = {'!','@','#','$','%','^','&','*','(',')','-','_','+','='};
             
       		boolean lowerFlag = false;
       		boolean upperFlag =false;
       		boolean spcFlag = false;
       		
       		if( (pass1.length()>13)) {
       			JOptionPane.showMessageDialog(suv, "��й�ȣ�� �ִ� 12�ڸ� ���� �����մϴ�.");
       			suv.getJpfPass1().setText("");
                suv.getJpfPass2().setText("");
                suv.getJpfPass1().requestFocus();
                return;
       		}else{
       			for(int i=0; i<pass1.length(); i++) {
    				for(int j=0; j<lowerCase.length; j++) {
    					if(pass1.charAt(i) == lowerCase[j]) {
    						lowerFlag = true;
    					}
    				}
    				for(int j=0; j<upperCase.length; j++) {
    					if(pass1.charAt(i) == upperCase[j]) {
    						upperFlag = true;
    					}
    				}
    				for(int j=0; j<spSymbol.length; j++) {
    					if(pass1.charAt(i) == spSymbol[j]) {
    						spcFlag = true;
    					}
    				}
    			}//end for
       			
       			if(lowerFlag && upperFlag && spcFlag) {
       				System.out.println("�ùٸ� ��й�ȣ!!");
       			}else {
       				JOptionPane.showMessageDialog(suv, "��й�ȣ�� �ҹ���,�빮��,Ư�����ڰ� ���ԵǾ���մϴ�.");
       				suv.getJpfPass1().setText("");
       				suv.getJpfPass2().setText("");
       				suv.getJpfPass1().requestFocus();
       				return;
       			}
    			
       		}//end else
       		
               /////////////////////////////////////////////�ֹι�ȣ ���� �Ϸ� 
               String ssn = ssn1+ssn2;
               int[] flagNum= {2,3,4,5,6,7,8,9,2,3,4,5};
               int sum = 0;
               
              for(int i=0; i<flagNum.length; i++) {
            	  
            	  sum += ((int)ssn.charAt(i)-48)*((flagNum[i]));
            	  //System.out.println("ssn.charat :"+ssn.charAt(i));
            	 // System.out.println("flag num :"+flagNum[i]);
            	  //System.out.println("sum :"+sum);
            	  ///���� ���� - �� sum �� ����� �ȳ����� �������� ������ �׷���?
            	  }//end for
              sum = (11-(sum%11))%10;
               if(!(sum == ssn.charAt(12)-48 )) {
            	   JOptionPane.showMessageDialog(suv, "�ùٸ� �ֹι�ȣ�� �ƴմϴ�.");
            	   return;
               }//end if       //////////�ֹι�ȣ ���� �Ϸ�
               
               String chkTel = tel.replaceAll("-", "");
               
               try {
               Integer.parseInt(chkTel);
               }catch (NumberFormatException npe) {
            	   JOptionPane.showMessageDialog(suv, "����ó�� ���ڸ� �Է� �����մϴ�.\n ����)000-0000-0000");
            	   return;
               }
               
               if(chkTel.length()!=11) {
            	   JOptionPane.showMessageDialog(suv, "��ȭ��ȣ ������ �߸��Ǿ����ϴ�.\n"+" ����)000-0000-0000");
            	   return;
               }
               //////////////////////////����ó ��� ����////////////�������
               ///////////�Ʒ����� �̸��� ����//////////
               if(email.length() < 13) {
            	  JOptionPane.showMessageDialog(suv, "�̸����� �ּ� 12�� �̻��̾���մϴ�.");
            	  	return;
               }else {
            	   if(email.indexOf("@")==-1 || email.indexOf(".")==-1) {
            		   JOptionPane.showMessageDialog(suv, "�̸��� ������ �߸��Ǿ����ϴ�.");
            		   return;
            	   }//end if
               }//end else
               ////////////////////////�̸��� ���� �� ////////////////
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
          try {
			c_dao.insertUser(uivo);
			JOptionPane.showMessageDialog(suv, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
			suv.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB�������� insert ����");
		}
          
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