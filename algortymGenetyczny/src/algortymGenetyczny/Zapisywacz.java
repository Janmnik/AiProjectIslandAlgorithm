package algortymGenetyczny;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Zapisywacz {
	
	public BufferedWriter writer;
	
	public Zapisywacz(String fileName) {
		try {
		writer = new BufferedWriter(new FileWriter(fileName, true));
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
	public void WriteToFile(String str) {
		try {
			writer.append(str);
			writer.append("\n");
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	public void zamknij() {
		try {
			writer.close();
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
}