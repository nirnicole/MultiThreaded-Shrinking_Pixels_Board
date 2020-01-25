import java.awt.Color;
import javax.swing.JPanel;

//costum jpanel with adjustment for the milling board
public class millingPanel extends JPanel{
	
	private int lineIndex;
	private int columnIndex;
	private Color beckgroundColor;	//we need bouth backgrounds features to implement temp color when mouse pointer enters
	private boolean nextUpdate;		//true = black, false=white

	//costume constructor
	public millingPanel(int line , int column, Color beckgroundColor) {
		this.lineIndex = line;
		this.columnIndex = column;
		this.nextUpdate=false;
		this.beckgroundColor = beckgroundColor;
		setBackground(beckgroundColor);
	}
	
    //getters and setters
    //
	public int getLineIndex() 	{return lineIndex;}
	public int getColumnIndex() 	{return columnIndex;}
	public Color getColor() 	{return this.beckgroundColor;}
	public void setColor(Color c) 	{setBackground(c);	this.beckgroundColor = c;}	//we make sure both methods are matched
	public boolean getNextUpdate() 	{return nextUpdate;}
	public void setNextUpdate(boolean nextUpdate) 	{this.nextUpdate = nextUpdate;}
	
	//clean panel
	public void clearPanel() {
		setColor(Color.white);
		this.beckgroundColor = Color.white;
	}
	
}//end of class
