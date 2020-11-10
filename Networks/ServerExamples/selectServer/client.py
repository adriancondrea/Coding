import socket
import sys
import select

if __name__ == '__main__':
    ip_address = sys.argv[1]
    port = int(sys.argv[2])
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((ip_address, port))
    master = [sys.stdin, client_socket]
    while True:
        read_fds = master
        ready_to_read, _, _ = select.select(read_fds, [], [])
        if sys.stdin in ready_to_read:
            data = input()
            client_socket.send(bytes(data, "ascii"))
            if data == "QUIT":
                client_socket.close()
                break
        elif client_socket in ready_to_read:
            data = client_socket.recv(1024).decode("ascii")
            print(data)