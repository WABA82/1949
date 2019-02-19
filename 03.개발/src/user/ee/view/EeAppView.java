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

		String[] erColumns = { "��ȣ", "����������ȣ", "������ȣ", "����", "ȸ���", "����", "�ٹ�����", "�з�", "�������", "�޿�", "������", "��������" };
		dtmApp = new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp = new JTable(dtmApp);
		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		/* ��ġ */
		add("Center", jspEeInfo);

		/* �̺�Ʈ ��� */
		EeAppController eac = new EeAppController(this, ee_id);
		addWindowListener(eac);
		jtApp.addMouseListener(eac);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 1000, 500);
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
