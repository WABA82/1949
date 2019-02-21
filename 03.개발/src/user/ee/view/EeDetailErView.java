package user.ee.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.ee.controller.EeDetailErController;
import user.ee.vo.DetailErInfoVO;

/**
 * �Ϲݻ���� - ������������ - �󼼱������� View
 */
@SuppressWarnings("serial")
public class EeDetailErView extends JDialog {

	/* �ν��Ͻ� ���� ���� */
	private JLabel jlHeart;
	private JButton jbCoInfo, jbApply, jbClose;
	private boolean flagHeart;

	public EeDetailErView(JDialog SDialog, DetailErInfoVO deivo, String erNum, String eeId, String appStatus) {
		super(SDialog, "�󼼱�������", true);/* â�� ���� */
		System.out.println(appStatus);
		/* ������Ʈ ���� */
		ImageIcon erLogo = new ImageIcon("C:/dev/1949/03.����/src/file/coImg/no_co_img1.png");
		// �󺧵�
		JLabel jlImage = new JLabel(erLogo);
		JLabel jlSubject = new JLabel("����");
		JLabel jlCoName = new JLabel("ȸ���");
		JLabel jlRank = new JLabel("����");
		JLabel jlSal = new JLabel("�޿�");
		JLabel jlName = new JLabel("�̸�");
		JLabel jlTel = new JLabel("����ó");
		JLabel jlEmail = new JLabel("�̸���");
		JLabel jlEducation = new JLabel("�з�");
		JLabel jlLoc = new JLabel("�ٹ�����");
		JLabel jlHireType = new JLabel("�������");
		JLabel jlPortfolio = new JLabel("��Ʈ������");

		// �ʵ�
		JTextField jtfSubject = new JTextField();
		JTextField jtfCoName = new JTextField();
		JTextField jtfSal = new JTextField();
		JTextField jtfName = new JTextField();
		JTextField jtfTel = new JTextField();
		JTextField jtfEmail = new JTextField();

		// �޺��ڽ���
		JTextField jtfRank = new JTextField();
		JTextField jtfEducation = new JTextField();
		JTextField jtfLoc = new JTextField();
		JTextField jtfHireType = new JTextField();
		JTextField jtfPortfolio = new JTextField();

		// ������
		JTextArea jtaErDesc = new JTextArea();
		jtaErDesc.setRows(6);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		ImageIcon imgSkill = new ImageIcon("C:/dev/1949/03.����/src/admin/img/co/����Ŭ.png");
		JLabel jlSkill1, jlSkill2, jlSkill3, jlSkill4, jlSkill5, jlSkill6, jlSkill7, jlSkill8;
		jlSkill1 = new JLabel(imgSkill);
		jlSkill1.setBorder(new LineBorder(Color.BLACK));
		jlSkill2 = new JLabel("");
		jlSkill2.setBorder(new LineBorder(Color.BLACK));
		jlSkill3 = new JLabel("");
		jlSkill3.setBorder(new LineBorder(Color.BLACK));
		jlSkill4 = new JLabel("");
		jlSkill4.setBorder(new LineBorder(Color.BLACK));
		jlSkill5 = new JLabel("");
		jlSkill5.setBorder(new LineBorder(Color.BLACK));
		jlSkill6 = new JLabel("");
		jlSkill6.setBorder(new LineBorder(Color.BLACK));
		jlSkill7 = new JLabel("");
		jlSkill7.setBorder(new LineBorder(Color.BLACK));
		jlSkill8 = new JLabel("");
		jlSkill8.setBorder(new LineBorder(Color.BLACK));

		// ��ư��
		if (deivo.getInterest().equals("0")) {
			ImageIcon heart = new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/b_heart.png");
			jlHeart = new JLabel(heart);
		} else if (deivo.getInterest().equals("1")) {
			ImageIcon heart = new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/r_heart.png");
			jlHeart = new JLabel(heart);
			flagHeart = true;
		}
		jbCoInfo = new JButton("ȸ������");
		jbApply = new JButton("�����ϱ�");
		jbClose = new JButton("�ݱ�");

		jtfSubject.setEditable(false);
		jtfName.setEditable(false);
		jtfTel.setEditable(false);
		jtfEmail.setEditable(false);
		jtfCoName.setEditable(false);

		jtfSal.setEditable(false);
		jtfRank.setEditable(false);
		jtfEducation.setEditable(false);
		jtfLoc.setEditable(false);
		jtfHireType.setEditable(false);
		jtfPortfolio.setEditable(false);

		/* ������Ʈ ũ�� ���� */
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(null);
		wrapPanel.setBorder(new TitledBorder("��������"));

		jlSubject.setBounds(15, 25, 60, 20);
		jtfSubject.setBounds(50, 22, 330, 23);

		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(null);
		imgPanel.setBorder(new TitledBorder("ȸ��ΰ�"));
		imgPanel.setBounds(15, 52, 185, 192);
		jlImage.setBounds(7, 15, 170, 170);
		imgPanel.add(jlImage);

		jlCoName.setBounds(215, 90, 60, 20);
		jlName.setBounds(215, 120, 60, 20);
		jlTel.setBounds(215, 150, 60, 20);
		jlEmail.setBounds(215, 180, 60, 20);
		jtfCoName.setBounds(265, 90, 110, 25);
		jtfName.setBounds(265, 120, 110, 25);
		jtfTel.setBounds(265, 150, 110, 25);
		jtfEmail.setBounds(265, 180, 110, 25);

		jlHireType.setBounds(20, 255, 60, 20);
		jlRank.setBounds(20, 285, 60, 20);
		jlSal.setBounds(20, 315, 60, 20);
		jlEducation.setBounds(215, 255, 60, 20);
		jlLoc.setBounds(215, 285, 60, 20);
		jlPortfolio.setBounds(215, 315, 80, 20);

		jtfHireType.setBounds(77, 255, 120, 25);
		jtfRank.setBounds(77, 285, 120, 25);
		jtfSal.setBounds(77, 315, 120, 25);
		jtfEducation.setBounds(285, 255, 90, 25);
		jtfLoc.setBounds(285, 285, 90, 25);
		jtfPortfolio.setBounds(285, 315, 90, 25);

		wrapPanel.add(jlSubject);
		wrapPanel.add(imgPanel);
		wrapPanel.add(jlCoName);
		wrapPanel.add(jlRank);
		wrapPanel.add(jlSal);
		wrapPanel.add(jlName);
		wrapPanel.add(jlTel);
		wrapPanel.add(jlEmail);
		wrapPanel.add(jlEducation);
		wrapPanel.add(jlLoc);
		wrapPanel.add(jlHireType);
		wrapPanel.add(jlPortfolio);

		wrapPanel.add(jtfSubject);
		wrapPanel.add(jtfCoName);
		wrapPanel.add(jtfName);
		wrapPanel.add(jtfTel);
		wrapPanel.add(jtfEmail);
		wrapPanel.add(jtfHireType);
		wrapPanel.add(jtfRank);
		wrapPanel.add(jtfSal);
		wrapPanel.add(jtfEducation);
		wrapPanel.add(jtfLoc);
		wrapPanel.add(jtfPortfolio);

		// ������ ������Ʈ ũ�⼳��
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("������"));
		erDescPanel.setBounds(15, 350, 365, 135);
		erDescPanel.add(jspErDesc);

