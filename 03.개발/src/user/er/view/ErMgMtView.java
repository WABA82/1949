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
		super(rmv,"구인 정보 관리",true);
		
		String[] eeInfoColumns= {"번호","구인정보번호","제목","직급","근무지역","학력","고용형태","등록일"};
		dtmEr=new DefaultTableModel(eeInfoColumns, 0) {
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
		
		////////// 가데이터
		erId = "moonlight";
		/////////
		ErMgMtController emmc = new ErMgMtController(this,list,erId);
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
		ErMgMtView emmv = new ErMgMtView(null, null, null);
	}
	
}
