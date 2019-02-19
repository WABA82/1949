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
	private DefaultTableModel dtmEr;

	public ErAppView(ErMainView rmv, String er_id) {
		super(rmv, "���� ��Ȳ", true);

		String[] erInfoColumns = { "��ȣ", "����������ȣ", "����", "����", "�ٹ�����", "�з�", "�������", "�����" };
		dtmEr = new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr = new JTable(dtmEr);
		JScrollPane jspEeInfo = new JScrollPane(jtEr);

		JLabel jlEeInfo = new JLabel("���� ����� �������� �� : ");

		/* ������Ʈ ũ�⼳�� */
		setLayout(null);// ������ġ ����
		jlEeInfo.setBounds(800, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);

		/* ������Ʈ ��ġ */
		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		ErAppController erac = new ErAppController(this, er_id);
		addWindowListener(erac);
		jtEr.addMouseListener(erac);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 1000, 500);
		setResizable(true);
		setVisible(true);
	}

	public JTable getJtEeInfo() {
		return jtEr;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}

	public static void main(String[] args) {
		new ErAppView(null, "meteo77");
	}// main

}// class
