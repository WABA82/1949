package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import user.common.view.ChangeUserInfoView;
import user.common.view.SearchAddrView;
import user.common.view.SignUpView;
import user.common.vo.AddrVO;
import user.dao.CommonDAO;

public class SearchAddrController extends WindowAdapter implements ActionListener {

   private SearchAddrView sav;
   private SignUpView suv;
   private ChangeUserInfoView cuiv;
   private SignUpController suc;
   private ChangeUserInfoController cuic;
   private List<String> listSeq;
   
   public SearchAddrController(SearchAddrView sav, SignUpController suc, ChangeUserInfoController cuic) {
      this.sav = sav;
      if (suc != null && cuic == null) {
         this.suv = suc.getSuv();
         this.suc = suc;
      } else {
         this.cuiv = cuic.getCuiv();
         this.cuic = cuic;
      }
      
      listSeq=new ArrayList<String>();
   }//생성자
   
   private void msgCenter(String msg) {
      if (suv != null) {
         JOptionPane.showMessageDialog(suv, msg);
      } else {
         JOptionPane.showMessageDialog(cuiv, msg);
      }
   }
   
   @Override
   public void actionPerformed(ActionEvent ae) {
      if(ae.getSource()==sav.getJbSearch() || ae.getSource()==sav.getJtfDong()) {
         String dong = sav.getJtfDong().getText().trim();
         if(dong.equals("")||dong==null) {
            JOptionPane.showMessageDialog(sav, "동을 입력하세요.");
            return;
         }//end if
         search(dong); //String ?dong?
      }
      
      if(ae.getSource()==sav.getJbOk()) {
         int row = sav.getJtZip().getSelectedRow();
         //suv.getJtfAddr1().setText(sav.getJtZip().getValueAt(row, 0).toString());
         StringBuilder addr = new StringBuilder();
         addr.append(sav.getJtZip().getValueAt(row, 1)).append(" ")
         .append(sav.getJtZip().getValueAt(row, 2)).append(" ")
         .append(sav.getJtZip().getValueAt(row, 3)).append(" ")
         .append(sav.getJtZip().getValueAt(row, 4));
         String zip ="";
         zip= sav.getJtZip().getValueAt(row, 0).toString();
         //System.out.println(zip);//테스트하느라 - 필요없음
         String addrSeq= listSeq.get(row);
         
         
         if (suv != null) {
            suv.getJtfAddr1().setText(addr.toString());
            suv.getJtfZip().setText(zip);
            suc.setAddrSeq(addrSeq);// seq
            //System.out.println("선택된 행의 seq : "+addrSeq);
         } else {
        	 //혜원추가(주소검색 시 상세주소초기화)
        	 cuiv.getJtfAddr2().setText("");
               cuiv.getJtfZipcode().setText(sav.getJtZip().getValueAt(row, 0).toString());
               cuiv.getJtfAddr1().setText(addr.toString());
               cuic.setAddrSeq(addrSeq); //이렇게 seq를 저장해서 수정 시 사용
            }
         sav.dispose();
      }
      
      if(ae.getSource()==sav.getJbCancel()) {
         sav.dispose();
      }
   }//버튼 처리
   
   @Override
   public void windowClosing(WindowEvent e) {
      sav.dispose();
   }//closing
   
   public void search(String dong) {
      boolean flag =false;
      
      CommonDAO c_dao =CommonDAO.getInstance();
      try {
         DefaultTableModel dtm = sav.getDtmZip();
         List<AddrVO> list = c_dao.selectAddr(dong);
         dtm.setRowCount(0);
      
         if(list.isEmpty()) {
            flag =true;
         }
         //addrSeq = "";
         Object[] rowData =null;
         AddrVO av =null;
      
         for(int i=0; i<list.size(); i++) {
            av=list.get(i);
            //우편번호, 시도, 구군, 동, 번지
            rowData = new Object[6];
            rowData[0] = av.getZipcode();
            rowData[1] = av.getSido();
            rowData[2] = av.getGugun();
            rowData[3] = av.getDong();
            rowData[4] = av.getBunji();
            listSeq.add(av.getSeq());
            //System.out.println("조회되는 seq 번호 :"+av.getSeq()); test 용
            
            dtm.addRow(rowData);
         }//end for
      } catch (SQLException e) {
         msgCenter("DB에서 문제 발생");
         e.printStackTrace();
      }//end catch
      if(flag) {
         msgCenter("조회된 결과가 없습니다.");
      }
      
   }//search
   

}//class