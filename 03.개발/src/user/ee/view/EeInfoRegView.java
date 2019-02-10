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

import user.ee.controller.EeInfoRegController;

/**
 *	 �⺻ ���� ���� -�����-
 *	19.02.07
 * @author owner
 */
public class EeInfoRegView extends JDialog {

	private JButton jbRegister, jbRegisterExt, jbRegisterImg, jbClose;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	
	public EeInfoRegView(EeMainView emv) {
		super(emv, "�⺻ ���� ����",true);
		
		//image
		ImageIcon ii=new ImageIcon("C:/dev/1949/03.����/��������/�����ڻ���/150x200px/��Ű.jpg");
		JLabel jlImage=new JLabel(ii);
		jlImage.setBorder(new TitledBorder("������ �̹���"));
		jlImage.setBounds(38, 20, 160, 225);
		add(jlImage);
		
		jbRegisterImg=new JButton("�̹��� ����");
		add(jbRegisterImg);
		jbRegisterImg.setBounds(42, 260, 150, 30);
		
		jbRegisterExt = new JButton("�ܺ��̷¼� ���");
		add(jbRegisterExt);
		jbRegisterExt.setBounds(50, 360, 150, 30);
		
		jbRegister = new JButton("���");
		add(jbRegister);
		jbRegister.setBounds(240, 360, 100, 30);
		
		jbClose=new JButton("�ݱ�");
		add(jbClose);
		jbClose.setBounds(355, 360, 100, 30);
				
		
		
		//Label
		JLabel jlName=new JLabel("�̸�");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlRank=new JLabel("����");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlLoc=new JLabel("�ٹ�����");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlEdu=new JLabel("�з�");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlAge=new JLabel("����");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlPort=new JLabel("��Ʈ������ ����");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlGender=new JLabel("����");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlResume=new JLabel("�ܺ��̷¼�");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		
		//Combobox  jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
		String[] rank= {"����", "���"};
		jcbRank =new JComboBox<>(rank);
		String[] loc= {"����", "���", "��õ", "����", "����", "�泲", "���", "����", "����", "����", "�뱸", "���", "�λ�", "���", "�泲",
				"����", "����", "����"};
		jcbLoc=new JComboBox<>(loc);
		String[] edu= {"����", "�ʴ���", "����", "����", "�ڻ�"};
		jcbEducation=new JComboBox<>(edu);
		String[] port= {"YES", "NO"};
		jcbPortfolio=new JComboBox<>(port);
		
		//Label
		add(jlName);
		jlName.setBounds(265, 20, 50, 20);
		add(jlRank);
		jlRank.setBounds(265, 60, 50, 20);
		add(jlLoc);
		jlLoc.setBounds(255, 100, 70, 20);
		add(jlEdu);
		jlEdu.setBounds(265, 138, 50, 30);
		add(jlAge);
		jlAge.setBounds(265, 175, 50, 30);
		add(jlPort);
		jlPort.setBounds(215, 215, 100, 30);
		add(jlGender);
		jlGender.setBounds(265, 255, 50, 30);
		add(jlResume);
		jlResume.setBounds(235, 295, 100, 30);
		
		//JText
		JTextField jtfName=new JTextField(10);
		add(jtfName);
		jtfName.setBounds(325, 22, 130, 20);
		jtfName.setEditable(false);
		
		JTextField jtfAge=new JTextField(10);
		add(jtfAge);
		jtfAge.setBounds(325, 182, 130, 20);
		jtfAge.setEditable(false);
		
		JTextField jtfGender=new JTextField(10);
		add(jtfGender);
		jtfGender.setBounds(325, 262, 130, 20);
		jtfGender.setEditable(false);
		
		
		jtfExtResume=new JTextField(10);
		add(jtfExtResume);
		jtfExtResume.setBounds(325, 302, 130, 20);
		
		//Combobox
		add(jcbRank);
		jcbRank.setBounds(325,62,130,20);
		add(jcbLoc);
		jcbLoc.setBounds(325,102,130,20);
		add(jcbEducation);
		jcbEducation.setBounds(325,142,130,20);
		add(jcbPortfolio);
		jcbPortfolio.setBounds(325,222,130,20);
		
		//�̺�Ʈ ���
		EeInfoRegController eirc=new EeInfoRegController(this);
		jbRegisterExt.addActionListener(eirc);
		
		
		setLayout(null);
		setBounds(100, 100, 490, 465);
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}//EeInfoRegView

	public JButton getJbRegister() {
		return jbRegister;
	}

	public JButton getJbRegisterExt() {
		return jbRegisterExt;
	}

	public JButton getJbRegisterImg() {
		return jbRegisterImg;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}

	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}

	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}

	public JComboBox<String> getJcbPortfolio() {
		return jcbPortfolio;
	}

	public JTextField getJtfExtResume() {
		return jtfExtResume;
	}
	
	
	
//	public static void main(String[] args) {
//		new EeInfoRegView(null);
//	}//main
	
	
	
}//class
