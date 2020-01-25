import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//MVC model
//control class
public class PMController{

		private PMView view; 	//mvc view instance
		private PMModel model;	//mvc model instance
		
		//constructor
		public PMController(PMView view, PMModel model) {
			this.view = view;
			this.model = model;						
			
			ButtonListenerCMD listener = new ButtonListenerCMD();
			view.getCmdGo().addActionListener(listener);
			view.getCmdClear().addActionListener(listener);
			
			for(int i=0; i<model.getMATRIX_DIMENSION() ; i++)
				for(int j=0; j<model.getMATRIX_DIMENSION() ; j++)
					model.getMillingPanels()[i][j].addMouseListener(new MouseListenerCMD());
			
		}//end of constructor
		
		//get next update to view
		public void updateView() {
			System.out.println("-> updating matrix ");
			  
			for(int i=0; i<model.getMATRIX_DIMENSION() ; i++)
				for(int j=0; j<model.getMATRIX_DIMENSION() ; j++)
					if(model.getMillingPanels()[i][j].getNextUpdate())
							model.getMillingPanels()[i][j].setColor(Color.black);
						else model.getMillingPanels()[i][j].setColor(Color.white);

			 model.setBoardReady(false);
			 JOptionPane.showMessageDialog(null, "Phase completed!");
			 System.out.println("-> matrix updated");
			 view.repaint();
			  
		}//end of method
		
		//buttons actions
		public class ButtonListenerCMD implements ActionListener { 
			public void  actionPerformed(ActionEvent e) {
				  //set game to play
				  if(e.getSource() == view.getCmdGo()) {
					  for(int i=0; i<model.getNUM_OF_TRANSITIONS(); i++)
					  {
						  model.millPhase();
						  updateView();
						  try {
							  TimeUnit.SECONDS.sleep(1);
							  } catch (InterruptedException e1) {	e1.printStackTrace();}
					  }
					  model.setBoardReady(true);  
					 JOptionPane.showMessageDialog(null, "transitions completed,\npress clear to start over!");
				  }
				  
				  //clear board
				  if(e.getSource() == view.getCmdClear()) {
						  for(int i=0; i<model.getMATRIX_DIMENSION() ; i++)
							for(int j=0; j<model.getMATRIX_DIMENSION() ; j++)
								model.getMillingPanels()[i][j].clearPanel();
					  model.setBoardReady(false);
					  JOptionPane.showMessageDialog(null, "Clearing board!");
			  }
			  
				  view.repaint(); //anyway
			}//end of action preformed
		}//end of button listner

		//mose actions
		private class MouseListenerCMD implements MouseListener { 
			    // default constructor 
				MouseListenerCMD() { }
			    public void mousePressed(MouseEvent e) { } 
			    public void mouseReleased(MouseEvent e)  { } 
			    
			    //reset panel to original color when exit
			    public void mouseExited(MouseEvent e)  { 
		            Object source = e.getSource();
		            if(source instanceof JPanel){
		            	if(!model.isBoardReady())
		            	{
		            		millingPanel panelPressed = (millingPanel) source;
		                	panelPressed.setColor(panelPressed.getColor());
		            	}
		            }
			    	view.repaint();
			    } 
			    
			    //change panel to temp color when visited with mouse pointer
			    public void mouseEntered(MouseEvent e)  {
		            Object source = e.getSource();
		            if(source instanceof JPanel){
		            	if(!model.isBoardReady())
		            	{
		            		millingPanel panelPressed = (millingPanel) source;
		                	panelPressed.setBackground(Color.lightGray);;
		            	}
		            }
			    	view.repaint();
			    } 

			    // change panel color to opposite color when clicked
			    public void mouseClicked(MouseEvent e) 
			    { 
		            Object source = e.getSource();
		            if(source instanceof JPanel){
		            	if(!model.isBoardReady())
		            	{
		            		millingPanel panelPressed = (millingPanel) source;
		                	if(panelPressed.getColor().equals(Color.black))
		                		panelPressed.setColor(Color.white);
		                		else panelPressed.setColor(Color.black) ;
		            	}
		            }
			    	view.repaint();
			    }
		 }
		
		//show GUI
		public void showMvc()	 {	this.view.setVisible(true);}		//i chose to show the view through the controller

}//end of class