package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.ErMainView;
import user.er.vo.CoInfoVO;
import user.util.UserLog;
import user.util.UserUtil;

public class CoInfoModifyController extends WindowAdapter implements ActionListener, MouseListener {

	private CoInfoModifyView cimv;
	
	private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	private boolean imgFlag1, imgFlag2, imgFlag3, imgFlag4;
	
	private String path, name;
	private ErDAO erdao;
	private CoInfoVO cvo;
	private ErMainView emv;
	
	private UserUtil uu;
	private UserLog ul;

	public CoInfoModifyController(CoInfoModifyView cimv,  CoInfoVO cvo, ErMainView emv) {
		this.cimv = cimv;
		this.cvo=cvo;
		this.emv=emv;
		erdao = ErDAO.getInstance();
		
		uu = new UserUtil();
		ul = new UserLog();
	}// 생성자

	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}// 윈도우 닫기

	@Override
	public void actionPerformed(ActionEvent ae) {
		/* 수정 버튼 눌렀을 때 */
		if (ae.getSource() == cimv.getJbModify()) {
			try {
				modify();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == cimv.getJbClose()) {
			cimv.dispose();
		} // else if

	}// actionPerformed

	public void modify() throws UnknownHostException, IOException {
		String erId=cvo.getErId();
		String coNum=cvo.getCoNum();
		String coName = cimv.getJtfCoName().getText().trim();
		String estDate = cimv.getJtfEstDate().getText().trim();
		int memberNum = 0;
		
		
		if (coName.equals("")) {
			JOptionPane.showMessageDialog(cimv, "회사명을 입력해주세요.");
			return;
		} // end if

		if (estDate.isEmpty()) {
			JOptionPane.showMessageDialog(cimv, "설립일을 입력해주세요.");
			return;
		} // end if
		
		if(estDate.length()  < 7 ) { //////////////////////// 예외처리 해주세요..
        	 JOptionPane.showMessageDialog(cimv, "설립년도의 입력형식을 아래와 같은 형식으로 해주세요\nex)19901217\nex)1990-12-17");
        	 return;
         }//end if

		try {
			memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim()); 
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(cimv, "사원수는 숫자만 입력가능합니다.");
			return;
		} // end catch
		
		if (memberNum == 0) {
			JOptionPane.showMessageDialog(cimv, "사원수를 입력해주세요.");
			return;
		} // end if
		
		

		String imgName1 = "";
		String imgName2 = "";
		String imgName3 = "";
		String imgName4 = "";
		
		
		
		if (!imgFlag1) { // 변경되지 않았으면 기존 이미지 그대로
			imgName1 = cvo.getImg1();
		} else {
			imgName1 = System.currentTimeMillis()+uploadImg1.getName();
		}
		if (!imgFlag2) { // 변경되지 않았으면 기존 이미지 그대로
			imgName2 = cvo.getImg2();
		} else {
			imgName2 = System.currentTimeMillis()+uploadImg2.getName();
		}
		if (!imgFlag3) { // 변경되지 않았으면 기존 이미지 그대로
			imgName3 = cvo.getImg3();
		} else {
			imgName3 = System.currentTimeMillis()+uploadImg3.getName();
		}
		if (!imgFlag4) { // 변경되지 않았으면 기존 이미지 그대로
			imgName4 = cvo.getImg4();
		} else {
			imgName4 = System.currentTimeMillis()+uploadImg4.getName();
		}
		
		String coDesc = cimv.getJtaCoDesc().getText().trim();

		StringBuilder updateMsg = new StringBuilder();
		updateMsg
		.append("회사 수정정보 \n").append("회사 명 : ").append(cimv.getJtfCoName().getText()).append("\n")
		.append("설립 년도 : ").append(cimv.getJtfEstDate().getText()).append("\n").append("사원 수  : ")
		.append(cimv.getMemberNum().getText()).append("\n").append("기업 설명  : ")
		.append(cimv.getJtaCoDesc().getText()).append("\n").append("위의 정보로 회사의 정보가 변경됩니다. 변경하시겠습니까?");

		switch (JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION:
				CoInfoVO newCvo = new CoInfoVO(erId, coNum, coName, imgName1, imgName2,
						imgName3, imgName4, estDate, coDesc, memberNum);
				try {
					if (erdao.updateCoInfo(newCvo)) {
						
						Socket client = null;
						DataOutputStream dos = null;
						DataInputStream dis = null;
						FileInputStream fis = null;
						FileOutputStream fos = null;
						
						// 변경된 이미지 기존 파일은 삭제(유저, 파일서버), 파일서버에 추가, 요청 
						File originFile = null;
						if (imgFlag1) {
							System.out.println("==== 기존파일 : "+cvo.getImg1());
							originFile = new File("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg1());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg1(), "co", client, dos, dis);
							uu.addNewFile(imgName1, uploadImg1, "co", client, dos, dis, fis);
							uu.reqFile(imgName1, "co", client, dos, dis, fos);
							System.out.println("이미지1 수정");
						}
						if (imgFlag2) {
							originFile = new File("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg2());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg2(), "co", client, dos, dis);
							uu.addNewFile(imgName2, uploadImg2, "co", client, dos, dis, fis);
							uu.reqFile(imgName2, "co", client, dos, dis, fos);
							System.out.println("이미지2 수정");
						}
						if (imgFlag3) {
							originFile = new File("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg3());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg3(), "co", client, dos, dis);
							uu.addNewFile(imgName3, uploadImg3, "co", client, dos, dis, fis);
							uu.reqFile(imgName3, "co", client, dos, dis, fos);
							System.out.println("이미지3 수정");
						}
						if (imgFlag4) {
							originFile = new File("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg4());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg4(), "co", client, dos, dis);
							uu.addNewFile(imgName4, uploadImg4, "co", client, dos, dis, fis);
							uu.reqFile(imgName4, "co", client, dos, dis, fos);
							System.out.println("이미지4 수정");
						}
						
						JOptionPane.showMessageDialog(cimv, "회사 정보가 수정 되었습니다");
						ErMainVO updateEmvo=CommonDAO.getInstance().selectErMain(erId, "Y");
						ul.sendLog(erId, "["+coNum+"]기업정보 수정");
						new ErMainView(updateEmvo);
						cimv.dispose();
						emv.dispose();
					} else {
						JOptionPane.showMessageDialog(cimv, "회사정보 수정 실패(DB문제)");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}// end switch
	}// modify

	public void changeImg(JLabel jl, int imgNumber) {
		boolean flag = false;
		FileDialog fd = new FileDialog(cimv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();
		
		if (path == null || name == null) {
			return;
		}
		
		String[] extFlag = { "jpg", "jpeg", "png", "bmp", "gif" };
		for (String ext : extFlag) {
			if (name.toLowerCase().endsWith(ext)) {
				flag = true;
			} // end if
		} // end for

		if (flag) {
			if (imgNumber == 1) {
				uploadImg1 = new File(path + name);
				imgFlag1 = true;
				cimv.getJlImg1().setIcon(new ImageIcon(uploadImg1.getAbsolutePath()));
			} else if (imgNumber == 2) {
				uploadImg2 = new File(path + name);
				imgFlag2 = true;
				cimv.getJlImg2().setIcon(new ImageIcon(uploadImg2.getAbsolutePath()));
			} else if (imgNumber == 3) {
				uploadImg3 = new File(path + name);
				imgFlag3 = true;
				cimv.getJlImg3().setIcon(new ImageIcon(uploadImg3.getAbsolutePath()));
			} else if (imgNumber == 4) {
				uploadImg4 = new File(path + name);
				imgFlag4 = true;
				cimv.getJlImg4().setIcon(new ImageIcon(uploadImg4.getAbsolutePath()));
			} // end else if
		} else {
			JOptionPane.showMessageDialog(cimv, name + "파일은 이미지형식이 아닙니다.\n(jpg, jpeg, png, bmp, gif파일 등록 가능)");
		} // end else
	}// chgImg

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cimv.getJlImg1()) {
			changeImg(cimv.getJlImg1(), 1);
		} // end if

		if (me.getSource() == cimv.getJlImg2()) {
			changeImg(cimv.getJlImg2(), 2);
		} // end if

		if (me.getSource() == cimv.getJlImg3()) {
			changeImg(cimv.getJlImg3(), 3);
		} // end if

		if (me.getSource() == cimv.getJlImg4()) {
			changeImg(cimv.getJlImg4(), 4);
		} // end if

	}// mouseClicked

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
}// class
