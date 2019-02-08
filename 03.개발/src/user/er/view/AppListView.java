package user.er.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.ErMainVO;
import user.er.vo.ErAddVO;
import user.er.vo.ErHiringVO;
import user.er.vo.ErListVO;

@SuppressWarnings("serial")
public class AppListView extends JDialog {

	private JTable jtApp;
	private DefaultTableModel dtmApp;
	
	public AppListView(ErAppView rav, List<ErAddVO> rhvo) {
		super(rav,"�� ���� ��Ȳ",true);
	
		String[] eeInfoColumns= {"��ȣ","������ȣ","�̹���","�̸�","����","�ٹ�����","�з�","����","��Ʈ������ ����","����","������","��������"};
		dtmApp=new DefaultTableModel(eeInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtApp=new JTable(dtmApp);
		JScrollPane jspEeInfo=new JScrollPane(jtApp);
		
		JLabel jlEeInfo=new JLabel("�� ������ �� : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(880, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public JTable getJtEeInfo() {
		return jtApp;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmApp;
	}
	
/*	public static void main(String[] args) {
		ErMainVO em_vo = new ErMainVO("ad", "dsa", "sad", "sd");
		ErMainView rmv=new ErMainView(em_vo);
		List<ErListVO> rl_vo= new ArrayList<ErListVO>();
		ErAppView rav=new ErAppView(rmv,rl_vo);
		List<ErAddVO> rhvo=new ArrayList<ErAddVO>(); 
		new AppListView(rav,rhvo);
	}*/

}
