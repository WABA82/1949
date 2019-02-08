package admin.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 0207 상세구인정보 View클래스
 */
@SuppressWarnings("serial")
public class ErModifyView extends JDialog {
	
	/* 인스턴스 변수 */
	JTextField jtfSubject, jtfSal; /* jtfRank */
	JTextArea jtaErDesc;
	JCheckBox jchJava, jchJspServlet, jchSpring, jchOracle, jchHTML, jchCSS, jchLinux, jchJS;
	JComboBox<String> jcbRank, jcbEducation, jcbLoc, jcbHireType, jcbPortfolio;
	JButton jbModify, jbRemove, jbCancel;

	public ErModifyView() {
		super();/* 창의 제목 */

		/* 컴포넌트 생성하기 */
		// 라벨들
		JLabel jlImage = new JLabel("이미지");
		JLabel jlName = new JLabel("이름");
		JLabel jlTel = new JLabel("연락처");
		JLabel jlEmail = new JLabel("이메일");
		JLabel jlSubject = new JLabel("제목");
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlRank = new JLabel("직급");
		JLabel jlSal = new JLabel("급여");
		JLabel jlEducation = new JLabel("학력");
		JLabel jlLoc = new JLabel("근무지역");
		JLabel jlHireType = new JLabel("고용형태");
		JLabel jlPortfolio = new JLabel("포트폴리오");

		// 텍스트 필드 - 수정불가
		JTextField jtfName = new JTextField();
		JTextField jtfTel = new JTextField();
		JTextField jtfEmail = new JTextField();
		JTextField jtfCoName = new JTextField();
		jtfName.setEditable(false);
		jtfTel.setEditable(false);
		jtfEmail.setEditable(false);
		jtfCoName.setEditable(false);

		// 텍스트 필드
		jtfSubject = new JTextField();
		jtfSal = new JTextField();

		// 상세정보
		jtaErDesc = new JTextArea();
		jtaErDesc.setRows(5);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		// 필요기술스택
		jchJava = new JCheckBox("Java");
		jchJspServlet = new JCheckBox("JspServlet");
		jchSpring = new JCheckBox("Spring");
		jchOracle = new JCheckBox("Oracle");
		jchHTML = new JCheckBox("HTML");
		jchCSS = new JCheckBox("HTML");
		jchLinux = new JCheckBox("Linux");
		jchJS = new JCheckBox("JavaScript");

		// 구인정보 콤보박스
		String[] rItem = { "신입", "경력" };
		jcbRank = new JComboBox<>(rItem);
		String[] eItem = { "고졸", "초대졸", "석사", "박사" };
		jcbEducation = new JComboBox<>(eItem);
		String[] lItem = { "서울", "경기", "인천", "대전", "세종", "충남", "충북", "광주", "전남", "전북", "대구", "경북", "부산", "울산", "경남", "강원", "제주", "전국" };
		jcbLoc = new JComboBox<>(lItem);
		String[] hItem = { "정규직", "계약직", "프리 " };
		jcbHireType = new JComboBox<>(hItem);
		String[] pItem = { "YES", "NO" };
		jcbPortfolio = new JComboBox<>(pItem);

		// 버튼들
		jbModify = new JButton("수정");
		jbRemove = new JButton("삭제");
		jbCancel = new JButton("닫기");

		/* 컴포넌트 크기 설정 */
		setLayout(null); // 수동배치 설정

		// 상단
		JPanel imgPanel = new JPanel();
		imgPanel.setBounds(20, 15, 170, 170);
		imgPanel.setBorder(new TitledBorder("회사로고"));
		imgPanel.add(jlImage);

		jlName.setBounds(210, 40, 60, 20);
		jlTel.setBounds(210, 80, 60, 20);
		jlEmail.setBounds(210, 120, 60, 20);
		jtfName.setBounds(255, 37, 115, 23);
		jtfTel.setBounds(255, 77, 115, 23);
		jtfEmail.setBounds(255, 117, 115, 23);

		// 구인정보
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(15, 200, 365, 180);
		infoPanel.setBorder(new TitledBorder("구인정보"));
		jlSubject.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlCoName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlSal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlEducation.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlHireType.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlPortfolio.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));

		jlSubject.setBounds(10, 25, 60, 20);
		jlCoName.setBounds(10, 55, 60, 20);
		jlRank.setBounds(10, 85, 60, 20);
		jlSal.setBounds(10, 115, 60, 20);

		jlEducation.setBounds(180, 55, 60, 20);
		jlLoc.setBounds(180, 85, 60, 20);
		jlHireType.setBounds(180, 115, 60, 20);
		jlPortfolio.setBounds(180, 145, 80, 20);

		jtfSubject.setBounds(50, 25, 300, 20);
		jtfCoName.setBounds(50, 55, 100, 20);
		jcbRank.setBounds(50, 85, 100, 20);
		jtfSal.setBounds(50, 115, 100, 20);

		jcbEducation.setBounds(250, 55, 100, 25);
		jcbLoc.setBounds(250, 85, 100, 25);
		jcbHireType.setBounds(250, 115, 100, 25);
		jcbPortfolio.setBounds(250, 145, 100, 25);

		infoPanel.add(jlSubject);
		infoPanel.add(jlCoName);
		infoPanel.add(jlRank);
		infoPanel.add(jlSal);
		infoPanel.add(jlEducation);
		infoPanel.add(jlLoc);
		infoPanel.add(jlHireType);
		infoPanel.add(jlPortfolio);

		infoPanel.add(jtfSubject);
		infoPanel.add(jtfCoName);
		infoPanel.add(jcbRank);
		infoPanel.add(jtfSal);
		infoPanel.add(jcbEducation);
		infoPanel.add(jcbLoc);
		infoPanel.add(jcbHireType);
		infoPanel.add(jcbPortfolio);

		// 상세정보
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("상세정보"));
		erDescPanel.setBounds(15, 385, 365, 110);
		erDescPanel.add(jspErDesc);

		// 필요 기술스택
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(new GridLayout(2, 4, 0, 0));
		skillGridPanel.setBounds(15, 500, 365, 60);
		skillGridPanel.setBorder(new TitledBorder("필요 기술스택"));
		skillGridPanel.add(jchJava);
		skillGridPanel.add(jchJspServlet);
		skillGridPanel.add(jchSpring);
		skillGridPanel.add(jchOracle);
		skillGridPanel.add(jchHTML);
		skillGridPanel.add(jchCSS);
		skillGridPanel.add(jchLinux);
		skillGridPanel.add(jchJS);

		// 최하단 버튼
		jbModify.setBounds(180, 570, 60, 25);
		jbRemove.setBounds(245, 570, 60, 25);
		jbCancel.setBounds(310, 570, 60, 25);

		/* 프레임에 배치 */
		add(imgPanel);
		add(jlName);
		add(jlTel);
		add(jlEmail);
		add(jtfName);
		add(jtfTel);
		add(jtfEmail);
		add(infoPanel);
		add(erDescPanel);
		add(skillGridPanel);
		add(jbModify);
		add(jbRemove);
		add(jbCancel);

		/* 이벤트등록 */

		/* 프레임 크기 설정 및 가시화 */
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 650);
		setVisible(true);
	}// 생성자

	public static void main(String[] args) {
		new ErModifyView();
	}// main : 단위테스트용
}// class