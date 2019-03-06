package user.common.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import user.common.controller.ChangeUserInfoController;
import user.common.controller.SearchAddrController;
import user.common.controller.SignUpController;

public class SearchAddrView extends JDialog {
	
	private JTextField jtfDong;
	private DefaultTableModel dtmZip;
	private JTable jtZip;
	private JButton jbSearch, jbOk, jbCancel;

	public SearchAddrView(JDialog jd, SignUpController suc, ChangeUserInfoController cuic) {
		super(jd,"�ּҰ˻�", true);
		
		jtfDong = new JTextField(20);
		String[] columnNames = { "�����ȣ", "�õ�", "����", "��", "����", /*"sqe"*/ };
		dtmZip = new DefaultTableModel(columnNames, 3) {//���� ���ϰ� ����
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtZip = new JTable(dtmZip);
		jtZip.getTableHeader().setReorderingAllowed(false);//�÷� �̵�����
		JScrollPane jspZip = new JScrollPane(jtZip);
		
		jbSearch = new JButton("�˻�");
		jbOk = new JButton("����");
		jbCancel = new JButton("���");
		
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("�� �˻�"));
		topPanel.add(jtfDong);
		topPanel.add(jbSearch);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(jbOk); 
		bottomPanel.add(jbCancel); 
		
		add(BorderLayout.NORTH, topPanel);
		add(BorderLayout.CENTER, jspZip);
		add(BorderLayout.SOUTH, bottomPanel);
		
		SearchAddrController sac = new SearchAddrController(this, suc, cuic);
		jbSearch.addActionListener(sac);
		jbOk.addActionListener(sac);
		jbCancel.addActionListener(sac);
		
		addWindowListener(sac);
		
		setBounds(400, 200, 500, 300);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new SearchAddrView();
	}*/
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
