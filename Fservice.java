package emoAirport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

public class Fservice extends Thread {//This class is for 1st-class service station
	//get & set
	int stop = 0;
	int free = 1;
	int busy =2;
	private int serviceStatus = stop;  //busy,free,stop
	private String name;
	private int totalServiceTime;
	
	//Service station
    private final CountDownLatch countDown;
    private final CountDownLatch await;
	public Fservice(CountDownLatch countDown, CountDownLatch await, String name, int time) {
		super(name);
        this.countDown = countDown;
        this.await = await;
		this.name = name;
		this.totalServiceTime = time;
	}
	//The service thread
	@Override
	public void run() {
		serviceStatus = free;
		while(serviceStatus == free) {
			
			FPassenger firstPerson = FlineUp.getFqueue().justGetFleader(); //get the 1st person of the queue
			
			if(firstPerson!=null) {
				if(firstPerson.getServStatus() == 0) {
					firstPerson.setServiceStatus(1);
					
					long actArrTime = firstPerson.getACTarrTime(); 
					int NextServiceTime = firstPerson.FgetServTime();//see how long this person need.
					long waitTime = System.currentTimeMillis();
					long actWaitTime = waitTime - actArrTime; //see how long this person has waited.
					FwaitCalcu.everyFWait.add(actWaitTime); //Add his waiting time to the wait Array.
				

				FPassenger nowServicePerson = FlineUp.getFqueue().getFleader(); //remove this passenger from the queue.	
				if(NextServiceTime != -1) {
					try {
						System.out.println(name + " is working. Need " + NextServiceTime + " seconds to do service this time.");
						serviceStatus = busy;
						totalServiceTime += NextServiceTime; 
						TimeUnit.SECONDS.sleep(NextServiceTime);  
						serviceStatus = free;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				}else {
					continue;
				}

			}

//get the situation of the coach queue.
			//if a 1st-class service is free and the queue for the coach is not empty
			if(firstPerson == null & lineUp.getCqueue().getCpasNum()!=0) { 
				CPassenger ServiceCPerson = lineUp.getCqueue().justGetCleader(); 
				if(ServiceCPerson != null) {
					long actArrTime = ServiceCPerson.getACTarrTime(); 
					int servCtime = ServiceCPerson.getServTime();
					serviceStatus = busy;
					long CwaitTime = System.currentTimeMillis(); 
					long actWaitTime = CwaitTime - actArrTime; 
					CwaitCalcu.everyCWait.add(actWaitTime); 
					CPassenger RemoveCPerson = lineUp.getCqueue().getCleader(); 
					
					try {
						System.out.println(name + " is helping coach stations to provide services.");
						TimeUnit.SECONDS.sleep(servCtime);
						totalServiceTime += servCtime; 
						serviceStatus = free;	
					} catch (InterruptedException e) {
						e.printStackTrace();
				} 
				}
			}
			if(!FlineUp.getFqueue().isWorking()) {
				if(firstPerson == null) {
					serviceStatus = stop;
					System.out.println(name + " job finished.");
					serviceStation.getStationServiceTime().add(new serviceStation(name, totalServiceTime));
					await.countDown();
				}
				continue;
			}
		}
	}
}
