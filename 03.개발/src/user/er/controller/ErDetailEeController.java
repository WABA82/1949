package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.ErDetailEeView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErInterestVO;
import user.util.UserLog;

public class ErDetailEeController extends WindowAdapter implements ActionListener, MouseListener {
   private ErDetailEeView edev;
   private String eeNum, erId;
   private boolean mouseClickFlag; 
   private ErInterestVO eivo;
   private ErDAO erdao;
   private DetailEeInfoVO devo;
   private UserLog ul;
   
   public ErDetailEeController(ErDetailEeView edev, String eeNum, String erId, boolean flagHeart) {
      this.edev = edev;
      this.eeNum = eeNum;
      this.erId = erId;
      mouseClickFlag=flagHeart;
      erdao= ErDAO.getInstance();
      ul=new UserLog();
   }
   public void addInterestEe() {
      eivo = new ErInterestVO(eeNum,erId);
      
      try {
         erdao.insertInterestEe(eivo);
      } catch (SQLException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(edev, "추가에 실패했습니다.");
         return;
      }

      edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/src/user/img/r_heart.png"));
      JOptionPane.showMessageDialog(edev, "관심 구직자에 추가되었습니다!");
      ul.sendLog(erId, "["+eeNum+ "]번호 유저를 관심 구직자로 추가하였습니다.");
      try {
         devo= erdao.selectDetailEe(eeNum, erId);

      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
   
   public void removeInterestEe() {
      boolean deleteFlag=false;
      eivo = new ErInterestVO(eeNum, erId);
      
      try {
         deleteFlag = erdao.deleteInterestEe(eivo);
      } catch (SQLException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
         
      }
      if(deleteFlag) {
         JOptionPane.showMessageDialog(edev, "관심 구직자를 취소했습니다.");
         ul.sendLog(erId, "["+eeNum+ "]번호 유저를 관심 구직자에서 취소하였습니다.");
         edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/src/user/img/b_heart.png"));
      }else {
         JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
      }
   }//removeInterestEr
   
   public void extRsmDown() throws UnknownHostException, IOException{
      //ee_info에서  erNum으로 조회해서 있는지 없는지 조회해서 이력서 이름받기
      try {
         devo= erdao.selectDetailEe(eeNum, erId);
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(edev, "DB에러!!");
         e.printStackTrace();
      }
      if (devo.getExtResume() == null) {
         JOptionPane.showMessageDialog(edev, "이 지원자가 등록한 외부이력서가 없습니다.");
         return;
      } // end if

      if (devo.getExtResume() != null) {
         
         // FileDialog로 이력서 저장장소, 파일명을 구하고 그 장소,파일로 fos로 write
         FileDialog fdSave = new FileDialog(edev, "받을 경로 선택", FileDialog.SAVE);
         fdSave.setVisible(true);
         
         String path = fdSave.getDirectory();
         String name = fdSave.getFile();
         String resumeName=devo.getExtResume();
         String ext ="";
         
         //확장자 만들기
         ext = resumeName.substring(resumeName.lastIndexOf("."));
         
         Socket socket = null;
         DataOutputStream dos = null;
         DataInputStream dis = null;
         FileOutputStream fos = null;

         try {
            System.out.println("111");
            socket = new Socket("localhost", 7002);
            // socket = new Socket("211.63.89.144", 7002);
            System.out.println("--"+socket);
            dos = new DataOutputStream(socket.getOutputStream());

            // 서버에게 이력서파일 전송 요청 보내기.
            dos.writeUTF("ee_ext_request");
            dos.flush();
            System.out.println("222");

            // 서버에게 요청할 파일명 보내기.
            dos.writeUTF(devo.getExtResume().trim());
            dos.flush();
            System.out.println("333");

            dis = new DataInputStream(socket.getInputStream());

            int fileCnt = 0; // 서버에서 보내오는 파일 조각의 갯수.
            int data = 0; // 서버에서 보내오는 데이터

            // 전달받을 파일 조각의 갯수
            fileCnt = dis.readInt();
            
            fos = new FileOutputStream(path+name+ext);
            System.out.println("----"+path+name+ext);

            byte[] readData = new byte[512];
            while (fileCnt > 0) {
               data = dis.read(readData); // 서버에서 전송한 파일조각을 읽어들여
               fos.write(readData, 0, data);// 생성한 파일로 기록
               fos.flush();
               fileCnt--;
            } // end while
            
            dos.writeUTF("종료되었습니다.");
            dos.flush();
            JOptionPane.showMessageDialog(edev, "파일 다운이 완료되었습니다!");
            
         } finally {
            if(fos != null) {
               fos.close();
            }// end if
            if (dos != null) {
               dos.close();
            } // end if
            if (socket != null) {
               socket.close();
            } // end if
         } // end finally
      } // end if

   }
   
   @Override
   public void mouseClicked(MouseEvent me) {
      if(me.getSource()==edev.getJlHeart()) {
         mouseClickFlag = !mouseClickFlag;
      }
      if(mouseClickFlag) {
         addInterestEe();
      }else {
         removeInterestEe();
      }
   }
   
   @Override
   public void actionPerformed(ActionEvent ae) {
      if(ae.getSource()==edev.getJbRsmDown()) {
         try {
            
            extRsmDown();
         } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(edev, "DB오류!");
            e.printStackTrace();
         } catch (IOException e) {
            JOptionPane.showMessageDialog(edev, "DB오류!");
            e.printStackTrace();
         }
      }
      if(ae.getSource()==edev.getJbClose()) {
         edev.dispose();
      }
   }
   
   @Override
   public void windowClosing(WindowEvent e) {
      edev.dispose();
   }

   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}
}