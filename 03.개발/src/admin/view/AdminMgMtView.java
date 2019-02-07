package admin.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AdminMgMtView extends JFrame {

	private JTabbedPane jtb;
	private DefaultTableModel dtmUser, dtmEe, dtmEr, dtmCo;

	
	public AdminMgMtView() {
		super("1949-��ü����");
		
		jtb=new JTabbedPane();
		
		String[] userColumns= {"��ȣ","���̵�","�̸�","����ó","�ּ�","�̸���","ȸ��Ÿ��","�����"};
		dtmUser=new DefaultTableModel(userColumns, 4) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpUser=new JPanel();
		
		
		jtb.add("ȸ������", jpUser);
		
	
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","��������","�̸�","����ó","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmEe=new DefaultTableModel(eeColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpEe=new JPanel();
		
		jtb.add("������������", jpEe);
		
		String[] erColumns= {"��ȣ","�⺻������ȣ","�̹���","�Ϲݻ����","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","�ܺ��̷¼� ���ϸ�","�����"};
		dtmEe=new DefaultTableModel(erColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpEr=new JPanel();
		
		jtb.add("������ �⺻��������", jpEr);
		
		String[] coColumns= {"��ȣ","ȸ���ȣ","�̹���","ȸ���","��������","�����⵵","�����","�����"};
		dtmEe=new DefaultTableModel(coColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpCo=new JPanel();
		
		jtb.add("ȸ������ ����", jpCo);
		
		add("Center",jtb);
		
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















