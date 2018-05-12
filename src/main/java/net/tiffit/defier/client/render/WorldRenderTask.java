package net.tiffit.defier.client.render;

import net.tiffit.defier.proxy.ClientProxy;

public class WorldRenderTask {

	private int remaining;
	private Runnable run;
	private int wait = 50; //1 tick (1/20 second)
	private long last_check;
	
	public boolean done = false;
	
	public WorldRenderTask(Runnable run, int time){
		this.run = run;
		this.remaining = time;
	}
	
	public boolean canRun(){
		if(last_check == 0)return true;
		return System.currentTimeMillis() - last_check >= wait;
	}
	
	public void run(){
		remaining--;
		last_check = System.currentTimeMillis();
		if(remaining == 0)done = true;
		else run.run();
	}
	
}
