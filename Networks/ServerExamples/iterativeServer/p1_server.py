# 1.   A client sends to the server an array of numbers. Server returns the sum of the numbers
# this program uses an interative server
import select
import socket
import struct


def tcp_server_init(ip_address, port):
    return socket.create_server((ip_address, port), family=socket.AF_INET, backlog=10, reuse_port=True)


def tcp_recv_int(sock):
    number = struct.unpack('!i', sock.recv(4))[0]
    print("Received number {}".format(number))
    return number


def tcp_recv_array(sock):
    arrayLen = tcp_recv_int(sock)
    array = []
    for e in range(arrayLen):
        array.append(tcp_recv_int(sock))
    return array


def tcp_send_int(sock, number):
    print('Sending {} to {}'.format(number, sock))
    number = struct.pack('!i', number)
    sock.send(number)


def compute_sum(array):
    s = 0
    for e in array:
        s += e
    return s


if __name__ == '__main__':
    print('Waiting for incoming connections')
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    while True:
        client_socket, addrc = rs.accept()
        array = tcp_recv_array(client_socket)
        sum = compute_sum(array)
        tcp_send_int(client_socket, sum)
