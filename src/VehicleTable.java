import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class VehicleTable extends JFrame{
	
	private String id;
	private boolean isAdmin;
	private String[] header = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","������","����","����(km)","���ӱ�"};
	private String[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton back;
	private JButton buy;//���Ź�ư
	private JButton register, modify;//���� ���, ����, ����
	private JComboBox<String> make, model, detailedModel;
	
	public VehicleTable(String id, boolean isAdmin)
	{
		super("�Ź�����");
		this.id = id;
		this.isAdmin = isAdmin;
		
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(200, 200);
		//getContentPane().setBackground(Color.white);
		setSize(1600, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		back = new JButton("�ڷ� ����");
		back.setBounds(950, 30, 150, 30);
		add(back);
		
		data = connection.selectVehicles(isAdmin);
		
		if(isAdmin)//������ ����϶� �������ε� �߰�
		{
			String[] temp = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","��������","������","����","����(km)","���ӱ�"};
			header = new String[temp.length];
			for(int i=0;i<temp.length;i++)
				header[i] = temp[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//�����Ұ�
		
		table = new JTable(model);
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
	    table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		
	    table.getColumn("���� ��ȣ").setPreferredWidth(50);
	    table.getColumn("��ⷮ(cc)").setPreferredWidth(80);
	    table.getColumn("����").setPreferredWidth(100);
	    table.getColumn("����(km)").setPreferredWidth(50);
	    
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//���� ���ø��
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100, 100, 1400, 400);
		add(scrollPane);
		
		register = new JButton("���� ���");
		register.setBounds(100, 520, 150, 30);
		add(register);
		
		modify = new JButton("���� ���� ����");
		modify.setBounds(270, 520, 150, 30);
		add(modify);
		
		buy = new JButton("����");
		buy.setBounds(440, 520, 150, 30);
		add(buy);
		
		if(!isAdmin)//������϶� ��ư ����
		{
			register.setVisible(false);
			modify.setVisible(false);
		}
		
		
		Handler h = new Handler();
		back.addActionListener(h);
		register.addActionListener(h);
		modify.addActionListener(h);
		
		setVisible(true);
	}

	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==back)//�ڷΰ���
			{
				//setVisible(false);
				new AccountMenu(id, isAdmin);
				dispose();
				
			}
			else if(event.getSource()==register)//���� ���
			{
				//setVisible(false);
				new RegisterVehicle(id,isAdmin);
				dispose();
				
			}
			else if(event.getSource()==modify)//�������� ����
			{
				if(table.getSelectedRow()!=-1)
				{
					int row = table.getSelectedRow();
					String vehicleNumber = String.valueOf(table.getValueAt(row, 0));
					new ModifyVehicle(vehicleNumber, id, isAdmin);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "������ ���ϴ� ������ �������ֽʽÿ�");
					return ;
				}
			
			}
			else if(event.getSource()==buy)//����ó��
			{
				if(table.getSelectedRow()!=-1)
				{
					int row = table.getSelectedRow();
					String vehicleNumber = String.valueOf(table.getValueAt(row, 0));
					//TODO : ����ó��
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "���Ÿ� ���ϴ� ������ �������ֽʽÿ�");
					return ;
				}
			}
		}
	}
}
