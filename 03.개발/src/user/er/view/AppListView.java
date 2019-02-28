package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.AppListController;

@SuppressWarnings("serial")
public class AppListView extends JDialog {

	private JTable jtApp;
	private JLabel jlEeInfo;
	private DefaultTableModel dtmApp;

	public AppListView(ErAppView erav, String er_num) {
		super(erav, "상세 지원 현황", true);

		jlEeInfo = new JLabel("총 지원자 수 : ");

		String[] eeInfoColumns = { "번호", "지원번호", "이미지", "이름", "직급", "근무지역", "학력", "나이", "포트폴리오 유무", "성별", "지원일",
				"지원상태" };
		dtmApp = new DefaultTableModel(eeInfoColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp = new JTable(dtmApp) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};

		/* 테이블의 크기 조정 : 이미지 사이즈 150x200 */
		// 높이 조정
		jtApp.setRowHeight(200);
		// 넓이 조정
		jtApp.getColumnModel().getColumn(0).setPreferredWidth(40); // 번호
		jtApp.getColumnModel().getColumn(1).setPreferredWidth(100); // 기본정보번호
		jtApp.getColumnModel().getColumn(2).setPreferredWidth(150); // 이미지
		jtApp.getColumnModel().getColumn(3).setPreferredWidth(70); // 이름
		jtApp.getColumnModel().getColumn(4).setPreferredWidth(50); // 직급
		jtApp.getColumnModel().getColumn(5).setPreferredWidth(70); // 근무지역
		jtApp.getColumnModel().getColumn(6).setPreferredWidth(50); // 학력
		jtApp.getColumnModel().getColumn(7).setPreferredWidth(50); // 나이
		jtApp.getColumnModel().getColumn(8).setPreferredWidth(100); // 포트폴리오
		jtApp.getColumnModel().getColumn(9).setPreferredWidth(50); // 성별
		jtApp.getColumnModel().getColumn(10).setPreferredWidth(100); // 등록일

		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		setLayout(null);

		jlEeInfo.setBounds(10, 15, 200, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		AppListController alc = new AppListController(this, er_num);
		addWindowListener(alc);
		jtApp.addMouseListener(alc);

		/* 프레임 크기 설정 및 가시화 */
		setBounds((erav.getX() + 150), (erav.getY() + 50), 800, 500);
		setResizable(false);
		setVisible(true);

	}// 생성자

	public JTable getJtEeInfo() {
		return jtApp;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new AppListView(null, "er_000033");
	}// main

}// class
