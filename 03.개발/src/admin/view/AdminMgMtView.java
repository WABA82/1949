package admin.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import admin.controller.AdminMgMtController;

@SuppressWarnings("serial")
public class AdminMgMtView extends JFrame {

	private JTabbedPane jtb;
	private DefaultTableModel dtmUser, dtmEe, dtmEr, dtmCo;

	public AdminMgMtView() {
		super("1949-��ü����");
		
		jtb=new JTabbedPane();
		
		String[] userColumns= {"��ȣ","���̵�","�̸�","����ó","�ּ�","�̸���","ȸ��Ÿ��","�����"};
		dtmUser=new DefaultTableModel(userColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable jtUser=new JTable(dtmUser);
		
		JScrollPane jspUser=new JScrollPane(jtUser);
		
		jtb.add("ȸ������", jspUser);
		
	
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","��������","�̸�","����ó","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmEe=new DefaultTableModel(eeColumns, 40) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable jtEe=new JTable(dtmEe);

		JScrollPane jspEe=new JScrollPane(jtEe);
		
		jtb.add("������������", jspEe);
		
		String[] erColumns= {"��ȣ","�⺻������ȣ","�̹���","�Ϲݻ����","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","�ܺ��̷¼� ���ϸ�","�����"};

		dtmEr=new DefaultTableModel(erColumns, 40) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable jtEr=new JTable(dtmEr);
		JScrollPane jspEr=new JScrollPane(jtEr);
		
		
		jtb.add("������ �⺻��������", jspEr);
		
		String[] coColumns= {"��ȣ","ȸ���ȣ","�̹���","ȸ���","��������","�����⵵","�����","�����"};

		dtmCo=new DefaultTableModel(coColumns, 40) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable jtCo=new JTable(dtmCo);
		JScrollPane jspCo=new JScrollPane(jtCo);
		
		jtb.add("ȸ������ ����", jspCo);
		
		add("Center",jtb);
		
		AdminMgMtController ammc = new AdminMgMtController(this);
		jtUser.addMouseListener(ammc);
		jtEe.addMouseListener(ammc);
		jtEr.addMouseListener(ammc);
		jtCo.addMouseListener(ammc);
		addWindowListener(ammc);
		
		setBounds(100, 100, 1500, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
}//class















