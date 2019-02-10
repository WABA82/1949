package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.vo.EeMainVO;
import user.ee.view.EeHiringView;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeInterestView;
import user.ee.view.EeMainView;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	
	public EeMainController(EeMainView emv, EeMainVO emvo) {
		this.emv = emv;
	}
	
	public void checkActivation() {
		
	}
	
	
	public void mngUser() {
		new EeInfoRegView( emv );
	}
	
	public void mngEe() {
		
	}
	
	public void showHiring() {
//		new EeHiringView(emv);
	}
	
	public void showInterestEr() {
//		new EeInterestView(emv, ehvo);
	}
	
	public void showApp() {
//		new EeAppView();
	}
	
	/**
	 * 	private EeMainView emv;
	 *  마우스 클릭시 종료 jlLogOut
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() ==emv.getJlLogOut() ) {
			emv.dispose();
			
		}//end if
		
		
		
	}//mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == emv.getJbEeInfo()) {
			mngUser();
		}//end if
		
		if(ae.getSource() == emv.getJbErInfo()) {
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
