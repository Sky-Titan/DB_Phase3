import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;



public class RegisterVehicle extends JFrame{
	
	private JLabel serialnumber_label, mileage_label, model_label, detailed_model_label, price_label, model_year_label, fuel_label, color_label, capacity_label, ishybrid_label, isopen_label;
	private JTextField serialnumber, mileage, price;
	private JComboBox<String> model, detailed_model, m_year, m_month, m_day, fuel, color, capacity, ishybrid, isopen;
	private String[] model_data, detailed_model_data, fuel_data, color_data, capacity_data, ishybrid_data= {"O","X"}, isopen_data= {"O","X"};
	private JButton complete, cancel;
	
	private int start_width = 50, start_height = 20;
	private String id;
	private boolean isAdmin;
	
	public RegisterVehicle(String id, boolean isAdmin)
	{
		super("차량 매물 등록");
		
		this.id = id;
		this.isAdmin = isAdmin;
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 200);
		//getContentPane().setBackground(Color.white);
		setSize(310, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		model_data = connection.selectModel();
		//detailed_model_data = connection.selectDetailedModel(modelname);
		fuel_data = connection.selectFuel();
		color_data = connection.selectColor();
		capacity_data = connection.selectCapacity();
		
		serialnumber_label = new JLabel("차량 번호: ");
		serialnumber_label.setBounds(start_width, start_height, 80, 30);
		add(serialnumber_label);
		
		serialnumber = new JTextField(15);
		serialnumber.setBounds(start_width, start_height+30, 210, 30);
		add(serialnumber);
		
		mileage_label = new JLabel("주행거리: ");
		mileage_label.setBounds(start_width, start_height+60, 80, 30);
		add(mileage_label);
		
		mileage = new JTextField(15);
		mileage.setBounds(start_width, start_height+90, 210, 30);
		add(mileage);
		
		model_label = new JLabel("모델: ");
		model_label.setBounds(start_width, start_height+120, 80, 30);
		add(model_label);
		
		model = new JComboBox<String>(model_data);
		model.setBounds(start_width, start_height+150, 80, 30);
		add(model);
		
		detailed_model_label = new JLabel("세부모델: ");
		detailed_model_label.setBounds(start_width+80, start_height+120, 80, 30);
		add(detailed_model_label);
		
		detailed_model_data = connection.selectDetailedModel(model.getSelectedItem().toString());
		detailed_model = new JComboBox<String>(detailed_model_data);
		detailed_model.setBounds(start_width+80, start_height+150, 80, 30);
		add(detailed_model);
		
		model.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detailed_model_data = connection.selectDetailedModel(model.getSelectedItem().toString());
				DefaultComboBoxModel model = new DefaultComboBoxModel(detailed_model_data);
				detailed_model.setModel(model);
			}
		});
		
		price_label = new JLabel("가격: ");
		price_label.setBounds(start_width, start_height+180, 80, 30);
		add(price_label);
		
		price = new JTextField(15);
		price.setBounds(start_width, start_height+210, 210, 30);
		price.setDocument(new IntegerDocument());
		add(price);
		
		model_year_label = new JLabel("연식: ");
		model_year_label.setBounds(start_width, start_height+240, 80, 30);
		add(model_year_label);
		
		ArrayList<String> years = new ArrayList<String>();
		for(int i = 1950; i<=2019;i++)
			years.add(i+"");
		
		m_year = new JComboBox<String>((String[])years.toArray(new String[years.size()]));
		m_year.setBounds(start_width, start_height+270, 100, 30);
		add(m_year);
		
		String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		m_month = new JComboBox<String>(months);
		m_month.setBounds(start_width+100, start_height+270, 50, 30);
		add(m_month);
		
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
		Calendar cal = Calendar.getInstance(timeZone);
		cal.set(Integer.parseInt(m_year.getSelectedItem().toString()), Integer.parseInt(m_month.getSelectedItem().toString())-1, 1);
		int end_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	
		ArrayList<String> days = new ArrayList<>();
		for(int i=1;i<=end_day;i++)
		days.add(i+"");
		
		m_day = new JComboBox<String>((String[])days.toArray(new String[days.size()]));
		m_day.setBounds(start_width+150, start_height+270, 50, 30);
		add(m_day);
		
		m_year.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
				Calendar cal = Calendar.getInstance(timeZone);
				cal.set(Integer.parseInt(m_year.getSelectedItem().toString()), Integer.parseInt(m_month.getSelectedItem().toString())-1, 1);
				int end_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
				ArrayList<String> days = new ArrayList<>();
				for(int i=1;i<=end_day;i++)
				days.add(i+"");
			
				DefaultComboBoxModel model = new DefaultComboBoxModel((String[])days.toArray(new String[days.size()]));
				m_day.setModel(model);
			}
		});
		m_month.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
				Calendar cal = Calendar.getInstance(timeZone);
				cal.set(Integer.parseInt(m_year.getSelectedItem().toString()), Integer.parseInt(m_month.getSelectedItem().toString())-1, 1);
				int end_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
				ArrayList<String> days = new ArrayList<>();
				for(int i=1;i<=end_day;i++)
				days.add(i+"");
			
				DefaultComboBoxModel model = new DefaultComboBoxModel((String[])days.toArray(new String[days.size()]));
				m_day.setModel(model);
			}
		});
		
		fuel_label = new JLabel("연료: ");
		fuel_label.setBounds(start_width, start_height+300, 80, 30);
		add(fuel_label);
		
		fuel = new JComboBox<String>(fuel_data);
		fuel.setBounds(start_width, start_height+330, 80, 30);
		add(fuel);
		
		color_label = new JLabel("색상: ");
		color_label.setBounds(start_width+80, start_height+300, 80, 30);
		add(color_label);
		
		color = new JComboBox<String>(color_data);
		color.setBounds(start_width+80, start_height+330, 80, 30);
		add(color);
		
		capacity_label = new JLabel("배기량: ");
		capacity_label.setBounds(start_width+160, start_height+300, 80, 30);
		add(capacity_label);
		
		capacity = new JComboBox<String>(capacity_data);
		capacity.setBounds(start_width+160, start_height+330, 80, 30);
		add(capacity);
		
		
		
		ishybrid_label = new JLabel("하이브리드: ");
		ishybrid_label.setBounds(start_width, start_height+360, 80, 30);
		add(ishybrid_label);
		
		ishybrid = new JComboBox<String>(ishybrid_data);
		ishybrid.setBounds(start_width, start_height+390, 50, 30);
		add(ishybrid);
		
		if(fuel.getSelectedItem().toString().contains("+"))
			ishybrid.setSelectedIndex(0);
		else
			ishybrid.setSelectedIndex(1);
		
		fuel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(fuel.getSelectedItem().toString().contains("+"))
					ishybrid.setSelectedIndex(0);
				else
					ishybrid.setSelectedIndex(1);
			}
		});
		
		isopen_label = new JLabel("공개: ");
		isopen_label.setBounds(start_width+80, start_height+360, 80, 30);
		add(isopen_label);
		
		isopen = new JComboBox<String>(ishybrid_data);
		isopen.setBounds(start_width+80, start_height+390, 50, 30);
		add(isopen);
		
		complete = new JButton("완료");
		complete.setBounds(start_width+20, start_height+420, 80, 30);
		add(complete);
		
		cancel = new JButton("취소");
		cancel.setBounds(start_width+90+20, start_height+420, 80, 30);
		add(cancel);
		
		Handler h=new Handler();
		complete.addActionListener(h);
		cancel.addActionListener(h);
		
		setVisible(true);
	}

	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==complete)//등록완료
			{
				//TODO : VEHICLE에 INSERT
				String serialnumberString = serialnumber.getText();
				String mileageString = mileage.getText();
				String priceString = price.getText();
				
				if(!serialnumberString.equals("") && !mileageString.equals("") && !priceString.equals(""))
				{
					String mdate;
					String year=m_year.getSelectedItem().toString();
					String month = m_month.getSelectedItem().toString();
					String day = m_day.getSelectedItem().toString();
					if(Integer.parseInt(month) < 10)
						month = "0"+month;
					
					if(Integer.parseInt(day) < 10)
						day = "0"+day;
					
					mdate = "TO_DATE('"+year+"-"+month+"-"+day+"', 'yyyy-mm-dd')";
					
					String modelString = model.getSelectedItem().toString();
					String detailedmodelString = detailed_model.getSelectedItem().toString();
					String fuelString = fuel.getSelectedItem().toString();
					String colorString = color.getSelectedItem().toString();
					String capacityString = capacity.getSelectedItem().toString();
					String ishybridString;
					if(ishybrid.getSelectedIndex()==0)
						ishybridString = "1";
					else
						ishybridString = "0";
					
					String isopenString;
					
					if(isopen.getSelectedIndex()==0)
						isopenString = "1";
					else
						isopenString = "0";
					
					DBConnection connection = new DBConnection();
					
					boolean result = connection.insertVehicles(serialnumberString, mileageString, modelString, detailedmodelString, priceString, mdate, fuelString, colorString, capacityString, ishybridString, isopenString);
					if(result == true)//회원가입 완료
					{
						new VehicleTable(id, isAdmin);
						dispose();
					}
					else//회원가입 실패
					{
						JOptionPane.showMessageDialog(null, "서버에러");
						return ;
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "필수 입력 정보를 입력해주세요");
					return ;
				}
			}
			else if(event.getSource()==cancel)//뒤로가기
			{
				new VehicleTable(id, isAdmin);
				dispose();
			}
		}
		
	}
}
