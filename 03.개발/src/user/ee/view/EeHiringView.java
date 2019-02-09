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
		super(emv,"구인 정보 보기",true);
				
		String[] eeColumns= {"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태","급여","등록일"};
		dtmErInfo=new DefaultTableModel(eeColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		///////////가데이터//////////////////
		//"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태","급여","등록일"
		Object[] rowData1 = {"1","er_000001",	"테스트제목1",	"1", "C","서울", "C", "Y", "2500","2019-01-30 오후 7:06:43"};
		Object[] rowData2 = {"2","er_000002",	"테스트제목2",	"2", "C", "서울", "C", "Y", "2300", "2019-01-29 오후 7:06:42"};		
		dtmErInfo.addRow(rowData1);
		dtmErInfo.addRow(rowData2);
		
		String sort[]= {"등록일순","직급순","급여순"};
		jcbSort=new JComboBox<String>(sort);
		
		jtErInfo=new JTable(dtmErInfo);
		JScrollPane jspEe=new JScrollPane(jtErInfo);
		
		jbDetailSearch=new JButton("조건검색");
		
		JLabel jlSearch=new JLabel("기업명 검색");
		jtfSearch = new JTextField();
		
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
		
		//이벤트 추가
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















