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
	private JLabel jlEeInfo;
	private DefaultTableModel dtmApp;

	public AppListView(ErAppView erav, String er_num) {
		super(erav, "�� ���� ��Ȳ", true);

		jlEeInfo = new JLabel("�� ������ �� : ");

		String[] eeInfoColumns = { "��ȣ", "������ȣ", "�̹���", "�̸�", "����", "�ٹ�����", "�з�", "����", "��Ʈ������ ����", "����", "������",
				"��������" };
		dtmApp = new DefaultTableModel(eeInfoColumns, 0) {
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

		/* ���̺��� ũ�� ���� : �̹��� ������ 150x200 */
		// ���� ����
		jtApp.setRowHeight(200);
		// ���� ����
		jtApp.getColumnModel().getColumn(0).setPreferredWidth(40); // ��ȣ
		jtApp.getColumnModel().getColumn(1).setPreferredWidth(100); // �⺻������ȣ
		jtApp.getColumnModel().getColumn(2).setPreferredWidth(150); // �̹���
		jtApp.getColumnModel().getColumn(3).setPreferredWidth(70); // �̸�
		jtApp.getColumnModel().getColumn(4).setPreferredWidth(50); // ����
		jtApp.getColumnModel().getColumn(5).setPreferredWidth(70); // �ٹ�����
		jtApp.getColumnModel().getColumn(6).setPreferredWidth(50); // �з�
		jtApp.getColumnModel().getColumn(7).setPreferredWidth(50); // ����
		jtApp.getColumnModel().getColumn(8).setPreferredWidth(100); // ��Ʈ������
		jtApp.getColumnModel().getColumn(9).setPreferredWidth(50); // ����
		jtApp.getColumnModel().getColumn(10).setPreferredWidth(100); // �����

		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		setLayout(null);

		jlEeInfo.setBounds(10, 15, 200, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		AppListController alc = new AppListController(this, er_num);
		addWindowListener(alc);
		jtApp.addMouseListener(alc);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds((erav.getX() + 150), (erav.getY() + 50), 800, 500);
		setResizable(false);
		setVisible(true);

	}// ������

	public JTable getJtEeInfo() {
		return jtApp;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new AppListView(null, "er_000033");
	}// main

}// class
