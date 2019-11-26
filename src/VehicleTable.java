import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JButton back,search,condition_search;
	private JButton buy;//���Ź�ư
	private JButton register, modify;//���� ���, ����, ����
	private JComboBox<String> make, model, detailed_model;
	private String[] make_data, model_data, detailed_model_data;
	
	private JLabel make_label,model_label, detailed_model_label;
	
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
		Handler h = new Handler();
		
		make_label = new JLabel("������: ");
		make_label.setBounds(100, 30, 80, 30);
		add(make_label);
		
		make_data = connection.selectMakes();
		
		make = new JComboBox<String>(make_data);
		make.setBounds(180, 30, 100, 30);
		add(make);
		
		model_label = new JLabel("��: ");
		model_label.setBounds(280, 30, 80, 30);
		add(model_label);
		
		
		
		model_data = new String[1];
		model_data[0]="";
		
		model = new JComboBox<String>(model_data);
		model.setBounds(360, 30, 100, 30);
		add(model);
		
		make.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!make.getSelectedItem().toString().equals("��ü"))
				{
					String[] temp = connection.selectModel(make.getSelectedItem().toString());
					model_data = new String[temp.length+1];
					model_data[0]="��ü";
					for(int i=1;i<model_data.length;i++)
						model_data[i] = temp[i-1];
					
					DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(model_data);
					model.setModel(comboBoxModel);
				}
				else
				{
					model_data = new String[1];
					model_data[0]="";
					
					DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(model_data);
					model.setModel(comboBoxModel);
					
					detailed_model_data = new String[1];
					detailed_model_data[0]="";
					
					comboBoxModel = new DefaultComboBoxModel(detailed_model_data);
					detailed_model.setModel(comboBoxModel);
				}
			}
		});
		
		
		detailed_model_data = new String[1];
		detailed_model_data[0]="";
		
		detailed_model = new JComboBox<String>(detailed_model_data);
		detailed_model.setBounds(460, 30, 100, 30);
		add(detailed_model);
		
		model.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!model.getSelectedItem().toString().equals("��ü"))
				{
					String[] temp = connection.selectDetailedModel(model.getSelectedItem().toString());
					detailed_model_data = new String[temp.length+1];
					detailed_model_data[0]="��ü";
					for(int i=1;i<model_data.length;i++)
						detailed_model_data[i] = temp[i-1];
					
					
					DefaultComboBoxModel model = new DefaultComboBoxModel(detailed_model_data);
					detailed_model.setModel(model);
				}
				else
				{
					detailed_model_data = new String[1];
					detailed_model_data[0]="";
					DefaultComboBoxModel model = new DefaultComboBoxModel(detailed_model_data);
					detailed_model.setModel(model);
					
				}
			}
		});
		
		
		search = new JButton("�˻�");
		search.setBounds(570, 30, 150, 30);
		add(search);
		search.addActionListener(h);
		
		condition_search = new JButton("���� �˻��ϱ�");
		condition_search.setBounds(730, 30, 150, 30);
		add(condition_search);
		condition_search.addActionListener(h);
		
		back = new JButton("�ڷ� ����");
		back.setBounds(950, 30, 150, 30);
		add(back);
		
		data = connection.selectVehicles(isAdmin);
		
		if(isAdmin)//������ ����϶� �������ε� �߰�
		{
			String[] temp2 = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","��������","������","����","����(km)","���ӱ�"};
			header = new String[temp2.length];
			for(int i=0;i<temp2.length;i++)
				header[i] = temp2[i];
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
		
		buy.addActionListener(h);
		
		if(!isAdmin)//������϶� ��ư ����
		{
			register.setVisible(false);
			modify.setVisible(false);
		}
		
		
		
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
			DBConnection connection = new DBConnection();
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
					
					TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
					Calendar cal = Calendar.getInstance(timeZone);
					
					String year = cal.get(Calendar.YEAR)+"";
					String month = String.valueOf(cal.get(Calendar.MONTH)+1);
					String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					System.out.println(year+" "+month+" "+day);
					String orderdate ="TO_DATE('"+year+"-"+month+"-"+day+"', 'yyyy-mm-dd')";
					String buyerId = id;
					String model = String.valueOf(table.getValueAt(row, 2));
					String detailed_model = String.valueOf(table.getValueAt(row, 3));
					String price = String.valueOf(table.getValueAt(row, 4));
					String model_year = "TO_DATE('"+String.valueOf(table.getValueAt(row, 5))+"', 'yyyy-mm-dd')";
					String fuel = String.valueOf(table.getValueAt(row, 6));
					String color = String.valueOf(table.getValueAt(row, 7));
					String ishybrid;
					
					if(String.valueOf(table.getValueAt(row, 9)).equals("O"))
						ishybrid = "1";
					else
						ishybrid = "0";
					//����ó��
					boolean result = connection.buyVehicles(vehicleNumber, orderdate, buyerId, model, detailed_model, price, model_year, fuel, color, ishybrid);
					if(result)
					{
						JOptionPane.showMessageDialog(null, "�����Ͽ����ϴ�.");
						
						//���̺� ���ΰ�ħ
						data = connection.selectVehicles(isAdmin);
						
						if(isAdmin)//������ ����϶� �������ε� �߰�
						{
							String[] temp2 = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","��������","������","����","����(km)","���ӱ�"};
							header = new String[temp2.length];
							for(int i=0;i<temp2.length;i++)
								header[i] = temp2[i];
						}
						
						DefaultTableModel tablemodel = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//�����Ұ�
						table.setModel(tablemodel);
						
						table.setRowHeight(40);
						table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
					    table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
						
					    table.getColumn("���� ��ȣ").setPreferredWidth(50);
					    table.getColumn("��ⷮ(cc)").setPreferredWidth(80);
					    table.getColumn("����").setPreferredWidth(100);
					    table.getColumn("����(km)").setPreferredWidth(50);
					    
						table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//���� ���ø��
						return ;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "���Ž���");
						return ;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "���Ÿ� ���ϴ� ������ �������ֽʽÿ�");
					return ;
				}
			}
			else if(event.getSource()==search)//�˻�
			{
				//���̺� �˻�
				if(!make.getSelectedItem().toString().equals("��ü"))
				{	
					if(!model.getSelectedItem().toString().equals("��ü"))
					{
						if(!detailed_model.getSelectedItem().toString().equals("��ü"))
						{
							data = connection.selectVehiclesByModelname(model.getSelectedItem().toString(), detailed_model.getSelectedItem().toString(), isAdmin);
						}
						else
						{
							data = connection.selectVehiclesByModelname(model.getSelectedItem().toString(), isAdmin);
						}
					}
					else
					{
						data = connection.selectVehicles(make.getSelectedItem().toString(), isAdmin);
					}
				
				}
				else
				{
					data = connection.selectVehicles(isAdmin);
				}
				
				if(isAdmin)//������ ����϶� �������ε� �߰�
				{
					String[] temp2 = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","��������","������","����","����(km)","���ӱ�"};
					header = new String[temp2.length];
					for(int i=0;i<temp2.length;i++)
						header[i] = temp2[i];
				}
				
				DefaultTableModel tablemodel = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//�����Ұ�
				table.setModel(tablemodel);
				
				table.setRowHeight(40);
				table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
			    table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
				
			    table.getColumn("���� ��ȣ").setPreferredWidth(50);
			    table.getColumn("��ⷮ(cc)").setPreferredWidth(80);
			    table.getColumn("����").setPreferredWidth(100);
			    table.getColumn("����(km)").setPreferredWidth(50);
			    
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//���� ���ø��
			}
			else if(event.getSource()==condition_search)//���Ǻ� �˻�
			{
				new ConditionSearch(id, isAdmin, table);
				make.setSelectedItem("��ü");
				data = connection.selectVehicles(isAdmin);
				if(isAdmin)//������ ����϶� �������ε� �߰�
				{
					String[] temp2 = {"���� ��ȣ","����Ÿ�(km)","��","���θ�","����(��)","����","����","����","��ⷮ(cc)","���̺긮��","��������","������","����","����(km)","���ӱ�"};
					header = new String[temp2.length];
					for(int i=0;i<temp2.length;i++)
						header[i] = temp2[i];
				}
				
				DefaultTableModel tablemodel = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//�����Ұ�
				table.setModel(tablemodel);
				
				table.setRowHeight(40);
				table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
			    table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
				
			    table.getColumn("���� ��ȣ").setPreferredWidth(50);
			    table.getColumn("��ⷮ(cc)").setPreferredWidth(80);
			    table.getColumn("����").setPreferredWidth(100);
			    table.getColumn("����(km)").setPreferredWidth(50);
			    
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
			}
		}
	}
}
