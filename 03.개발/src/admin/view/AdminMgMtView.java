package admin.view;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import admin.controller.AdminMgMtController;

@SuppressWarnings("serial")
public class AdminMgMtView extends JDialog {

	private JTable jtUser, jtEe, jtEr, jtCo; // ���� �߰��� �ν��Ͻ�������(Ŭ���̺�Ʈ ó���� ����)
	private JTabbedPane jtb;
	private DefaultTableModel dtmUser, dtmEe, dtmEr, dtmCo;
	
	private JPopupMenu jpUser, jpEe, jpEr, jpCo;
	private JMenuItem jmUserId, jmUserName, jmEeNum, jmEeName, 
		jmEeId, jmErNum, jmErCo, jmErId, jmCoNum, jmCoName, jmCoId,
		jmUserReset, jmEeReset, jmErReset, jmCoReset; 

	public AdminMgMtView(AdminMainView amv) {
		super(amv, "1949-��ü����", true);
		
		jtb=new JTabbedPane();
		
		String[] userColumns= {"��ȣ","���̵�","�̸�","����ó","�ּ�","�̸���","ȸ��Ÿ��","�����"};
		dtmUser=new DefaultTableModel(userColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtUser=new JTable(dtmUser);
		jtUser.setRowHeight(30); // �� ��������
		jtUser.getTableHeader().setReorderingAllowed(false); // �� �̵�����
		jtUser.getTableHeader().setResizingAllowed(false); // �� �ʺ񺯰� ����
		// width - 1500
		jtUser.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(2).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(4).setPreferredWidth(400);
		jtUser.getColumnModel().getColumn(5).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(7).setPreferredWidth(200);
		JScrollPane jspUser=new JScrollPane(jtUser);
		jtb.add("ȸ������", jspUser);
		
		String[] erColumns= {"��ȣ","����������ȣ","����","ȸ���","��������","�̸�","����ó","����","�ٹ�����","�з�","�������","�޿�","�����"};
		
		dtmEr=new DefaultTableModel(erColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		jtEr.setRowHeight(30); // �� ��������
		jtEr.getTableHeader().setReorderingAllowed(false); // �� �̵�����
		jtEr.getTableHeader().setResizingAllowed(false); // �� �ʺ񺯰� ����
		// width - 1500
		jtEr.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(2).setPreferredWidth(400);
		jtEr.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(5).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(7).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(8).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(9).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(10).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(11).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(12).setPreferredWidth(100);
		JScrollPane jspEr=new JScrollPane(jtEr);
		jtb.add("������ �⺻��������", jspEr);
		
		
		String[] eeColumns= {"��ȣ","�⺻������ȣ","�̹���","�Ϲݻ����","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","�ܺ��̷¼� ���ϸ�","�����"};
		dtmEe=new DefaultTableModel(eeColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		jtEe=new JTable(dtmEe);
		jtEe.setRowHeight(200); // �� ��������
		jtEe.getTableHeader().setReorderingAllowed(false); // �� �̵�����
		jtEe.getTableHeader().setResizingAllowed(false); // �� �ʺ񺯰� ����
		// width - 1500
		jtEe.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtEe.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(5).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(7).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(8).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(9).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(10).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(11).setPreferredWidth(200);
		jtEe.getColumnModel().getColumn(12).setPreferredWidth(100);
		JScrollPane jspEe=new JScrollPane(jtEe);
		jtb.add("������ ������������", jspEe);
		
		
		String[] coColumns= {"��ȣ","ȸ���ȣ","�̹���","ȸ���","��������","�����⵵","�����","�����"};
		dtmCo=new DefaultTableModel(coColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		
		jtCo=new JTable(dtmCo);
		jtCo.setRowHeight(170); // �� ��������
		jtCo.getTableHeader().setReorderingAllowed(false); // �� �̵�����
		jtCo.getTableHeader().setResizingAllowed(false); // �� �ʺ񺯰� ����
		// width - 1500
		jtCo.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtCo.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(4).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(5).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(6).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(7).setPreferredWidth(200);
		JScrollPane jspCo=new JScrollPane(jtCo);
		jtb.add("ȸ������ ����", jspCo);
		
		add("Center",jtb);
		
		////////�˾� ���ǰ˻� ��� �߰� 0307 ////////////
		// ���� - id, �̸� �˻�
		// ������ - ����������ȣ, ȸ���, id �˻�
		// ������ - �⺻������ȣ, �̸�, id �˻�
		//ȸ�� - ȸ���ȣ, ȸ���, id �˻�
		jmUserId = new JMenuItem("ID�� �˻�");
		jmUserName = new JMenuItem("�̸����� �˻�");
		jmUserReset = new JMenuItem("�˻����� �ʱ�ȭ");
		jpUser = new JPopupMenu();
		jpUser.add(jmUserReset);
		jpUser.addSeparator();
		jpUser.add(jmUserId);
		jpUser.add(jmUserName);
		
		jmEeNum = new JMenuItem("�⺻������ȣ�� �˻�");
		jmEeName = new JMenuItem("�̸����� �˻�");
		jmEeId = new JMenuItem("ID�� �˻�");
		jmEeReset = new JMenuItem("�˻����� �ʱ�ȭ");
		jpEe = new JPopupMenu();
		jpEe.add(jmEeReset);
		jpEe.addSeparator();
		jpEe.add(jmEeNum);
		jpEe.add(jmEeName);
		jpEe.add(jmEeId);
		
		jmErNum = new JMenuItem("����������ȣ�� �˻�"); 
		jmErCo = new JMenuItem("ȸ������� �˻�");
		jmErId = new JMenuItem("�ۼ��� ID�� �˻�");
		jmErReset = new JMenuItem("�˻����� �ʱ�ȭ");
		jpEr = new JPopupMenu();
		jpEr.add(jmErReset);
		jpEr.addSeparator();
		jpEr.add(jmErNum);
		jpEr.add(jmErCo);
		jpEr.add(jmErId);
		
		jmCoNum = new JMenuItem("ȸ���ȣ�� �˻�");
		jmCoName = new JMenuItem("ȸ������� �˻�");
		jmCoId = new JMenuItem("����� ID�� �˻�");
		jmCoReset = new JMenuItem("�˻����� �ʱ�ȭ");
		jpCo = new JPopupMenu();
		jpCo.add(jmCoReset);
		jpCo.addSeparator();
		jpCo.add(jmCoNum);
		jpCo.add(jmCoName);
		jpCo.add(jmCoId);
		
		AdminMgMtController ammc = new AdminMgMtController(this);
		jtb.addMouseListener(ammc);
		jtUser.addMouseListener(ammc);
		jtEr.addMouseListener(ammc);
		jtEe.addMouseListener(ammc);
		jtCo.addMouseListener(ammc);
		
		jmUserId.addMouseListener(ammc);
		jmUserName.addMouseListener(ammc);
		jmUserReset.addMouseListener(ammc);
		
		jmEeId.addMouseListener(ammc);
		jmEeName.addMouseListener(ammc);
		jmEeNum.addMouseListener(ammc);
		jmEeReset.addMouseListener(ammc);
		
		jmErCo.addMouseListener(ammc);
		jmErId.addMouseListener(ammc);
		jmErNum.addMouseListener(ammc);
		jmErReset.addMouseListener(ammc);
		
		jmCoId.addMouseListener(ammc);
		jmCoName.addMouseListener(ammc);
		jmCoNum.addMouseListener(ammc);
		jmCoReset.addMouseListener(ammc);
		
		addWindowListener(ammc);
		
		setBounds(100, 100, 1500, 700);
		setResizable(false);
		setVisible(true);
	}//AdminMainView
	
	public JTabbedPane getJtb() {
		return jtb;
	}
	
	public DefaultTableModel getDtmUser() {
		return dtmUser;
	}
	
	public DefaultTableModel getDtmEe() {
		return dtmEe;
	}
	
	public DefaultTableModel getDtmEr() {
		return dtmEr;
	}
	
	public DefaultTableModel getDtmCo() {
		return dtmCo;
	}

	public JTable getJtUser() {
		return jtUser;
	}

	public JTable getJtEe() {
		return jtEe;
	}

	public JTable getJtEr() {
		return jtEr;
	}

	public JTable getJtCo() {
		return jtCo;
	}

	public JPopupMenu getJpUser() {
		return jpUser;
	}

	public JPopupMenu getJpEe() {
		return jpEe;
	}

	public JPopupMenu getJpEr() {
		return jpEr;
	}

	public JPopupMenu getJpCo() {
		return jpCo;
	}

	public JMenuItem getJmUserId() {
		return jmUserId;
	}

	public JMenuItem getJmUserName() {
		return jmUserName;
	}

	public JMenuItem getJmEeNum() {
		return jmEeNum;
	}

	public JMenuItem getJmEeName() {
		return jmEeName;
	}

	public JMenuItem getJmEeId() {
		return jmEeId;
	}

	public JMenuItem getJmErNum() {
		return jmErNum;
	}

	public JMenuItem getJmErCo() {
		return jmErCo;
	}

	public JMenuItem getJmErId() {
		return jmErId;
	}

	public JMenuItem getJmCoNum() {
		return jmCoNum;
	}

	public JMenuItem getJmCoName() {
		return jmCoName;
	}

	public JMenuItem getJmCoId() {
		return jmCoId;
	}

	public JMenuItem getJmUserReset() {
		return jmUserReset;
	}

	public JMenuItem getJmEeReset() {
		return jmEeReset;
	}

	public JMenuItem getJmErReset() {
		return jmErReset;
	}

	public JMenuItem getJmCoReset() {
		return jmCoReset;
	}
}//class















