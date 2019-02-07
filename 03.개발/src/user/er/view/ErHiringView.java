package user.er.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ErHiringView extends JDialog {

	private JButton jbDetailSearch, jbSearch;
	private JComboBox<String> jcbSort;
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	
	public ErHiringView(ErMainView emv) {
		super(emv,"구인 정보 보기",true);
		
		
		String[] eeColumns= {"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태", "급여","등록일"};
		dtmEeInfo=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEeInfo=new JTable(dtmEeInfo);
		JScrollPane jspEe=new JScrollPane(jtEeInfo);
		
		jbDetailSearch=new JButton("조건검색");
		JLabel jlSearch=new JLabel("기업명 검색");
		JTextField jtfSearch=new JTextField();
		jbSearch=new JButton("검색");
		
		String sort[]= {"등록일순", "직급순", "급여순"};
		jcbSort=new JComboBox<String>(sort);
		
		
		JPanel jpEeNorth=new JPanel();
		jpEeNorth.add(jbDetailSearch);
		jpEeNorth.add(jlSearch);
		jpEeNorth.add(jtfSearch);
		jpEeNorth.add(jbSearch);
		
		
		add("Center",jspEe);
		add("North",jcbSort);
		add("South",jpEeNorth);
		
		setBounds(100, 100, 800, 600);
		setVisible(true);
		
	}//ErHiringView

	
	
	public JButton getJbDetailSearch() {
		return jbDetailSearch;
	}



	public JButton getJbSearch() {
		return jbSearch;
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



	public static void main(String[] args) {
		ErMainView emv=new ErMainView();
		new ErHiringView(emv);
	}

	
	
}//class















