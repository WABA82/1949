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
		super(emv,"구인 정보 보기",true);
				
		String[] eeColumns= {"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태","급여","등록일"};
		dtmErInfo=new DefaultTableModel(eeColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtErInfo = new JTable(dtmErInfo) ;
				
		jtErInfo.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtErInfo.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(2).setPreferredWidth(250);
		jtErInfo.getColumnModel().getColumn(3).setPreferredWidth(150);
		jtErInfo.getColumnModel().getColumn(4).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(5).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(6).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(7).setPreferredWidth(60);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(70);
		jtErInfo.getColumnModel().getColumn(9).setPreferredWidth(130);
		
		jtErInfo.setRowHeight(30);
		
		String sort[]= {"등록일순","직급순","급여순"};
		jcbSort=new JComboBox<String>(sort);
		
		JScrollPane jspEe=new JScrollPane(jtErInfo);
		
		jbDetailSearch=new JButton("조건검색");
		jbAllView = new JButton("초기화");
		
		JLabel jlSearch=new JLabel("기업명 검색");
		jtfSearch = new JTextField();
		
		
		jbWordSearch=new JButton("검색");
		
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
		
		//이벤트 추가
		EeHiringController ehc = new EeHiringController(this,eeId);
		jcbSort.addActionListener(ehc);
		jbDetailSearch.addActionListener(ehc);
		jtfSearch.addActionListener(ehc);
		jbWordSearch.addActionListener(ehc);
		jtErInfo.addMouseListener(ehc);
		jbAllView.addActionListener(ehc);
		
		addWindowListener(ehc);
		
		
		setBounds(100, 100, 1000, 550);
		setResizable(false);
		setVisible(true);
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

	
}//class















