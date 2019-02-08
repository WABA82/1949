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

import user.common.vo.EeMainVO;
import user.ee.controller.EeHiringController;
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeHiringView extends JDialog {

	private JButton jbDetailSearch,jbWordSearch;
	private JComboBox<String> jcbSort;
	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;
	private JTextField jtfSearch;
	
	public EeHiringView(EeMainView emv, List<EeHiringVO> eh_vo, String s) {
		super(emv,"���� ���� ����",true);
				
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmErInfo=new DefaultTableModel(eeColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		///////////��������//////////////////
		//"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"
		Object[] rowData1 = {"1","er_000001",	"�׽�Ʈ����1",	"1", "C","����", "C", "Y", "2500","2019-01-30 ���� 7:06:43"};
		Object[] rowData2 = {"2","er_000002",	"�׽�Ʈ����2",	"2", "C", "����", "C", "Y", "2300", "2019-01-29 ���� 7:06:42"};		
		dtmErInfo.addRow(rowData1);
		dtmErInfo.addRow(rowData2);
		
		String sort[]= {"����ϼ�","���޼�","�޿���"};
		jcbSort=new JComboBox<String>(sort);
		
		jtErInfo=new JTable(dtmErInfo);
		JScrollPane jspEe=new JScrollPane(jtErInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		
		JLabel jlSearch=new JLabel("����� �˻�");
		jtfSearch = new JTextField();
		
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
		
		
		EeHiringController ehc = new EeHiringController(this);
		jcbSort.addActionListener(ehc);
		jbDetailSearch.addActionListener(ehc);
		jtfSearch.addActionListener(ehc);
		jbWordSearch.addActionListener(ehc);
		jtErInfo.addMouseListener(ehc);
		addWindowListener(ehc);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		
	}//ErHiringView

	

	public JTextField getJtfSearch() {
		return jtfSearch;
	}



	public JTable getJtErInfo() {
		return jtErInfo;
	}

	public JButton getJbWordSearch() {
		return jbWordSearch;
	}

	public DefaultTableModel getDtmErInfo() {
		return dtmErInfo;
	}

	public JButton getJbDetailSearch() {
		return jbDetailSearch;
	}

	public JComboBox<String> getJcbSort() {
		return jcbSort;
	}

	public static void main(String[] args) {
		EeMainVO em_vo= new EeMainVO("ds", "sdf", "ds", "asd");
		EeMainView emv=new EeMainView(em_vo);
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		String s=new String();
		new EeHiringView(emv, ehvo, s);
	}
	
	
}//class















