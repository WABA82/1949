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

import user.ee.vo.DetailErInfoVO;

/**
 * �Ϲݻ���� - ������������ - �󼼱������� View
 */
@SuppressWarnings("serial")
public class EeDetailErView extends JDialog {

	/* �ν��Ͻ� ���� ���� */
	JLabel jlHeart;
	JButton jbCoInfo, jbApply, jbClose;

	public EeDetailErView(JDialog jd, DetailErInfoVO deivo, String erNum, String appStatus) {
		super(jd, "�󼼱�������", true);/* â�� ���� */

		/* ������Ʈ ���� */
		ImageIcon erLogo = new ImageIcon("C:/dev/1949/03.����/src/user/img/co/no_co_img1.png");
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
		JLabel jlSkill1 = new JLabel(imgSkill);
		jlSkill1.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill2 = new JLabel("�̹���2");
		jlSkill2.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill3 = new JLabel("�̹���3");
		jlSkill3.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill4 = new JLabel("�̹���4");
		jlSkill4.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill5 = new JLabel("�̹���5");
		jlSkill5.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill6 = new JLabel("�̹���6");
		jlSkill6.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill7 = new JLabel("�̹���7");
		jlSkill7.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill8 = new JLabel("�̹���8");
		jlSkill8.setBorder(new LineBorder(Color.BLACK));

		// ��ư��
		ImageIcon heart = new ImageIcon("C:/dev/1949/03.����/src/user/img/co/����Ʈ.png");
		jlHeart = new JLabel(heart);
//		jlHeart.setBorder(new LineBorder(Color.BLACK));
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
		skillGridPanel.add(jlSkill1);
		skillGridPanel.add(jlSkill2);
		skillGridPanel.add(jlSkill3);
		skillGridPanel.add(jlSkill4);
		skillGridPanel.add(jlSkill5);
		skillGridPanel.add(jlSkill6);
		skillGridPanel.add(jlSkill7);
		skillGridPanel.add(jlSkill8);

		wrapPanel.add(skillGridPanel);

		jlHeart.setBounds(65, 572, 32, 32);
		jbCoInfo.setBounds(105, 575, 100, 25);
		jbApply.setBounds(210, 575, 100, 25);
		jbClose.setBounds(315, 575, 60, 25);

		wrapPanel.add(jlHeart);
		wrapPanel.add(jbCoInfo);
		wrapPanel.add(jbApply);
		wrapPanel.add(jbClose);

		/* �����ӿ� ��ġ */
		add(wrapPanel);

		/* ������ ũ�� ���� �� ����ȭ */
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 660);
		setVisible(true);

	}// ������

	/*****************getter*****************/
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
	/*****************getter*****************/

	public static void main(String[] args) {
		new EeDetailErView(null, null, null, null);
	}// main

}// class
