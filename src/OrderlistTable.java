import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class OrderlistTable extends JFrame{
	
	private String id;
	private boolean isAdmin;
	private String[] header = {"거래 번호","거래 날짜","구매자ID","모델","세부모델","가격(원)","연식","연료","색상","하이브리드"};
	private String[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	
	public OrderlistTable(String id,boolean isAdmin)
	{
		super("거래 내역");
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
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		data = connection.selectOrderlist(id, isAdmin);
		
		DefaultTableModel model = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//편집불가
		
		table = new JTable(model);
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
	    table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//단일 선택모드
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100, 100, 1000, 400);
		add(scrollPane);
		
		setVisible(true);
	}

}
