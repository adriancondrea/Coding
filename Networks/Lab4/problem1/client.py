import socket, time

if __name__ == '__main__':
    udp_ip_address = input("enter ip address to ping: ")
    udp_port_number = int(input("enter port number to ping: "))
    message = input('enter message: ')

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    print("sending ping:")
    currentTime = time.time()
    sendMessage = client_socket.sendto(str.encode(message), (udp_ip_address, udp_port_number))
    if sendMessage < 0:
        print('error pinging %s on port %s!'.format(udp_ip_address, udp_port_number))
    else:
        print('sent ping successfully!')
    print('getting message: ')
    message, address = client_socket.recvfrom(100)
    finalTime = time.time() - currentTime
    message = message.decode()
    if address[0] == udp_ip_address and address[1] == udp_port_number:
        print('got message:', message, 'from', address, 'roundtrip =', finalTime)
    else:
        print('command timed out!')