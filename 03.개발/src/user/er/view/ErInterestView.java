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
	private DefaultTableModel dtmEeInfo;

	public ErInterestView(ErMainView rmv, String er_id) {
		super(rmv, "���� ������", true);

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
		JScrollPane jspEeInfo = new JScrollPane(jtEeInfo);

		JLabel jlEeInfo = new JLabel("�� ���� ������ �� : ");

		setLayout(null);

		jlEeInfo.setBounds(830, 8, 130, 30);
		jspEeInfo.setBounds(0, 40, 1000, 450);

		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		ErInterestController eric = new ErInterestController(this, er_id);
		addWindowListener(eric);
		jtEeInfo.addMouseListener(eric);

		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
	}// ������

	public JTable getJtEeInfo() {
		return jtEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}

	public static void main(String[] args) {
		new ErInterestView(null, "moonlight");
	}

}// class
