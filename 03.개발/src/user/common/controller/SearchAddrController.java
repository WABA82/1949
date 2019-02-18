package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import user.common.view.SearchAddrView;
import user.common.view.SignUpView;
import user.common.vo.AddrVO;
import user.dao.CommonDAO;

public class SearchAddrController extends WindowAdapter implements ActionListener {

	private SearchAddrView sav;
	private JDialog jd;
	private String addrSeq;
	private SignUpView suv;
	
	public SearchAddrController(SearchAddrView sav, JDialog jd, String addrSeq, SignUpView suv) {
		this.sav = sav;
		this.jd = jd;
		this.addrSeq = addrSeq;
		this.suv = suv;
	}//������
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==sav.getJbSearch()) {
			String dong = sav.getJtfDong().getText().trim();
			if(dong.equals("")||dong==null) {
				JOptionPane.showMessageDialog(sav, "���� �Է��ϼ���.");
				return;
			}//end if
			search(dong); //String ?dong?
		}else if(ae.getSource()==sav.getJbOk()) {
			int row = sav.getJtZip().getSelectedRow();
			//suv.getJtfAddr1().setText(sav.getJtZip().getValueAt(row, 0).toString());
			StringBuilder addr = new StringBuilder();
			addr.append(sav.getJtZip().getValueAt(row, 1)).append("��(��) ")
			.append(sav.getJtZip().getValueAt(row, 2)).append(" ")
			.append(sav.getJtZip().getValueAt(row, 3)).append(" ")
			.append(sav.getJtZip().getValueAt(row, 4));
			System.out.println(addr);//addr�� �� �����µ�...
			suv.getJtfAddr1().setText(addr.toString());
			sav.dispose();
			
		
		}else if(ae.getSource()==sav.getJbCancel()) {
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
		
			Object[] rowData =null;
			AddrVO av =null;
		
			for(int i=0; i<list.size(); i++) {
				av=list.get(i);
				//�����ȣ, �õ�, ����, ��, ����
				rowData = new Object[5];
				rowData[0] = av.getZipcode();
				rowData[1] = av.getSido();
				rowData[2] = av.getGugun();
				rowData[3] = av.getDong();
				rowData[4] = av.getBunji();
				
				dtm.addRow(rowData);
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(sav, "DB���� ���� �߻�");
			e.printStackTrace();
		}//end catch
		if(flag) {
			JOptionPane.showMessageDialog(sav, "��ȸ�� ����� �����ϴ�.");
		}
		
	}//search
	

}//class
