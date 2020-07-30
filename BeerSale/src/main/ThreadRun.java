package main;

public class ThreadRun implements Runnable {

	@Override
	public void run() {
		new RunMain().run();
	}
}
