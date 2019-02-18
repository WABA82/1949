package admin.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import admin.controller.AdminMgMtController;

@SuppressWarnings("serial")
public class AdminMgMtView extends JDialog {

	private JTable jtUser, jtEe, jtEr, jtCo; // 새로 추가한 인스턴스변수들(클릭이벤트 처리를 위해)
	private JTabbedPane jtb;
	private DefaultTableModel dtmUser, dtmEe, dtmEr, dtmCo;

	public AdminMgMtView(AdminMainView amv) {
		super(amv, "1949-전체관리", true);
		
		jtb=new JTabbedPane();
		
		String[] userColumns= {"번호","아이디","이름","연락처","주소","이메일","회원타입","등록일"};
		dtmUser=new DefaultTableModel(userColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtUser=new JTable(dtmUser);
		jtUser.setRowHeight(30); // 행 높이조절
		jtUser.getTableHeader().setReorderingAllowed(false); // 열 이동막김
		jtUser.getTableHeader().setResizingAllowed(false); // 열 너비변경 막기
		// width - 1500
		jtUser.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(2).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(4).setPreferredWidth(400);
		jtUser.getColumnModel().getColumn(5).setPreferredWidth(200);
		jtUser.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtUser.getColumnModel().getColumn(7).setPreferredWidth(200);
		JScrollPane jspUser=new JScrollPane(jtUser);
		jtb.add("회원관리", jspUser);
		
		String[] erColumns= {"번호","구인정보번호","제목","회사명","기업사용자","이름","연락처","직급","근무지역","학력","고용형태","급여","등록일"};
		
		dtmEr=new DefaultTableModel(erColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		jtEr.setRowHeight(30); // 행 높이조절
		jtEr.getTableHeader().setReorderingAllowed(false); // 열 이동막김
		jtEr.getTableHeader().setResizingAllowed(false); // 열 너비변경 막기
		// width - 1500
		jtEr.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(2).setPreferredWidth(400);
		jtEr.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(5).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(7).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(8).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(9).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(10).setPreferredWidth(100);
		jtEr.getColumnModel().getColumn(11).setPreferredWidth(50);
		jtEr.getColumnModel().getColumn(12).setPreferredWidth(100);
		JScrollPane jspEr=new JScrollPane(jtEr);
		jtb.add("구직자 기본정보관리", jspEr);
		
		
		String[] eeColumns= {"번호","기본정보번호","이미지","일반사용자","이름","직급","근무지역","학력","나이","포트폴리오 유무","성별","외부이력서 파일명","등록일"};
		dtmEe=new DefaultTableModel(eeColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		jtEe=new JTable(dtmEe);
		jtEe.setRowHeight(200); // 행 높이조절
		jtEe.getTableHeader().setReorderingAllowed(false); // 열 이동막김
		jtEe.getTableHeader().setResizingAllowed(false); // 열 너비변경 막기
		// width - 1500
		jtEe.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtEe.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(4).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(5).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(7).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(8).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(9).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(10).setPreferredWidth(100);
		jtEe.getColumnModel().getColumn(11).setPreferredWidth(200);
		jtEe.getColumnModel().getColumn(12).setPreferredWidth(100);
		JScrollPane jspEe=new JScrollPane(jtEe);
		jtb.add("구인자 구인정보관리", jspEe);
		
		
		String[] coColumns= {"번호","회사번호","이미지","회사명","기업사용자","설립년도","사원수","등록일"};
		dtmCo=new DefaultTableModel(coColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		
		jtCo=new JTable(dtmCo);
		jtCo.setRowHeight(170); // 행 높이조절
		jtCo.getTableHeader().setReorderingAllowed(false); // 열 이동막김
		jtCo.getTableHeader().setResizingAllowed(false); // 열 너비변경 막기
		// width - 1500
		jtCo.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtCo.getColumnModel().getColumn(1).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(2).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(3).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(4).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(5).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(6).setPreferredWidth(200);
		jtCo.getColumnModel().getColumn(7).setPreferredWidth(200);
		JScrollPane jspCo=new JScrollPane(jtCo);
		jtb.add("회사정보 관리", jspCo);
		
		add("Center",jtb);
		
		AdminMgMtController ammc = new AdminMgMtController(this);
		jtb.addMouseListener(ammc);
		jtUser.addMouseListener(ammc);
		jtEr.addMouseListener(ammc);
		jtEe.addMouseListener(ammc);
		jtCo.addMouseListener(ammc);
		addWindowListener(ammc);
		
		setBounds(100, 100, 1500, 700);
		setResizable(false);
		setVisible(true);
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

	public JTable getJtUser() {
		return jtUser;
	}

	public JTable getJtEe() {
		return jtEe;
	}

	public JTable getJtEr() {
		return jtEr;
	}

	public JTable getJtCo() {
		return jtCo;
	}
}//class















