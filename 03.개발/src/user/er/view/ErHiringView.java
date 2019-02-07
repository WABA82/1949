package user.er.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ErHiringView extends JDialog {

	private JButton jbDetailSearch, jbSearch;
	private JComboBox<String> jcbSort;
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	
	public ErHiringView(ErMainView emv) {
		super(emv,"���� ���� ����",true);
		
		
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������", "�޿�","�����"};
		dtmEeInfo=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEeInfo=new JTable(dtmEeInfo);
		JScrollPane jspEe=new JScrollPane(jtEeInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		JLabel jlSearch=new JLabel("����� �˻�");
		JTextField jtfSearch=new JTextField();
		jbSearch=new JButton("�˻�");
		
		String sort[]= {"����ϼ�", "���޼�", "�޿���"};
		jcbSort=new JComboBox<String>(sort);
		
		
		JPanel jpEeNorth=new JPanel();
		jpEeNorth.add(jbDetailSearch);
		jpEeNorth.add(jlSearch);
		jpEeNorth.add(jtfSearch);
		jpEeNorth.add(jbSearch);
		
		
		add("Center",jspEe);
		add("North",jcbSort);
		add("South",jpEeNorth);
		
		setBounds(100, 100, 800, 600);
		setVisible(true);
		
	}//ErHiringView

	
	
	public JButton getJbDetailSearch() {
		return jbDetailSearch;
	}



	public JButton getJbSearch() {
		return jbSearch;
	}



	public JComboBox<String> getJcbSort() {
		return jcbSort;
	}



	public JTable getJtEeInfo() {
		return jtEeInfo;
	}



	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}



	public static void main(String[] args) {
		ErMainView emv=new ErMainView();
		new ErHiringView(emv);
	}

	
	
}//class















