package user.er.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ErAppView extends JDialog {


	private JTable jtEr;
	private DefaultTableModel dtmEr;
	
	public ErAppView(ErMainView rmv) {
		super(rmv,"���� ��Ȳ",true);
	
		String[] erInfoColumns= {"��ȣ","����������ȣ","����","����","�ٹ�����","�з�","�������","�����"};
		dtmEr=new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEeInfo=new JScrollPane(jtEr);
		
		JLabel jlEeInfo=new JLabel("���� ����� �������� �� : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(800, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTable getJtEeInfo() {
		return jtEr;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}
	/*public static void main(String[] args) {
		
		  ErMainView rmv=new ErMainView(); 
		  new ErAppView(rmv);
		 
	}*/

}
