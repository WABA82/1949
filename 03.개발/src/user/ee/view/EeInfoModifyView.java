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

public class EeInfoModifyView extends JDialog {

	private JButton jbModify, jbModifyExt, jbModifyImg, jbClose;
	private JComboBox<String> jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	private JLabel jlImag;
	public EeInfoModifyView() {
//		super("�⺻ ���� ����",true);

		// image
		ImageIcon ii=new ImageIcon("C:/dev/homework/1949/1949/03.����/��������/�����ڻ���/150x200px/��Ű.jpg");
		jlImag=new JLabel(ii);
		jlImag.setBorder(new TitledBorder("������ �̹���"));
		jlImag.setBounds(38, 20, 160, 225);
		add(jlImag);

		jbModifyImg = new JButton("�̹��� ����");
		add(jbModifyImg);
		jbModifyImg.setBounds(42, 260, 150, 30);

		jbModifyExt = new JButton("�ܺ��̷¼� ����");
		add(jbModifyExt);
		jbModifyExt.setBounds(50, 360, 150, 30);

		jbModify = new JButton("����");
		add(jbModify);
		jbModify.setBounds(240, 360, 100, 30);

		jbClose = new JButton("���");
		add(jbClose);
		jbClose.setBounds(355, 360, 100, 30);

		// Label
		JLabel jlName = new JLabel("�̸�");
		jlName.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlRank = new JLabel("����");
		jlRank.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlLoc = new JLabel("�ٹ�����");
		jlLoc.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlEdu = new JLabel("�з�");
		jlEdu.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlAge = new JLabel("����");
		jlAge.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlPort = new JLabel("��Ʈ������ ����");
		jlPort.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlGender = new JLabel("����");
		jlGender.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		JLabel jlResume = new JLabel("�ܺ��̷¼�");
		jlResume.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		// Combobox jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
		String[] rank = { "����", "���" };
		jcbRank = new JComboBox<>(rank);
		String[] loc = { "����", "���", "��õ", "����", "����", "�泲", "���", "����", "����", "����", "�뱸", "���", "�λ�", "���", "�泲", "����",
				"����", "����" };
		jcbLoc = new JComboBox<>(loc);
		String[] edu = { "����", "�ʴ���", "����", "����", "�ڻ�" };
		jcbEducation = new JComboBox<>(edu);
		String[] port = { "YES", "NO" };
		jcbPortfolio = new JComboBox<>(port);

		// Label
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
				
				JTextField jtfAge=new JTextField(10);
				add(jtfAge);
				jtfAge.setBounds(325, 182, 130, 20);
				
				JTextField jtfGender=new JTextField(10);
				add(jtfGender);
				jtfGender.setBounds(325, 262, 130, 20);
				
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

		setLayout(null);
		setBounds(100, 100, 490, 460);

		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}//������

	public static void main(String[] args) {
		new EeInfoModifyView();
	}
}//class
