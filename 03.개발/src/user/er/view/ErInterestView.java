package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErInterestController;

/**
 * 관심구직자 목록을 볼 수 있는 창.
 * 
 * @author owner
 *
 */
@SuppressWarnings("serial")
public class ErInterestView extends JDialog {
	private JTable jtEeInfo;
	private JLabel jlEeInfo;
	private DefaultTableModel dtmEeInfo;

	public ErInterestView(ErMainView rmv, String er_id) {
		super(rmv, "관심 구직자", true);

		jlEeInfo = new JLabel("내 관심 구직자 수 : ");

		String[] eeInfoColumns = { "번호", "기본정보번호", "이미지", "이름", "직급", "근무지역", "학력", "나이", "포트폴리오 유무", "성별", "등록일" };
		dtmEeInfo = new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jtEeInfo = new JTable(dtmEeInfo) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};

		/* 테이블의 크기 조정 : 이미지 사이즈 150x200 */
		// 높이 조정
		jtEeInfo.setRowHeight(200);
		// 넓이 조정
		jtEeInfo.getColumnModel().getColumn(0).setPreferredWidth(40); // 번호
		jtEeInfo.getColumnModel().getColumn(1).setPreferredWidth(100); // 기본정보번호
		jtEeInfo.getColumnModel().getColumn(2).setPreferredWidth(150); // 이미지
		jtEeInfo.getColumnModel().getColumn(3).setPreferredWidth(70); // 이름
		jtEeInfo.getColumnModel().getColumn(4).setPreferredWidth(50); // 직급
		jtEeInfo.getColumnModel().getColumn(5).setPreferredWidth(70); // 근무지역
		jtEeInfo.getColumnModel().getColumn(6).setPreferredWidth(50); // 학력
		jtEeInfo.getColumnModel().getColumn(7).setPreferredWidth(50); // 나이
		jtEeInfo.getColumnModel().getColumn(8).setPreferredWidth(100); // 포트폴리오
		jtEeInfo.getColumnModel().getColumn(9).setPreferredWidth(50); // 성별
		jtEeInfo.getColumnModel().getColumn(10).setPreferredWidth(100); // 등록일

		JScrollPane jspEeInfo = new JScrollPane(jtEeInfo);

		/* 배치 */
		setLayout(null); // 수동 배치 설정.

		jlEeInfo.setBounds(10, 15, 150, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		ErInterestController eric = new ErInterestController(this, er_id);
		addWindowListener(eric);
		jtEeInfo.addMouseListener(eric);

		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}// 생성자

	public JTable getJtEeInfo() {
		return jtEeInfo;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}

	public static void main(String[] args) {
		new ErInterestView(null, "memoaa");
	}

}// class
