package user.ee.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeInterestController;
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeInterestView extends JDialog {

	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;

	public EeInterestView(EeMainView emv, List<EeHiringVO> ehvo) {
		super(emv, "관심 구인 정보", true);

		/* 컴포넌트 생성 */
		JLabel jlEeInfo = new JLabel("내 관심 구인정보 수 : ");

		String[] erInfoColumns = { "번호", "구인정보번호", "제목", "회사명", "직급", "근무지역", "학력", "고용형태", "급여", "등록일" };
		dtmErInfo = new DefaultTableModel(erInfoColumns, 15) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};

		jtErInfo = new JTable(dtmErInfo);
		JScrollPane jspEeInfo = new JScrollPane(jtErInfo);

		/* 컴포넌트 크기 설정 */

		// 라벨 크기 설정
		JPanel northPanel = new JPanel();
		northPanel.setSize(100, 100);
		northPanel.add(jlEeInfo);

		// jtErInfo의 행 높이 설정
		jtErInfo.setRowHeight(25);
		// jtErInfo의 크기 컬럼 넓이 설정
		jtErInfo.getColumnModel().getColumn(0).setPreferredWidth(30);
		jtErInfo.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(2).setPreferredWidth(125);
		jtErInfo.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtErInfo.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(5).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(6).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(7).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(50);
		jtErInfo.getColumnModel().getColumn(8).setPreferredWidth(60);

		/* 프레임에 배치 */
		setLayout(new BorderLayout(25, 25));
		add("North", jlEeInfo);
		add("Center", jspEeInfo);

		/* 이벤트 등록 */
		EeInterestController eic = new EeInterestController(this, ehvo);
		addWindowListener(eic);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 800, 400);
		setResizable(true);
		setVisible(true);
	}// 생성자

	///////////////////// getter/////////////////////
	public JTable getjtErInfo() {
		return jtErInfo;
	}

	public DefaultTableModel getdtmErInfo() {
		return dtmErInfo;
	}
<<<<<<< HEAD
	///////////////////// getter/////////////////////

	public static void main(String[] args) {
		new EeInterestView(null, null);
	}// main

}// class
=======
	public static void main(String[] args) {
		EeMainVO em_vo= new EeMainVO("fsd", "fdsf", "sdf", "dsfs");
		EeMainView emv=new EeMainView(em_vo);
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		new EeInterestView(emv,ehvo);
	}

}
>>>>>>> af80011f6d431b5fccc4b5723a06494ace16b48f
