import javax.swing.JFrame;
import javax.swing.UIManager;

public class VehicleTable extends JFrame{
	
	private String id;
	private boolean isAdmin;
	
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
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		
		setVisible(true);
	}

}
