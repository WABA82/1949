package user.er.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.ErMainVO;
import user.er.controller.ErInterestController;
import user.er.vo.ErHiringVO;

@SuppressWarnings("serial")
public class ErInterestView extends JDialog {
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;

	public ErInterestView(ErMainView rmv, String er_id) {
		super(rmv, "관심 구직자", true);

		String[] eeInfoColumns = { "번호", "기본정보번호", "이미지", "이름", "직급", "근무지역", "학력", "나이", "포트폴리오 유무", "성별", "등록일" };
		dtmEeInfo = new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEeInfo = new JTable(dtmEeInfo);
		JScrollPane jspEeInfo = new JScrollPane(jtEeInfo);

		JLabel jlEeInfo = new JLabel("내 관심 구직자 수 : ");

		setLayout(null);

		jlEeInfo.setBounds(830, 8, 130, 30);
		jspEeInfo.setBounds(0, 40, 1000, 450);

		add(jlEeInfo);
		add(jspEeInfo);

		/* 이벤트 등록 */
		ErInterestController eric = new ErInterestController(this, er_id);
		addWindowListener(eric);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
	}// 생성자

	public JTable getJtEeInfo() {
		return jtEeInfo;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEeInfo;
	}

	public static void main(String[] args) {
		new ErInterestView(null, "gang123");
	}

}// class
