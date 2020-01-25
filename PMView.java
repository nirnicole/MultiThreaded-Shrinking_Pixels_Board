import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//MVC model
//view class
public class PMView extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel = new JPanel();
	private JPanel buttonsMainPanel = new JPanel();
	private JButton cmdGo = new JButton("Go");
	private JButton cmdClear = new JButton("Clear");
	
	//costume constructor
	public PMView(PMModel model) {
		super("Milling Pixels"); 
		this.setLayout(new BorderLayout());
		int MATRIX_DIMENSION = model.getMATRIX_DIMENSION();
		
		mainPanel.setLayout(new GridLayout(MATRIX_DIMENSION,MATRIX_DIMENSION,1,1));
		for(int i=0; i<MATRIX_DIMENSION ; i++)
			for(int j=0; j<MATRIX_DIMENSION ; j++)
				mainPanel.add(model.getMillingPanels()[i][j]);
					
		buttonsMainPanel.add(cmdGo);
		buttonsMainPanel.add(cmdClear);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(buttonsMainPanel, BorderLayout.SOUTH);
		this.setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);		
	}//end of constructor
	
	//getters and setters
	//
	public JButton getCmdGo()	 {	return cmdGo;}
	public void setCmdGo(JButton cmdGo)	 { this.cmdGo = cmdGo;}
	public JButton getCmdClear()	 {return cmdClear;}
	public void setCmdClear(JButton cmdClear) 	{this.cmdClear = cmdClear;}

}//end of class