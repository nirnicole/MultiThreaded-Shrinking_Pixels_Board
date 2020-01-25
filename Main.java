//nir nicole, mmn 15
//parrallel milling boards genarator
//main class.
public class Main{

	public static void main(String[] args) {
		
		PMModel model = new PMModel();
		PMView view = new PMView(model);
		PMController control = new PMController(view,model);
		
		control.showMvc();
		
	}//end of main

}//end of class
