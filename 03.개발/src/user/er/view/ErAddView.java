package user.er.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.ErAddController;
import user.er.controller.ErMgMtController;
import user.er.vo.ErDefaultVO;
import user.util.UserUtil;

@SuppressWarnings("serial")
public class ErAddView extends JDialog {

	/* �ν��Ͻ� ���� */
	JTextField jtfSubject, jtfSal;
	JTextArea jtaErDesc;
	JCheckBox jchJava, jchJspServlet, jchSpring, jchOracle, jchHTML, jchCSS, jchLinux, jchJS;
	JComboBox<String> jcbRank, jcbEducation, jcbLoc, jcbHireType, jcbPortfolio;
	JButton jbReg, jbCancel;

	public ErAddView(ErMgMtView emmv, ErMgMtController emmc, ErDefaultVO edfvo, String erId) {
		super(emmv, "���� ���� ���", true);/* â�� ���� */

		/* ������Ʈ �����ϱ� */
		// �̹��������� : ȸ��ΰ�
		
		// �󺧵�
		File imgFile = new File("C:/dev/1949/03.����/src/user/img/co/"+edfvo.getImg1());
		if(!imgFile.exists()) {
			Socket client = null;
			DataInputStream dis =null;
			DataOutputStream dos = null;
			FileOutputStream fos = null;
			try {
				UserUtil uu = new UserUtil();
				uu.reqFile(edfvo.getImg1(), "co", client, dos, dis, fos);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "�̹����� �޾ƿ��µ� �����߽��ϴ�.");
			}
		}
		
		JLabel jlImage = new JLabel(new ImageIcon("C:/dev/1949/03.����/src/user/img/co/"+edfvo.getImg1()));
		JLabel jlName = new JLabel("�̸�");
		JLabel jlTel = new JLabel("����ó");
		JLabel jlEmail = new JLabel("�̸���");
		JLabel jlSubject = new JLabel("����");
		JLabel jlCoName = new JLabel("ȸ���");
		JLabel jlRank = new JLabel("����");
		JLabel jlSal = new JLabel("�޿�");
		JLabel jlEducation = new JLabel("�з�");
		JLabel jlLoc = new JLabel("�ٹ�����");
		JLabel jlHireType = new JLabel("�������");
		JLabel jlPortfolio = new JLabel("��Ʈ������");

		// �ؽ�Ʈ �ʵ� - �����Ұ�
		//�ڵ� �߰����: ȸ��ΰ�, �̸�, ����ó, �̸���, ȸ���
		JTextField jtfName = new JTextField(edfvo.getName());
		JTextField jtfTel = new JTextField(edfvo.getTel());
		JTextField jtfEmail = new JTextField(edfvo.getEmail());
		JTextField jtfCoName = new JTextField(edfvo.getCoName());
		jtfName.setEditable(false);
		jtfTel.setEditable(false);
		jtfEmail.setEditable(false);
		jtfCoName.setEditable(false);

		// �ؽ�Ʈ �ʵ�
		jtfSubject = new JTextField();
		jtfSal = new JTextField();

		// ������
		jtaErDesc = new JTextArea();
		jtaErDesc.setRows(5);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		// �ʿ�������
		jchJava = new JCheckBox("Java");
		jchJspServlet = new JCheckBox("JspServlet");
		jchSpring = new JCheckBox("Spring");
		jchOracle = new JCheckBox("Oracle");
		jchHTML = new JCheckBox("HTML");
		jchCSS = new JCheckBox("HTML");
		jchLinux = new JCheckBox("Linux");
		jchJS = new JCheckBox("JavaScript");

