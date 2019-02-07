package admin.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import admin.controller.AdminMainController;

public class AdminMainView extends JFrame {
	
	private JList<String> jlLog;
	private DefaultListModel<String> dlmLog;
	private JButton jbMgmt, jbServerOn, jbSaveLog, jbExit;

	public AdminMainView() {
		super("1949 - 서버관리자");
		
		dlmLog = new DefaultListModel<>();
		jlLog = new JList<>(dlmLog);
		jbMgmt = new JButton("전체 관리");
		jbServerOn = new JButton("서버 구동");
		jbSaveLog = new JButton("로그 저장");
		jbExit = new JButton("종료");

		JScrollPane jspLog = new JScrollPane(jlLog);
		jspLog.setBorder(new TitledBorder("Log"));
		
		JPanel rightPanel = new JPanel();
		
		JPanel leftPanel = new JPanel();
		
		leftPanel.setBorder(new TitledBorder("Management"));
		leftPanel.setLayout(null);
		leftPanel.add(jbMgmt);
		jbMgmt.setBounds(20, 25, 200, 80);
		
		rightPanel.setBorder(new TitledBorder("System"));
		rightPanel.setLayout(null);
		rightPanel.add(jbServerOn);
		rightPanel.add(jbSaveLog);
		rightPanel.add(jbExit);
		
		jbServerOn.setBounds(20, 25, 130, 80);
		jbSaveLog.setBounds(160, 25, 125, 35);
		jbExit.setBounds(160, 70, 125, 35);
		
		setLayout(null);
		
		jspLog.setBounds(15,10,555,200);
		leftPanel.setBounds(15,220, 240, 120);
		rightPanel.setBounds(270, 220, 300, 120);
		
		add(jspLog);
		add(leftPanel);
		add(rightPanel);
		
		AdminMainController amc = new AdminMainController(this);
		jbMgmt.addActionListener(amc);
		jbServerOn.addActionListener(amc);
		jbSaveLog.addActionListener(amc);
		jbExit.addActionListener(amc);
		addWindowListener(amc);
		
		setResizable(false);
		setBounds(400, 300, 600, 400);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new AdminMainView();
	}*/
	public JList<String> getJlLog() {
		return jlLog;
	}
	public DefaultListModel<String> getDlmLog() {
		return dlmLog;
	}
	public JButton getJbMgmt() {
		return jbMgmt;
	}
	public JButton getJbServerOn() {
		return jbServerOn;
	}
	public JButton getJbSaveLog() {
		return jbSaveLog;
	}
	public JButton getJbExit() {
		return jbExit;
	}
}
