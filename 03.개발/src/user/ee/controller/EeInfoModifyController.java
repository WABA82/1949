package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeMainView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;
import user.ee.vo.EeRegVO;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	private File uploadImg;
	private EeDAO eedao;
	private EeMainView emv;
	
	public EeInfoModifyController(EeInfoModifyView eimv, EeInfoVO eivo, EeMainView emv) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.emv=emv;
		eedao=EeDAO.getInstance();
	}

	// 이력서 수정하는 Method
	private void modifyEe() {

		String tempRank = eimv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("신입", "C").replaceAll("경력", "N");
		String loc = eimv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eimv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eimv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eimv.getJtfExtResume().getText();
		StringBuilder updateMsg= new StringBuilder();
//		uploadImg=new File( "C:/dev/1949/03.개발/src/file/eeImg/" + eivo.getImg());
	
		if(uploadImg == null) {
			uploadImg = new File("C:/dev/1949/03.개발/src/file/eeImg/" + eivo.getImg());
		}//end if
		
		///////////여기서 수정////////////
//		if(uploadImg == null) {
//			uploadImg= new File("C:/dev/1949/03.개발/src/file/eeImg/"+eivo.getImg()); //수정
//		}//end if
		
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
			System.out.println(uploadImg.getName());
		EeModifyVO emvo= new EeModifyVO(eivo.getEeNum(), uploadImg.getName(),
				rank,loc,education, portfolio, extResume);
				System.out.println(emvo);
		try {
			eedao.updateEeInfo(emvo); //DB테이블에서 update수행
			JOptionPane.showMessageDialog(eimv, "개인정보가 변경되었습니다.");
			
			//메인창 종료휴 업데이트로 띄우기
			EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eivo.getEeId(), "Y");
			new EeMainView(updateEmvo);
			emv.dispose();
			
			////////////////////////////////////////////////////이미지 시작///////////////////////////////////
			if( !uploadImg.getName().equals("")) {// 변경한 이미지가 존재하는 경우
				try {
					
					File original = new File("C:/dev/1949/03.개발/src/file/eeImg/"+eimv.getJlImag());
					original.delete();
					uploadImgFile(uploadImg.getAbsoluteFile());
				} catch (IOException e) {
					e.printStackTrace();
				}//end cath
			}//end if
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//수정된 사항을 보여주는창.
		}//end switch
		
	}// modifyEe

	/**
	 * 이미지를 바꾸는 method
	 */
	private void ChangeImg() {
		boolean flag= false;
		
		FileDialog fd = new FileDialog(eimv, "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		
		if(path != null) {
			if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || 
					name.endsWith(".bmp") || name.endsWith(".gif")) {
				flag=true;
			}//end if
		 	if(flag) {
			uploadImg =  new File(  path + name );
				eimv.getJlImag().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
			} else {
				JOptionPane.showMessageDialog(eimv, name + "은 사용할수 없습니다.");
			}//else if
		}//end if
	}// changeImg

	//이력서 변경과 등록
	private void changeExt() {
		FileDialog fd = new FileDialog(eimv, "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		String fdDir = fd.getDirectory();
		String fdName = fd.getFile();
		String filePath = fdDir + fdName;
		File file = new File(filePath);
		changeExt(file);
	}//changeExt
	
	private void changeExt(File file) {
		boolean flag=false;
		String extResume="";
		extResume=file.getName();
	
		if(file.exists()) {
			if( extResume.endsWith(".txt") || extResume.endsWith(".pdf")) {
				flag=true;
			}//end if
			
			if(flag) {
				eimv.getJtfExtResume().setText(extResume);
				eimv.getJtfExtResume().setEditable(false);
				JTextField jtf=eimv.getJtfExtResume();
				jtf.setText(extResume);
			}else {
				JOptionPane.showMessageDialog(eimv, "외부이력서는 doc, pdf만 가능합니다.");
				return;
			}//end else
		}//end if
	}//changeExt

	/////////////////////////////////이미지 업로드///////////////////////////////////
	private void uploadImgFile(File file) throws IOException{
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
		fis = new FileInputStream(file);
		byte[] readData = new byte[512];
		
		int len = 0;
		String uploadPath= "C:/dev/1949/03.개발/src/user/img/ee/";
		fos = new FileOutputStream(uploadPath+file.getName());
		
		while( (len=fis.read(readData)) != -1) {
			fos.write(readData, 0, len);
			fos.flush();
		}//end while
		
		}finally {
			if(fis!=null) {
				fis.close();
			}//end if
			if(fos!=null) {
				fos.close();
			}//end if
			
		}//end finally
		
	}//uploadImg
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
				modifyEe();
				eimv.dispose();

		} // end if

		if (ae.getSource() == eimv.getJbModifyImg()) {
			ChangeImg();
		} // end if

		if (ae.getSource() == eimv.getJbModifyExt()) {
			changeExt();
		}

		if(ae.getSource() == eimv.getJbClose()) {
			eimv.dispose();
		}
	
	}// actionPerformed

}
