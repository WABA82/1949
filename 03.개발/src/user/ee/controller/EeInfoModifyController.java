package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;
import user.util.UserLog;
import user.util.UserUtil;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	
	private File uploadImg;
	private File uploadExt;
	private boolean imgChgFlag;
	private boolean extChgFlag;
	
	private UserUtil uu;
	private UserLog ul;
	
	private EeDAO eedao;
	private EeMainView emv;
	
	public EeInfoModifyController(EeInfoModifyView eimv, EeInfoVO eivo, EeMainView emv) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.emv=emv;
		eedao=EeDAO.getInstance();
		uu = new UserUtil();
		ul = new UserLog();
	}

	// 이력서 수정하는 Method
	private void modifyEe() throws IOException {

		String tempRank = eimv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.equals("신입") ? "C" : "N";
		String loc = eimv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eimv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eimv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.equals("YES") ? "Y" : "N";
		String extResume = eimv.getJtfExtResume().getText();
		StringBuilder updateMsg= new StringBuilder();
	
		if(!imgChgFlag) {
			uploadImg = new File("C:/dev/1949/03.개발/src/user/img/ee/" + eivo.getImg());
		}//end if
		
		updateMsg
		.append("개인정보 변경 사항 \n")
		.append("이름 : "+eivo.getName()+"\n")
		.append("직급 : "+tempRank+"\n")
		.append("근무지역 : "+loc+"\n")
		.append("학력 : "+education+"\n")
		.append("나이 : "+eivo.getAge()+"\n")
		.append("포토폴리오 유무 : "+tempPortfolio+"\n")
		.append("성별 : "+eivo.getGender()+"\n")
		.append("외부이력서 : "+extResume+"\n")
		.append("위의 정보로 변경하시겠습니까?");
		
		switch(JOptionPane.showConfirmDialog(eimv, updateMsg)) {
		case JOptionPane.OK_OPTION :
			System.out.println("---이미지명 : "+uploadImg.getName());
			
			String imgName = "";
			String extName = "";
			
			if(imgChgFlag) {
				imgName = System.currentTimeMillis()+uploadImg.getName();
			} else {
				imgName = eivo.getImg();
			}
			
			if(extChgFlag) {
				extName = System.currentTimeMillis()+uploadExt.getName();
			} else {
				extName = eivo.getExtResume();
			}

			EeModifyVO emvo = new EeModifyVO(eivo.getEeNum(), imgName,
					rank,loc,education, portfolio, extName);
			
			System.out.println(emvo);
			try {
				if(eedao.updateEeInfo(emvo)) {
					JOptionPane.showMessageDialog(eimv, "개인정보가 변경되었습니다.");
					
					///////////////////////// 0302 영근 이미지 FS에 추가기능 구현 ////////////////////////////////
					Socket client = null;
					DataOutputStream dos = null;
					DataInputStream dis = null;
					FileInputStream fis = null;
					FileOutputStream fos = null;

					if (imgChgFlag) {
						File original = new File("C:/dev/1949/03.개발/src/user/img/ee/"+eivo.getImg());
						original.delete();
						
						System.out.println("getImg = "+eivo.getImg());
						// 새로운 이미지 파일 FileServer에 삭제, 추가, 요청
						System.out.println("111");
						uu.deleteFile(eivo.getImg(), "ee", client, dos, dis);
						System.out.println("--- 기존 이미지 삭제");
						
						uu.addNewFile(imgName, uploadImg, "ee", client, dos, dis, fis); // 새로운 이미지를 전송
						System.out.println("--- 이미지파일 수정");
						
						// FilerServer로부터 이미지 요청
						uu.reqFile(imgName, "ee", client, dos, dis, fos); // 새로운 이미지를 FS에게 요청, 저장
						System.out.println("--- 이미지파일 요청");
					}
					
					if (extChgFlag && !"".equals(eimv.getJtfExtResume().getText().trim())) {
						// 새로운 이력서 파일이 존재하면 FileServer에 삭제, 추가
						uu.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
						System.out.println("--- 기존이력서 삭제");
						
						uu.addNewFile(extName, uploadExt, "ext", client, dos, dis, fis);
						System.out.println("--- 이력서 파일 수정");
					}
					
					ul.sendLog(eivo.getEeId(), "기본정보 수정");
					
					//메인창 종료휴 업데이트로 띄우기
					EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eivo.getEeId(), "Y");
					new EeMainView(updateEmvo);
					emv.dispose();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		//수정된 사항을 보여주는창.
		}//end switch
		
	}// modifyEe

	/**
	 * 이미지를 바꾸는 method
	 */
	private void changeImg() {
		boolean flag = false;
		
		FileDialog fd = new FileDialog(eimv, "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		
		if (path == null || name == null) {
			return;
		} 	
		
		if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || 
				name.endsWith(".bmp") || name.endsWith(".gif")) {
			flag=true;
		}//end if
		
	 	if(flag) {
	 		imgChgFlag = true;
	 		uploadImg =  new File(path + name);
	 		System.out.println("upload img : "+uploadImg.getAbsolutePath());
	 		eimv.getJlImag().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
		} else {
			JOptionPane.showMessageDialog(eimv, name + "은 사용할수 없습니다.");
		}//else if
	}// changeImg

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
				try {
					modifyEe();
					eimv.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} // end if

		if (ae.getSource() == eimv.getJbModifyImg()) {
			changeImg();
		} // end if

		if (ae.getSource() == eimv.getJbModifyExt()) {
			new ModifyExtView(eimv, null, null, eimv, this, "modi");
		}

		if(ae.getSource() == eimv.getJbClose()) {
			eimv.dispose();
		}
	}// actionPerformed

	public void setUploadExt(File uploadExt) {
		this.uploadExt = uploadExt;
	}

	public void setExtChgFlag(boolean extChgFlag) {
		this.extChgFlag = extChgFlag;
	}
}
