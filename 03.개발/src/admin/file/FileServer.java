package admin.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FileServer extends Thread {

	private List<FileServerHelper> listHelper;
	private ServerSocket serverFile;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;

	public FileServer() {
		listHelper = new ArrayList<FileServerHelper>();
	}

	@Override
	public void run() {
		try {
			serverFile = new ServerSocket(7002); // 파일서버 7002포트

			while (true) {
				client = serverFile.accept();
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());

				FileServerHelper fsh = new FileServerHelper(listHelper, client, dis, dos);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverFile != null) {
					serverFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
