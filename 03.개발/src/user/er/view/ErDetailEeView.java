
package user.er.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.ErDetailEeController;
import user.er.vo.DetailEeInfoVO;

/**
 * ����� 19.02.08
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class ErDetailEeView extends JDialog {

	private JButton jbRsmDown, jbClose;
	private JLabel jlHeart;
	private boolean flagHeart;

	public ErDetailEeView(JDialog SDialog, DetailEeInfoVO devo, String eeNum, String erId, String interest) {
		super(SDialog, "������ �� ����", true);
		setLayout(null);
		ImageIcon ii = new ImageIcon("C:/dev/1949/03.����/src/file/eeImg/" + devo.getImg());
		JLabel jlImage = new JLabel(ii);
		jlImage.setBorder(new TitledBorder("������ �̹���"));
		jlImage.setBounds(38, 20, 160, 225);
		add(jlImage);

		if (interest.equals("0")) {
			ImageIcon iiheart = new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/b_heart.png");
			jlHeart = new JLabel(iiheart);
		} else if (interest.equals("1")) {
			ImageIcon heart = new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/r_heart.png");
			jlHeart = new JLabel(heart);
			flagHeart = true;
		}
		jlHeart.setBounds(280, 413, 60, 60);
		add(jlHeart);

		jbRsmDown = new JButton("Ŭ���Ͽ� �ٿ�ε�");
		add(jbRsmDown);
		jbRsmDown.setBounds(305, 375, 145, 27);

		jbClose = new JButton("�ݱ�");
		add(jbClose);
		jbClose.setBounds(345, 430, 100, 30);

		// Label
		JLabel jlName = new JLabel("�̸�");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlTel = new JLabel("����ó");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEmail = new JLabel("�̸���");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlRank = new JLabel("����");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlLoc = new JLabel("�ٹ�����");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEdu = new JLabel("�з�");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlAge = new JLabel("����");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlPort = new JLabel("��Ʈ������ ����");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlGender = new JLabel("����");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlResume = new JLabel("�ܺ��̷¼�");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		// ����ó �̸���,
		// Label
		add(jlName);
		jlName.setBounds(255, 20, 50, 20);
		add(jlTel);
		jlTel.setBounds(252, 60, 50, 20);
		add(jlEmail);
		jlEmail.setBounds(248, 100, 70, 20);
		add(jlRank);
		jlRank.setBounds(255, 138, 50, 30);
		add(jlLoc);
		jlLoc.setBounds(242, 175, 70, 30);
		add(jlEdu);
		jlEdu.setBounds(255, 215, 100, 30);
		add(jlAge);
		jlAge.setBounds(255, 255, 50, 30);
		add(jlPort);
		jlPort.setBounds(210, 295, 100, 30);
		add(jlGender);
		jlGender.setBounds(255, 335, 100, 30);
		add(jlResume);
		jlResume.setBounds(228, 375, 100, 30);

		// text
		JTextField jtfName = new JTextField(devo.getName());
		add(jtfName);
		jtfName.setBounds(315, 22, 130, 20);
		jtfName.setEditable(false);

		JTextField jtfTel = new JTextField(devo.getTel());
		add(jtfTel);
		jtfTel.setBounds(315, 62, 130, 20);
		jtfTel.setEditable(false);

		JTextField jtfEmail = new JTextField(devo.getEmail());
		add(jtfEmail);
		jtfEmail.setBounds(315, 102, 130, 20);
		jtfEmail.setEditable(false);
		
		JTextField jtfRank = new JTextField("");
		if(devo.getRank().equals("N")) {
			jtfRank.setText("����");
		}else if(devo.getRank().equals("C")) {
			jtfRank.setText("���");
		}
		add(jtfRank);
		jtfRank.setBounds(315, 142, 130, 20);
		jtfRank.setEditable(false);
		JTextField jtfLoc = new JTextField(devo.getLoc());
		add(jtfLoc);
		jtfLoc.setBounds(315, 182, 130, 20);
		jtfLoc.setEditable(false);

		JTextField jtfEdu = new JTextField(devo.getEducation());
		add(jtfEdu);
		jtfEdu.setBounds(315, 222, 130, 20);
		jtfEdu.setEditable(false);

		JTextField jtfAge = new JTextField(String.valueOf(devo.getAge()));
		add(jtfAge);
		jtfAge.setBounds(315, 262, 130, 20);
		jtfAge.setEditable(false);
		
		JTextField jtfPort = new JTextField("");
		if(devo.getPortfolio().equals("Y")) {
			jtfPort.setText("����");
		}else if(devo.getPortfolio().equals("N")) {
			jtfPort.setText("����");
		}
		add(jtfPort);
		jtfPort.setBounds(315, 302, 130, 20);
		jtfPort.setEditable(false);

		JTextField jtfGender = new JTextField(devo.getGender());
		if(devo.getGender().equals("M")) {
			jtfGender.setText("����");
		}else if(devo.getGender().equals("F")) {
			jtfGender.setText("����");
		}
		add(jtfGender);
		jtfGender.setBounds(315, 342, 130, 20);
		jtfGender.setEditable(false);

		System.out.println(flagHeart);
		ErDetailEeController edec = new ErDetailEeController(this, eeNum, erId, flagHeart);

		jlHeart.addMouseListener(edec);
		jbRsmDown.addActionListener(edec);
		jbClose.addActionListener(edec);
		addWindowListener(edec);

		setBounds(SDialog.getX() + 150, SDialog.getY() + 50, 490, 520);
		setVisible(true);
		setResizable(false);

	}// ������

	public JButton getJbRsmDown() {
		return jbRsmDown;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JLabel getJlHeart() {
		return jlHeart;
	}

}// class

