package user.ee.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.vo.EeHiringVO;

public class EeInterestView extends JDialog {

	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	public EeInterestView(EeMainView emv, List<EeHiringVO> ehvo) {
		super(emv,"관심 구인 정보",true);
	
		String[] erInfoColumns= {"번호","구인정보번호","제목","회사명","직급","근무지역","학력","고용형태","급여","등록일"};
		dtmEeInfo=new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEeInfo=new JTable(dtmEeInfo);
		JScrollPane jspEeInfo=new JScrollPane(jtEeInfo);
		
		JLabel jlEeInfo=new JLabel("내 관심 구인정보 수 : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(830, 8, 130, 30);
		jspEeInfo.setBounds(0, 40, 1000, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JTable getJtEeInfo() {
		return jtEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}
	/*public static void main(String[] args) {
		EeMainView emv=new EeMainView();
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		new EeInterestView(emv,ehvo);
	}*/

}
