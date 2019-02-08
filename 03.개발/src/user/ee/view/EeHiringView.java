package user.ee.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeHiringView extends JDialog {

	private JButton jbDetailSearch,jbWordSearch;
	private JComboBox<String> jcbSort;
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	
	public EeHiringView(EeMainView emv, List<EeHiringVO> ehvo, String s) {
		super(emv,"���� ���� ����",true);
				
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmEeInfo=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		String sort[]= {"����ϼ�","���޼�","�޿���"};
		jcbSort=new JComboBox<String>(sort);
		
		jtEeInfo=new JTable(dtmEeInfo);
		JScrollPane jspEe=new JScrollPane(jtEeInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		
		JLabel jlSearch=new JLabel("����� �˻�");
		JTextField jtfSearch=new JTextField();
		
		jbWordSearch=new JButton("�˻�");
		
		setLayout(null);
		
		jcbSort.setBounds(10, 10, 100, 30);
		jspEe.setBounds(0, 50, 995, 360);
		jbDetailSearch.setBounds(10, 425, 100, 30);
		jlSearch.setBounds(620, 425, 100, 30);
		jtfSearch.setBounds(700, 425, 190, 30);
		jbWordSearch.setBounds(900, 425, 80, 30);
		
		add(jcbSort);
		add(jspEe);
		add(jbDetailSearch);
		add(jlSearch);
		add(jtfSearch);
		add(jbWordSearch);
	
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//ErHiringView

	

	public JButton getJbDetailSearch() {
		return jbDetailSearch;
	}



	public JButton getJbSearch() {
		return jbWordSearch;
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
		EeMainView emv=new EeMainView();
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		String s=new String();
		new EeHiringView(emv, ehvo, s);
	}
	
	
}//class















