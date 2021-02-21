package emoAirport;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Cservice extends Thread { //This class is for coach service station
	//get & set
	int stop = 0;int free = 1;int busy =2;
	private int serviceStatus = stop;  //busy,free,stop
	private String name;
	private int totalServiceTime;
	
	//Service station
    private final CountDownLatch countDown;
    private final CountDownLatch await;
	public Cservice(CountDownLatch countDown, CountDownLatch await, String name, int time) {
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
			CPassenger firstPerson = lineUp.getCqueue().justGetCleader(); //get the 1st person of the queue
			if(firstPerson!=null) { 
				if(firstPerson.getServStatus() == 0) { //see whether this person has already taken by the other station.
					firstPerson.setServiceStatus(1);
					long actArrTime = firstPerson.getACTarrTime(); 
					long waitTime = System.currentTimeMillis(); 
					int NextServiceTime = firstPerson.getServTime(); //see how long this person need.
					long actWaitTime = waitTime - actArrTime; //see how long this person has waited.
					CwaitCalcu.everyCWait.add(actWaitTime); //Add his waiting time to the wait Array.

				CPassenger nowServicePerson = lineUp.getCqueue().getCleader(); //remove this passenger from the queue.	

					if(NextServiceTime != -1) {
					try {
						System.out.println(name + " is working. Need " + NextServiceTime + " seconds to do service this time.");
						serviceStatus = busy;
						totalServiceTime += NextServiceTime; 
						TimeUnit.SECONDS.sleep(NextServiceTime);  //serving.
						serviceStatus = free;
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					}
				}else {
					continue;
				}
			}	
				if(!lineUp.getCqueue().isWorking()) {
					if(lineUp.getCpasNum() == 0) {
						serviceStatus = stop;
						System.out.println(name + " job finished.");
						//Save this station's information
						serviceStation.getStationServiceTime().add(new serviceStation(name, totalServiceTime)); 
						await.countDown();
					}
					continue;
				}
			}			
		}
	}
	
