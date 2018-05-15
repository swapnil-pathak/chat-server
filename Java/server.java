import java.io.*; 
import java.net.*; 
public class ChatServer 
{ 
	static Socket clientSocket=null; 
	static ServerSocket serverSocket=null; 

	// Server will handle max 20 clients... 
	static ClientThread t[]=new ClientThread[20]; 

	public static void main(String args[]) 
	{ 
		try 
		{ 
			// creating server socket at port 8189 

			serverSocket=new ServerSocket(8189); 
		} 
		catch(Exception e) 
		{ 
			System.out.println(e); 
		} 

		while(true)  
		{ 
	    		try 
			{ 
				clientSocket = serverSocket.accept(); 
				for(int i=0; i<20; i++) 
				{ 
					    if(t[i]==null) 
					    { 
						 //  creating threads for each client 
							 
						     t[i]=new ClientThread(clientSocket,t); 
						     t[i].start(); 
						     break; 
			                    } 
		                } 
	                } 
	                catch (IOException e) 
			{ 
				System.out.println(e); 
			}  
                } 
	} 
} 


//For Actual chat handling 
class ClientThread extends Thread 
{ 
	PrintStream os=null; 
	BufferedReader in=null; 
	Socket clientSocket=null; 
	ClientThread t[]; 
	 
	public ClientThread(Socket cs,ClientThread[] t) 
	{ 
		this.clientSocket=cs; 
		this.t=t; 
	} 

	public void run() 
	{ 
		String name; 
		String line; 
		try 
		{ 

			// creating input n output streams for each clint thread 

			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os=new PrintStream(clientSocket.getOutputStream()); 
			os.println("Enter your name:"); 
			name=in.readLine(); 
			os.println("Welcome " +name+" to the chat room!!");	 
			 
			// if new client into the chat room... informing other clients 
			for(int i=0;i<20;i++) 
				if(t[i]!=null && t[i]!=this) 
					t[i].os.println("A new user '"+name+"' has entered the chat room..."); 

			while(true) 
			{ 
				line=in.readLine(); 
				if(line.trim().equals("Bye"))  //Till user says Bye, continue to broadcast 
					break; 
				for(int i=0;i<20;i++) 
				{ 
					if(t[i]!=null) 
						t[i].os.println("<"+name+">"+line); //Taking string from in.readLine() and 
						                                     //broadcasting it to all threads	 
				} 
			} 
			 
			for(int i=0; i<20; i++) 
				if (t[i]!=null && t[i]!=this)  
				    t[i].os.println(" The user "+name+" is leaving the chat room !!!" ); 
	    
			 os.println(" Bye "+name+"....!"); 

			// clean up process so that new clients can be handled by the server 
			for(int i=0; i<20; i++) 
				if (t[i]==this) 
					 t[i]=null;  
		 
			// closing input, output streams and socket... 
			in.close(); 
	    		os.close(); 
	    		clientSocket.close(); 
		} 
		catch(IOException e) 
		{ 
			System.out.println(e); 
		}	 
	} 
}
