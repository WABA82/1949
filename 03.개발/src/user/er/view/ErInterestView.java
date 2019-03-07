package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErInterestController;

/**
 * ���ɱ����� ����� �� �� �ִ� â.
 * 
 * @author owner
 *
 */
@SuppressWarnings("serial")
public class ErInterestView extends JDialog {
	private JTable jtEeInfo;
	private JLabel jlEeInfo;
	private DefaultTableModel dtmEeInfo;

	public ErInterestView(ErMainView rmv, String er_id) {
		super(rmv, "���� ������", true);

		jlEeInfo = new JLabel("�� ���� ������ �� : ");

		String[] eeInfoColumns = { "��ȣ", "�⺻������ȣ", "�̹���", "�̸�", "����", "�ٹ�����", "�з�", "����", "��Ʈ������ ����", "����", "�����" };
		dtmEeInfo = new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jtEeInfo = new JTable(dtmEeInfo) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};

		/* ���̺��� ũ�� ���� : �̹��� ������ 150x200 */
		// ���� ����
		jtEeInfo.setRowHeight(200);
		// ���� ����
		jtEeInfo.getColumnModel().getColumn(0).setPreferredWidth(40); // ��ȣ
		jtEeInfo.getColumnModel().getColumn(1).setPreferredWidth(100); // �⺻������ȣ
		jtEeInfo.getColumnModel().getColumn(2).setPreferredWidth(150); // �̹���
		jtEeInfo.getColumnModel().getColumn(3).setPreferredWidth(70); // �̸�
		jtEeInfo.getColumnModel().getColumn(4).setPreferredWidth(50); // ����
		jtEeInfo.getColumnModel().getColumn(5).setPreferredWidth(70); // �ٹ�����
		jtEeInfo.getColumnModel().getColumn(6).setPreferredWidth(50); // �з�
		jtEeInfo.getColumnModel().getColumn(7).setPreferredWidth(50); // ����
		jtEeInfo.getColumnModel().getColumn(8).setPreferredWidth(100); // ��Ʈ������
		jtEeInfo.getColumnModel().getColumn(9).setPreferredWidth(50); // ����
		jtEeInfo.getColumnModel().getColumn(10).setPreferredWidth(100); // �����

		JScrollPane jspEeInfo = new JScrollPane(jtEeInfo);

		/* ��ġ */
		setLayout(null); // ���� ��ġ ����.

		jlEeInfo.setBounds(10, 15, 150, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		ErInterestController eric = new ErInterestController(this, er_id);
		addWindowListener(eric);
		jtEeInfo.addMouseListener(eric);

		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}// ������

	public JTable getJtEeInfo() {
		return jtEeInfo;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}

	public static void main(String[] args) {
		new ErInterestView(null, "memoaa");
	}

}// class
