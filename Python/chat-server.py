import sys 
import socket
import select
import random

CONN_LIST = []
BUFLEN = 8192
HOST = ''
PORT = 0
MY_IP = 0

def chat_server():

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    PORT = random.randint(1025, 65535)
    
    server_socket.bind((MY_IP, PORT))
    server_socket.listen(2)
 
    CONN_LIST.append(server_socket)
 
    print("Listening on port ",PORT)
    print("Connect using $telnet %s <PORT_NUM>" %(MY_IP))
 
    while True:
        try:
            rlist, wlist, xlist = select.select(CONN_LIST,[],[],0)
      
            for connection in rlist:
                if connection == server_socket: 
                    socketfd, addr = server_socket.accept()
                    CONN_LIST.append(socketfd)
                    print(str(addr)+" connected")
    
                else:
                    try:
                        data = connection.recv(BUFLEN)
                        if data:
                            write_to_another_socket(server_socket, connection, data)  
                        else:
                            if connection in CONN_LIST:
                                CONN_LIST.remove(connection)
    
                            write_to_another_socket(server_socket, connection, ("Connection to "+str(addr)+" lost")) 
                    except:
                        write_to_another_socket(server_socket, connection, ("Connection to "+str(addr)+" lost"))
                        continue
        except KeyboardInterrupt:
            print("Socket closed")
            break
    server_socket.close()
    
def write_to_another_socket (server_socket, connection, message):
    for socket in CONN_LIST:
        if socket != server_socket and socket != connection :
            try :
                socket.send(message)
            except :
                socket.close()
                if socket in CONN_LIST:
                    CONN_LIST.remove(socket)
 
if __name__ == "__main__":
    hostname = socket.gethostname()
    MY_IP = socket.gethostbyname(hostname)
    sys.exit(chat_server())         
