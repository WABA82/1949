package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import user.common.view.LoginView;
import user.common.vo.ErMainVO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.CoInfoRegView;
import user.er.view.ErMainView;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;

public class ErMainController extends WindowAdapter implements ActionListener, MouseListener {
	
	private ErMainView emv;
	private String erId;
	
	private ErMainVO ermvo;
	private CoInsertVO civo;
	private CoInfoVO cvo;
	private ErDAO erdao;
	public ErMainController(ErMainView emv, ErMainVO ermvo) {
		this.emv = emv;
//		this.erId=erId;
		this.ermvo=ermvo;
		erdao=ErDAO.getInstance();
	}
	
	public void mngUser() throws SQLException {
		if(ermvo.getActivation().equals("S")) {
			new CoInfoRegView(emv, ermvo.getErId());
		}else {
				cvo=erdao.selectCoInfo(ermvo.getErId());
				new CoInfoModifyView(emv, cvo);
		}//end else
			
	}//end if
	
	public void showHiring() {
		
	}
	
	public void mngEr() {
		
	}
	
	public void showApp() {
		
	}
	
	public void showInterestEe() {
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() == emv.getJlLogOut()) {
			new LoginView();
			emv.dispose();
		}//end if
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == emv.getJbCoMgmt()) {
			try {
				mngUser();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

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
