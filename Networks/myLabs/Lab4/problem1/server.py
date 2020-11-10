import socket
import threading
server_ip_address = '127.0.0.1'

def hadle_request(address):
    #create a new socket for sending request, because send/recv is blocking and we want our server to be
    #concurrent
    response_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    response_socket.sendto(b"Hello!", address)
    response_socket.close()

if __name__ == '__main__':
    server_port = int(input("enter port number: "))
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_socket.bind((server_ip_address, server_port))

    while 1:
        message, address = server_socket.recvfrom(100)

        threading.Thread(target=hadle_request, args=(address,)).start()
        print('got message: ', message.decode())
        print('sending back ping to', address[0], 'on port', address[1])
        server_socket.sendto(message, address)