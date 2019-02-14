package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import user.er.dto.ErHiringCdtDTO;
import user.er.view.ErDetailSearchView;
import user.er.view.ErHiringView;
import user.er.vo.ErHiringVO;

public class ErHiringController extends WindowAdapter implements ActionListener, MouseListener {
	private ErHiringView ehv;
	private List<ErHiringVO> list;
	private String erId;
	private ErHiringCdtDTO dhcdto;
	public ErHiringController(ErHiringView ehv, List<ErHiringVO> list, String erId) {
		this.ehv = ehv;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent me) {
		switch(me.getClickCount()) {
		case 2:
			if(me.getSource()==ehv.getJtEeInfo())
			{
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ehv.getJbDetailSearch()) {
			new ErDetailSearchView(ehv, this); 
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		ehv.dispose();
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
