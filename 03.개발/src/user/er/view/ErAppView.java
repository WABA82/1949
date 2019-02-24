package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.er.controller.ErAppController;

@SuppressWarnings("serial")
public class ErAppView extends JDialog {

	private JTable jtEr;
	private JLabel jlEeInfo;
	private DefaultTableModel dtmEr;

	public ErAppView(ErMainView rmv, String er_id) {
		super(rmv, "지원 현황", true);

		jlEeInfo = new JLabel("내 구인정보 수 : ");
		
		String[] erInfoColumns = { "번호", "구인정보번호", "제목", "직급", "근무지역", "학력", "고용형태", "등록일" };
		dtmEr = new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		jtEr = new JTable(dtmEr);
		JScrollPane jspEeInfo = new JScrollPane(jtEr);

		/* 테이블의 크기 조정 */
		// 높이 조정
		jtEr.setRowHeight(25);
		// 넓이 조정
		jtEr.getColumnModel().getColumn(0).setPreferredWidth(40); // 번호
		jtEr.getColumnModel().getColumn(1).setPreferredWidth(100); // 구인정보번호
		jtEr.getColumnModel().getColumn(2).setPreferredWidth(250); // 제목
		jtEr.getColumnModel().getColumn(3).setPreferredWidth(50); // 직급
		jtEr.getColumnModel().getColumn(4).setPreferredWidth(50); // 근무지역
		jtEr.getColumnModel().getColumn(5).setPreferredWidth(50); // 학력
		jtEr.getColumnModel().getColumn(6).setPreferredWidth(70); // 고용형태
		jtEr.getColumnModel().getColumn(7).setPreferredWidth(100); // 등록일

		/* 컴포넌트 크기설정 */
		setLayout(null);// 수동배치 설정
		jlEeInfo.setBounds(10, 15, 150, 25);
		jspEeInfo.setBounds(0, 50, 795, 495);

		/* 컴포넌트 배치 */
		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		ErAppController erac = new ErAppController(this, er_id);
		addWindowListener(erac);
		jtEr.addMouseListener(erac);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setVisible(true);
	}

	public JTable getJtEeInfo() {
		return jtEr;
	}

	public JLabel getJlEeInfo() {
		return jlEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}

	public static void main(String[] args) {
		new ErAppView(null, "meteo77");
	}// main

}// class
