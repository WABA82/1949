package user.ee.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *	 기본 정보 관리 -김건하-
 *	19.02.07
 * @author owner
 */
public class EeInfoRegView extends JDialog {

	private JButton jbRegister, jbRegisterExt, jbRegisterImg, jbClose;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	
	public EeInfoRegView() {
//		super("기본 정보 관리",true);
		
		//image
		ImageIcon ii=new ImageIcon("C:/dev/workspace/javase_prj2/src/day0114/images/각키.png");
		JLabel jlImage=new JLabel(ii);
		JScrollPane jsp=new JScrollPane(jlImage);
		jsp.setBorder(new TitledBorder("구직자 이미지"));
		jsp.setBounds(20, 20, 200, 200);
		add(jsp);
		
		jbRegisterImg=new JButton("이미지 변경");
		add(jbRegisterImg);
		jbRegisterImg.setBounds(42, 230, 150, 30);
		
		jbRegisterExt = new JButton("외부이력서 등록");
		add(jbRegisterExt);
		jbRegisterExt.setBounds(50, 380, 150, 30);
		
		jbRegister = new JButton("등록");
		add(jbRegister);
		jbRegister.setBounds(240, 380, 100, 30);
		
		jbClose=new JButton("닫기");
		add(jbClose);
		jbClose.setBounds(355, 380, 100, 30);
				
		
		
		//Label
		JLabel jlName=new JLabel("이름");
		jlName.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlRank=new JLabel("직급");
		jlRank.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlLoc=new JLabel("근무지역");
		jlLoc.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlEdu=new JLabel("학력");
		jlEdu.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlAge=new JLabel("나이");
		jlAge.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlPort=new JLabel("포트폴리오 유무");
		jlPort.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlGender=new JLabel("성별");
		jlGender.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		JLabel jlResume=new JLabel("외부이력서");
		jlResume.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		
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
		
		//Label
		add(jlName);
		jlName.setBounds(270, 40, 50, 20);
		add(jlRank);
		jlRank.setBounds(270, 80, 50, 20);
		add(jlLoc);
		jlLoc.setBounds(260, 120, 70, 20);
		add(jlEdu);
		jlEdu.setBounds(270, 155, 50, 30);
		add(jlAge);
		jlAge.setBounds(270, 190, 50, 30);
		add(jlPort);
		jlPort.setBounds(220, 235, 100, 30);
		add(jlGender);
		jlGender.setBounds(270, 280, 50, 30);
		add(jlResume);
		jlResume.setBounds(240, 320, 100, 30);
		
		//JText
		JTextField jtfName=new JTextField(10);
		add(jtfName);
		jtfName.setBounds(330, 42, 130, 20);
		
		JTextField jtfAge=new JTextField(10);
		add(jtfAge);
		jtfAge.setBounds(330, 202, 130, 20);
		
		JTextField jtfGender=new JTextField(10);
		add(jtfGender);
		jtfGender.setBounds(330, 282, 130, 20);
		
		JTextField jtfResume=new JTextField(10);
		add(jtfResume);
		jtfResume.setBounds(330, 322, 130, 20);
		
		//Combobox
		add(jcbRank);
		jcbRank.setBounds(330,82,130,20);
		add(jcbLoc);
		jcbLoc.setBounds(330,122,130,20);
		add(jcbEducation);
		jcbEducation.setBounds(330,162,130,20);
		add(jcbPortfolio);
		jcbPortfolio.setBounds(330,242,130,20);
		
		
		
		
		setLayout(null);
		setBounds(100, 100, 490, 460);
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
	}//EeInfoRegView
	public static void main(String[] args) {
		new EeInfoRegView();
	}//main
	
}//class
