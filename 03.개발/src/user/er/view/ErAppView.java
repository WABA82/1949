package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ErAppView extends JDialog {
	private JTable jtEr;
	private DefaultTableModel dtmEr;
	
	public ErAppView(ErMainView emv) {
		super(emv, "���� ��Ȳ",true);
		
		String[] eeColumns= {"��ȣ","������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������", "�޿�","������","��������"};
		dtmEr=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEr=new JScrollPane(jtEr);
		
		
		add("Center",jspEr);
		
		setBounds(100, 100, 800, 600);
		setVisible(true);
	}
	public static void main(String[] args) {
		ErMainView emv=new ErMainView();
		new ErAppView(emv);
	}
}
