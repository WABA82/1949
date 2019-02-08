package user.er.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.vo.ErHiringVO;

@SuppressWarnings("serial")
public class ErHiringView extends JDialog {


	private JButton jbDetailSearch,jbSearch;
	private JComboBox<String> jcbSort;
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	
	public ErHiringView(ErMainView rmv, List<ErHiringVO> rhvo, String s) {
		super(rmv,"������ ���� ����",true);
				
		String[] erColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmEeInfo=new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String sort[]= {"����ϼ�","���޼�","�޿���"};
		jcbSort=new JComboBox<String>(sort);
		
		jtEeInfo=new JTable(dtmEeInfo);
		JScrollPane jspEe=new JScrollPane(jtEeInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		
		
		
		setLayout(null);
		
		jcbSort.setBounds(10, 10, 100, 30);
		jbDetailSearch.setBounds(880, 10, 100, 30);
		jspEe.setBounds(0, 50, 995, 450);
		
		add(jcbSort);
		add(jspEe);
		add(jbDetailSearch);
	
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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







	/*public static void main(String[] args) {
		ErMainView rmv=new ErMainView();
		List<ErHiringVO> rhvo=new ArrayList<ErHiringVO>();
		String s=new String();
		new ErHiringView(rmv,rhvo,s);
	}*/

	
	
}//class















