import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class ConditionSearch extends JFrame{
	
	private String id;
	private boolean isAdmin;
	
	private JButton complete, back;
	private JTable table;
	
	private JLabel category_label, fuel_label, color_label, capacity_label, transmission_label, model_label, detailed_model_label;
	
	private JComboBox<String> category, fuel, color, capacity, transmission,model, detailed_model;
	private String[] category_data, fuel_data, color_data, capacity_data, transmission_data, model_data, detailed_model_data;
	private JTextField category_result, fuel_result, color_result, capacity_result, transmission_result, model_result, detailed_model_result;
	
	private ArrayList<String> category_result_list, fuel_result_list, color_result_list, capacity_result_list, transmission_result_list, model_result_list, detailed_model_result_list;
	
	private int start_width = 50, start_height = 20;
	
	public ConditionSearch(String id, boolean isAdmin, JTable table)
	{
		super("특정 조건 검색");
		
		this.id=id;
		this.isAdmin=isAdmin;
		this.table=table;
		
		try
		{
		   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
		}
		setLocation(800, 200);
		setSize(310, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		Handler h = new Handler();
		
		category_result_list = new ArrayList<String>();
		fuel_result_list = new ArrayList<>();
		color_result_list = new ArrayList<String>();
		capacity_result_list = new ArrayList<>();
		transmission_result_list = new ArrayList<>();
		model_result_list = new ArrayList<>();
		detailed_model_result_list = new ArrayList<String>();
		
		category_data = connection.selectCategories();
		
		String temp[] = connection.selectFuel();
		fuel_data = new String[temp.length+1];
		fuel_data[0] = "전체";
		for(int i=1;i<temp.length+1;i++)
			fuel_data[i] = temp[i-1];
		
		temp = connection.selectColor();
		color_data = new String[temp.length+1];
		color_data[0] = "전체";
		for(int i=1;i<temp.length+1;i++)
			color_data[i] = temp[i-1];
		
		temp = connection.selectCapacity();
		capacity_data = new String[temp.length+1];
		capacity_data[0] = "전체";
		for(int i=1;i<temp.length+1;i++)
			capacity_data[i] = temp[i-1];
		
		temp = connection.selectTransmission();
		transmission_data = new String[temp.length+1];
		transmission_data[0] = "전체";
		for(int i=1;i<temp.length+1;i++)
			transmission_data[i] = temp[i-1];
		
		
		
		category_label = new JLabel("카테고리: ");
		category_label.setBounds(start_width, start_height, 80, 30);
		add(category_label);
		
		category = new JComboBox<String>(category_data);
		category.setBounds(start_width, start_height+30, 100, 30);
		add(category);
		
		category_result = new JTextField("");
		category_result.setEditable(false);
		category_result.setBounds(start_width, start_height+60, 210, 30);
		add(category_result);
		
		fuel_label = new JLabel("연료: ");
		fuel_label.setBounds(start_width, start_height+90, 50, 30);
		add(fuel_label);
		
		fuel = new JComboBox<String>(fuel_data);
		fuel.setBounds(start_width, start_height+120, 100, 30);
		add(fuel);
		
		fuel_result = new JTextField("");
		fuel_result.setEditable(false);
		fuel_result.setBounds(start_width, start_height+150, 210, 30);
		add(fuel_result);
		
		color_label = new JLabel("색상: ");
		color_label.setBounds(start_width, start_height+180, 50, 30);
		add(color_label);
		
		color = new JComboBox<String>(color_data);
		color.setBounds(start_width, start_height+210, 100, 30);
		add(color);
		
		color_result = new JTextField("");
		color_result.setEditable(false);
		color_result.setBounds(start_width, start_height+240, 210, 30);
		add(color_result);
		
		capacity_label = new JLabel("배기량: ");
		capacity_label.setBounds(start_width, start_height+270, 50, 30);
		add(capacity_label);
		
		capacity = new JComboBox<String>(capacity_data);
		capacity.setBounds(start_width, start_height+300, 100, 30);
		add(capacity);
		
		capacity_result = new JTextField("");
		capacity_result.setEditable(false);
		capacity_result.setBounds(start_width, start_height+330, 210, 30);
		add(capacity_result);
		
		transmission_label = new JLabel("변속기: ");
		transmission_label.setBounds(start_width, start_height+360, 50, 30);
		add(transmission_label);
		
		transmission = new JComboBox<String>(transmission_data);
		transmission.setBounds(start_width, start_height+390, 100, 30);
		add(transmission);
		
		transmission_result = new JTextField("");
		transmission_result.setEditable(false);
		transmission_result.setBounds(start_width, start_height+420, 210, 30);
		add(transmission_result);
		
		model_label = new JLabel("모델: ");
		model_label.setBounds(start_width, start_height+450, 80, 30);
		add(model_label);
		
		temp = connection.selectModel();
		model_data = new String[temp.length+1];
		model_data[0] = "전체";
		for(int i=1;i<temp.length+1;i++)
			model_data[i] = temp[i-1];
		
		model = new JComboBox<String>(model_data);
		model.setBounds(start_width, start_height+480, 100, 30);
		add(model);
		
		model_result = new JTextField("");
		model_result.setEditable(false);
		model_result.setBounds(start_width, start_height+510, 210, 30);
		add(model_result);
		
		detailed_model_label = new JLabel("세부모델: ");
		detailed_model_label.setBounds(start_width, start_height+540, 80, 30);
		add(detailed_model_label);
		
		
		detailed_model_data = new String[1];
		detailed_model_data[0]="";
		detailed_model = new JComboBox<String>(detailed_model_data);
		detailed_model.setBounds(start_width, start_height+570, 100, 30);
		add(detailed_model);
		
		detailed_model_result = new JTextField("");
		detailed_model_result.setEditable(false);
		detailed_model_result.setBounds(start_width, start_height+600, 210, 30);
		add(detailed_model_result);
		
		
		
		complete = new JButton("검색");
		complete.setBounds(start_width, start_height+640, 100, 30);
		add(complete);
		
		back = new JButton("취소");
		back.setBounds(start_width+110, start_height+640, 100, 30);
		add(back);
		
		complete.addActionListener(h);
		back.addActionListener(h);
		
		category.addActionListener(h);
		fuel.addActionListener(h);
		color.addActionListener(h);
		transmission.addActionListener(h);
		capacity.addActionListener(h);
		model.addActionListener(h);
		detailed_model.addActionListener(h);
		
		setVisible(true);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			
			DBConnection connection = new DBConnection();
			if(event.getSource()==complete)
			{
				//TODO : TABLE에 검색 실행
				if(category_result_list.size()==0 && color_result_list.size()==0 && capacity_result_list.size()==0 && fuel_result_list.size()==0 && model_result_list.size()==0 && detailed_model_result_list.size()==0 && transmission_result_list.size()==0)
				{
					//모두 선택된게 없으면 안됨
					JOptionPane.showMessageDialog(null, "한 가지 이상의 조건을 선택해주십시오");
					return;
				}
				String[][] data = connection.voptionBy(isAdmin, category_result_list, color_result_list, capacity_result_list, fuel_result_list, model_result_list, detailed_model_result_list, transmission_result_list);
				String header[]={"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드","제조사","차종","연비(km)","변속기"};
				if(isAdmin)//관리자 모드일땐 공개여부도 추가
				{
					String[] temp2 = {"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드","공개여부","제조사","차종","연비(km)","변속기"};
					header = new String[temp2.length];
					for(int i=0;i<temp2.length;i++)
						header[i] = temp2[i];
				}
				
				DefaultTableModel tablemodel = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//편집불가
				table.setModel(tablemodel);
				
				table.setRowHeight(40);
				table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
			    table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
				
			    table.getColumn("차량 번호").setPreferredWidth(50);
			    table.getColumn("배기량(cc)").setPreferredWidth(80);
			    table.getColumn("연료").setPreferredWidth(100);
			    table.getColumn("연비(km)").setPreferredWidth(50);
			    
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				//new VehicleTable(id, isAdmin);
				dispose();
			}
			else if(event.getSource()==back)
			{
				//new VehicleTable(id, isAdmin);
				dispose();
			}
			else if(event.getSource()==category)
			{
				if(!category.getSelectedItem().toString().equals("전체"))
				{
					category_result.setText(category_result.getText()+" "+category.getSelectedItem().toString());
					category_result_list.add(category.getSelectedItem().toString());
				}
				else
				{
					category_result.setText("");
					category_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==fuel)
			{
				if(!fuel.getSelectedItem().toString().equals("전체"))
				{
					fuel_result.setText(fuel_result.getText()+" "+fuel.getSelectedItem().toString());
					fuel_result_list.add(fuel.getSelectedItem().toString());
				}
				else
				{
					fuel_result.setText("");
					fuel_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==color)
			{
				if(!color.getSelectedItem().toString().equals("전체"))
				{
					color_result.setText(color_result.getText()+" "+color.getSelectedItem().toString());
					color_result_list.add(color.getSelectedItem().toString());
				}
				else
				{
					color_result.setText("");
					color_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==capacity)
			{
				if(!capacity.getSelectedItem().toString().equals("전체"))
				{
					capacity_result.setText(capacity_result.getText()+" "+capacity.getSelectedItem().toString());
					capacity_result_list.add(capacity.getSelectedItem().toString());
				}
				else
				{
					capacity_result.setText("");
					capacity_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==transmission)
			{
				if(!transmission.getSelectedItem().toString().equals("전체"))
				{
					transmission_result.setText(transmission_result.getText()+" "+transmission.getSelectedItem().toString());
					transmission_result_list.add(transmission.getSelectedItem().toString());
				}
				else
				{
					transmission_result.setText("");
					transmission_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==model)
			{
				if(!model.getSelectedItem().toString().equals("전체"))
				{
					
					model_result.setText(model_result.getText()+" "+model.getSelectedItem().toString());
					model_result_list.add(model.getSelectedItem().toString());
					
					String[] temp = connection.selectDetailedModel(model.getSelectedItem().toString());
					detailed_model_data = new String[temp.length+1];
					detailed_model_data[0] = "전체";
					for(int i=1;i<temp.length+1;i++)
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
					
					model_result.setText("");
					detailed_model_result.setText("");
					
					model_result_list = new ArrayList<>();
					detailed_model_result_list = new ArrayList<>();
				}
			}
			else if(event.getSource()==detailed_model)
			{
				if(!detailed_model.getSelectedItem().toString().equals("전체"))
				{
					detailed_model_result.setText(detailed_model_result.getText()+" "+detailed_model.getSelectedItem().toString());
					detailed_model_result_list.add(detailed_model.getSelectedItem().toString());
					model_result.setText(model_result.getText()+" "+model.getSelectedItem().toString());
					model_result_list.add(model.getSelectedItem().toString());
				}
				else
				{
					model_result.setText("");
					model_result_list = new ArrayList<>();
					
					model.setSelectedIndex(0);
					
					detailed_model_result.setText("");
					detailed_model_result_list = new ArrayList<>();
					
				}
			}
		}
	}

}
