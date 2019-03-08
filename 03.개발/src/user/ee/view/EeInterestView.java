package user.ee.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeInterestController;

/**
 * ���� ���� ��Ȳ â
 * 
 * @author ����
 *
 */
@SuppressWarnings("serial")
public class EeInterestView extends JDialog {

	private JTable jtErInfo;
	private JLabel jlEeInfo; // ��ȸ�� ������ �� ����ϴ� ������Ʈ
	private DefaultTableModel dtmErInfo;

	public EeInterestView(EeMainView emv, String eeid) {
		super(emv, "���� ���� ����", true);

		/* ������Ʈ ���� */
		jlEeInfo = new JLabel("�� ���� �������� �� : ");

		String[] erInfoColumns = { "��ȣ", "����������ȣ", "����", "ȸ���", "����", "�ٹ�����", "�з�", "�������", "�޿�", "�����" };
		dtmErInfo = new DefaultTableModel(erInfoColumns, 20) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};

		jtErInfo = new JTable(dtmErInfo);

		/* ���̺� ���� ���� ���� ���� */
		// jtErInfo�� �� ���� ����
		jtErInfo.setRowHeight(25);
		// jtErInfo�� ũ�� �÷� ���� ����
		jtErInfo.getColumnModel().getColumn(0).setPreferredWidth(30);
		jtErInfo.getColumnModel().getColumn(1).setPreferredWidth(80);
		jtErInfo.getColumnModel().getColumn(2).setPreferredWidth(220);
		jtErInfo.getColumnModel().getColumn(3).setPreferredWidth(80);
		jtErInfo.getColumnModel().getColumn(4).setPreferredWidth(40);
		jtErInfo.getColumnModel().getColumn(5).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(6).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(7).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(60);

		JScrollPane jspEeInfo = new JScrollPane(jtErInfo);
		// jspEeInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		/* ������Ʈ ũ�� ���� */
		// �� ũ�� ����
		JPanel northPanel = new JPanel();
		northPanel.setSize(100, 100);
		northPanel.add(jlEeInfo);

		/* �����ӿ� ��ġ */
		setLayout(null); // ���� ��ġ ����
		jlEeInfo.setBounds(10, 15, 200, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeInfo);
		add(jspEeInfo);

		/* �̺�Ʈ ��� */
		EeInterestController eic = new EeInterestController(this, eeid);
		addWindowListener(eic);
		jtErInfo.addMouseListener(eic);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}// ������

	///////////////////// getter/////////////////////
	public JTable getjtErInfo() {
		return jtErInfo;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getdtmErInfo() {
		return dtmErInfo;
	}
	///////////////////// getter/////////////////////

}// class
