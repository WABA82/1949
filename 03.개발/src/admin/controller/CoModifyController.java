package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.view.UserModifyView;
import admin.vo.CoInfoVO;
import admin.vo.CoModifyVO;
import admin.vo.UserInfoVO;
import sun.applet.resources.MsgAppletViewer_zh_CN;

public class CoModifyController extends WindowAdapter implements MouseListener, ActionListener {
	private CoModifyView cmv;
	private AdminMgMtView ammv;
	private CoInfoVO civo;
	
	private String newImg1, newImg2, newImg3, newImg4;
	
	private static final int DBL_CLICK = 2;
	
	public CoModifyController(CoModifyView cmv, AdminMgMtView ammv, CoInfoVO civo) {
		this.cmv= cmv;
		this.ammv = ammv;
		this.civo = civo;
		
		newImg1=""; newImg2=""; newImg3=""; newImg4="";
	}//생성자 끝
	
	@Override
	public void windowClosing(WindowEvent e) {
		cmv.dispose();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(cmv, msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cmv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cmv.getJbRemove()){
			remove();
		}else if(ae.getSource()==cmv.getJbClose()) {
			cmv.dispose();
		}//버튼
	}

	public void modify() {
		try {
			System.out.println("수정");
			
			String coNum = civo.getCoNum();
			String coName = cmv.getJtfCoName().getText().trim();
			// 날짜를 포맷화 하는 메소드 필요(yyyy-mm-dd)
			// 메소드 처리 후 값 할당
			String estDate = cmv.getJtfEstDate().getText().trim();
			String coDesc = cmv.getJtaCoDesc().getText().trim();
			
			String img1,img2,img3,img4 = "";
			// img 변경 시 새로운 이미지명 추가
			if (newImg1.equals("")) {
				img1 = civo.getImg1();
			} else {
				img1 = newImg1;
			}
			if (newImg2.equals("")) {
				img2 = civo.getImg2();
			} else {
				img2 = newImg2;
			}
			if (newImg3.equals("")) {
				img3 = civo.getImg3();
			} else {
				img3 = newImg3;
			}
			if (newImg4.equals("")) {
				img4 = civo.getImg4();
			} else {
				img4 = newImg4;
			}
			
			// NumberFormatException 예외처리 필요
			int memberNum = Integer.parseInt(cmv.getMemberNum().getText().trim());
			
			CoModifyVO cmvo = new CoModifyVO(coNum, coName, estDate, coDesc, 
					img1, img2, img3, img4, memberNum);
		
			// DB에 정보를 변경하고 이미지를 fileServer에 전송하는 메소드 필요 ///////////
			if(AdminDAO.getInstance().updateCo(cmvo)) {
				
				msgCenter("회사정보가 수정되었습니다.");
				cmv.dispose();
				CoInfoVO clvo = AdminDAO.getInstance().selectOneCo(coNum);
				new CoModifyView(ammv, clvo);
			} else {
				msgCenter("회사정보 변경에 실패했습니다.");
			}
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}//modify
	
	public void remove() { //////////////////// 삭제는 집에서 작업 ///////////////////////
		System.out.println("삭제");
	}//remove /////////////////////////////////////////////////////////////////////////////
	
	public void changeImg(JLabel jl) {
		
		// 이미지 변경, 이전 이미지명과 새로운 이미지명을 인스턴스에 저장
		
		FileDialog fd = new FileDialog(cmv, "이미지 변경", FileDialog.LOAD);
		fd.setVisible(true);
		
		System.out.println(fd.getDirectory());
		System.out.println(fd.getFile());
		
		String[] arryExt = { "jpg", "png", "jpeg", "gif" };
		String selectedFileExt = fd.getFile().substring(fd.getFile().lastIndexOf(".")+1);
		
		
		
		
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getClickCount()) {
		case DBL_CLICK: // 이미지 더블클릭으로 변경
			
			if(e.getSource() == cmv.getJlImg1()) {
				changeImg(cmv.getJlImg1());
			}
			
			if(e.getSource() == cmv.getJlImg2()) {
				changeImg(cmv.getJlImg2());
				
			}
			
			if(e.getSource() == cmv.getJlImg3()) {
				changeImg(cmv.getJlImg3());
				
			}
			
			if(e.getSource() == cmv.getJlImg4()) {
				changeImg(cmv.getJlImg4());
			}
			
			break;
		}
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
