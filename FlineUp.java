package emoAirport;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FlineUp { //This class is for 1st-class queue.

	//A linkedlist for 1st-class people
	LinkedList<FPassenger> Fqueue = new LinkedList<FPassenger>();//准备一个头等舱旅客队伍

	private int FmaxWaitNum = 0;//最大排队人数
	private boolean working = true;
	public boolean isWorking() {
		return working;
	}

	//get & set
	public int getFpasNum() { 
		return Fqueue.size();
	}
	public int getMaxWaitNum() {
		return FmaxWaitNum;
	}
	//remove the first person from the queue.
	public synchronized FPassenger getFleader() { 
		if(Fqueue == null || Fqueue.size() < 1) {
			return null;
		}
		return Fqueue.removeFirst();
	}
	//to know the person's service time
	public synchronized int getFleaderServTime() { 
		if(Fqueue == null || Fqueue.size() < 1) {
			return -1;
		}
		return Fqueue.getFirst().FgetServTime();
	}
	//get the first person
	public synchronized FPassenger justGetFleader() { 
		if(Fqueue == null || Fqueue.size() < 1) {
			return null;
		}
		return Fqueue.getFirst();
	}
	//A thread to produce passenger
	private class generatePassenger extends Thread{
		
		@Override
		public void run() {	
				System.out.println("Start to produce first-class passenger.");
				while(working) {
					Random random = new Random();
					int a = random.nextInt(99);
						if(a < 9){
								try {
									Random rand = new Random();
									//get the average rate
									int b = Math.abs(simulator.userSetting.getFarrvRate()); 
									int c = Math.abs(simulator.userSetting.getFservRate()); 
									//use nextGaussion to make a random number.
							    	int FarrvTime =(int) (rand.nextGaussian() * 1) + b; 
							    	int FservTime =(int) (rand.nextGaussian() * 1) + c; 
							    	long nowTime = System.currentTimeMillis(); 
							    	
									TimeUnit.SECONDS.sleep(FarrvTime); 
									System.out.println("Here comes a first-class passenger.");
									Fqueue.addLast(new FPassenger(FarrvTime,FservTime,nowTime,0,0)); 
									if (FmaxWaitNum < Fqueue.size()) { 
											FmaxWaitNum = Fqueue.size();
										}
								} catch (InterruptedException e) {
									e.printStackTrace();
								} 
								 
						}
			}
				//If working = false
				System.out.println("Close the first-class queue. Right now there are"+ getFpasNum()+"people in the queue.");
		}
	}

	private static FlineUp FLineUp  =  new FlineUp();

	private FlineUp() {
		generatePassenger q = new generatePassenger();
		q.start();
	}
	public static FlineUp getFqueue() {
		return FLineUp;
	}

	public void stop() {
		if (working) {
			working = false;
		}

}
}
