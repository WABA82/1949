package admin.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AdminMgMtView extends JFrame {

	private JTabbedPane jtb;
	private DefaultTableModel dtmUser, dtmEe, dtmEr, dtmCo;

	
	public AdminMgMtView() {
		super("1949-전체관리");
		
		jtb=new JTabbedPane();
		
		String[] userColumns= {"번호","아이디","이름","연락처","주소","이메일","회원타입","등록일"};
		dtmUser=new DefaultTableModel(userColumns, 4) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpUser=new JPanel();
		
		
		jtb.add("회원관리", jpUser);
		
	
		String[] eeColumns= {"번호","구인정보번호","제목","회사명","기업사용자","이름","연락처","직급","근무지역","학력","고용형태","급여","등록일"};
		dtmEe=new DefaultTableModel(eeColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpEe=new JPanel();
		
		jtb.add("구인정보관리", jpEe);
		
		String[] erColumns= {"번호","기본정보번호","이미지","일반사용자","이름","직급","근무지역","학력","나이","포트폴리오 유무","성별","외부이력서 파일명","등록일"};
		dtmEe=new DefaultTableModel(erColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpEr=new JPanel();
		
		jtb.add("구직자 기본정보관리", jpEr);
		
		String[] coColumns= {"번호","회사번호","이미지","회사명","기업사용자","설립년도","사원수","등록일"};
		dtmEe=new DefaultTableModel(coColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JPanel jpCo=new JPanel();
		
		jtb.add("회사정보 관리", jpCo);
		
		add("Center",jtb);
		
	}//AdminMainView


	public JTabbedPane getJtb() {
		return jtb;
	}


	public DefaultTableModel getDtmUser() {
		return dtmUser;
	}


	public DefaultTableModel getDtmEe() {
		return dtmEe;
	}


	public DefaultTableModel getDtmEr() {
		return dtmEr;
	}


	public DefaultTableModel getDtmCo() {
		return dtmCo;
	}
	
	
}//class















