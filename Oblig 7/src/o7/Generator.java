package o7;

import java.util.Random;

import o5.Labyrint;

public class Generator{
	
	private long seed;
	private Random rand;
	
	private Generator(){
		this.rand = new Random();
		this.seed = rand.nextLong();
	}
	
	private Generator(long seed) {
		this.rand = new Random();
		this.seed = seed;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public void genSeed(long seed) {
		this.seed = rand.nextLong();
	}
	
	public void genLaburint(int width, int height) {
		this.rand.setSeed(seed);
		
		Labyrint l = new Labyrint(width, height);
		
		
		
	}
	
}
