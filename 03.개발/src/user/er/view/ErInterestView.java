package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ErInterestView extends JDialog {
	private JTable jtEeInfo;
	private DefaultTableModel dtmEeInfo;
	
	public ErInterestView(ErMainView emv) {//
			super(emv,"���� ���� ����",true);
			
			
			String[] eeColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������", "�޿�","�����"};
			dtmEeInfo=new DefaultTableModel(eeColumns, 40) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			jtEeInfo=new JTable(dtmEeInfo);
			JScrollPane jspEe=new JScrollPane(jtEeInfo);
			
			JLabel jlCount=new JLabel("�� ���� �������� �� : 00");
			
			add("Center",jspEe);
			add("North",jlCount);
			
			setBounds(100, 100, 800, 600);
			setVisible(true);
			
		}//ErHiringView
	
		public JTable getJtEeInfo() {
			return jtEeInfo;
		}

		public DefaultTableModel getDtmEeInfo() {
			return dtmEeInfo;
		}

		
		public static void main(String[] args) {
			ErMainView emv=new ErMainView();
			new ErInterestView(emv);
		}
		
	}//class

















