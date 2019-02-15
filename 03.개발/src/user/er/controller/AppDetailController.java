package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.AppDetailView;
import user.er.vo.DetailAppEeVO;

public class AppDetailController extends WindowAdapter implements ActionListener {

	/* �ν��Ͻ� ���� */
	private AppDetailView adv;
	private ErDAO er_dao;

	public AppDetailController(AppDetailView adv, String app_num) {
		this.adv = adv;
		er_dao = ErDAO.getInstance();
		setInfo(app_num);
	}// ������

	public void setInfo(String app_num) {
		DetailAppEeVO daevo = null;
		try {
			daevo = er_dao.selectDetailAppEe(app_num);
			if (daevo != null) {
				String imgPath = "C:/dev/1949/03.����/��������/�����ڻ���/150x200px/";
				adv.getJlImage().setIcon(new ImageIcon(imgPath + daevo.getImg()));
				adv.getJtfName().setText(daevo.getName());
				adv.getJtfTel().setText(daevo.getTel());
				adv.getJtfEmail().setText(daevo.getEmail());
				adv.getJtfRank().setText(daevo.getRank());
				adv.getJtfLoc().setText(daevo.getLoc());
				adv.getJtfEdu().setText(daevo.getEducation());
				adv.getJtfAge().setText(String.valueOf(daevo.getAge()));
				adv.getJtfPort().setText(daevo.getPortfolio());
				adv.getJtfGender().setText(daevo.getGender());
			} else {
				JOptionPane.showMessageDialog(adv, "����� ���ΰ��� ���� ������ �����ڰ� �����ϴ�.");
			} // end else

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setInfo()

	@Override
	public void windowClosing(WindowEvent e) {
		adv.dispose();
	}// windowClosing(WindowEvent e)

	@Override
	public void actionPerformed(ActionEvent e) {
	}// actionPerformed

}// class
