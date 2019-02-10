package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	private EeInfoRegView eirv;
	public EeInfoRegController(EeInfoRegView eirv) {
	this.eirv=eirv;
	
	
	}//»ý¼ºÀÚ
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv);
		}
	}//actionPerformed

}
