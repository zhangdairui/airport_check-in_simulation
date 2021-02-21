package emoAirport;

public class Setting {
	//This class is for recording the user's setting.
	
	private long overTime;
	private int CarrvRate, FarrvRate, CservRate, FservRate;
	public Setting(long a, int b, int c, int d, int e) {
		overTime = a;
		CarrvRate = b;
		FarrvRate = c;
		CservRate = d;
		FservRate = e;
	}
	public long getOverTime() { 
		return overTime;
	}
	public int getCarrvRate() { 
		return CarrvRate;
	}
	public int getFarrvRate() { 
		return FarrvRate;
	}
	public int getCservRate() {
		return CservRate;
	}
	public int getFservRate() { 
		return FservRate;
	}
	public void setOverTime(long setOverTime) {
		overTime = setOverTime;
	}
	public void setCarrvRate(int setCarrvRate) {
		CarrvRate = setCarrvRate;
	}
	public void setFarrvRate(int setFarrvRate) {
		FarrvRate = setFarrvRate;
	}
	public void setCservRate(int setCservRate) {
		CservRate = setCservRate;
	}
	public void setFservRate(int setFservRate) {
		FservRate = setFservRate;
	}
}
