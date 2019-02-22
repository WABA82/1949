package user.ee.view;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeAppController;

/**
 * �Ϲݻ����(������)�� ������Ȳ â.
 * 
 * @author owner
 *
 */
@SuppressWarnings("serial")
public class EeAppView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;

	private EeAppView(EeMainView emv, String ee_id) {
		super(emv, "���� ��Ȳ", true);

		String[] erColumns = { "��ȣ", /*"����������ȣ",*/ "������ȣ", "����", "ȸ���", "����", "�ٹ�����", "�з�", "�������", "�޿�", "������", "��������" };
		dtmApp = new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp = new JTable(dtmApp);
		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		/* ���̺� �÷��� ������ �����ϱ� */
		jtApp.getColumnModel().getColumn(0).setPreferredWidth(50);// ��ȣ
		/*jtApp.getColumnModel().getColumn(1).setPreferredWidth(120);*/// ����������ȣ
		jtApp.getColumnModel().getColumn(1).setPreferredWidth(140);// ������ȣ
		jtApp.getColumnModel().getColumn(2).setPreferredWidth(300);// ����
		jtApp.getColumnModel().getColumn(3).setPreferredWidth(120);// ȸ���
		jtApp.getColumnModel().getColumn(4).setPreferredWidth(60);// ����
		jtApp.getColumnModel().getColumn(5).setPreferredWidth(80);// �ٹ�����
		jtApp.getColumnModel().getColumn(6).setPreferredWidth(60);// �з�
		jtApp.getColumnModel().getColumn(7).setPreferredWidth(100);// �������
		jtApp.getColumnModel().getColumn(8).setPreferredWidth(70);// �޿�
		jtApp.getColumnModel().getColumn(9).setPreferredWidth(120);// ������
		jtApp.getColumnModel().getColumn(10).setPreferredWidth(100);// ��������

		jtApp.setRowHeight(25);
		
		/* ��ġ */
		add("Center", jspEeInfo);

		/* �̺�Ʈ ��� */
		EeAppController eac = new EeAppController(this, ee_id);
		addWindowListener(eac);
		jtApp.addMouseListener(eac);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}// ������

	public JTable getJtEr() {
		return jtApp;
	}

	public DefaultTableModel getDtmEr() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new EeAppView(null, "gong1");
	}// main

}// class
