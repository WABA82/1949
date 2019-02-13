package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sun.reflect.generics.visitor.Reifier;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeRegVO;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* �ν��Ͻ����� */
	private EeInfoRegView eirv;
	private String uploadImg;
	private EeDAO eedao;
	private String eeid = "";// �� ���̵�.

	public EeInfoRegController(EeInfoRegView eirv, String eeid) {
		this.eirv = eirv;
		this.eeid = eeid;
		uploadImg = "";
		eedao = EeDAO.getInstance();
		setInfo("kun90");
	}// ������

	// �̹������۵� ���� ����
	public void register(String eeid) {
		if (uploadImg.isEmpty()) {
			JOptionPane.showMessageDialog(eirv, "�̹����� �ʼ� �Դϴ�.");
			return;
		}

		String rank = eirv.getJcbRank().getSelectedItem().toString();
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().toString();
		String portfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String extResume = eirv.getJtfExtResume().getSelectedText();

		File file = new File(uploadImg);

//		try {
//		EeInsertVO eivo= new EeInsertVO(eeid, file.getName(), rank, loc, education, portfolio, extResume );
////				( file.getName(), rank, loc, education, portfolio, extResume, );
////			eedao.insertEeinfo(eivo);
//			
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(eirv, "��ϵǾ����ϴ�");
//			
//			e.printStackTrace();
//		}//DB�� �߰�
	}// register

	public void changeImg() {
		FileDialog fd = new FileDialog(eirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")) {
				JOptionPane.showMessageDialog(eirv, name + "�� ����Ҽ� �����ϴ�.");
			} else {
				uploadImg = path + name;
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg));
			}
		}
	}// changeImg

	/**
	 * �̸�, ����, ����
	 */
	public void setInfo(String eeid) {
		JTextField name = eirv.getJtfName();
		JTextField age = eirv.getJtfAge();
		JTextField gender = eirv.getJtfGender();
		EeRegVO ervo = null;
		try {
			ervo = eedao.selectEeReg(eeid);
			name.setText(ervo.getName());
			age.setText(String.valueOf(ervo.getAge()));
			gender.setText(ervo.getGender());

		} catch (SQLException e) {
			e.printStackTrace();
		}// end catch
	}// setInfo.

	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterImg()) {
			changeImg();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv);
		} // end if

		if (ae.getSource() == eirv.getJbRegister()) {
			register(eeid);
		} // end if

	}// actionPerformed

}// class