		// �������� �޺��ڽ�
		String[] rItem = { "����", "���" };
		jcbRank = new JComboBox<>(rItem);
		String[] eItem = { "����", "�ʴ���", "����", "�ڻ�" };
		jcbEducation = new JComboBox<>(eItem);
		String[] lItem = { "����", "���", "��õ", "����", "����", "�泲", "���", "����", "����", "����", "�뱸", "���", "�λ�", "���", "�泲",
				"����", "����", "����" };
		jcbLoc = new JComboBox<>(lItem);
		String[] hItem = { "������", "�����", "����" };
		jcbHireType = new JComboBox<>(hItem);
		String[] pItem = { "YES", "NO" };
		jcbPortfolio = new JComboBox<>(pItem);

		// ��ư��
		jbReg = new JButton("���");
		jbCancel = new JButton("�ݱ�");

		/* ������Ʈ ũ�� ���� */
		setLayout(null); // ������ġ ����

		// ���
		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(null);
		imgPanel.setBorder(new TitledBorder("ȸ��ΰ�"));
		imgPanel.setBounds(15, 8, 185, 192);
		jlImage.setBounds(7, 15, 170, 170);
		imgPanel.add(jlImage);

		jlName.setBounds(210, 40, 60, 20);
		jlTel.setBounds(210, 80, 60, 20);
		jlEmail.setBounds(210, 120, 60, 20);
		jtfName.setBounds(255, 37, 115, 23);
		jtfTel.setBounds(255, 77, 115, 23);
		jtfEmail.setBounds(255, 117, 115, 23);

		// ��������
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(15, 205, 365, 180);
		infoPanel.setBorder(new TitledBorder("��������"));
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

		// ������
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("������"));
		erDescPanel.setBounds(15, 455, 365, 110);
		erDescPanel.add(jspErDesc);

		// �ʿ� �������
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(new GridLayout(2, 4, 0, 0));
		skillGridPanel.setBounds(15, 390, 365, 60);
		skillGridPanel.setBorder(new TitledBorder("�ʿ� �������"));
		skillGridPanel.add(jchJava);
		skillGridPanel.add(jchJspServlet);
		skillGridPanel.add(jchSpring);
		skillGridPanel.add(jchOracle);
		skillGridPanel.add(jchHTML);
		skillGridPanel.add(jchCSS);
		skillGridPanel.add(jchLinux);
		skillGridPanel.add(jchJS);

		// ���ϴ� ��ư
		jbReg.setBounds(140, 575, 60, 25);
		jbCancel.setBounds(210, 575, 60, 25);

		/* �����ӿ� ��ġ */
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
		add(jbReg);
		add(jbCancel);

		/* �̺�Ʈ��� */
		ErAddController edc = new ErAddController(this,emmc,erId);
		jbReg.addActionListener(edc);
		jbCancel.addActionListener(edc);
		addWindowListener(edc);
		/* ������ ũ�� ���� �� ����ȭ */
		setBounds(100, 100, 410, 660);
		setVisible(true);

	}// ������

	/******** getter ********/

	public JTextField getJtfSubject() {
		return jtfSubject;
	}

	public JTextField getJtfSal() {
		return jtfSal;
	}

	public JTextArea getJtaErDesc() {
		return jtaErDesc;
	}

	public JCheckBox getJchJava() {
		return jchJava;
	}

	public JCheckBox getJchJspServlet() {
		return jchJspServlet;
	}

	public JCheckBox getJchSpring() {
		return jchSpring;
	}

	public JCheckBox getJchOracle() {
		return jchOracle;
	}

	public JCheckBox getJchHTML() {
		return jchHTML;
	}

	public JCheckBox getJchCSS() {
		return jchCSS;
	}

	public JCheckBox getJchLinux() {
		return jchLinux;
	}

	public JCheckBox getJchJS() {
		return jchJS;
	}

	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}

	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}

	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}

	public JComboBox<String> getJcbHireType() {
		return jcbHireType;
	}

	public JComboBox<String> getJcbPortfolio() {
		return jcbPortfolio;
	}

	public JButton getJbReg() {
		return jbReg;
	}

	public JButton getJbCancel() {
		return jbCancel;
	}

	/******** getter ********/
/*
	public static void main(String[] args) {
		new ErAddView(null, null, null);
	}// main
*/
}// class
