package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import user.dao.EeDAO;
import user.ee.dto.EeHiringCdtDTO;
import user.ee.view.EeDetailSearchView;
import user.ee.view.EeHiringView;
import user.ee.vo.EeHiringVO;

public class EeHiringController extends WindowAdapter implements ActionListener, MouseListener {
	private EeHiringView ehv;
	private List<EeHiringVO> list;
	private String eeId;
	private EeHiringCdtDTO dhcdto;
	private EeDAO e_dao;
	private int count; 
	public EeHiringController(EeHiringView ehv) {
		this.ehv = ehv;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		switch(me.getClickCount()) {
		case 2:
			if(me.getSource()==ehv.getJtErInfo()) {
				JTable jt = ehv.getJtErInfo();
				System.out.println(jt.getSelectedRow());
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ehv.getJbDetailSearch()) {
			EeDetailSearchView edsv = new EeDetailSearchView(ehv, this);
		}
		
		if(ae.getSource()==ehv.getJbWordSearch()) {
			//�˻��� ������
			
			//++ �߰��Ұ�: ��� �˻��� �� dao���� �޼ҵ� �ҷ��� ������ ������ �ٽ� �������
			for(int i=0; i<ehv.getJtErInfo().getRowCount();i++) {
				if(ehv.getJtErInfo().getValueAt(i, 3).toString().equals(ehv.getJtfSearch().getText().trim())) {
					count++;
				}else {
					ehv.getDtmErInfo().removeRow(i);
				}
			}//end for
			
			if(count!=0) {
				JOptionPane.showMessageDialog(ehv, "��"+count+"�� ����� �ֽ��ϴ�.");
			}
		}
		if(ae.getSource()==ehv.getJcbSort()) {
			//�����ϴ� �޺��ڽ�
		}
		if(ae.getSource()==ehv.getJtErInfo()) {
			//�ؽ�Ʈ�ʵ� ���͸� ������
		}
	}
	
	@Override
	public void windowClosing(WindowEvent we) {
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
