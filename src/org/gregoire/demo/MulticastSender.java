package org.gregoire.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSender {

	public static void main(String[] args) {
		// Which port should we send to
		int port = 5004;
		// Which address
//		String group = "239.10.1.1"; // "225.4.5.6";
		String group = "225.4.5.6";
		// Which ttl
		int ttl = 4;
		// Create the socket but we don't bind it as we are only going to send data
		MulticastSocket socket = null;
		try {
			socket = new MulticastSocket();
			socket.setTimeToLive(ttl);
			// Note that we don't have to join the multicast group if we are only sending data and not receiving
			// Fill the buffer with some data
			byte[] buf = "Java is awesome and this is some data for bytes".getBytes();
			do {
				// Create a DatagramPacket
				DatagramPacket pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
				// Do a send. Note that send takes a byte for the ttl and not an int.
				socket.send(pack);
				System.out.println("Sent " + buf.length + " bytes");
				try {
	                Thread.sleep(1000L);
	            } catch (InterruptedException e) { 
	            }
			} while (true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				// And when we have finished sending data close the socket
				socket.close();
			}
		}
	}
}
