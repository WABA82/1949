package user.er.view;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppListView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;
	
	public AppListView(ErAppView rav) {
		super(rav,"�� ���� ��Ȳ",true);
	
		String[] eeInfoColumns= {"��ȣ","������ȣ","�̹���","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","������","��������"};
		dtmApp=new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp=new JTable(dtmApp);
		JScrollPane jspEeInfo=new JScrollPane(jtApp);
		
		JLabel jlEeInfo=new JLabel("�� ������ �� : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(880, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTable getJtEeInfo() {
		return jtApp;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}
	
	public static void main(String[] args) {
		ErMainView rmv=new ErMainView();
		ErAppView rav=new ErAppView(rmv);
		new AppListView(rav);
	}

}
