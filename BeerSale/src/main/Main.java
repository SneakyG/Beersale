package main;

public class Main {

	public static void main(String[] args) {
		ThreadRun tr = new ThreadRun();
		Thread thread = new Thread(tr);
		thread.start();
	}
}
