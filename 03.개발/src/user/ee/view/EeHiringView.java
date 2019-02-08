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
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeHiringView extends JDialog {

	private JButton jbDetailSearch,jbWordSearch;
	private JComboBox<String> jcbSort;
	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;
	
	
	public EeHiringView(EeMainView emv, List<EeHiringVO> ehvo, String s) {
		super(emv,"구인 정보 보기",true);
				
		String[] eeColumns= {"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태","급여","등록일"};
		dtmErInfo=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		String sort[]= {"등록일순","직급순","급여순"};
		jcbSort=new JComboBox<String>(sort);
		
		jtErInfo=new JTable(dtmErInfo);
		JScrollPane jspEe=new JScrollPane(jtErInfo);
		
		jbDetailSearch=new JButton("조건검색");
		
		JLabel jlSearch=new JLabel("기업명 검색");
		JTextField jtfSearch=new JTextField();
		
		jbWordSearch=new JButton("검색");
		
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
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
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



	public JTable getjtErInfo() {
		return jtErInfo;
	}



	public DefaultTableModel getdtmErInfo() {
		return dtmErInfo;
	}



/*	public static void main(String[] args) {
		EeMainVO em_vo= new EeMainVO("ds", "sdf", "ds", "asd");
		EeMainView emv=new EeMainView(em_vo);
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		String s=new String();
		new EeHiringView(emv, ehvo, s);
	}*/
	
	
}//class