		wrapPanel.add(erDescPanel);

		// �ʿ� �������
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(null);
		skillGridPanel.setBounds(10, 490, 370, 75);
		skillGridPanel.setBorder(new TitledBorder("�ʿ� �������"));
		jlSkill1.setBounds(7, 25, 40, 40);
		jlSkill2.setBounds(52, 25, 40, 40);
		jlSkill3.setBounds(97, 25, 40, 40);
		jlSkill4.setBounds(142, 25, 40, 40);
		jlSkill5.setBounds(187, 25, 40, 40);
		jlSkill6.setBounds(232, 25, 40, 40);
		jlSkill7.setBounds(277, 25, 40, 40);
		jlSkill8.setBounds(322, 25, 40, 40);

		JLabel[] arrLbSkill = { jlSkill1, jlSkill2, jlSkill3, jlSkill4, jlSkill5, jlSkill6, jlSkill7, jlSkill8 };
		for (int i = 0; i < deivo.getSkill().size(); i++) {
			if (deivo.getSkill().get(i).equals("Java")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/Java.png"));
			} else if (deivo.getSkill().get(i).equals("JSP/Servlet")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/jsp_servelt.png"));

			} else if (deivo.getSkill().get(i).equals("Spring")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/spring.png"));

			} else if (deivo.getSkill().get(i).equals("Oracle")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/oracle.png"));

			} else if (deivo.getSkill().get(i).equals("HTML")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/html.png"));

			} else if (deivo.getSkill().get(i).equals("CSS")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/css.png"));

			} else if (deivo.getSkill().get(i).equals("Linux")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/linux.png"));

			} else if (deivo.getSkill().get(i).equals("JS")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.����/��������/Test_ Skill_Image/js.png"));
			}
			skillGridPanel.add(arrLbSkill[i]);
		} // end for

