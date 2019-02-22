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
   }//������
   
   private void msgCenter(String msg) {
      if (suv != null) {
         JOptionPane.showMessageDialog(suv, msg);
      } else {
         JOptionPane.showMessageDialog(cuiv, msg);
      }
   }
   
   @Override
   public void actionPerformed(ActionEvent ae) {
      if(ae.getSource()==sav.getJbSearch()) {
         String dong = sav.getJtfDong().getText().trim();
         if(dong.equals("")||dong==null) {
            JOptionPane.showMessageDialog(sav, "���� �Է��ϼ���.");
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
         //System.out.println(zip);//�׽�Ʈ�ϴ��� - �ʿ����
         String addrSeq= listSeq.get(row);
         
         
         if (suv != null) {
            suv.getJtfAddr1().setText(addr.toString());
            suv.getJtfZip().setText(zip);
            suc.setAddrSeq(addrSeq);// seq
            //System.out.println("���õ� ���� seq : "+addrSeq);
         } else {
        	 //�����߰�(�ּҰ˻� �� ���ּ��ʱ�ȭ)
        	 cuiv.getJtfAddr2().setText("");
               cuiv.getJtfZipcode().setText(sav.getJtZip().getValueAt(row, 0).toString());
               cuiv.getJtfAddr1().setText(addr.toString());
               cuic.setAddrSeq(addrSeq); //�̷��� seq�� �����ؼ� ���� �� ���
            }
         sav.dispose();
      }
      
      if(ae.getSource()==sav.getJbCancel()) {
         sav.dispose();
      }
   }//��ư ó��
   
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
            //�����ȣ, �õ�, ����, ��, ����
            rowData = new Object[6];
            rowData[0] = av.getZipcode();
            rowData[1] = av.getSido();
            rowData[2] = av.getGugun();
            rowData[3] = av.getDong();
            rowData[4] = av.getBunji();
            listSeq.add(av.getSeq());
            //System.out.println("��ȸ�Ǵ� seq ��ȣ :"+av.getSeq()); test ��
            
            dtm.addRow(rowData);
         }//end for
      } catch (SQLException e) {
         msgCenter("DB���� ���� �߻�");
         e.printStackTrace();
      }//end catch
      if(flag) {
         msgCenter("��ȸ�� ����� �����ϴ�.");
      }
      
   }//search
   

}//class