package pack;

import java.util.Scanner;
import java.io.*;


public class LZ {
	private String Path = "";
	
	public void set_path(String p) {
		Path = p;
	}
	public String get_path() {
		return Path;
	}
	
	public String read () { // Reads a file 
		File file = new File (Path);
		String Data = "";
		try {
		Scanner read = new Scanner(file);
		read.useDelimiter("\\Z"); // keep reading until eof
		Data = read.next();
		read.close();
		}
		
		catch (IOException  exp) {
			exp.printStackTrace();
		}
		return Data;
	}
	
	public String [] split () {
		String Data = read();
		String[] arr = new String [1000];
		int idx = 128;

		String temp = Character.toString(Data.charAt(0));
		
		for (int i = 1; i < Data.length(); i++) {	
			temp += Character.toString(Data.charAt(i));
			
			if (temp.length() > 1) {
				if (Find(arr, temp) == -1) {
					arr[idx] = temp;
					temp = "";
					temp += arr[idx].charAt(arr[idx++].length() - 1);
				}
			}
			
		}
		return arr;
	}
	
	public int Find(String[]arr, String s) {
		for(int i = 0; i < arr.length; i++) {
			if  (arr[i] == null)
				continue;
			if (arr[i].equals(s))
				return i;
		}
		return -1;
	}
	
	public void Compress() throws IOException {
		Writer file = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("C:\\Users\\amr_x\\Desktop\\Compressed.txt"), "utf-8"));
		
		String Data = read(); // get the content of the file
		String [] dic = new String[1000];
		String temp = Character.toString( Data.charAt(0) );
		int t = 128;
		
		
		
		dic[(int)Data.charAt(0)] = (Character.toString(Data.charAt(0)));
		
		int t1 = 0;
		for(int i = 1; i < Data.length(); i++) {
			String s = Character.toString( Data.charAt(i) );
			int c = Find(dic, temp + s);
			int v = Find(dic, temp);
			
			t1 = c;
			if (t1 == -1)
				t1 = v;
			
			if (c != -1) {
				temp += s; 
			}
			
			else {
				if ( v == -1) {
					if (temp.length() == 1) {
						dic[(int)temp.charAt(0)] = temp;
						v = (int)temp.charAt(0);
					}
				}
					
				file.write(v + "");
				
				dic[t++] = temp + s;
				temp = s;			
			}
		}
		file.write(t1 + "");
		
		file.close();
	}

	public int [] Read() {
		File file = new File (Path);
		String data = "";
		try {
			Scanner read = new Scanner(file);
			read.useDelimiter("\\Z"); // keep reading until eof
			data = read.next();
			read.close();
			}
			
			catch (IOException  exp) {
				exp.printStackTrace();
			}
		int arr[] = new int [data.length()], idx = 0;
		String temp = "";
		
		for(int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '1') {
				temp += Character.toString(data.charAt(i)) + Character.toString(data.charAt(++i))
				+ Character.toString(data.charAt(++i));

				arr[idx++] =  Integer.parseInt(temp);
				temp = "";
			}
			else {
				temp += Character.toString(data.charAt(i)) + Character.toString(data.charAt(++i));
				arr[idx++] = Integer.parseInt(temp);
				temp = "";
			}
				
				
		}
		return arr;
	}
	
	public void Decompress()throws IOException {
		int [] Data = Read();
		FileWriter f = new FileWriter("C:\\Users\\amr_x\\Desktop\\Decompressed.txt", true);
		BufferedWriter b = new BufferedWriter(f);
		PrintWriter o = new PrintWriter(b);
		
		Path = "C:\\Users\\amr_x\\Desktop\\Decompressed.txt";
		

		String k = Character.toString((char) Data[0]);
		o.write(k);
		o.close();
		
		String [] dic = new String [1000];
		
		dic[(int)k.charAt(0)] = k;
		String last = "";
		
		for(int i = 1; i < Data.length; i++) {
			if (Data[i] == 0)
				continue;
			FileWriter fw = new FileWriter("C:\\Users\\amr_x\\Desktop\\Decompressed.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			if ((Data[i] >= 65 && Data[i] <= 90 || (Data[i] >= 97 && Data[i] <= 122))) {
					k = Character.toString((char) Data[i]);
					dic[Data[i]] = k;
					last = k;
					out.append(k);
					out.close();
					
			}
			else {
				if (dic[Data[i]] == null) {
					String [] arr = split();
					if (arr[Data[i]] == null)
						arr[Data[i]] = last + last.charAt(0) ;
					dic[Data[i]] = arr[Data[i]];
					last = dic[Data[i]];
					out.append(dic[Data[i]]);
					out.close();
				}
				else {
					out.append(dic[Data[i]]);
					last = dic[Data[i]];
					out.close();
				}
					
			}
			
		}
		//out.close();
		

	}
}