package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoRegView;
import user.er.view.ErMainView;
import user.er.vo.ActivationVO;
import user.er.vo.CoInsertVO;

public class CoInfoRegController extends WindowAdapter implements MouseListener, ActionListener {

   private CoInfoRegView cirv;
   private String erId;
   private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
   private String path, name;
   private ErDAO erdao;
   private CoInsertVO civo;
   private ErMainView emv;

   public CoInfoRegController(CoInfoRegView cirv, String erId, ErMainView emv) {
      this.cirv = cirv;
      this.erId = erId;
      this.emv=emv;
      erdao = ErDAO.getInstance();
   }// ������

   @Override
   public void windowClosing(WindowEvent e) {
      cirv.dispose();
   }// ������ �ݱ�

   @Override
   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == cirv.getJbReg()) {
         try {
            register();
         } catch (SQLException e) {
        	 JOptionPane.showMessageDialog(cirv, "DB����");
            e.printStackTrace();
         }
      } else if (ae.getSource() == cirv.getJbClose()) {
         cirv.dispose();
      }
   }// ��ư ����

   @Override
   public void mouseClicked(MouseEvent me) {
      if (me.getSource() == cirv.getJlImg1()) {
         chgImg(cirv.getJlImg1(), 1);
      } // end if

      if (me.getSource() == cirv.getJlImg2()) {
         chgImg(cirv.getJlImg2(), 2);
      } // end if

      if (me.getSource() == cirv.getJlImg3()) {
         chgImg(cirv.getJlImg3(), 3);
      } // end if

      if (me.getSource() == cirv.getJlImg4()) {
         chgImg(cirv.getJlImg4(), 4);
      } // end if

   }// mouseClicked

   public void register() throws SQLException {
	   boolean insertFlag = false;
      if (uploadImg1 == null) {
         JOptionPane.showMessageDialog(cirv, "���λ����� �Է����ּž��մϴ�");
         return;
      } // end if

         String coName = cirv.getJtfCoName().getText().trim();
         String estDate = cirv.getJtfEstDate().getText().trim();
         String coDesc = cirv.getJtaCoDesc().getText().trim();
         
         if (coName.equals("")) {
        	 JOptionPane.showMessageDialog(cirv, "ȸ����� �Է����ּ���.");
        	 return;
         } // end if
         
         if (estDate.isEmpty()) {
        	 JOptionPane.showMessageDialog(cirv, "�������� �Է����ּ���.");
        	 return;
         }//end if
         
         if(estDate.length() != 8) {
        	 JOptionPane.showMessageDialog(cirv, "�����⵵�� �Է������� ���� ���� 8�ڸ��� ���ּ���\nex)19910717");
        	 return;
         }//end if
         
         int memberNum=0;
         try {
        	 memberNum = Integer.parseInt(cirv.getMemberNum().getText().trim());
          
          if (memberNum == 0 ) {
        	  JOptionPane.showMessageDialog(cirv, "������� �Է����ּ���.");
        	  return;
          } // end if
          
         }catch(NumberFormatException nfe){
        	 JOptionPane.showMessageDialog(cirv, " ������� ���ڸ� �Է� �����մϴ�.");
        	 return;
         }//end catch

         if(coDesc.equals("")) {
        	 JOptionPane.showMessageDialog(cirv, "��� ������ �Է����ּ���!");
        	 return;
         }
         
         if (uploadImg1 == null) {
            uploadImg1 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img1.png");
         }//end if

         if (uploadImg2 == null) {
            uploadImg2 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img2.png");
         }//end if


         if (uploadImg3 == null) {
            uploadImg3 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img3.png");
         }//end if

         if (uploadImg4 == null) {
            uploadImg4 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img4.png");
         }//end if
         
//         System.out.println(uploadImg1+" "+uploadImg2 );

         CoInsertVO civo = null;
         civo = new CoInsertVO(erId, uploadImg1.getName(), uploadImg2.getName(), uploadImg3.getName(), uploadImg4.getName(), coName,
               estDate, coDesc, memberNum);
         ActivationVO avo=new ActivationVO(erId);
         System.out.println("civo�� : "+civo+"avo�� ���� : "+avo);
         
         insertFlag = erdao.updateErInfo(civo, avo);
         if(insertFlag) {
        	 JOptionPane.showMessageDialog(cirv, "ȸ�簡 ��ϵǾ����ϴ�.");
         }
         
         System.out.println("���");
         JOptionPane.showMessageDialog(cirv, "ȸ�� ������ ��ϵǾ����ϴ�\n�������� ���� ������ ��ȸ�����մϴ�.");
         ErMainVO updateEmvo=  CommonDAO.getInstance().selectErMain(erId, "Y");
         new ErMainView(updateEmvo);
         emv.dispose();
         cirv.dispose();
   }// register

   public void chgImg(JLabel jl, int imgNumber) {
      boolean flag= false;
      FileDialog fd = new FileDialog(cirv, "�̹��� ����", FileDialog.LOAD);
      fd.setVisible(true);

      path = fd.getDirectory();
      name = fd.getFile();
      
      if ( path != null ) {
         String[] extFlag= {"jpg", "jpeg", "png", "bmp", "gif" };
         for( String ext : extFlag) {
            if(name.toLowerCase().endsWith(ext)) {
               flag = true;
            }//end if
         }//end for
         
         if(flag) {
            if( imgNumber == 1) {
               uploadImg1 = new File(path + name);
               cirv.getJlImg1().setIcon(new ImageIcon(uploadImg1.getAbsolutePath()));
            }else if( imgNumber == 2 ) {
               uploadImg2 = new File(path + name);
               cirv.getJlImg2().setIcon(new ImageIcon(uploadImg2.getAbsolutePath()));
            }else if( imgNumber == 3 ) {
               uploadImg3 = new File(path + name);
               cirv.getJlImg3().setIcon(new ImageIcon(uploadImg3.getAbsolutePath()));
            }else if( imgNumber == 4 ) {
               uploadImg4 = new File(path + name);
               cirv.getJlImg4().setIcon(new ImageIcon(uploadImg4.getAbsolutePath()));
            }//end else if
         }else {
            JOptionPane.showMessageDialog(cirv, name+"�� �̹����� �ƴմϴ�.");
         }//end else
//   System.out.println("�̹��� ����");

   } // end if
   }// chgImg

   @Override
   public void mousePressed(MouseEvent e) {   }
   @Override
   public void mouseReleased(MouseEvent e) {   }
   @Override
   public void mouseEntered(MouseEvent e) {   }
   @Override
   public void mouseExited(MouseEvent e) {   }
}