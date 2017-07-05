package data_base_history;

public class History_list {
	
	//private variables
	int _id;
	String _name;
	String _phone_number;
	String _status;
	
	// Empty constructor
	public History_list(){
		
	}
	// constructor

	// constructor
	public History_list(String Status){
		this._status = Status;

	}
	// getting ID

	// getting name
	public String getStatus(){
		return this._status;
	}
	
	// setting name
	public void setStatus(String Status){
		this._status = Status;
	}
	

}
