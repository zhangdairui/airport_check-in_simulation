package emoAirport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

public class simulator{
	static long overTime;
	static int CarrvRate;
	static int FarrvRate;
	static int CservRate;
	static int FservRate;
	static Setting userSetting = new Setting(overTime, CarrvRate, FarrvRate, CservRate, FservRate);
	//let user set the parameters include the duration of the simulation time, the coach average rate and average service rate, the 1st-class average arrival rate and average service rate.
	public static long setOverTime() {
		long overTime = 0;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please input the duration of the check-in. This number must be an integer. Input example: 30.");
			String lineOverTime = reader.readLine();
			overTime = Long.parseLong(lineOverTime);
		}catch(Exception e) {
			System.out.println(e);
		}
		return overTime*1000;
	}
	
	public static int setCarrvRate() {
		int CarrvRate = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Please input the coach average arrival rate.(Actual arrival times are random) This number must be an integer. Input example: 1.");
			String lineOverTime = reader.readLine();
			CarrvRate = Integer.parseInt(lineOverTime);
		}catch(IOException e) {
			System.out.println(e);
		}
		return CarrvRate;
	}
	
	public static int setFarrvRate() {
		int FarrvRate = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Please input the first-class average arrival rate.(Actual arrival times are random) This number must be an integer. Input example: 3.");
		String lineOverTime = reader.readLine();
		FarrvRate = Integer.parseInt(lineOverTime);
		}catch(IOException e) {
			System.out.println(e);
		}
		return FarrvRate;
	}
	
	public static int setCservRate() {
		int CservRate = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Please input the coach average service rate(Actual service times are random). This number must be an integer. Input example: 5.");
		String lineOverTime = reader.readLine();
		CservRate = Integer.parseInt(lineOverTime);
		}catch(IOException e) {
			System.out.println(e);
		}
		return CservRate;
	}
	
	public static int setFservRate() {
		int FservRate = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Please input the first-class average service rate(Actual service times are random). This number must be an integer. Input example: 6.");
		String lineOverTime = reader.readLine();
		FservRate = Integer.parseInt(lineOverTime);
		}catch(IOException e) {
			System.out.println(e);
		}
		return FservRate;
	}

	
	//Calculate the average waiting time for each queue

	public static double calcuCAVG() {
		long Csum = 0;
		for(long n : CwaitCalcu.everyCWait) {
			Csum += n;
		}
		long aaa = Csum / 1000;
		long people = (int) CwaitCalcu.everyCWait.size();
		double avg =(double) aaa*1.0 / people; 
		return  avg;

	}
	public static double calcuFAVG() {
		long Fsum = 0;
		for(long n : FwaitCalcu.everyFWait) {
			Fsum += n;
		}
		long aaa = Fsum / 1000;
		long people = (int) FwaitCalcu.everyFWait.size();
		double avg =(double) aaa*1.0 / people; 
		return  avg;

	}
	
	public static void main(String[] args) {
		//get user setting
		overTime = setOverTime();
		CarrvRate = setCarrvRate();
		FarrvRate = setFarrvRate();
		CservRate = setCservRate();
		FservRate = setFservRate();
		//save the parameters to the userSetting.
		userSetting.setOverTime(overTime);
		userSetting.setCarrvRate(CarrvRate);
		userSetting.setFarrvRate(FarrvRate);
		userSetting.setCservRate(CservRate);
		userSetting.setFservRate(FservRate);
		
		
		System.out.println("===========The setting parameters are as follows==========");
		System.out.println("The duration of the check-in: "+(overTime/1000)+"seconds.");
		System.out.println("The coach average arrival rate is: "+CarrvRate+" people/second.");
		System.out.println("The first-class average arrival rate is: "+FarrvRate+" people/second.");
		System.out.println("The coach average service rate is: "+CservRate+" seconds.");
		System.out.println("The first-class average service rate is: "+FservRate+" seconds.");
		System.out.println("===========Simulation Start!!!!!==========");
		
		long beginTime = System.currentTimeMillis(); //the starting time
		boolean working = true; //simulation is running
	
		CountDownLatch countDown = new CountDownLatch(1); //use countDown to wait for a Pool of Threads to Complete.
		CountDownLatch Fawait = new CountDownLatch(3); 
		CountDownLatch Cawait = new CountDownLatch(4);
		
		for(int i= 0; i<3; i++) { //prepare 3 first-class service stations.
			Fservice fservice = new Fservice(countDown, Fawait, ("The first-class station No." + i), 0); //0 is the total service time for this station.
			fservice.start();
		}
		
		for(int i= 0; i<4; i++) { //prepare 4 coach service stations.
			Cservice cservice = new Cservice(countDown, Cawait, ("The coach station No." + i), 0 ); 
			cservice.start();
		}
		
		while(working) {
			long nowTime = System.currentTimeMillis();
			FlineUp.getFqueue();
			if((nowTime - beginTime) > overTime & working) { //When time up
				lineUp.getCqueue().stop(); 
				FlineUp.getFqueue().stop();
				working = false; 
		        
		        try {
					Cawait.await(); //wait for the threads ending.
					Fawait.await(); 
					long totalTime = (System.currentTimeMillis() - beginTime)/1000; //calculate the actual duration of the simulation
					int totalint= new Long(totalTime).intValue();  
					System.out.println("=================Simulation End=================");
					
					
					//---------------------The maximum length for each queue
					int Cqmax = lineUp.getCqueue().getMaxWaitNum();
					int Fqmax = FlineUp.getFqueue().getMaxWaitNum();
					
					//---------------------Calculate the maximum waiting time and average waiting time for each queue.
					System.out.println("The average waiting time for the coach queue: " + calcuCAVG() + " seconds.");
					long max = Collections.max(CwaitCalcu.everyCWait);
					System.out.println("The maximum waiting time for the coach queue: " + max/1000 + " seconds.");
					System.out.println("The average waiting time for the 1st-class queue: " + calcuFAVG() + " seconds.");
					long Fmax = Collections.max(FwaitCalcu.everyFWait);
					System.out.println("The maximum waiting time for the 1st-class queue: " + Fmax/1000 + " seconds.");
		
					//Calculate every service station's busy rate
					for(serviceStation n: serviceStation.everyStation) {
						float a = (float)n.sumServTime / (float) totalint;
						DecimalFormat decimalFormat = new DecimalFormat(".00");//the format 0.00
						String p = decimalFormat.format(a*100.0);
						System.out.println(n.stationType + " total working time: " + n.sumServTime+ " seconds. The rate of occupancy of this station is: "+ p +"%");
					}
					
					System.out.println("The simulation duration has been set to: " + (overTime/1000) + " seconds.");
					System.out.println("Actual running duration is: " + totalint + " seconds.");
					System.out.println("The maximum length of the queue for coach queue is: " + Cqmax + " people.");
					System.out.println("The maximum length of the queue for 1st-class queue is: " + Fqmax + " people.");
		        } catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
