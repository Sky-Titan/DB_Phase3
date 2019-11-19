import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javafx.scene.chart.PieChart.Data;

public class SignUp extends JFrame{

	private JLabel id_label, pw_label, pw_confirm_label, kind_label, fname_label, lname_label, phone_label;//필수 입력
	private JLabel bdate_label, gender_label, job_label, country_label, city_label, detail_address_label;//추가 입력
	private JTextField id, fname, lname, phone, job, country, city, detail_address;
	private JComboBox<String> kind, gender, b_year, b_month, b_day;
	private JPasswordField  pw, pw_confirm;//비밀번호
	private String account_kind[] = {"Admin","Customer"};

	private int start_width = 50, start_height = 20;

	private JButton complete, cancel;
	
	public SignUp()
	{
		super("회원가입");
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 30);
		//getContentPane().setBackground(Color.white);
		setSize(310, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		
		id_label = new JLabel("ID : (*)");
		id_label.setBounds(start_width, start_height, 50, 30);
		add(id_label);
		
		id = new JTextField(15);
		id.setBounds(start_width, start_height+30, 210, 30);
		add(id);
		
		pw_label = new JLabel("비밀번호 : (*)");
		pw_label.setBounds(start_width, start_height+60, 70, 30);
		add(pw_label);
		
		pw = new JPasswordField(15);
		pw.setBounds(start_width, start_height+90, 210, 30);
		add(pw);
		
		pw_confirm_label = new JLabel("비밀번호 확인 : (*)");
		pw_confirm_label.setBounds(start_width, start_height+120, 100, 30);
		add(pw_confirm_label);
		
		pw_confirm = new JPasswordField(15);
		pw_confirm.setBounds(start_width, start_height+150, 210, 30);
		add(pw_confirm);
		
		kind_label = new JLabel("계정 종류 : (*)");
		kind_label.setBounds(start_width, start_height+180, 80, 30);
		add(kind_label);
		
		kind = new JComboBox<String>(account_kind);
		kind.setBounds(start_width, start_height+210, 100, 30);
		add(kind);
		
		fname_label = new JLabel("이름 : (*)");
		fname_label.setBounds(start_width, start_height+240, 80, 30);
		add(fname_label);
		
		fname = new JTextField(15);
		fname.setBounds(start_width, start_height+270, 210, 30);
		add(fname);
		
		lname_label = new JLabel("성 : (*)");
		lname_label.setBounds(start_width, start_height+300, 80, 30);
		add(lname_label);
		
		lname = new JTextField(15);
		lname.setBounds(start_width, start_height+330, 210, 30);
		add(lname);
		
		phone_label = new JLabel("휴대폰 번호 : (*)");
		phone_label.setBounds(start_width, start_height+360, 100, 30);
		add(phone_label);
		
		phone = new JTextField(20);
		phone.setBounds(start_width, start_height+390, 210, 30);
		phone.setDocument(new IntegerDocument());
		add(phone);
		
		bdate_label = new JLabel("생년월일 : ");
		bdate_label.setBounds(start_width, start_height+420, 80, 30);
		add(bdate_label);
		
		ArrayList<String> years = new ArrayList<String>();
		years.add("");//생년월일은 추가입력정보라 입력 안할 수도 있음
		for(int i = 1950; i<=2000;i++)
			years.add(i+"");
		
		b_year = new JComboBox<String>((String[])years.toArray(new String[years.size()]));
		b_year.setBounds(start_width, start_height+450, 100, 30);
		add(b_year);
		
		String[] months = {""};
		
		b_month = new JComboBox<String>(months);
		b_month.setBounds(start_width+100, start_height+450, 50, 30);
		add(b_month);
		

		
		ArrayList<String> days = new ArrayList<>();
		days.add("");//입력 안햇을 경우
		
		
		b_day = new JComboBox<String>((String[])days.toArray(new String[days.size()]));
		b_day.setBounds(start_width+150, start_height+450, 50, 30);
		add(b_day);
		
		
		b_year.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(b_year.getSelectedIndex() != 0)//입력 했을경우
				{
					String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
					DefaultComboBoxModel model = new DefaultComboBoxModel(months);
					b_month.setModel(model);
					
					TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
					Calendar cal = Calendar.getInstance(timeZone);
					cal.set(Integer.parseInt(b_year.getSelectedItem().toString()), Integer.parseInt(b_month.getSelectedItem().toString())-1, 1);
					int end_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
					ArrayList<String> days = new ArrayList<>();
					for(int i=1;i<=end_day;i++)
					days.add(i+"");
				
					model = new DefaultComboBoxModel((String[])days.toArray(new String[days.size()]));
					b_day.setModel(model);
				}
				else//입력 안했을 경우 -""인경우
				{
					//둘다 ""로 설정
					
					ArrayList<String> months = new ArrayList<>();
					months.add("");//입력 안햇을 경우
				
					DefaultComboBoxModel model = new DefaultComboBoxModel((String[])months.toArray(new String[months.size()]));
					b_month.setModel(model);
					
					ArrayList<String> days = new ArrayList<>();
					days.add("");//입력 안햇을 경우
				
					model = new DefaultComboBoxModel((String[])days.toArray(new String[days.size()]));
					b_day.setModel(model);
				}
			}
		});
		
		b_month.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
				Calendar cal = Calendar.getInstance(timeZone);
				cal.set(Integer.parseInt(b_year.getSelectedItem().toString()), Integer.parseInt(b_month.getSelectedItem().toString())-1, 1);
				int end_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				ArrayList<String> days = new ArrayList<>();
				
				for(int i=1;i<=end_day;i++)
					days.add(i+"");
				
				DefaultComboBoxModel model = new DefaultComboBoxModel((String[])days.toArray(new String[days.size()]));
				b_day.setModel(model);
			}
		});
		
		gender_label = new JLabel("성별 : ");
		gender_label.setBounds(start_width, start_height+480, 80, 30);
		add(gender_label);
		
		String[] genders = {"","Female","Male"};
		gender = new JComboBox<String>(genders);
		gender.setBounds(start_width, start_height+510, 100, 30);
		add(gender);
		
		job_label = new JLabel("직업 : ");
		job_label.setBounds(start_width, start_height+540, 80, 30);
		add(job_label);
		
		job = new JTextField(20);
		job.setBounds(start_width, start_height+570, 210, 30);
		add(job);
		
		country_label = new JLabel("국가 : ");
		country_label.setBounds(start_width, start_height+600, 80, 30);
		add(country_label);
		
		country = new JTextField(20);
		country.setBounds(start_width, start_height+630, 210, 30);
		add(country);
		
		city_label = new JLabel("도시 : ");
		city_label.setBounds(start_width, start_height+660, 80, 30);
		add(city_label);
		
		city = new JTextField(20);
		city.setBounds(start_width, start_height+690, 210, 30);
		add(city);
		
		detail_address_label = new JLabel("주소 : ");
		detail_address_label.setBounds(start_width, start_height+720, 80, 30);
		add(detail_address_label);
		
		detail_address = new JTextField(30);
		detail_address.setBounds(start_width, start_height+750, 210, 30);
		add(detail_address);
		
		complete = new JButton("완료");
		complete.setBounds(start_width+20, start_height+790, 80, 30);
		add(complete);
		
		cancel = new JButton("취소");
		cancel.setBounds(start_width+90+20, start_height+790, 80, 30);
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
			if(event.getSource()==complete)//회원가입
			{
				String idString = id.getText();
				String pwString = pw.getText();
				String fnameString = fname.getText();
				String lnameString = lname.getText();
				String phoneString = phone.getText();
				String kindString = kind.getSelectedItem().toString();
				
				//추가입력정보
				String bdate;
				String year = b_year.getSelectedItem().toString();
				String month = b_month.getSelectedItem().toString();
				String day = b_day.getSelectedItem().toString();
				if(!year.equals("") && !month.equals("") && !day.equals(""))
				{
					if(Integer.parseInt(month) < 10)
						month = "0"+month;
					
					if(Integer.parseInt(day) < 10)
						day = "0"+day;
					
					bdate = "TO_DATE('"+year+"-"+month+"-"+day+"', 'yyyy-mm-dd')";
				}
				else
				{
					bdate = "null";
				}
				
				String genderString;
				if(!gender.getSelectedItem().toString().equals(""))
					genderString = "'"+gender.getSelectedItem().toString()+"'";
				else
					genderString = "null";
				
				String jobString;
				if(!job.getText().equals(""))
					jobString = "'"+job.getText()+"'";
				else
					jobString = "null";
				
				String countryString;
				if(!country.getText().equals(""))
					countryString = "'"+country.getText()+"'";
				else
					countryString = "null";
				
				String cityString;
				if(!city.getText().equals(""))
					cityString = "'"+city.getText()+"'";
				else
					cityString = "null";
				
				String detailAddressString;
				if(!detail_address.getText().equals(""))
					detailAddressString = "'"+detail_address.getText()+"'";
				else
					detailAddressString = "null";
				
				if(!id.getText().equals("") && !pw.getText().equals("") && !pw_confirm.getText().equals("") && !fname.getText().equals("") 
						&& !lname.getText().equals("") && !phone.getText().equals(""))//필수 정보 입력 여부 
				{
					
					if(pw.getText().equals(pw_confirm.getText()))//비번과 비번확인 같아야함
					{
						//TODO : DB에 이미 존재하는 회원인지 먼저 확인 후 가입(account에 insert) 요청
						DBConnection connection = new DBConnection();
						boolean result = connection.isMember(idString);
						if(result==false)//회원 존재 x - 가입가능
						{
							boolean result2 = connection.signUpDB(idString, pwString, fnameString, lnameString, phoneString, kindString, 
									bdate, genderString, jobString, countryString, cityString, detailAddressString);
							if(result2 == true)//회원가입 완료
							{
								//아무것도 안함
							}
							else//회원가입 실패
							{
								JOptionPane.showMessageDialog(null, "서버에러");
								return ;
							}
								
						}
						else
						{
							JOptionPane.showMessageDialog(null, "해당 id가 이미 존재합니다");
							return ;
						}
					}
					else//비번과 비번확인 다르면 경고
					{
						JOptionPane.showMessageDialog(null, "비밀번호가 같지 않습니다");
						return ;
					}
				}
				else// 필수 정보 입력 안하면 경고
				{
					JOptionPane.showMessageDialog(null, "필수 입력 정보를 입력해주세요");
					return ;
				}
			}
			else//취소 일 땐 아무것도 안함
			{
				
			}
			new Login();
			dispose();
		}
	}
}
