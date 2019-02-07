package user.ee.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *	 기본 정보 관리 -김건하-
 *	19.02.07
 * @author owner
 */
public class EeInfoRegView extends JDialog {

	private JButton jbRegister, jbRegisterExt, jbRegisterImg, jcClose;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	
	public EeInfoRegView() {
//		super("기본 정보 관리",true);
		
		//Label
		JLabel jlName=new JLabel("이름");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlRank=new JLabel("직급");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlLoc=new JLabel("근무지역");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlEdu=new JLabel("학력");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlAge=new JLabel("나이");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlPort=new JLabel("포트폴리오 유무");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlGender=new JLabel("성별");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		JLabel jlResume=new JLabel("외부이력서");
		jlName.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		
		//JText
		JTextField jtfName=new JTextField(10);
		
		
		
		//Label
		jlName.setBounds(270, 40, 50, 20);
		add(jlName);
		jlRank.setBounds(270, 70, 50, 20);
		add(jlRank);
		jlLoc.setBounds(270, 100, 70, 20);
		add(jlLoc);
//		add(jlName);
//		jlName.setBounds(50, 50, 50, 30);
//		add(jlName);
//		jlName.setBounds(50, 50, 50, 30);
//		add(jlName);
//		jlName.setBounds(50, 50, 50, 30);
//		add(jlName);
//		jlName.setBounds(50, 50, 50, 30);
//		add(jlName);
//		jlName.setBounds(50, 50, 50, 30);
		
		
		add(jtfName);
		jtfName.setBounds(380, 70, 100, 20);
		
		
		
		setLayout(null);
		setBounds(100, 100, 500, 600);
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
	}//EeInfoRegView
	public static void main(String[] args) {
		new EeInfoRegView();
	}
}//class
