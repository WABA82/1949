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
	}
	
	/**
	 * 동을 입력받아 주소정보를 테이블에 보여주는 메소드
	 * @param dong
	 * @throws SQLException
	 */
	public void search(String dong) throws SQLException {
		list = AdminDAO.getInstance().selectAddr(dong);
		
		if(list.size() == 0) {
			msgCenter("검색된 결과가 없습니다.");
			return;
		}
		
		DefaultTableModel dtm = sav.getDtmZip();
		
		dtm.setRowCount(0);
		
		String[] rowData = new String[5];
		AddrVO avo = null;
		for(int i=0; i<list.size(); i++) {
			avo = list.get(i);
			
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
				msgCenter("동 이름을 입력해주세요.");
				sav.getJtfDong().requestFocus();
				return;
			}
			
			try {
				search(dong);
			} catch (SQLException e1) {
				msgCenter("DB에서 문제 발생");
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == sav.getJbOk()) {
			
			if(!searchFlag) {
				msgCenter("검색을 먼저 수행해주세요.");
				sav.getJtfDong().requestFocus();
				return;
			}
			
			JTable jt = sav.getJtZip();
			
			int selectedRow = jt.getSelectedRow();

			// 선택된 레코드의 seq를 어떻게 알아내지?
			// -list를 전역으로 올렸음.
			// -데이터는 순차적으로 들어감 검색되는것 부터 끝까지
			// -선택된 로우는 순서대로 0,1,2
			
			// 주소 시퀀스 저장
			umc.setAddrSeq(list.get(selectedRow).getSeq());
			
			// zipcode, addr1에 선택 내용 반영
			String zipcode = (String)jt.getValueAt(selectedRow, 0);
			StringBuilder addr1 = new StringBuilder();
			addr1.append((String)jt.getValueAt(selectedRow, 1))
			.append(" ").append((String)jt.getValueAt(selectedRow, 2))
			.append(" ").append((String)jt.getValueAt(selectedRow, 3))
			.append(" ").append((String)jt.getValueAt(selectedRow, 4));
			
			umv.getJtfZip().setText(zipcode);
			umv.getJtfAddr1().setText(addr1.toString());
			umv.getJtfAddr2().setText("");
			
			sav.dispose();
		}
		
		if (e.getSource() == sav.getJbCancel()) {
			sav.dispose();
		}
	}
}
