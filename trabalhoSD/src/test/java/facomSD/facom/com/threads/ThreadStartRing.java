package facomSD.facom.com.threads;

import servidor.BuildRing;

public class ThreadStartRing implements Runnable{
	private int n=0;
	private int m=0;
	
	public ThreadStartRing(int n, int m) {
		this.n = n;
		this.m = m;
	}

	@Override
	public void run() {
		BuildRing r= new BuildRing();
		r.buildRing(n, m);
	}

}
