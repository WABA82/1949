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

import user.ee.controller.EeInfoModifyController;
import user.ee.vo.EeInfoVO;

public class EeInfoModifyView extends JDialog {

	private JButton jbModify, jbModifyExt, jbModifyImg, jbClose;
	private JComboBox<String> jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	private JLabel jlImag;

	private EeMainView emv;

	public EeInfoModifyView(EeMainView emv, EeInfoVO eivo) {
		super(emv, "�⺻ ���� ���� ����", true);
		this.emv = emv;
		// image
		ImageIcon ii = new ImageIcon("C:/dev/1949/03.����/src/file/eeImg/" + eivo.getImg());
		jlImag = new JLabel(ii);
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
		
		switch (eivo.getRank()) {
		case "C":
			jcbRank.setSelectedIndex(0);
			break;
		case "N":
			jcbRank.setSelectedIndex(1);
			break;
		}// end switch

		String[] loc = { "����", "���", "��õ", "����", "����", "�泲", "���", "����", "����", "����", "�뱸", "���", "�λ�", "���", "�泲", "����",
				"����", "����" };
		jcbLoc = new JComboBox<>(loc);
	
		String vo_loc = eivo.getLoc().trim();
		for (int i = 0; i < loc.length; i++) {
			if (loc[i].equals(vo_loc)) {
				jcbLoc.setSelectedIndex(i);
			} // end if
		}// end for

		String[] edu = { "����", "�ʴ���", "����", "����", "�ڻ�" };
		jcbEducation = new JComboBox<>(edu);
		
		String co_edu=eivo.getEducation().trim();
		for( int i=0; i<edu.length; i++) {
			if(edu[i].equals(co_edu)) {
				jcbEducation.setSelectedIndex(i);
			}//end if
		}//end for
		
		String[] port = { "YES", "NO" };
		jcbPortfolio = new JComboBox<>(port);

//	switch (eivo.getPortfolio()) {
//	case "Y":
//		jcbPortfolio.setSelectedIndex(0);
//	case "N":
//		jcbPortfolio.setSelectedIndex(1);
//	}//end switch
		
		if(eivo.getPortfolio().equals("Y")) {
			jcbPortfolio.setSelectedIndex(0);
		}else {
			jcbPortfolio.setSelectedIndex(1);
		}
		
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

		// JText
		JTextField jtfName = new JTextField(eivo.getName());
		add(jtfName);
		jtfName.setEditable(false);
		jtfName.setBounds(325, 22, 130, 20);

		JTextField jtfAge = new JTextField(String.valueOf(eivo.getAge()));
		add(jtfAge);
		jtfAge.setEditable(false);
		jtfAge.setBounds(325, 182, 130, 20);

		JTextField jtfGender = new JTextField(eivo.getGender());
		add(jtfGender);
		jtfGender.setEditable(false);
		jtfGender.setBounds(325, 262, 130, 20);

		jtfExtResume = new JTextField(eivo.getExtResume());
		add(jtfExtResume);
		jtfExtResume.setEditable(false);
		jtfExtResume.setBounds(325, 302, 130, 20);

		// Combobox
		add(jcbRank);
		jcbRank.setBounds(325, 62, 130, 20);
		add(jcbLoc);
		jcbLoc.setBounds(325, 102, 130, 20);
		add(jcbEducation);
		jcbEducation.setBounds(325, 142, 130, 20);
		add(jcbPortfolio);
		jcbPortfolio.setBounds(325, 222, 130, 20);

		// �̺�Ʈ ó��
		EeInfoModifyController eimc = new EeInfoModifyController(this, eivo.getEeNum(), eivo);
		jbModify.addActionListener(eimc);
		jbModifyImg.addActionListener(eimc);
		jbModifyExt.addActionListener(eimc);
		jbClose.addActionListener(eimc);

		setLayout(null);
		setBounds(100, 100, 490, 460);

		setVisible(true);
		setResizable(false);
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}// ������

	public JButton getJbModify() {
		return jbModify;
	}

	public JButton getJbModifyExt() {
		return jbModifyExt;
	}

	public JButton getJbModifyImg() {
		return jbModifyImg;
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

	public JLabel getJlImag() {
		return jlImag;
	}

	public EeMainView getEmv() {
		return emv;
	}

}// class
