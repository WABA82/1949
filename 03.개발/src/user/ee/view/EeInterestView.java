package user.ee.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeInterestController;
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeInterestView extends JDialog {

	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;

	public EeInterestView(EeMainView emv, List<EeHiringVO> ehvo) {
		super(emv, "���� ���� ����", true);

		/* ������Ʈ ���� */
		JLabel jlEeInfo = new JLabel("�� ���� �������� �� : ");

		String[] erInfoColumns = { "��ȣ", "����������ȣ", "����", "ȸ���", "����", "�ٹ�����", "�з�", "�������", "�޿�", "�����" };
		dtmErInfo = new DefaultTableModel(erInfoColumns, 15) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};

		jtErInfo = new JTable(dtmErInfo);
		JScrollPane jspEeInfo = new JScrollPane(jtErInfo);

		/* ������Ʈ ũ�� ���� */

		// �� ũ�� ����
		JPanel northPanel = new JPanel();
		northPanel.setSize(100, 100);
		northPanel.add(jlEeInfo);

		// jtErInfo�� �� ���� ����
		jtErInfo.setRowHeight(25);
		// jtErInfo�� ũ�� �÷� ���� ����
		jtErInfo.getColumnModel().getColumn(0).setPreferredWidth(30);
		jtErInfo.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(2).setPreferredWidth(125);
		jtErInfo.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(5).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(6).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(7).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(60);

		/* �����ӿ� ��ġ */
		setLayout(new BorderLayout(25, 25));
		add("North", jlEeInfo);
		add("Center", jspEeInfo);

		/* �̺�Ʈ ��� */
		EeInterestController eic = new EeInterestController(this, ehvo);
		addWindowListener(eic);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 800, 400);
		setResizable(true);
		setVisible(true);
	}// ������

	///////////////////// getter/////////////////////
	public JTable getjtErInfo() {
		return jtErInfo;
	}

	public DefaultTableModel getdtmErInfo() {
		return dtmErInfo;
	}
<<<<<<< HEAD
	///////////////////// getter/////////////////////

	public static void main(String[] args) {
		new EeInterestView(null, null);
	}// main

}// class
=======
	public static void main(String[] args) {
		EeMainVO em_vo= new EeMainVO("fsd", "fdsf", "sdf", "dsfs");
		EeMainView emv=new EeMainView(em_vo);
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		new EeInterestView(emv,ehvo);
	}

}
>>>>>>> af80011f6d431b5fccc4b5723a06494ace16b48f
