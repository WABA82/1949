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
	private DefaultTableModel dtmEr;

	public ErAppView(ErMainView rmv, String er_id) {
		super(rmv, "지원 현황", true);

		String[] erInfoColumns = { "번호", "구인정보번호", "제목", "직급", "근무지역", "학력", "고용형태", "등록일" };
		dtmEr = new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr = new JTable(dtmEr);
		JScrollPane jspEeInfo = new JScrollPane(jtEr);

		JLabel jlEeInfo = new JLabel("내가 등록한 구인정보 수 : ");

		/* 컴포넌트 크기설정 */
		setLayout(null);// 수동배치 설정
		jlEeInfo.setBounds(800, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);

		/* 컴포넌트 배치 */
		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		ErAppController erac = new ErAppController(this, er_id);
		addWindowListener(erac);
		jtEr.addMouseListener(erac);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 1000, 500);
		setResizable(true);
		setVisible(true);
	}

	public JTable getJtEeInfo() {
		return jtEr;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}

	public static void main(String[] args) {
		new ErAppView(null, "lucky012");
	}// main

}// class
