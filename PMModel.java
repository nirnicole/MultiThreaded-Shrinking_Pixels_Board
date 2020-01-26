import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

//MVC model
//model class
public class PMModel {
	private int MATRIX_DIMENSION=0;    //MATRIX_DIMENSION *MATRIX_DIMENSION, inputed as n
	private int NUM_OF_PROCESSES=0;    //number of process allowd to run concurrently
	private int NUM_OF_TRANSITIONS=0;    //number of transitions on matrix
	private static boolean boardReady = false;	//locking board in prograss
	private static millingPanel[][] millingPanels;	//data panels(costum class extend jpanel)

	//constructor
	public PMModel() {
		//input matrix dimensions from user
		try {
			this.MATRIX_DIMENSION = Integer.parseInt(JOptionPane.showInputDialog("Please enter the dimension for the pixels matrix:") );    
		}catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, "Error: \nincorrect syntax for input number."); }
		
		millingPanels = new millingPanel[MATRIX_DIMENSION][MATRIX_DIMENSION];	
		for(int i=0; i<MATRIX_DIMENSION ; i++)
			for(int j=0; j<MATRIX_DIMENSION ; j++)
				millingPanels[i][j] = new millingPanel(i,j,Color.white);
			
	}//end of constructor

	//getters and setters
	//
	public  millingPanel[][] getMillingPanels()	 {return  millingPanels;}
	public void setMillingPanels( millingPanel[][] millingPanels)	 {	this.millingPanels = millingPanels;}
	public int getMATRIX_DIMENSION() 	{return MATRIX_DIMENSION;}
	public void setMATRIX_DIMENSION(int mATRIX_DIMENSION) 	{MATRIX_DIMENSION = mATRIX_DIMENSION;}
	public int getNUM_OF_PROCESSES()	 {return NUM_OF_PROCESSES;}
	public void setNUM_OF_PROCESSES(int nUM_OF_PROCESSES) 	{NUM_OF_PROCESSES = nUM_OF_PROCESSES;}
	public int getNUM_OF_TRANSITIONS() 	{return NUM_OF_TRANSITIONS;}
	public void setNUM_OF_TRANSITIONS(int nUM_OF_TRANSITIONS) 	{NUM_OF_TRANSITIONS = nUM_OF_TRANSITIONS;}
	public static boolean isBoardReady() 	{return boardReady;}
	public static void setBoardReady(boolean flag) 	{boardReady = flag;}
	
	//aka our multithreaded process
	public void millPhase() {

		System.out.println("-> starting milling ");
		ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_PROCESSES); // or 1, limit processes running concurrently to fixed number inputed
		//we want a controller to hold mainthread(millphase thread in our case, its the parent of the thread-workers we create) for all thread-workers,
		// the amount same as row count. i chose to implement my own low level api rather than use join to get use to wait and notify..
		threadController tc = new threadController(MATRIX_DIMENSION); 												
		millingThread[] tArr = new millingThread[MATRIX_DIMENSION];

		for(int line=0; line< MATRIX_DIMENSION;line++ )  
			{
			tArr[line] = new millingThread(line, line+1, tc); // new thread gets (line to work on, thread number from 1 to rowcount, thread controller).
			executor.execute(tArr[line]);	//executer handels concurrent limit.
			}
		
		executor.shutdown();
		tc.waitForThreads();	//wait() for all (count=MATRIX_DIMENSION) thread-workers to finish with our costum thread contoller
		System.out.println("-> end of milling ");
	}//end of method
	
	//our thread(worker-thread) class
	private class millingThread extends Thread {
		public int threadID=0;
		private int line;
		private threadController tController;

		//constructor
		public millingThread(int line, int threadID, threadController tController) {
			this.line = line;
			this.threadID = threadID;
			this.tController = tController;
		}
		
		@Override
		public void run() {
		
			tController.updateRunningThread();
			System.out.println("-> executing" +"\t" + "thread " + threadID + "\t" + "running threads: " + tController.getCurrentRunning());	
			
			for(int row = 0; row < MATRIX_DIMENSION ; row++)
				millingPanels[line][row].setNextUpdate(isToUpdateByArea(line,row));		//updating next-update to each cell in line submited
			
			System.out.println("-> finished  " + "\t" + "thread "+ threadID);
			tController.threadFinished();		//updates that thread has finished
		}
	}//end of thread class
	
	//private method to check cell area for next update(to simplify code in threadclass)
	private boolean isToUpdateByArea(int line, int row) {
		
		if(millingPanels[line][row].getColor().equals(Color.black))
		{
			for(int i = line-1; i<=line+1; i++ )
				for(int j = row-1; j<=row+1; j++ )
				{	//matrix boundaries.
					if(i>=0 && j>=0 && i<MATRIX_DIMENSION && j<MATRIX_DIMENSION)
						if(millingPanels[i][j].getColor().equals(Color.white))
								return false; //found white neighbor, end loop and return false.
				}
			return true; //if no neighbor found white, stays black.
		}
		return false;
	}//end of method
	
	//updating model numver of processes and transitions
	public void update() {
		try {
			NUM_OF_PROCESSES = Integer.parseInt(JOptionPane.showInputDialog("Please enter number of procceses:") ) ;    
			NUM_OF_TRANSITIONS = Integer.parseInt(JOptionPane.showInputDialog("Please enter number of transitions:") );    
		}catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, "Error: \nincorrect syntax for input number."); }
	}//end of method
	
}//end of class
