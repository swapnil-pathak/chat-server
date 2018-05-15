import java.io.*; 
import java.net.*; 
public class ChatClient implements Runnable 
{ 
	static Socket clientSocket=null; 
	static PrintStream os=null; 
	static BufferedReader in=null,is=null; 
	static boolean closed=false; 
	 
	public static void main(String args[]) 
	{ 
		try 
		{ 
			clientSocket=new Socket("192.168.4.27",8189); 
			in = new BufferedReader(new InputStreamReader(System.in)); 
            		os = new PrintStream(clientSocket.getOutputStream()); 
is = new BufferedReader(newInputStreamReader(clientSocket.getInputStream()));
		} 
		catch(Exception e) 
		{ 
			System.out.println(e); 
		} 
		 
		if(clientSocket != null && os!= null && is!= null) 
		{ 
			try 
			{ 
				new Thread(new ChatClient()).start(); 
				while(!closed) 
				{ 
					os.println(in.readLine()); 
				} 
				os.close(); 
				is.close(); 
				clientSocket.close(); 

			} 
			 
			catch(IOException e) 
			{ 
				System.out.println(e); 
			} 
		}	 
	} 


	public void run() 
	{ 
		String response; 
		try 
		{ 
			while((response=is.readLine())!=null) 
			{ 
				System.out.println(response); 
				if(response.trim().equals("Bye")) 
					break; 
			} 
			closed=true; 
		} 
		catch(Exception e) 
		{ 
			System.out.println(e); 
		} 
	} 
}
