package user.er.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.ErMainVO;
import user.er.vo.ErListVO;

public class ErMgMtView extends JDialog {

	private JButton jbRegEr;
	private DefaultTableModel dtmEr;
	private JTable jtEr;
	
	public ErMgMtView(ErMainView rmv, ErListVO rlvo, String s) {
		super(rmv,"���� ���� ����",true);
		
		String[] eeInfoColumns= {"��ȣ","����������ȣ","����","����","�ٹ�����","�з�","�������","�����"};
		dtmEr=new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEeInfo=new JScrollPane(jtEr);
		
		jbRegEr=new JButton("�� ���α� ���");
		
		setLayout(null);
		
		jspEeInfo.setBounds(0, 50, 1000, 415);
		jbRegEr.setBounds(850, 10, 130, 30);
		
		add(jspEeInfo);
		add(jbRegEr);
		
		setBounds(100, 100, 1015, 500);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
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


/*	public static void main(String[] args) {
		ErMainVO rm_vo =new ErMainVO("sad", "sad", "ad", "sad");
		 ErMainView rmv=new ErMainView(rm_vo);
		 ErListVO rlvo=new ErListVO();
		 String s=new String();
		new ErMgMtView(rmv,rlvo,s);
	}*/
	
}
