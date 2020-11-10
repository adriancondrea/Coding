# 1.   A client sends to the server an array of numbers. Server returns the sum of the numbers.
import socket
import struct
import sys


def tcp_client_init(ip_address, port):
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
    print('Sending {} to {}'.format(array, sock))
    print('Sending array length = {}'.format(len(array)))
    tcp_send_int(sock, len(array))
    for e in array:
        print('Sending element {}'.format(e))
        tcp_send_int(sock, e)


ip_address = '0.0.0.0'
port = 1234
sock = tcp_client_init(ip_address, port)

if __name__ == '__main__':
    array = []
    while True:
        cmd = input('Enter element, e to exit\n')
        if cmd == 'e':
            break
        try:
            cmd = int(cmd)
            array.append(cmd)
        except ValueError:
            print('enter a valid choice!')
            pass

    tcp_send_array(sock, array)
    print('sum of elements is {}'.format(tcp_recv_int(sock)))
    sock.close()
