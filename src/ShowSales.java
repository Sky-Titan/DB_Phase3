import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ShowSales extends JFrame{
	
	private String id;
	private boolean isAdmin;
	
	private JComboBox<String> month, year, makes;
	
	private JLabel result_label,month_label, year_label, make_label;
	
	private JButton complete,back;
	
	private JTextField make_result,month_result,year_result;
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
		setSize(300, 500);
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
		
		month_label = new JLabel("����: ");
		month_label.setBounds(110, 150, 80, 30);
		add(month_label);
		
		month = new JComboBox<String>(month_data);
		month.setBounds(110, 180, 100, 30);
		add(month);
		
		month_result = new JTextField("0");
		month_result.setEditable(false);
		month_result.setBounds(110, 240, 100, 30);
		add(month_result);
		
		complete = new JButton("Ȯ��");
		complete.setBounds(110, 270, 80, 30);
		add(complete);
		
		complete.addActionListener(h);
		
		
		back = new JButton("�ڷ� ����");
		back.setBounds(110, 300, 80, 30);
		add(back);
		
		setVisible(true);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==complete)//����� ����
			{
				//TODO : DB���� ����� �˻�
			}
			else if(event.getSource()==back)
			{
				dispose();
			}
		}
	}
}

