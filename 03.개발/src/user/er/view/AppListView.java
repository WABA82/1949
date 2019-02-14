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
	private DefaultTableModel dtmApp;

	public AppListView(ErAppView erav, String er_num) {
		super(erav, "상세 지원 현황", true);

		String[] eeInfoColumns = { "번호", "지원번호", "이미지", "이름", "직급", "근무지역", "학력", "나이", "포트폴리오 유무", "성별", "지원일",
				"지원상태" };
		dtmApp = new DefaultTableModel(eeInfoColumns, 40) {
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
		JScrollPane jspEeInfo = new JScrollPane(jtApp);

		JLabel jlEeInfo = new JLabel("총 지원자 수 : ");

		setLayout(null);

		jlEeInfo.setBounds(880, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);

		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		AppListController alc = new AppListController(this, er_num);
		addWindowListener(alc);
		jtApp.addMouseListener(alc);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);

	}// 생성자

	public JTable getJtEeInfo() {
		return jtApp;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new AppListView(null, "er_000028");
	}// main

}// class
