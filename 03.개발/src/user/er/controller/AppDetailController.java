package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.AppDetailView;
import user.er.vo.DetailAppEeVO;
import user.er.vo.ErAppStatusVO;

/**
 * 상세 지원자 정보 창의 컨트롤러.
 * 
 * @author 재현
 *
 */
public class AppDetailController extends WindowAdapter implements ActionListener {

	// DEFAULT 'U'
	// 'U' - unread 응답대기
	// 'R' - read 열람
	// 'A' - approved 지원수락
	// 'D' - denied 지원거절

	/* 인스턴스 변수 */
	private AppDetailView adv;
	private ErDAO er_dao;
	private String app_num;
	private DetailAppEeVO daevo = null;

	// appStatusFlag는 이미 응답을 했는지 안했는지를 판단하는 flag입니다.
	// false - 아직 응답하지 않았을 경우
	// true - 이미 응답을 한 경우
	private boolean appStatusFlag = false;

	public AppDetailController(AppDetailView adv, String app_num) {
		this.adv = adv;
		this.app_num = app_num;
		er_dao = ErDAO.getInstance();
		setInfo(app_num);
	}// 생성자

	/**
	 * 상세 지원자 정보창의 기본 정보들을 셋팅하는 메서드.
	 * 
	 * @param app_num
	 */
	public void setInfo(String app_num) {
		try {
			daevo = er_dao.selectDetailAppEe(app_num);

			if (daevo != null) {
				String imgPath = "C:/dev/1949/03.개발/가데이터/구직자사진/150x200px/";
				adv.getJlImage().setIcon(new ImageIcon(imgPath + daevo.getImg()));
				adv.getJtfName().setText(daevo.getName());
				adv.getJtfTel().setText(daevo.getTel());
				adv.getJtfEmail().setText(daevo.getEmail());
				adv.getJtfRank().setText(daevo.getRank());
				adv.getJtfLoc().setText(daevo.getLoc());
				adv.getJtfEdu().setText(daevo.getEducation());
				adv.getJtfAge().setText(String.valueOf(daevo.getAge()));
				adv.getJtfPort().setText(daevo.getPortfolio());
				adv.getJtfGender().setText(daevo.getGender());

				switch (daevo.getApp_status()) {
				case "U": // 지원상태를 R로 바꿔준다.
					er_dao.updateAppSatus(new ErAppStatusVO(app_num, "R"));
					break;
				case "A": // 이전에 지원상태를 응답한 경우.
					appStatusFlag = true;
					break;
				case "D": // 이전에 지원상태를 응답한 경우.
					appStatusFlag = true;
					break;
				}// end if

			} else {
				JOptionPane.showMessageDialog(adv, "등록한 구인공고에 아직 지원한 구직자가 없습니다.");
			} // end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch
	}// setInfo()

	/**
	 * 종료처리
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		adv.dispose();
	}// windowClosing(WindowEvent e)

	/**
	 * 버튼들에서 이벤트 발생시 처리할 메서드.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adv.getJbAccept()) { // 지원수락 버튼 이벤트 처리
			if (appStatusFlag) { // 이미 응답을 했을 경우.
				JOptionPane.showMessageDialog(adv, "이미 지원자에게 응답을 했습니다.");
				return;
			} // end if

			if (!appStatusFlag) {
				switch (JOptionPane.showConfirmDialog(adv, "이 지원자의 지원을 수락 하시겠습니까?\n 한번 수락 하면 되돌릴 수 없습니다.")) {
				case JOptionPane.OK_OPTION:
					changeStatusAccept();
				}// end switch
			} // end if
		} // end if

		if (e.getSource() == adv.getJbRefuse()) {// 지원거절 버튼 이벤트 처리
			if (appStatusFlag) { // 이미 응답을 했을 경우.
				JOptionPane.showMessageDialog(adv, "이미 지원자에게 응답을 했습니다.");
				return;
			} // end if

			switch (JOptionPane.showConfirmDialog(adv, "이 지원자의 지원을 거절 하시겠습니까?\n 한번 거절 하면 되돌릴 수 없습니다.")) {
			case JOptionPane.OK_OPTION:
				changeStatusRefuse();
			}// end switch
		} // end if

		if (e.getSource() == adv.getJbExtRsm()) { // 외부이력서 버튼 이벤트 처리

			try {
				extResumeDown();
			} catch (UnknownHostException e1) {
				JOptionPane.showMessageDialog(adv, "서버를 알 수 없습니다.");
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(adv, "연결 실패");
				e1.printStackTrace();
			} // end catch

		} // end if

		if (e.getSource() == adv.getJbClose()) { // 닫기 버튼 이벤트 처리
			adv.dispose();
		} // end if
	}// actionPerformed

	/**
	 * DB application 지원상태를 수락으로 변경하는 메서드.
	 */
	public void changeStatusAccept() {
		try {
			if (!er_dao.updateAppSatus(new ErAppStatusVO(app_num, "A"))) { // 정상작동 했을 경우.
				JOptionPane.showMessageDialog(adv, "이 지원자의 지원을 수락 하였습니다.");
				appStatusFlag = true;
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB에서 문제가 발생했습니다. 잠시 후 다시 이용해 주세요.");
			e.printStackTrace();
		} // end catch
	}// changeStatusAccept()

	/**
	 * DB application 지원상태를 거절으로 변경하는 메서드.
	 */
	public void changeStatusRefuse() {
		try {
			if (!er_dao.updateAppSatus(new ErAppStatusVO(app_num, "D"))) {// 정상작동 했을 경우.
				JOptionPane.showMessageDialog(adv, "이 지원자의 지원을 거절 하였습니다.");
				appStatusFlag = true;
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB에서 문제가 발생했습니다. 잠시 후 다시 이용해 주세요.");
			e.printStackTrace();
		} // end catch
	}// changeStatusAccept()

	/**
	 * 서버로 부터 지원자가 등록한 외부이력서를 다운받는 메서드.
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	public void extResumeDown() throws UnknownHostException, IOException {
		if (daevo.getExt_resume() == null) {
			JOptionPane.showMessageDialog(adv, "이 지원자가 등록한 외부이력서가 없습니다.");
			return;
		} // end if

		if (daevo.getExt_resume() != null) {

			Socket socket = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			FileOutputStream fos = null;

			try {
				socket = new Socket("211.63.89.144", 7002);
				dos = new DataOutputStream(socket.getOutputStream());

				// 서버에게 이력서파일 전송 요청 보내기.
				dos.writeUTF("ee_ext_request");
				dos.flush();

				// 서버에게 요청할 파일명 보내기.
				dos.writeUTF(daevo.getExt_resume());
				dos.flush();

				dis = new DataInputStream(socket.getInputStream());
				// System.out.println("클라이언트 "+ fileCnt+"개 받음");

				int fileCnt = 0; // 서버에서 보내오는 파일 조각의 갯수.
				int data = 0; // 서버에서 보내오는 데이터

				// 전달받을 파일 조각의 갯수
				fileCnt = dis.readInt();

				fos = new FileOutputStream("C:/dev/" + daevo.getExt_resume());

				byte[] readData = new byte[512];
				while (fileCnt > 0) {
					data = dis.read(readData); // 서버에서 전송한 파일조각을 읽어들여
					fos.write(readData, 0, data);// 생성한 파일로 기록
					fos.flush();
					fileCnt--;
				} // end while
				
				dos.writeUTF("종료되었습니다.");
				
			} finally {
				if(fos != null) {
					fos.close();
				}// end if
				if (dos != null) {
					dos.close();
				} // end if
				if (socket != null) {
					socket.close();
				} // end if
			} // end finally
		} // end if

	}// changeStatusAccept()

}// class
