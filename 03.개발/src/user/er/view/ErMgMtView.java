package user.er.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.vo.ErListVO;

public class ErMgMtView extends JDialog {

	private JButton jbRegEr;
	private DefaultTableModel dtmEr;
	private JTable jtEr;
	
	public ErMgMtView(ErMainView rmv, ErListVO rlvo, String s) {
		super(rmv,"구인 정보 관리",true);
		
		String[] eeInfoColumns= {"번호","구인정보번호","제목","직급","근무지역","학력","고용형태","등록일"};
		dtmEr=new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEeInfo=new JScrollPane(jtEr);
		
		jbRegEr=new JButton("새 구인글 등록");
		
		setLayout(null);
		
		jspEeInfo.setBounds(0, 50, 1000, 415);
		jbRegEr.setBounds(850, 10, 130, 30);
		
		add(jspEeInfo);
		add(jbRegEr);
		
		setBounds(100, 100, 1015, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
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


	/*public static void main(String[] args) {
		 ErMainView rmv=new ErMainView();
		 ErListVO rlvo=new ErListVO();
		 String s=new String();
		new ErMgMtView(rmv,rlvo,s);
	}*/
	
}
