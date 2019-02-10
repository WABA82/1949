package user.ee.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.EeMainVO;
import user.ee.vo.EeAppVO;

@SuppressWarnings("serial")
public class EeAppView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;
	
	private EeAppView(EeMainView emv, List<EeAppVO> eavo) {
		super(emv,"���� ��Ȳ", true);
		
		String[] erColumns= {"��ȣ","������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","������","��������"};
		dtmApp=new DefaultTableModel(erColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp=new JTable(dtmApp);
		JScrollPane jspEeInfo=new JScrollPane(jtApp);
		
		add("Center",jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	
	
	
	public JTable getJtEr() {
		return jtApp;
	}



	public DefaultTableModel getDtmEr() {
		return dtmApp;
	}



	public static void main(String[] args) {
		EeMainVO em_vo = new EeMainVO("asd", "sdf", "fds", "fsf");
		EeMainView emv=new EeMainView(em_vo);
		List<EeAppVO> eavo=new ArrayList<EeAppVO>();
		new EeAppView(emv,eavo);
	}
	
}
