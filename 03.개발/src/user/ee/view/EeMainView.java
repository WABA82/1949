package user.ee.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import user.common.vo.EeMainVO;
import user.ee.controller.EeMainController;

@SuppressWarnings("serial")
public class EeMainView extends JFrame {

	private JButton jbEeInfo, jbErInfo, jbInterestEr, jbApp;
	private JLabel jlUserInfo, jlLogOut, jlActivation, jlImg;

	public EeMainView(EeMainVO emvo) {
		
		super("1949 - �Ϲݻ���� ["+emvo.getName()+"]");
		JLabel jlAct = new JLabel("�⺻���� ��Ͽ��� : ");
		jlImg = new JLabel(new ImageIcon("C:/dev/1949/03.����/src/user/img/ee/"+emvo.getImg()));
		
		jlImg.setBorder(new TitledBorder("�� �̹���"));
		jlActivation = new JLabel( emvo.getActivation() );
			
		jlUserInfo = new JLabel(new ImageIcon("C:/dev/1949/03.����/src/user/img/userInfoMgmt.png") );
		jlLogOut = new JLabel(new ImageIcon("C:/dev/1949/03.����/src/user/img/logout.png"));
		
		jbEeInfo = new JButton("�⺻���� ����");
		jbErInfo = new JButton("�������� ����");
		jbInterestEr = new JButton("���� ��������");
		jbApp = new JButton("������Ȳ");

		System.out.println(emvo);
		setLayout(null);
		
		JLabel jlUserInfoMsg=new JLabel("ȸ������ ����");
		JLabel jlLogoutMsg=new JLabel("�α׾ƿ�");

		jlAct.setBounds(20, 20, 150, 30);
		jlActivation.setBounds(145, 20, 30, 30);
		jlImg.setBounds(15, 70, 200, 260);
		jlUserInfo.setBounds(260, 10, 100, 30); // ȸ����������(�̹����� ���� ����)
		jlUserInfoMsg.setBounds(267,35,100,30);
		jlLogOut.setBounds(340, 10, 100, 30); // �α׾ƿ�(�̹����� ���� ����)
		jlLogoutMsg.setBounds(363,35,100,30);

		jbEeInfo.setBounds(250, 70, 200, 50);
		jbErInfo.setBounds(250, 140, 200, 50);
		jbInterestEr.setBounds(250, 210, 200, 50);
		jbApp.setBounds(250, 280, 200, 50);

		add(jlAct);
		add(jlActivation);
		add(jlImg);
		add(jlUserInfoMsg);
		add(jlLogoutMsg);

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
	
	
}// class
