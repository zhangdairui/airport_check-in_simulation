package emoAirport;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class lineUp { //This class is for coach queue.

	//A linkedlist for coach people.
	static LinkedList<CPassenger> Cqueue = new LinkedList<CPassenger>();
	
	//Some parameters
	private boolean working = true;
	private int CmaxWaitNum = 0;
	
	public boolean isWorking() {
		return working;
	}

	//get & set
	public static int getCpasNum() { 
		return Cqueue.size();
	}
	public int getMaxWaitNum() {
		return CmaxWaitNum;
	}

	//remove the first person from the queue.
	public synchronized CPassenger getCleader() { 
		if(Cqueue == null || Cqueue.size() < 1) {
			return null;
		}
		return Cqueue.removeFirst();
	}
	
	//to know the person's service time
	public synchronized int getCleaderServTime() { 
		if(Cqueue == null || Cqueue.size() < 1) {
			return -1;
		}
		return Cqueue.getFirst().getServTime();
	}
	//get the first person
	public synchronized CPassenger justGetCleader() {
		if(Cqueue == null || Cqueue.size() < 1) {
			return null;
		}
		return Cqueue.getFirst();
	}
	//A thread to produce passenger
	private class generatePassenger extends Thread{
		
		@Override
		public void run() {	
				System.out.println("Start to produce coach passenger.");
				while(working) {
					Random random = new Random();
					int a = random.nextInt(99);
						if(a >= 9 & a<100){
						    try {
						    	Random rand = new Random();
						    	//get the average rate
						    	int b = simulator.userSetting.getCarrvRate(); 
						    	int c = simulator.userSetting.getCservRate(); 
						    	
						    	//use nextGaussion to make a random number.
						    	int CarrvTime =Math.abs((int) (rand.nextGaussian()) * 1 + b); 
						    	int CservTime =Math.abs((int) (rand.nextGaussian()) * 1 + c); 
						    	
						    	long nowTime = System.currentTimeMillis(); //current time
								 System.out.println("Here comes a coach passenger.");
								 Cqueue.addLast(new CPassenger(CarrvTime,CservTime, nowTime, 0, 0));  // a new coach passenger
								 TimeUnit.SECONDS.sleep(CarrvTime); 
								 if (CmaxWaitNum < Cqueue.size()) { //update the max number
										CmaxWaitNum = Cqueue.size();
									}

							} catch (InterruptedException e) {
								e.printStackTrace();
							}    
						}
			}
				//If working = false
				System.out.println("Close the coach queue. Right now there are"+ getCpasNum() + "people in the queue.");
				
		}
	}
	private static lineUp LineUp  =  new lineUp();

	private lineUp() {
		generatePassenger q = new generatePassenger();
		q.start();
	}
	public static lineUp getCqueue() {
		return LineUp;
	}
	public void stop() {
		if (working) {
			working = false;
		}

}
}
