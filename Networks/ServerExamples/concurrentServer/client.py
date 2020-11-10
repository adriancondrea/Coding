'''7.   The client reads a username and a password from the standard input. It sends the username and password to the server. The
server. The server responds with login successful or login failed. If login failed, closes the connection. '''

# we wanna go tcp with this
import socket
from time import sleep


def tcp_client_init(ip_address, port):
    # create the client socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((ip_address, port))
    return sock


def tcp_send_string(sock, string):
    print("Sending {}".format(string))
    sock.send(string.encode('ascii'))


def tcp_recv_string(sock):
    string = sock.recv(1024).decode('ascii')
    print("Received {}".format(string))
    return string


if __name__ == '__main__':
    username = input("Enter username: ")
    password = input("Enter password: ")
    socket = tcp_client_init('0.0.0.0', 1234)
    welcome_message = tcp_recv_string(socket)
    print(welcome_message)
    tcp_send_string(socket, username)
    sleep(2)
    tcp_send_string(socket, password)
    response = tcp_recv_string(socket)
    print(response)
