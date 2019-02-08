package user.ee.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import user.ee.controller.EeDetailSearchController;
import user.ee.controller.EeHiringController;

public class EeDetailSearchView extends JDialog {
	
	private JButton jbSearch, jbCancel;
	private JComboBox<String> jcbRank, jcbEducation, jcbLoc, jcbHireType;

	public EeDetailSearchView(EeHiringView ehv, EeHiringController ehc) {
		super(ehv, "조건검색", true);
//	public EeDetailSearchView() {
		
		JLabel jlTitle = new JLabel("검색옵션");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		
		jbSearch = new JButton("조건검색");
		jbCancel = new JButton("취소");

		String[] rankItems = { "===직급을 선택해주세요===","신입", "경력" };
		jcbRank = new JComboBox<String>(rankItems);
		
		String[] eduItems = { "===학력을 선택해주세요===","고졸","초대졸","대졸","석사","박사" };
		jcbEducation = new JComboBox<String>(eduItems);
		
		String[] locItems = { "=근무지역을 선택해주세요==","서울", "경기", "인천", "대전", "세종", "충남", "충북",
				"광주","전남","전북","대구","경북","부산","울산","경남","강원","제주","전국" };
		jcbLoc = new JComboBox<String>(locItems);
		
		String[] htItems = { "==고용형태를 선택해주세요==", "정규직","계약직","프리" };
		jcbHireType = new JComboBox<String>(htItems);
		
		setLayout(null);
		
		jlTitle.setBounds(105, 20, 100, 30);
		jcbRank.setBounds(50, 70, 200, 30);
		jcbEducation.setBounds(50, 120, 200, 30);
		jcbLoc.setBounds(50, 170, 200, 30);
		jcbHireType.setBounds(50,220,200,30);
		jbSearch.setBounds(55, 270, 90, 30);
		jbCancel.setBounds(155, 270, 90, 30);
		
		add(jlTitle);
		add(jcbRank);
		add(jcbEducation);
		add(jcbLoc);
		add(jcbHireType);
		add(jbSearch);
		add(jbCancel);
		
		EeDetailSearchController edsc = new EeDetailSearchController(this, null/*ehc*/);
		jbSearch.addActionListener(edsc);
		jbCancel.addActionListener(edsc);
		
		addWindowListener(edsc);
		
		setBounds(500, 100, 300, 360);
		setResizable(false);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new EeDetailSearchView();
	}*/
	public JButton getJbSearch() {
		return jbSearch;
	}
	public JButton getJbCancel() {
		return jbCancel;
	}
	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}
	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}
	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}
	public JComboBox<String> getJcbHireType() {
		return jcbHireType;
	}
}