		wrapPanel.add(skillGridPanel);

		jbClose.setBounds(315, 575, 60, 25);
		if (appStatus.trim() == null||appStatus.trim().equals("")) {
			jlHeart.setBounds(65, 572, 32, 32);
			jbCoInfo.setBounds(105, 575, 100, 25);
			jbApply.setBounds(210, 575, 100, 25);
			wrapPanel.add(jbApply);
		} else {
			jbCoInfo.setBounds(210, 575, 100, 25);
			jlHeart.setBounds(165, 572, 32, 32);
		}
		wrapPanel.add(jlHeart);
		wrapPanel.add(jbCoInfo);
		wrapPanel.add(jbClose);

		/* �����ӿ� ��ġ */
		add(wrapPanel);

		jtfSubject.setText(deivo.getSubject());
		jlImage.setIcon(new ImageIcon("C:/dev/1949/03.����/src/file/coImg/" + deivo.getImg1()));
		jtfCoName.setText(deivo.getCoName());
		jtfName.setText(deivo.getName());
		jtfTel.setText(deivo.getTel());
		jtfEmail.setText(deivo.getEmail());
		jtfEducation.setText(deivo.getEdudation());
		jtfLoc.setText(deivo.getLoc());
		jtfSal.setText(String.valueOf(deivo.getSal()));
		jtaErDesc.setText(deivo.getErDesc());
		jtaErDesc.setEditable(false);

		// hiretype 'C' - ������'N' - ��������'F' - ��������
		if (deivo.getHireType().equals("C")) {
			jtfHireType.setText("������");
		} else if (deivo.getHireType().equals("N")) {
			jtfHireType.setText("��������");
		} else if (deivo.getHireType().equals("F")) {
			jtfHireType.setText("��������");
		}

		// rank: 'N' - ���� 'C' - ���
		if (deivo.getRank().equals("C")) {
			jtfRank.setText("���");
		} else if (deivo.getRank().equals("N")) {
			jtfRank.setText("����");
		}// end if
		
		if(deivo.getPortfolio().equals("Y")) {
			jtfPortfolio.setText("����");
		}else if(deivo.getPortfolio().equals("N")) {
			jtfPortfolio.setText("����");
		}
		
		// �̺�Ʈ ��� //
		EeDetailErController edec = new EeDetailErController(this, erNum, eeId, flagHeart);
		jlHeart.addMouseListener(edec);
		jbCoInfo.addActionListener(edec);
		jbClose.addActionListener(edec);
		jbApply.addActionListener(edec);

		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 410, 660);
		setVisible(true);

	}// ������

	// �����׽���.
//	public static void main(String[] args) {
//		new EeDetailErView(null, null, null, null, null);
//	}

	public JLabel getJlHeart() {
		return jlHeart;
	}

	public JButton getJbCoInfo() {
		return jbCoInfo;
	}

	public JButton getJbApply() {
		return jbApply;
	}

	public JButton getJbClose() {
		return jbClose;
	}

}// class
