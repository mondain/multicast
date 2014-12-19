package org.gregoire.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastReceiver {

	public static void main(String[] args) throws IOException {
		int port = 5004;
		String groupIp = "239.10.1.1"; // "225.4.5.6";
		InetAddress group = InetAddress.getByName(groupIp);
		int ttl = 4;
		MulticastSocket socket = null;
		try {
			socket = new MulticastSocket(port);
			socket.setTimeToLive(ttl);
			socket.joinGroup(group);
			do {
				DatagramPacket packet;
				for (int i = 0; i < 5; i++) {
				    byte[] buf = new byte[256];
				    packet = new DatagramPacket(buf, buf.length);
				    socket.receive(packet);
				    String received = new String(packet.getData());
				    System.out.println("Received: " + received);
				}
			} while (true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.leaveGroup(group);
				// And when we have finished sending data close the socket
				socket.close();
			}
		}
	}
}
