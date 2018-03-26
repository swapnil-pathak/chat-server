# chat-server

This program writes arbitrary data from one socket to another. Clients connect to the server using 'telnet <SERVER_IP> <PORT_NUM>' and can exit using '^]'. Server accepts connections from only two clients. When clients are connected, clients can communicate using the server as middleman.
The server simply reads from one socket and writes to another socket.

To run this program, run the command 'python chat-server.py'

On client terminal, run the command 'telnet <SERVER_IP> <PORT_NUM>'

Test_Case: Connecting clients using telnet
Server output, tested on localhost
    ('Listening on port ', 13746)
    Connect using $telnet 127.0.1.1 <PORT_NUM>
    ('127.0.0.1', 51144) connected
