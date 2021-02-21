package emoAirport;

public class FPassenger {
		//Instance data
		private int arrivalTime, serviceTime, serStatus;
		private long ActualArrTime, waitTime;
		
		//Constructor to accept and initialize all instance data
		public FPassenger(int arrTime, int servTime, long ACTarrTime, long wtTime, int servStatus) {
			arrivalTime = arrTime;
			serviceTime = servTime;
			waitTime = wtTime;
			ActualArrTime = ACTarrTime;
			serStatus = servStatus;

		}
		//Getter methods
		public int FgetArrTime() {
			return arrivalTime;
		}
		public int FgetServTime() {
			return serviceTime;
		}
		public long getACTarrTime() {
			return ActualArrTime;
		}
		public int getServStatus() {
			return serStatus;
		}
		
		public long getWaitTime() {
			return waitTime;
		}

		//Setter methods
		public void FsetArrTime(int setArrTime) {
			arrivalTime = setArrTime;
		}
		public void setServiceStatus(int setServStatus) {
			serStatus = setServStatus;
		}
		public void FsetServiceTime(int setServTime) {
			serviceTime = setServTime;
		}
		public void setWaitTime(long setWaitTime) {
			waitTime = setWaitTime;
		}

	
	
}
