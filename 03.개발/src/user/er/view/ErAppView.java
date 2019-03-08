package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErAppController;

@SuppressWarnings("serial")
public class ErAppView extends JDialog {

	private JTable jtEr;
	private JLabel jlEeInfo;
	private DefaultTableModel dtmEr;

	public ErAppView(ErMainView rmv, String er_id) {
		super(rmv, "���� ��Ȳ", true);

		jlEeInfo = new JLabel("�� �������� �� : ");
		
		String[] erInfoColumns = { "��ȣ", "����������ȣ", "����", "����", "�ٹ�����", "�з�", "�������", "�����" };
		dtmEr = new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		jtEr = new JTable(dtmEr);
		JScrollPane jspEeInfo = new JScrollPane(jtEr);

		/* ���̺��� ũ�� ���� */
		// ���� ����
		jtEr.setRowHeight(25);
		// ���� ����
		jtEr.getColumnModel().getColumn(0).setPreferredWidth(40); // ��ȣ
		jtEr.getColumnModel().getColumn(1).setPreferredWidth(100); // ����������ȣ
		jtEr.getColumnModel().getColumn(2).setPreferredWidth(250); // ����
		jtEr.getColumnModel().getColumn(3).setPreferredWidth(50); // ����
		jtEr.getColumnModel().getColumn(4).setPreferredWidth(50); // �ٹ�����
		jtEr.getColumnModel().getColumn(5).setPreferredWidth(50); // �з�
		jtEr.getColumnModel().getColumn(6).setPreferredWidth(70); // �������
		jtEr.getColumnModel().getColumn(7).setPreferredWidth(100); // �����

		/* ������Ʈ ũ�⼳�� */
		setLayout(null);// ������ġ ����
		jlEeInfo.setBounds(10, 15, 150, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		/* ������Ʈ ��ġ */
		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		ErAppController erac = new ErAppController(this, er_id);
		addWindowListener(erac);
		jtEr.addMouseListener(erac);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}

	public JTable getJtEeInfo() {
		return jtEr;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}

}// class
