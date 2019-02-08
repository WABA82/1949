package user.ee.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * �Ϲݻ���� - ������������ - �󼼱������� View
 */
@SuppressWarnings("serial")
public class EeDetailErView extends JDialog {

	/* �ν��Ͻ� ���� ���� */
	JLabel jlHeart;
	JButton jbCoInfo, jbApply, jbClose;

	public EeDetailErView() {
		super();/* â�� ���� */

		/* ������Ʈ ���� */
		// �󺧵�
		JLabel jlImage = new JLabel("�̹���");
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
		String[] rItem = { "����", "���" };
		JComboBox<String> jcbRank = new JComboBox<>(rItem);
		String[] eItem = { "����", "�ʴ���", "����", "�ڻ�" };
		JComboBox<String> jcbEducation = new JComboBox<>(eItem);
		String[] lItem = { "����", "���", "��õ", "����", "����", "�泲", "���", "����", "����", "����", "�뱸", "���", "�λ�", "���", "�泲",
				"����", "����", "����" };
		JComboBox<String> jcbLoc = new JComboBox<>(lItem);
		String[] hItem = { "������", "�����", "���� " };
		JComboBox<String> jcbHireType = new JComboBox<>(hItem);
		String[] pItem = { "YES", "NO" };
		JComboBox<String> jcbPortfolio = new JComboBox<>(pItem);

		// ������
		JTextArea jtaErDesc = new JTextArea();
		jtaErDesc.setRows(6);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		JLabel jlSkill1 = new JLabel("�̹���1");
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
		jlHeart = new JLabel("��Ʈ");
		jlHeart.setBorder(new LineBorder(Color.BLACK));
		jbCoInfo = new JButton("ȸ������");
		jbApply = new JButton("�����ϱ�");
		jbClose = new JButton("�ݱ�");

		/* ������Ʈ ũ�� ���� */
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(null);
		wrapPanel.setBorder(new TitledBorder("��������"));

		jlSubject.setBounds(15, 30, 60, 20);
		wrapPanel.add(jlSubject);

		JPanel imgPanel = new JPanel();
		imgPanel.setBounds(20, 60, 170, 170);
		imgPanel.setBorder(new TitledBorder("ȸ��ΰ�"));
		imgPanel.add(jlImage);
		wrapPanel.add(imgPanel);

		jlCoName.setBounds(20, 240, 60, 20);
		jlRank.setBounds(20, 270, 60, 20);
		jlSal.setBounds(20, 300, 60, 20);
		jlName.setBounds(205, 100, 60, 20);
		jlTel.setBounds(205, 130, 60, 20);
		jlEmail.setBounds(205, 160, 60, 20);
		jlEducation.setBounds(205, 240, 60, 20);
		jlLoc.setBounds(205, 270, 60, 20);
		jlHireType.setBounds(205, 300, 60, 20);
		jlPortfolio.setBounds(205, 330, 80, 20);
		jtfSubject.setBounds(50, 25, 330, 25);
		jtfCoName.setBounds(70, 240, 120, 25);
		jcbRank.setBounds(70, 270, 120, 25);
		jtfSal.setBounds(70, 300, 120, 25);
		jtfName.setBounds(255, 100, 125, 25);
		jtfTel.setBounds(255, 130, 125, 25);
		jtfEmail.setBounds(255, 160, 125, 25);
		jcbEducation.setBounds(275, 240, 100, 25);
		jcbLoc.setBounds(275, 270, 100, 25);
		jcbHireType.setBounds(275, 300, 100, 25);
		jcbPortfolio.setBounds(275, 330, 100, 25);

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
		wrapPanel.add(jcbRank);
		wrapPanel.add(jtfSal);
		wrapPanel.add(jtfName);
		wrapPanel.add(jtfTel);
		wrapPanel.add(jtfEmail);

		wrapPanel.add(jcbEducation);
		wrapPanel.add(jcbLoc);
		wrapPanel.add(jcbHireType);
		wrapPanel.add(jcbPortfolio);

		// ������ ������Ʈ ũ�⼳��
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("������"));
		erDescPanel.setBounds(15, 360, 365, 120);
		erDescPanel.add(jspErDesc);

		wrapPanel.add(erDescPanel);

		// �ʿ� �������
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(null);
		skillGridPanel.setBounds(10, 490, 370, 75);
		skillGridPanel.setBorder(new TitledBorder("�ʿ� �������"));
		jlSkill1.setBounds(7, 20, 40, 40);
		jlSkill2.setBounds(52, 20, 40, 40);
		jlSkill3.setBounds(97, 20, 40, 40);
		jlSkill4.setBounds(142, 20, 40, 40);
		jlSkill5.setBounds(187, 20, 40, 40);
		jlSkill6.setBounds(232, 20, 40, 40);
		jlSkill7.setBounds(277, 20, 40, 40);
		jlSkill8.setBounds(322, 20, 40, 40);
		skillGridPanel.add(jlSkill1);
		skillGridPanel.add(jlSkill2);
		skillGridPanel.add(jlSkill3);
		skillGridPanel.add(jlSkill4);
		skillGridPanel.add(jlSkill5);
		skillGridPanel.add(jlSkill6);
		skillGridPanel.add(jlSkill7);
		skillGridPanel.add(jlSkill8);

		wrapPanel.add(skillGridPanel);

		jlHeart.setBounds(65, 570, 30, 30);
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
		setBounds(100, 100, 410, 650);
		setVisible(true);
	}// ������

	public static void main(String[] args) {
		new EeDetailErView();
	}// main

}// class
