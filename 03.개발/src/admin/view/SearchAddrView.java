package admin.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import admin.controller.SearchAddrController;
import admin.controller.UserModifyController;

public class SearchAddrView extends JDialog {
	
	private JTextField jtfDong;
	private DefaultTableModel dtmZip;
	private JTable jtZip;
	private JButton jbSearch, jbOk, jbCancel;

	public SearchAddrView(UserModifyView umv, UserModifyController umc) {
		super(umv,"주소검색", true);
		
		jtfDong = new JTextField(20);
		String[] columnNames = { "우편번호", "시도", "구군", "동", "번지" };
		dtmZip = new DefaultTableModel(columnNames, 0);
		jtZip = new JTable(dtmZip);
		JScrollPane jspZip = new JScrollPane(jtZip);
		
		jbSearch = new JButton("검색");
		jbOk = new JButton("선택");
		jbCancel = new JButton("취소");
		
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("동 검색"));
		topPanel.add(jtfDong);
		topPanel.add(jbSearch);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(jbOk); 
		bottomPanel.add(jbCancel); 
		
		add(BorderLayout.NORTH, topPanel);
		add(BorderLayout.CENTER, jspZip);
		add(BorderLayout.SOUTH, bottomPanel);
		
		SearchAddrController sac = new SearchAddrController(this, umv, umc);
		jtfDong.addActionListener(sac);
		jbSearch.addActionListener(sac);
		jbOk.addActionListener(sac);
		jbCancel.addActionListener(sac);
		
		addWindowListener(sac);
		
		setBounds(umv.getX()+100, umv.getY()+100, 500, 300);
		setResizable(false);
		setVisible(true);
	}
	
	public JTextField getJtfDong() {
		return jtfDong;
	}
	public DefaultTableModel getDtmZip() {
		return dtmZip;
	}
	public JTable getJtZip() {
		return jtZip;
	}
	public JButton getJbSearch() {
		return jbSearch;
	}
	public JButton getJbOk() {
		return jbOk;
	}
	public JButton getJbCancel() {
		return jbCancel;
	}
}
