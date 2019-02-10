package admin.view;

import javax.swing.JDialog;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class EeModifyView extends JDialog {

	private JButton jbModify, jbChangeExt, jbChangeImg, jbRemove, jbCancel;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtRsm, jtfName;
	private JLabel jlImg;
	public EeModifyView() {
//		super("기본 정보 관리",true);
		
		//image
		ImageIcon ii=new ImageIcon("C:\\dev\\1949\\03.개발\\가데이터\\구직자사진\\150x200px\\각키.jpg");
		jlImg=new JLabel(ii);
		jlImg.setBorder(new TitledBorder("구직자 이미지"));
		jlImg.setBounds(38, 20, 160, 225);
		add(jlImg);
		
		//콤보박스
		jbChangeImg=new JButton("이미지 변경");
		add(jbChangeImg);
		jbChangeImg.setBounds(42, 260, 150, 30);
		
		jbChangeExt = new JButton("외부이력서 수정");
		add(jbChangeExt);
		jbChangeExt.setBounds(45, 440, 150, 30);
		
		jbModify = new JButton("수정");
		add(jbModify);
		jbModify.setBounds(230, 440, 70, 30);
		
		jbRemove=new JButton("삭제");
		add(jbRemove);
		jbRemove.setBounds(310, 440, 70, 30);
		
		jbCancel=new JButton("닫기");
		add(jbCancel);
		jbCancel.setBounds(390, 440, 70, 30);
				
		
		//Label
		JLabel jlId=new JLabel("아이디");
		jlId.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlName=new JLabel("이름");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlRank=new JLabel("직급");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlLoc=new JLabel("근무지역");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlEdu=new JLabel("학력");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlAge=new JLabel("나이");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlPort=new JLabel("포트폴리오 유무");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlGender=new JLabel("성별");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlRegDate=new JLabel("등록일");
		jlRegDate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlResume=new JLabel("외부이력서");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		//Combobox  jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
		String[] rank= {"신입", "경력"};
		jcbRank =new JComboBox<>(rank);
		String[] loc= {"서울", "경기", "인천", "대전", "세종", "충남", "충북", "광주", "전남", "전북", "대구", "경북", "부산", "울산", "경남",
				"강원", "제주", "전국"};
		jcbLoc=new JComboBox<>(loc);
		String[] edu= {"고졸", "초대졸", "대졸", "석사", "박사"};
		jcbEducation=new JComboBox<>(edu);
		String[] port= {"YES", "NO"};
		jcbPortfolio=new JComboBox<>(port);
		
		//Label 좌표
		add(jlId);
		jlId.setBounds(260, 23, 50, 20);
		add(jlName);
		jlName.setBounds(265, 63, 50, 20);
		add(jlRank);
		jlRank.setBounds(265, 103, 50, 20);
		add(jlLoc);
		jlLoc.setBounds(252, 145, 70, 20);
		add(jlEdu);
		jlEdu.setBounds(265, 182, 50, 30);
		add(jlAge);
		jlAge.setBounds(265, 225, 50, 30);
		add(jlPort);
		jlPort.setBounds(215, 265, 100, 30);
		add(jlGender);
		jlGender.setBounds(265, 305, 50, 30);
		add(jlRegDate);
		jlRegDate.setBounds(255, 345, 100, 30);
		add(jlResume);
		jlResume.setBounds(235, 385, 100, 30);
		
		//JText
		
		//추가
		JTextField jtfId=new JTextField();
		add(jtfId);
		jtfId.setBounds(325, 25, 130, 20);
		jtfId.setEditable(false);
		
		jtfName=new JTextField(10);
		add(jtfName);
		jtfName.setBounds(325, 65, 130, 20);
		
		//Combobox
		add(jcbRank);
		jcbRank.setBounds(325,105,130,20);
		
		add(jcbLoc);
		jcbLoc.setBounds(325,145,130,20);
		
		add(jcbEducation);
		jcbEducation.setBounds(325,188,130,20);
		
		JTextField jtfAge=new JTextField(10);
		add(jtfAge);
		jtfAge.setBounds(325, 232, 130, 20);
		jtfAge.setEditable(false);
		
		add(jcbPortfolio);
		jcbPortfolio.setBounds(325,272,130,20);
		
		JTextField jtfGender=new JTextField(10);
		add(jtfGender);
		jtfGender.setBounds(325, 311, 130, 20);
		
		JTextField jtfRegDate=new JTextField();
		add(jtfRegDate);
		jtfRegDate.setBounds(325, 351, 130, 20);
		jtfRegDate.setEditable(false);
		
		jtfExtRsm=new JTextField(10);
		add(jtfExtRsm);
		jtfExtRsm.setBounds(325, 391, 130, 20);
		
		//레이아웃
		setLayout(null);
		setBounds(100, 100, 490, 525);
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}//EeInfoRegView
	public static void main(String[] args) {
		new EeModifyView();
	}//main
	
}//class
