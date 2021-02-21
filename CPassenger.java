package emoAirport;

public class CPassenger {
		//Instance data
		private int arrivalTime, serviceTime, serStatus;
		private long ActualArrTime, waitTime;
		
		//Constructor to accept and initialize all instance data
		public CPassenger(int arrTime, int servTime, long ACTarrTime, long wtTime, int servStatus) {
			arrivalTime = arrTime;
			serviceTime = servTime;
			ActualArrTime = ACTarrTime;
			waitTime = wtTime;
			serStatus = servStatus;
		}
		
		//Getter methods
		public int getArrTime() {
			return arrivalTime;
		}
		public int getServTime() {
			return serviceTime;
		}
		public int getServStatus() {
			return serStatus;
		}
		
		public long getACTarrTime() {
			return ActualArrTime;
		}
		
		public long getWaitTime() {
			return waitTime;
		}
		
		//Setter methods
		public void setArrTime(int setArrTime) {
			arrivalTime = setArrTime;
		}
		public void setServiceTime(int setServTime) {
			serviceTime = setServTime;
		}
		public void setServiceStatus(int setServStatus) {
			serStatus = setServStatus;
		}
		public void setWaitTime(long setWaitTime) {
			waitTime = setWaitTime;
		}

}
