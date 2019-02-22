package user.er.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErMgMtController;
import user.er.vo.ErListVO;


@SuppressWarnings("serial")
public class ErMgMtView extends JDialog {

	private JButton jbRegEr;
	private DefaultTableModel dtmEr;
	private JTable jtEr;
	
	public ErMgMtView(ErMainView rmv, List<ErListVO> list, String erId) {
		super(rmv,"���� ���� ����",true);
		
		String[] eeInfoColumns= {"��ȣ","����������ȣ","����","����","�ٹ�����","�з�","�������","�����"};
		dtmEr=new DefaultTableModel(eeInfoColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		jtEr.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(2).setPreferredWidth(370);
		jtEr.getColumnModel().getColumn(3).setPreferredWidth(70);
		jtEr.getColumnModel().getColumn(4).setPreferredWidth(70);
		jtEr.getColumnModel().getColumn(5).setPreferredWidth(70);
		jtEr.getColumnModel().getColumn(6).setPreferredWidth(70);
		jtEr.getColumnModel().getColumn(7).setPreferredWidth(100);
		jtEr.setRowHeight(30);
		
		JScrollPane jspEeInfo=new JScrollPane(jtEr);
		
		jbRegEr=new JButton("�� ���α� ���");
		
		setLayout(null);
		
		jspEeInfo.setBounds(0, 50, 1000, 415);
		jbRegEr.setBounds(850, 10, 130, 30);
		
		add(jspEeInfo);
		add(jbRegEr);
		
		ErMgMtController emmc = new ErMgMtController(this,erId);
		jbRegEr.addActionListener(emmc);
		jtEr.addMouseListener(emmc);
		
		setBounds(100, 100, 1015, 500);
		setVisible(true);
		
	}
	
	 public JButton getJbRegEr() {
		return jbRegEr;
	}


	public DefaultTableModel getDtmEr() {
		return dtmEr;
	}


	public JTable getJtEr() {
		return jtEr;
	}


	public static void main(String[] args) {
		new ErMgMtView(null, null, "moonlight");
	}
	
}
