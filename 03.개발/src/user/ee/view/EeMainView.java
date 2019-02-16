package user.ee.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.common.vo.EeMainVO;
import user.ee.controller.EeMainController;

public class EeMainView extends JFrame {

	/* �ӽ÷� ���� �������Դϴ�. */
	public static final String EE_ID = "";
	private EeMainVO emvo;
	private JButton jbEeInfo, jbErInfo, jbInterestEr, jbApp;
	private JLabel jlUserInfo, jlLogOut, jlActivation, jlImg;
//	private JTextField jtfName;
	
	public EeMainView(EeMainVO emvo) {
		super("1949 - �Ϲݻ���� ["+emvo.getName()+"]");
		this.emvo=emvo;
		JLabel jlAct = new JLabel("�⺻���� ��Ͽ��� : ");
		jlImg = new JLabel(new ImageIcon("C:/dev/homework/1949/1949/03.����/src/img/eeImg/"+emvo.getImg()));
		jlImg.setBorder(new TitledBorder("�� �̹���"));

		jlActivation = new JLabel("X"/* emvo.getActivation() */);
		jlUserInfo = new JLabel("ȸ����������");
		jlLogOut = new JLabel("�α׾ƿ�");

		jbEeInfo = new JButton("�⺻���� ����");
		jbErInfo = new JButton("�������� ����");
		jbInterestEr = new JButton("���� ��������");
		jbApp = new JButton("������Ȳ");

		setLayout(null);

		jlAct.setBounds(20, 20, 150, 30);
		jlActivation.setBounds(145, 20, 30, 30);
		jlImg.setBounds(15, 70, 200, 260);
		jlUserInfo.setBounds(260, 15, 100, 30); // ȸ����������(�̹����� ���� ����)
		jlLogOut.setBounds(380, 15, 100, 30); // �α׾ƿ�(�̹����� ���� ����)

		jbEeInfo.setBounds(250, 70, 200, 50);
		jbErInfo.setBounds(250, 140, 200, 50);
		jbInterestEr.setBounds(250, 210, 200, 50);
		jbApp.setBounds(250, 280, 200, 50);

		add(jlAct);
		add(jlActivation);
		add(jlImg);

		add(jlUserInfo);
		add(jlLogOut);
		add(jbEeInfo);
		add(jbErInfo);
		add(jbInterestEr);
		add(jbApp);

		EeMainController emc = new EeMainController(this, emvo);
		jbEeInfo.addActionListener(emc);
		jbErInfo.addActionListener(emc);
		jbInterestEr.addActionListener(emc);
		jbApp.addActionListener(emc);
		jlUserInfo.addMouseListener(emc);
		jlLogOut.addMouseListener(emc);

		addWindowListener(emc);

		setBounds(500, 200, 475, 385);
		setResizable(false);
		setVisible(true);
	}

	public JLabel getJlActivation() {
		return jlActivation;
	}

	public JButton getJbEeInfo() {
		return jbEeInfo;
	}

	public JButton getJbErInfo() {
		return jbErInfo;
	}

	public JButton getJbInterestEr() {
		return jbInterestEr;
	}

	public JButton getJbApp() {
		return jbApp;
	}

	public JLabel getJlUserInfo() {
		return jlUserInfo;
	}

	public JLabel getJlLogOut() {
		return jlLogOut;
	}

	
	public JLabel getJlImg() {
		return jlImg;
	}


}
