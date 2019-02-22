package user.ee.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeAppController;

/**
 * 일반사용자(구직자)의 지원현황 창.
 * 
 * @author owner
 *
 */
@SuppressWarnings("serial")
public class EeAppView extends JDialog {

	private JTable jtApp;
	private JLabel jlEeAppCnt;
	private DefaultTableModel dtmApp;

	private EeAppView(EeMainView emv, String ee_id) {
		super(emv, "지원 현황", true);

		jlEeAppCnt = new JLabel("내 지원 현황 수 : ");
		
		String[] erColumns = { "번호", /*"구인정보번호",*/ "지원번호", "제목", "회사명", "직급", "근무지역", "학력", "고용형태", "급여", "지원일", "지원상태" };
		dtmApp = new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp = new JTable(dtmApp);
		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		/* 테이블 컬럼의 사이즈 조정하기 */
		jtApp.getColumnModel().getColumn(0).setPreferredWidth(50);// 번호
		/*jtApp.getColumnModel().getColumn(1).setPreferredWidth(120);*/// 구인정보번호
		jtApp.getColumnModel().getColumn(1).setPreferredWidth(140);// 지원번호
		jtApp.getColumnModel().getColumn(2).setPreferredWidth(300);// 제목
		jtApp.getColumnModel().getColumn(3).setPreferredWidth(120);// 회사명
		jtApp.getColumnModel().getColumn(4).setPreferredWidth(60);// 직급
		jtApp.getColumnModel().getColumn(5).setPreferredWidth(80);// 근무지역
		jtApp.getColumnModel().getColumn(6).setPreferredWidth(60);// 학력
		jtApp.getColumnModel().getColumn(7).setPreferredWidth(100);// 고용형태
		jtApp.getColumnModel().getColumn(8).setPreferredWidth(70);// 급여
		jtApp.getColumnModel().getColumn(9).setPreferredWidth(120);// 지원일
		jtApp.getColumnModel().getColumn(10).setPreferredWidth(100);// 지원상태

		jtApp.setRowHeight(25);
		
		/* 배치 */
		setLayout(null); // 수동 배치 설정
		jlEeAppCnt.setBounds(10, 15, 200, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeAppCnt);
		add(jspEeInfo);

		/* 이벤트 등록 */
		EeAppController eac = new EeAppController(this, ee_id);
		addWindowListener(eac);
		jtApp.addMouseListener(eac);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}// 생성자

	public JTable getJtEr() {
		return jtApp;
	}
	
	public JLabel getJlEeAppCnt() {
		return jlEeAppCnt;
	}

	public DefaultTableModel getDtmEr() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new EeAppView(null, "gong1");
	}// main

}// class
