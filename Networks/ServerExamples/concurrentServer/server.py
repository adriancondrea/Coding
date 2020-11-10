'''7.   The client reads a username and a password from the standard input. It sends the username and password to the server. The
server. The server responds with login successful or login failed. If login failed, closes the connection. '''
import socket
import threading

accounts = {'adi':'1121', 'mihai':'0803'}
array = []
socketsArray = []
mylock = threading.Lock()
e = threading.Event()
e.clear()
threads = []
client_count = 0

def tcp_recv_string(sock):
    string = sock.recv(1024).decode('ascii')
    print("Received {}".format(string))
    return string

def tcp_send_string(sock, string):
    print("Sending {}".format(string))
    sock.send(string.encode('ascii'))

def worker(cs):
    global mylock, client_count, e, array, socketsArray, accounts
    my_idcount = client_count
    print('client #', client_count, 'from: ', cs.getpeername(), cs)
    message = 'Hello client #' + str(client_count) + ' ! Please send your login info!'
    cs.sendall(bytes(message, 'ascii'))
    mylock.acquire()
    socketsArray.append(cs)
    mylock.release()
    username = tcp_recv_string(cs)
    password = tcp_recv_string(cs)
    if username in accounts.keys():
        if accounts[username] == password:
            tcp_send_string(cs, 'Access granted!')
    else:
        tcp_send_string(cs, 'Access denied!')




if __name__ == '__main__':
    print('Waiting for incoming connections...')
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    while True:
        client_socket, addrc = rs.accept()
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        client_count += 1
        t.start()