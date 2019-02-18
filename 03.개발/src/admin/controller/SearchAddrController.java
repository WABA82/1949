package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import admin.dao.AdminDAO;
import admin.view.SearchAddrView;
import admin.view.UserModifyView;
import admin.vo.AddrVO;

public class SearchAddrController extends WindowAdapter implements ActionListener {

	private SearchAddrView sav;
	private UserModifyView umv;
	private UserModifyController umc;
	private List<AddrVO> list;
	private boolean searchFlag;
	
	public SearchAddrController(SearchAddrView sav, UserModifyView umv, UserModifyController umc) {
		this.sav = sav;
		this.umv = umv;
		this.umc = umc;
		System.out.println("���� add "+umc.getAddrSeq());
	}
	
	/**
	 * ���� �Է¹޾� �ּ������� ���̺� �����ִ� �޼ҵ�
	 * @param dong
	 * @throws SQLException
	 */
	public void search(String dong) throws SQLException {
		list = AdminDAO.getInstance().selectAddr(dong);
		
		if(list.size() == 0) {
			msgCenter("�˻��� ����� �����ϴ�.");
			return;
		}
		
		DefaultTableModel dtm = sav.getDtmZip();
		
		dtm.setRowCount(0);
		
		String[] rowData = new String[5];
		AddrVO avo = null;
		for(int i=0; i<list.size(); i++) {
			avo = list.get(i);
			System.out.println(avo);
			
			rowData[0] = avo.getZipcode();
			rowData[1] = avo.getSido();
			rowData[2] = avo.getGugun();
			rowData[3] = avo.getDong();
			rowData[4] = avo.getBunji();
			
			dtm.addRow(rowData);
		}
		
		searchFlag = true;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		sav.dispose();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(sav, msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sav.getJbSearch() || e.getSource() == sav.getJtfDong()) {
			
			String dong = sav.getJtfDong().getText().trim();
			
			if (dong == null || dong.equals("")) {
				msgCenter("�� �̸��� �Է����ּ���.");
				sav.getJtfDong().requestFocus();
				return;
			}
			
			try {
				search(dong);
			} catch (SQLException e1) {
				msgCenter("DB���� ���� �߻�");
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == sav.getJbOk()) {
			
			if(!searchFlag) {
				msgCenter("�˻��� ���� �������ּ���.");
				sav.getJtfDong().requestFocus();
				return;
			}
			
			JTable jt = sav.getJtZip();
			
			int selectedRow = jt.getSelectedRow();
			System.out.println(selectedRow);
			
			// ���õ� ���ڵ��� seq�� ��� �˾Ƴ���?
			// -list�� �������� �÷���.
			// -�����ʹ� ���������� �� �˻��Ǵ°� ���� ������
			// -���õ� �ο�� ������� 0,1,2
			
			// �ּ� ������ ����
			umc.setAddrSeq(list.get(selectedRow).getSeq());
			System.out.println("�� ������ : "+umc.getAddrSeq());
			
			// zipcode, addr1�� ���� ���� �ݿ�
			String zipcode = (String)jt.getValueAt(selectedRow, 0);
			StringBuilder addr1 = new StringBuilder();
			addr1.append((String)jt.getValueAt(selectedRow, 1))
			.append(" ").append((String)jt.getValueAt(selectedRow, 2))
			.append(" ").append((String)jt.getValueAt(selectedRow, 3))
			.append(" ").append((String)jt.getValueAt(selectedRow, 4));
			
			umv.getJtfZip().setText(zipcode);
			umv.getJtfAddr1().setText(addr1.toString());
			
			sav.dispose();
		}
		
		if (e.getSource() == sav.getJbCancel()) {
			sav.dispose();
		}
	}
}
