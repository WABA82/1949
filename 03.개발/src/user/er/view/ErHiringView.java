package user.er.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErHiringController;
import user.er.vo.ErHiringVO;

@SuppressWarnings("serial")
public class ErHiringView extends JDialog {


	private JButton jbDetailSearch, jbSelectAll;
	private JComboBox<String> jcbSort;
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	public ErHiringView(ErMainView rmv, List<ErHiringVO> list, String erId) {
		super(rmv,"������ ���� ����",true);
		String[] erColumns= {"��ȣ","�⺻������ȣ","�̹���","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","�����"};
		dtmEeInfo=new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEeInfo = new JTable(dtmEeInfo) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		//1000
		jtEeInfo.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtEeInfo.getColumnModel().getColumn(1).setPreferredWidth(130);//80
		jtEeInfo.getColumnModel().getColumn(2).setPreferredWidth(150);//230
		jtEeInfo.getColumnModel().getColumn(3).setPreferredWidth(80);//290
		jtEeInfo.getColumnModel().getColumn(4).setPreferredWidth(60);//350
		jtEeInfo.getColumnModel().getColumn(5).setPreferredWidth(70);//410
		jtEeInfo.getColumnModel().getColumn(6).setPreferredWidth(70);//470
		jtEeInfo.getColumnModel().getColumn(7).setPreferredWidth(50);//510
		jtEeInfo.getColumnModel().getColumn(8).setPreferredWidth(110);//570
		jtEeInfo.getColumnModel().getColumn(9).setPreferredWidth(60);//630
		jtEeInfo.getColumnModel().getColumn(10).setPreferredWidth(180);//800
		
		jtEeInfo.setRowHeight(200);
		String sort[]= {"����ϼ�","���޼�"};
		jcbSort=new JComboBox<String>(sort);
		
		JScrollPane jspEe=new JScrollPane(jtEeInfo);
		
		jbDetailSearch=new JButton("���ǰ˻�");
		jbSelectAll = new JButton("�ʱ�ȭ");
		
		setLayout(null);
		
		jcbSort.setBounds(10, 10, 100, 30);
		jbDetailSearch.setBounds(880, 10, 100, 30);
		jbSelectAll.setBounds(115, 10, 100, 30);
		jspEe.setBounds(0, 50, 995, 800); //450
		
		add(jcbSort);
		add(jspEe);
		add(jbDetailSearch);
		add(jbSelectAll);
		/////�̺�Ʈ//////
		ErHiringController ehc = new ErHiringController(this,erId);
		jbDetailSearch.addActionListener(ehc);
		jcbSort.addActionListener(ehc);
		jtEeInfo.addMouseListener(ehc);
		jbSelectAll.addActionListener(ehc);
		addWindowListener(ehc);
		
		setBounds(100, 100, 1000, 877); //527
		setResizable(false);
		setVisible(true);
		
	}//ErHiringView

	public JButton getJbDetailSearch() {
		return jbDetailSearch;
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

	public JButton getJbSelectAll() {
		return jbSelectAll;
	}
	
	
}//class















