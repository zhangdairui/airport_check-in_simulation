package emoAirport;

import java.util.ArrayList;
//this class is for recording the service stations' data.
public class serviceStation {
	int sumServTime; String stationType;
	
	public serviceStation(String a, int b){ 
		stationType = a;
		sumServTime = b;
	}
	
	static ArrayList<serviceStation> everyStation= new ArrayList<serviceStation>(); 
	public static ArrayList<serviceStation> getStationServiceTime() { 
			return everyStation;
		}	

}
