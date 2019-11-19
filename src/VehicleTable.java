import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	private String[] header = {"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드"};
	private String[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton back;
	private JButton register, modify;//차량 등록, 수정, 공개
	
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
		setLocation(500, 200);
		//getContentPane().setBackground(Color.white);
		setSize(1200, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		back = new JButton("뒤로 가기");
		back.setBounds(950, 30, 150, 30);
		add(back);
		
		data = connection.selectVehicles(isAdmin);
		
		if(isAdmin)//관리자 모드일땐 공개여부도 추가
		{
			String[] temp = {"차량 번호","주행거리(km)","모델","세부모델","가격(원)","연식","연료","색상","배기량(cc)","하이브리드","공개여부"};
			header = new String[11];
			for(int i=0;i<temp.length;i++)
				header[i] = temp[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//편집불가
		
		table = new JTable(model);
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
	    table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//단일 선택모드
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100, 100, 1000, 400);
		add(scrollPane);
		
		register = new JButton("차량 등록");
		register.setBounds(100, 520, 150, 30);
		add(register);
		
		modify = new JButton("차량 정보 수정");
		modify.setBounds(270, 520, 150, 30);
		add(modify);
		
		if(!isAdmin)//고객모드일땐 버튼 숨김
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
				//setVisible(false);
				dispose();
				//TODO : 차량 정보수정
			}
		}
	}
}
