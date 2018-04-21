import application.*;

public class Main {

	public static void main(String[] args) {
		Thread thread = new Thread(new GameFrame(new World()));
		thread.start();
	}
}
