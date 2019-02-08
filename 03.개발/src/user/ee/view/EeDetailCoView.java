package user.ee.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.ee.controller.EeDetailCoController;
import user.ee.vo.CoDetailVO;

@SuppressWarnings("serial")
public class EeDetailCoView extends JDialog {
	private JButton jbClose;
	private JLabel img1, img2, img3, img4;
	
	public EeDetailCoView(EeDetailErView edev, CoDetailVO cdvo) {
	
		JTextField jtfCoName = new JTextField();
		JTextField jtfEstDate = new JTextField();
		JTextField memberNum = new JTextField();			
			
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");
	
		JTextArea jtaCoDesc = new JTextArea();
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);
		
		jbClose = new JButton("닫기");
		
		JLabel jlImg1 = new JLabel("j");
		JLabel jlImg2 = new JLabel("j");
		JLabel jlImg3 = new JLabel("j");
		JLabel jlImg4 = new JLabel("j");
		
		//배치
		setLayout(null);
		
		jlCoName.setBounds(240, 45, 57, 26);
		jlEstDate.setBounds(240, 97, 57, 26);
		jlmemberNum.setBounds(240, 149, 57, 26);
		
		jtfCoName.setBounds(300, 45, 133, 29);
		jtfEstDate.setBounds(300, 97, 133, 29);
		memberNum.setBounds(300, 149, 133, 29);
		
		jlImg1.setBounds(36, 25, 170, 170);
		jlImg1.setBorder(new TitledBorder("회사 이미지"));
		
		jlImg2.setBounds(33, 208, 50, 50);
		jlImg3.setBounds(93, 208, 50, 50);
		jlImg4.setBounds(158, 208, 50, 50);
		
		jlImg2.setBorder(new LineBorder(Color.black));
		jlImg3.setBorder(new LineBorder(Color.black));
		jlImg4.setBorder(new LineBorder(Color.black));
		
		jspTaDesc.setBounds(27, 275, 405, 266);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));
		
		jbClose.setBounds(338, 562, 92, 24);
		setBounds(100, 100, 480, 650);
		setVisible(true);
		
		
		EeDetailCoController edcc = new EeDetailCoController(this);
		addWindowListener(edcc);
		
		jbClose.addActionListener(edcc);
		
		add(jlCoName);
		add(jlEstDate);
		add(jlmemberNum);
		
		add(jtfCoName);
		add(jtfEstDate);
		add(memberNum);
		
		add(jlImg1);
		add(jlImg2);
		add(jlImg3);
		add(jlImg4);
		
		add(jspTaDesc);
		
		add(jbClose);
		
		
		
	}//생성자
	
	
	
	public JButton getJbClose() {
		return jbClose;
	}



	public JLabel getImg1() {
		return img1;
	}



	public JLabel getImg2() {
		return img2;
	}



	public JLabel getImg3() {
		return img3;
	}



	public JLabel getImg4() {
		return img4;
	}



/*	public static void main(String[] args) {
		new EeDetailCoView();
	}*/
	
}
