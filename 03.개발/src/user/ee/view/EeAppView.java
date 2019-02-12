package user.ee.view;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.ee.controller.EeAppController;
import user.ee.vo.EeAppVO;


/**
 * 일반사용자(구직자)의 지원현황 창.
 * @author owner
 *
 */
@SuppressWarnings("serial")
public class EeAppView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;
	
	private EeAppView(EeMainView emv, List<EeAppVO> eavo) {
		super(emv,"지원 현황", true);
		
		String[] erColumns= {"번호","구인정보번호","지원번호","제목","회사명","직급","근무지역","학력","고용형태","급여","지원일","지원상태"};
		dtmApp=new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp=new JTable(dtmApp);
		JScrollPane jspEeInfo=new JScrollPane(jtApp);
		
		/* 배치 */
		add("Center",jspEeInfo);
		
		/* 이벤트 등록 */
		EeAppController eac = new EeAppController(this, "gong1");
		addWindowListener(eac);
		jtApp.addMouseListener(eac);
		
		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 1000, 500);
		setVisible(true);
	}// 생성자
	
	
	public JTable getJtEr() {
		return jtApp;
	}

	public DefaultTableModel getDtmEr() {
		return dtmApp;
	}

	public static void main(String[] args) {
		new EeAppView(null,null);
	}// main
	
}// class
