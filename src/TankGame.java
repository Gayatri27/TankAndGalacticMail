import application.GameFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TankGame implements Runnable {

	private static String ip = "localhost";
	private static int port = 9191;
	private Scanner scanner = new Scanner(System.in);
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ServerSocket serverSocket;
	private boolean connected = false;
	private Thread thread;

	public TankGame() {
		if(!connect()) {
			initializeServer();
		}

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while(!connected) {
			listenForServerRequest();
		}

		if(connected) {
			new GameFrame();
		}
	}

	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			System.out.println("Server connected to client.");
			connected = true;
		} catch (Exception e) {

		}
	}

	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			connected = true;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		if(args.length == 1) {
			// run as server
			port = Integer.parseInt(args[0]);
			System.out.println("Running as server on port " + args[0]);
		} else {
			// run as client
			ip = args[0];
			port = Integer.parseInt(args[1]);
			System.out.println("Running as client. Trying to connect to ip " + ip + " and port " + port);
		}

		new TankGame();
	}
}
