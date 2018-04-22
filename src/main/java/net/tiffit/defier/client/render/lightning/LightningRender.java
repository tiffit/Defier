package net.tiffit.defier.client.render.lightning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.math.Vec3d;

public class LightningRender {

	private static Random rand = new Random();
	
	public Vec3d start;
	public Vec3d end;
	public int color = 0xffffff;
	public int bendsMin = 2;
	public int bendsMax = 3;
	public double maxDeviation = 1;
	
	public List<LightningSegment> segments = new ArrayList<LightningSegment>();
	
	public LightningRender(Vec3d start, Vec3d end){
		this.start = start;
		this.end = end;
	}
	
	public void calculate(){
		int bends = bendsMin == bendsMax ? bendsMin : rand.nextInt(bendsMax - bendsMin) + bendsMin;
		Vec3d newStart = new Vec3d(0, 0, 0);
		Vec3d oldRandom = new Vec3d(0, 0, 0);
		for(int i = 1; i <= bends; i++){
			Vec3d random = new Vec3d(Math.random()*maxDeviation, Math.random()*maxDeviation, Math.random()*maxDeviation);
			if(i == bends)random = new Vec3d(0, 0, 0);
			Vec3d end = fromPos(1/(double)bends).add(random).subtract(oldRandom);
			LightningSegment segment = new LightningSegment(end, newStart.add(start), color);
			newStart = end.add(newStart);
			oldRandom = random;
			segments.add(segment);
		}
	}
	
	public void render(){
		for(int i = 0; i < segments.size(); i++){
			segments.get(i).render();
		}
		//RenderUtils.renderLine(new Vec3d(0, 0, 0), new Vec3d(x, y, z), start, color);
	}
	
	private Vec3d fromPos(double pos){
		double x = end.x*pos;
		double y = end.y*pos;
		double z = end.z*pos;
		return new Vec3d(x, y, z);
	}
	
}
