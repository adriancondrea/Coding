import struct
import socket

#TCP Connection

def tcp_create_rs():
    #create meeting socket
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    return rs

def create_client_socket(rs):
    #create client socket
    client_socket, addrc = rs.accept()
    return client_socket



#------------------------------------
def tcp_server_init(ip_address, port):
    #creates the RS socket for the server
    return socket.create_server((ip_address, port), family=socket.AF_INET, backlog=10, reuse_port=True)

def tcp_client_init(ip_address, port):
    #create the client socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((ip_address, port))
    return sock

def tcp_send_int(sock, number):
    print('Sending {} to {}'.format(number, sock))
    number = struct.pack('!i', number)
    sock.send(number)

def tcp_recv_int(sock):
    number = struct.unpack('!i', sock.recv(4))[0]
    print("Received number {}".format(number))
    return number

def tcp_send_array(sock, array):
    tcp_send_int(sock, len(array))
    for e in array:
        tcp_send_int(sock, e)

def tcp_recv_array(sock):
    arrayLen = tcp_recv_int(sock)
    array = []
    for e in range(arrayLen):
        array.append(tcp_recv_int(sock))


def tcp_send_string(sock, string):
    print("Sending {}".format(string))
    sock.send(string.encode('ascii'))

def tcp_recv_string(sock):
    string = sock.rec (1024).decode('ascii')
    print("Received {}".format(string))
    return string






#UDP
def udp_server_init(ip_address, port):
    sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
    sock.bind((ip_address, port))
    return sock

def udp_client_init():
    return socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)


def udp_send_int(sock, number, destination_address):
    print("Sending {}".format(number))
    sock.sendto(struct.pack('!i', number), destination_address)

def udp_recv_int(sock):
    number, address = sock.recvfrom(4)
    number = struct.unpack('!i', number)[0]
    print('Received number {} from {}'.format(number, address))
    return (number, address)

def udp_send_string(sock, string, destionation_address):
    print('Sending {}'.format(string))
    sock.sendto(string.encode('ascii'), destionation_address)

def udp_receive_string(sock):
    string, address = sock.recvfrom(1024)
    string = string.decode('ascii')
    print('Received {} from {}'.format(string, address))
    return (string, address)

def udp_send_array(sock, array, destination_address):
    print('sending {}'.format(array))
    sock.sendto(struct.pack('!i', len(array)), destination_address)
    for i in range(len(array)):
        sock.sendto(struct.pack('!i', array[i]), destination_address)




