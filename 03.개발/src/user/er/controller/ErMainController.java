package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import user.common.view.ChangeUserInfoView;
import user.common.view.LoginView;
import user.common.vo.ErMainVO;

import user.common.vo.UserInfoVO;

import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.CoInfoRegView;
import user.er.view.ErAppView;
import user.er.view.ErHiringView;
import user.er.view.ErInterestView;
import user.er.view.ErMainView;
import user.er.view.ErMgMtView;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;
import user.er.vo.ErHiringVO;
import user.er.vo.ErListVO;

public class ErMainController extends WindowAdapter implements ActionListener, MouseListener {
	
	private ErMainView emv;
	private String erId;
	private ErMainVO ermvo;
	private CoInsertVO civo;
	private CoInfoVO cvo;
	private ErDAO erdao;
	private CommonDAO C_dao;
	
	public ErMainController(ErMainView emv, ErMainVO ermvo) {

		this.emv = emv;
		this.ermvo=ermvo;
		erdao=ErDAO.getInstance();
		C_dao=CommonDAO.getInstance();
	}//������
	
	/**ȸ������ ���, ���� **/
	public void mngUser() throws SQLException {
		if(ermvo.getActivation().equals("N")) {
			new CoInfoRegView(emv, ermvo.getErId());
			System.out.println(ermvo);
			
		}else if(ermvo.getActivation().equals("Y")){
				cvo=erdao.selectCoInfo(ermvo.getErId());
				new CoInfoModifyView(emv, cvo);
		}//end else
			
	}//end if
	
	/** �������� ����� ���� method**/
	public void showHiring() {
		
		if(ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		}else if(ermvo.getActivation().equals("Y")){
			List<ErHiringVO> ehvo=new ArrayList<ErHiringVO>();
		new ErHiringView(emv, ehvo, ermvo.getErId());
		}//end if
		
	}//showHiring
	
	/** ȸ�������� �����ϴ� method**/
	public void mngEr() throws SQLException {
		JFrame jf=new JFrame();
			UserInfoVO uivo=C_dao.selectUserInfo(ermvo.getErId());
			new ChangeUserInfoView(jf, uivo);
	}//mngEr
	
	/** ���� �����ڸ� ���� �ִ� method**/
	public void showApp() {
		
		if(ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		}else if(ermvo.getActivation().equals("Y")){
			new ErAppView(emv, ermvo.getErId());
		}//end id
		
	}//showApp
	
	/***/
	public void showInterestEe() {
		
		if(ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		}else if(ermvo.getActivation().equals("Y")){
			new ErInterestView(emv, ermvo.getErId());
		}//end id
		
	}//showInteresrEe

	/***/
	public void ShowErMgMtview() {
		
		if(ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		}else if(ermvo.getActivation().equals("Y")){
			List<ErListVO> elvo=new ArrayList<ErListVO>();
			new ErMgMtView(emv, elvo, ermvo.getErId());
		}//end if
		
	}//ShowErMgView
	
	@Override
	public void mouseClicked(MouseEvent me) {
		
		if(me.getSource() == emv.getJlUserInfo()) {
			try {
				mngEr();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end if
		
		if(me.getSource() == emv.getJlLogOut()) {
			emv.dispose();
			new LoginView();
		}//end if
		
	}//mouseClicked
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == emv.getJbCoMgmt()) {
			try {
				mngUser();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end if
			
		if(ae.getSource() == emv.getJbEeInfo()) {
			showHiring();
		}//end if
		
		if(ae.getSource() == emv.getJbInterestEe()) {
			showInterestEe();
		}//end if
		
		if(ae.getSource() == emv.getJbApp()) {
			showApp();
		}//end if
		
		if(ae.getSource() == emv.getJbErMgmt()) {
			ShowErMgMtview();
		}//end if
		
	}//actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
