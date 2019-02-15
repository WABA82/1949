package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;

import user.dao.ErDAO;
import user.er.vo.DetailEeInfoVO;

/**
 * 
 * @author owner
 *
 */
public class ErDetailEeController extends WindowAdapter implements ActionListener, MouseListener {

	private ErDAO er_dao;

	public ErDetailEeController(String er_id, String ee_num) {
		er_dao = ErDAO.getInstance();
		setInfo(er_id, ee_num);
	}// 생성자

	////////////////// 재현 0213 진행중 //////////////////

	private void setInfo(String er_id, String ee_num) {
		DetailEeInfoVO deivo = er_dao.selectDetailEEInfo(er_id, ee_num);
	}// setInfo

	@Override
	public void mouseClicked(MouseEvent e) {
	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent e) {

	}// actionPerformed

	//////////////// 안쓰는 메서드////////////////
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	//////////////// 안쓰는 메서드////////////////
}// class
