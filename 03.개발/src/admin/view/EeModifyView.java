package admin.view;

import javax.swing.JDialog;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import admin.controller.AdminMgMtController;
import admin.controller.EeModifyController;
import admin.vo.EeInfoVO;


public class EeModifyView extends JDialog {

	private JButton jbModify, jbChangeExt, jbChangeImg, jbRemove, jbCancel, jbDownExt;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtRsm, jtfName;
	private JLabel jlImg;
	
	public EeModifyView(AdminMgMtView ammv, EeInfoVO eivo, AdminMgMtController ammc) {
		super(ammv, "�⺻ ���� ����",true);
		
		setLayout(null);

		//image 
		ImageIcon ii=new ImageIcon("C:/dev/1949/03.����/src/admin/img/ee/"+eivo.getImg());
		jlImg=new JLabel(ii);
		jlImg.setBorder(new TitledBorder("������ �̹���"));
		jlImg.setBounds(38, 20, 160, 225);
		add(jlImg);
		
		//�޺��ڽ�
		jbChangeImg=new JButton("�̹��� ����");
		add(jbChangeImg);
		jbChangeImg.setBounds(42, 260, 150, 30);
		
		jbChangeExt = new JButton("�ܺ��̷¼� ����");
		add(jbChangeExt);
		jbChangeExt.setBounds(45, 440, 150, 30);
		
		jbDownExt = new JButton("�ܺ��̷¼� �ٿ�");
		jbDownExt.setBounds(45, 400, 150, 30);
		
		jbModify = new JButton("����");
		add(jbModify);
		jbModify.setBounds(230, 440, 70, 30);
		
		jbRemove=new JButton("����");
		add(jbRemove);
		jbRemove.setBounds(310, 440, 70, 30);
		
		jbCancel=new JButton("�ݱ�");
		add(jbCancel);
		jbCancel.setBounds(390, 440, 70, 30);
				
		
		//Label
		JLabel jlId=new JLabel("���̵�");
		jlId.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
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
		
		JLabel jlRegDate=new JLabel("�����");
		jlRegDate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
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
		
		//Label ��ǥ
		add(jlId);
		jlId.setBounds(260, 23, 50, 20);
		add(jlName);
		jlName.setBounds(265, 63, 50, 20);
		add(jlRank);
		jlRank.setBounds(265, 103, 50, 20);
		add(jlLoc);
		jlLoc.setBounds(252, 145, 70, 20);
		add(jlEdu);
		jlEdu.setBounds(265, 182, 50, 30);
		add(jlAge);
		jlAge.setBounds(265, 225, 50, 30);
		add(jlPort);
		jlPort.setBounds(215, 265, 100, 30);
		add(jlGender);
		jlGender.setBounds(265, 305, 50, 30);
		add(jlRegDate);
		jlRegDate.setBounds(255, 345, 100, 30);
		add(jlResume);
		jlResume.setBounds(235, 385, 100, 30);
		
		//JText
		
		//�߰�
		JTextField jtfId=new JTextField(eivo.getId());
		add(jtfId);
		jtfId.setBounds(325, 25, 130, 20);
		jtfId.setEditable(false);
		
		jtfName=new JTextField(eivo.getName());
		add(jtfName);
		jtfName.setEditable(false);
		jtfName.setBounds(325, 65, 130, 20);
		
		//Combobox
		add(jcbRank);
		jcbRank.setBounds(325,105,130,20);
		jcbRank.setSelectedItem(eivo.getRank().equals("N") ? "����" : "���");
		
		add(jcbLoc);
		jcbLoc.setBounds(325,145,130,20);
		jcbLoc.setSelectedItem(eivo.getLoc());
		
		add(jcbEducation);
		jcbEducation.setBounds(325,188,130,20);
		jcbEducation.setSelectedItem(eivo.getEducation());
		
		JTextField jtfAge=new JTextField(String.valueOf(eivo.getAge()));
		add(jtfAge);
		jtfAge.setBounds(325, 232, 130, 20);
		jtfAge.setEditable(false);
		
		add(jcbPortfolio);
		jcbPortfolio.setBounds(325,272,130,20);
		jcbPortfolio.setSelectedItem(eivo.getPortfolio().equals("Y") ? "YES" : "NO");
		
		JTextField jtfGender=new JTextField(eivo.getGender().equals("F") ? "����" : "����");
		jtfGender.setEditable(false);
		add(jtfGender);
		jtfGender.setBounds(325, 311, 130, 20);
		
		JTextField jtfRegDate=new JTextField(eivo.getInputDate());
		add(jtfRegDate);
		jtfRegDate.setBounds(325, 351, 130, 20);
		jtfRegDate.setEditable(false);
		
		jtfExtRsm=new JTextField(eivo.getExtResume() == null ? "����" : eivo.getExtResume());
		jtfExtRsm.setEditable(false);
		add(jtfExtRsm);
		jtfExtRsm.setBounds(325, 391, 130, 20);
		
		//���̾ƿ�
		setBounds(ammv.getX()+500, ammv.getY()+50, 500, 540);
		
		EeModifyController emc = new EeModifyController(this, ammv, ammc, eivo);
		jbRemove.addActionListener(emc);
		jbModify.addActionListener(emc);
		jbChangeImg.addActionListener(emc);
		jbChangeExt.addActionListener(emc);
		jbCancel.addActionListener(emc);

		if (eivo.getExtResume() != null) {
			add(jbDownExt);
			jbDownExt.addActionListener(emc);
		}
		
		addWindowListener(emc);
		
		setResizable(false);
		setVisible(true);
	}//EeInfoRegView
	
	public JButton getJbDownExt() {
		return jbDownExt;
	}
	public JButton getJbModify() {
		return jbModify;
	}
	public JButton getJbChangeExt() {
		return jbChangeExt;
	}
	public JButton getJbChangeImg() {
		return jbChangeImg;
	}
	public JButton getJbRemove() {
		return jbRemove;
	}
	public JButton getJbCancel() {
		return jbCancel;
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
	public JTextField getJtfExtRsm() {
		return jtfExtRsm;
	}
	public JTextField getJtfName() {
		return jtfName;
	}
	public JLabel getJlImg() {
		return jlImg;
	}
}//class
