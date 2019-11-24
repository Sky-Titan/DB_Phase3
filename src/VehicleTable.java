import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private String[] header = {"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드","제조사","차종","연비(km)","변속기"};
	private String[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton back,search;
	private JButton buy;//구매버튼
	private JButton register, modify;//차량 등록, 수정, 공개
	private JComboBox<String> make, model, detailed_model;
	private String[] make_data, model_data, detailed_model_data;
	
	private JLabel make_label,model_label, detailed_model_label;
	
	public VehicleTable(String id, boolean isAdmin)
	{
		super("매물정보");
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
		
		make_label = new JLabel("제조사: ");
		make_label.setBounds(100, 30, 80, 30);
		add(make_label);
		
		make_data = connection.selectMakes();
		
		make = new JComboBox<String>(make_data);
		make.setBounds(180, 30, 100, 30);
		add(make);
		
		model_label = new JLabel("모델: ");
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
				if(!make.getSelectedItem().toString().equals("전체"))
				{
					String[] temp = connection.selectModel(make.getSelectedItem().toString());
					model_data = new String[temp.length+1];
					model_data[0]="전체";
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
				if(!model.getSelectedItem().toString().equals("전체"))
				{
					String[] temp = connection.selectDetailedModel(model.getSelectedItem().toString());
					detailed_model_data = new String[temp.length+1];
					detailed_model_data[0]="전체";
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
		
		
		search = new JButton("검색");
		search.setBounds(570, 30, 150, 30);
		add(search);
		search.addActionListener(h);
		
		back = new JButton("뒤로 가기");
		back.setBounds(950, 30, 150, 30);
		add(back);
		
		data = connection.selectVehicles(isAdmin);
		
		if(isAdmin)//관리자 모드일땐 공개여부도 추가
		{
			String[] temp2 = {"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드","공개여부","제조사","차종","연비(km)","변속기"};
			header = new String[temp2.length];
			for(int i=0;i<temp2.length;i++)
				header[i] = temp2[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//편집불가
		
		table = new JTable(model);
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
	    table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
	    table.getColumn("차량 번호").setPreferredWidth(50);
	    table.getColumn("배기량(cc)").setPreferredWidth(80);
	    table.getColumn("연료").setPreferredWidth(100);
	    table.getColumn("연비(km)").setPreferredWidth(50);
	    
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//단일 선택모드
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100, 100, 1400, 400);
		add(scrollPane);
		
		register = new JButton("차량 등록");
		register.setBounds(100, 520, 150, 30);
		add(register);
		
		modify = new JButton("차량 정보 수정");
		modify.setBounds(270, 520, 150, 30);
		add(modify);
		
		buy = new JButton("구매");
		buy.setBounds(440, 520, 150, 30);
		add(buy);
		
		if(!isAdmin)//고객모드일땐 버튼 숨김
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
			if(event.getSource()==back)//뒤로가기
			{
				//setVisible(false);
				new AccountMenu(id, isAdmin);
				dispose();
				
			}
			else if(event.getSource()==register)//차량 등록
			{
				//setVisible(false);
				new RegisterVehicle(id,isAdmin);
				dispose();
				
			}
			else if(event.getSource()==modify)//차량정보 수정
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
					JOptionPane.showMessageDialog(null, "수정을 원하는 차량을 선택해주십시오");
					return ;
				}
			
			}
			else if(event.getSource()==buy)//구매처리
			{
				if(table.getSelectedRow()!=-1)
				{
					int row = table.getSelectedRow();
					String vehicleNumber = String.valueOf(table.getValueAt(row, 0));
					//TODO : 구매처리
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "구매를 원하는 차량을 선택해주십시오");
					return ;
				}
			}
			else if(event.getSource()==search)//검색
			{
				
			}
		}
	}
}
