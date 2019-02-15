package user.ee.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeHiringController;
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeHiringView extends JDialog {

	private JButton jbDetailSearch,jbWordSearch, jbAllView ;
	private JComboBox<String> jcbSort;
	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;
	private JTextField jtfSearch;
	
	public EeHiringView(EeMainView emv, List<EeHiringVO> eh_vo, String eeId) {
		super(emv,"���� ���� ����",true);
				
		String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmErInfo=new DefaultTableModel(eeColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtErInfo = new JTable(dtmErInfo) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		jtErInfo.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtErInfo.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(2).setPreferredWidth(250);
		jtErInfo.getColumnModel().getColumn(3).setPreferredWidth(150);
		jtErInfo.getColumnModel().getColumn(4).setPreferredWidth(30);
		jtErInfo.getColumnModel().getColumn(5).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(6).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(7).setPreferredWidth(30);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(70);
		jtErInfo.getColumnModel().getColumn(9).setPreferredWidth(200);
		
		String sort[]= {"����ϼ�","���޼�","�޿���"};
		jcbSort=new JComboBox<String>(sort);
		
		JScrollPane jspEe=new JScrollPane(jtErInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		jbAllView = new JButton("��ü����");
		
		JLabel jlSearch=new JLabel("����� �˻�");
		jtfSearch = new JTextField();
		
		
		jbWordSearch=new JButton("�˻�");
		
		setLayout(null);
		
		jcbSort.setBounds(10, 10, 100, 30);
		jspEe.setBounds(0, 50, 980, 360);
		jbDetailSearch.setBounds(10, 425, 100, 30);
		jlSearch.setBounds(620, 425, 100, 30);
		jtfSearch.setBounds(700, 425, 190, 30);
		jbWordSearch.setBounds(900, 425, 80, 30);
		jbAllView.setBounds(120, 10, 100, 30);
		
		add(jcbSort);
		add(jspEe);
		add(jbDetailSearch);
		add(jlSearch);
		add(jtfSearch);
		add(jbWordSearch);
		add(jbAllView);
		
		//�̺�Ʈ �߰�
		eeId= "gong1";
		EeHiringController ehc = new EeHiringController(this,eeId);
		jcbSort.addActionListener(ehc);
		jbDetailSearch.addActionListener(ehc);
		jtfSearch.addActionListener(ehc);
		jbWordSearch.addActionListener(ehc);
		jtErInfo.addMouseListener(ehc);
		jbAllView.addActionListener(ehc);
		
		addWindowListener(ehc);
		
		
		setBounds(100, 100, 1000, 550);
		setVisible(true);
		setResizable(false);
		jtfSearch.requestFocus();
		
	}//ErHiringView

	

	public JTextField getJtfSearch() {
		return jtfSearch;
	}
	

	public JButton getJbAllView() {
		return jbAllView;
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
		
		new EeHiringView(null,null,null);
	}
	
	
}//class















