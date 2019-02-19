package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.AppListController;

@SuppressWarnings("serial")
public class AppListView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;

	public AppListView(ErAppView erav, String er_num) {
		super(erav, "�� ���� ��Ȳ", true);

		String[] eeInfoColumns = { "��ȣ", "������ȣ", "�̹���", "�̸�", "����", "�ٹ�����", "�з�", "����", "��Ʈ������ ����", "����", "������",
				"��������" };
		dtmApp = new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp = new JTable(dtmApp) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};
		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		JLabel jlEeInfo = new JLabel("�� ������ �� : ");

		setLayout(null);

		jlEeInfo.setBounds(880, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);

		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		AppListController alc = new AppListController(this, er_num);
		addWindowListener(alc);
		jtApp.addMouseListener(alc);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);

	}// ������

	public JTable getJtEeInfo() {
		return jtApp;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new AppListView(null, "er_000032");
	}// main

}// class
