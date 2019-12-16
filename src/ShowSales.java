import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ShowSales extends JFrame{
	
	private String id;
	private boolean isAdmin;
	
	private JComboBox<String> month, year, makes, year2;
	
	private JLabel result_label,month_label, year_label, make_label;
	
	private JButton back,complete;
	
	private JTextField total_result;
	private String[] month_data = {"��ü","1","2","3","4","5","6","7","8","9","10","11","12"};
	private String[] year_data;
	private String[] make_data;
	
	public ShowSales(String id,boolean isAdmin)
	{
		super("����� ����");
		this.id = id;
		this.isAdmin = isAdmin;
		
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 230);
		setSize(300, 360);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		Handler h = new Handler();
		
		make_label = new JLabel("������: ");
		make_label.setBounds(110, 30, 80, 30);
		add(make_label);
		
		make_data=connection.selectMakes();
		
		
		makes = new JComboBox<String>(make_data);
		makes.setBounds(110, 60, 100, 30);
		add(makes);

		
		year_label = new JLabel("������: ");
		year_label.setBounds(110, 90, 80, 30);
		add(year_label);
		
		ArrayList<String> years = new ArrayList<String>();
		years.add("��ü");
		for(int i = 1950; i<=2019;i++)
			years.add(i+"");
		
		year = new JComboBox<String>((String[])years.toArray(new String[years.size()]));
		year.setBounds(110, 120, 100, 30);
		add(year);
		
		year.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(year.getSelectedItem().toString().equals("��ü"))
				{
					month_data = new String[1];
					month_data[0]="";
					
					DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(month_data);
					month.setModel(comboBoxModel);
				}
				else
				{
					month_data = new String[13];
					month_data[0] = "��ü";
					for(int i=1;i<13;i++)
					{
						month_data[i]=i+"";
					}
					
					DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(month_data);
					month.setModel(comboBoxModel);
				}
			}
		});
		
		month_label = new JLabel("����: ");
		month_label.setBounds(110, 150, 80, 30);
		add(month_label);
		
		
		month_data = new String[1];
		month_data[0]="";
		month = new JComboBox<String>(month_data);
		month.setBounds(110, 180, 100, 30);
		add(month);
		
		total_result = new JTextField("0");
		total_result.setEditable(false);
		total_result.setBounds(110, 210, 100, 30);
		add(total_result);
		
		complete = new JButton("�Ϸ�");
		complete.setBounds(110, 240, 80, 30);
		add(complete);
		complete.addActionListener(h);
		
		back = new JButton("�ڷΰ���");
		back.setBounds(110, 270, 80, 30);
		add(back);
		
		back.addActionListener(h);
		
		
		setVisible(true);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			DBConnection connection = new DBConnection();
			if(event.getSource()==back)
			{
				new OrderlistTable(id, isAdmin);
				dispose();
			}
			else if(event.getSource()==complete)
			{
				//����� �̱�
				long result=0;
				String makes_result=makes.getSelectedItem().toString();
				String year_result=year.getSelectedItem().toString();
				String month_result=month.getSelectedItem().toString();
				if(!month.getSelectedItem().toString().equals("��ü"))
				{
					if(!month_result.equals(""))
					{if(Integer.parseInt(month.getSelectedItem().toString()) < 10)
					{
						month_result = "0"+month.getSelectedItem().toString();
					}
					}
				}
				
				if(makes_result.equals("��ü") && year_result.equals("��ü") && month_result.equals("��ü")) //�´� ���þ������� �����޽���
				{
					JOptionPane.showMessageDialog(null, "�ɼ��� �ּ� �Ѱ� �̻� �������ּ���");
					return;
				}
				else if(!makes_result.equals("��ü") && !year_result.equals("��ü") && !month_result.equals("��ü"))//���� ����
				{
					result = connection.selectTotalsalesBy(year_result, month_result, makes_result);
					total_result.setText(result+"");
				}
				else if(makes_result.equals("��ü") && !year_result.equals("��ü") && !month_result.equals("��ü"))//make ����x
				{
					result = connection.selectTotalsalesByYearMonth(year_result, month_result);
					total_result.setText(result+"");
				}
				else if(!makes_result.equals("��ü") && !year_result.equals("��ü") && month_result.equals("��ü"))// month ����x
				{
					result = connection.selectTotalsalesBy(year_result, makes_result);
					total_result.setText(result+"");
				}
				else if(!makes_result.equals("��ü") && year_result.equals("��ü") && month_result.equals("")) // year,month ����x
				{
					result = connection.selectTotalsalesBy(makes_result);
					total_result.setText(result+"");
				}
				else if(makes_result.equals("��ü") && !year_result.equals("��ü") && month_result.equals("��ü")) // make, month ����x
				{
					result = connection.selectTotalsalesByYear(year_result);
					total_result.setText(result+"");
				}
				
			}
		}
	}
}

