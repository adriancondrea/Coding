
__author__ = 'adi'

from copy import deepcopy
import socket
import threading
import random
import struct
import sys
import time

array = []
#used for keeping an array of connections to all clients. Useful when you have to send data to
#all clients
socketsArray = []
mylock = threading.Lock()
#stopping condition
emptyArray_sent = False
e = threading.Event()
e.clear()
threads = []
client_count = 0


def worker(cs):
    global mylock, emptyArray_sent, client_count, e, array, socketsArray

    my_idcount = client_count
    print('client #', client_count, 'from: ', cs.getpeername(), cs)
    message = 'Hello client #' + str(client_count) + ' ! Please send an integer N, followed by N float values !'
    cs.sendall(bytes(message, 'ascii'))

    mylock.acquire()
    socketsArray.append(cs)
    mylock.release()

    try:
        clientArraySize = cs.recv(4)
        clientArraySize = struct.unpack('!i', clientArraySize)[0]
        if clientArraySize == 0:
            mylock.acquire()
            emptyArray_sent = True
            mylock.release()
        clientArray = list()
        for i in range(clientArraySize):
            element = cs.recv(4)
            element = struct.unpack('!f', element)[0]
            clientArray.append(element)
        clientArray.sort()
        array = deepcopy(mergeArrays(array, clientArray, len(array), len(clientArray)))
        mylock.acquire()
        if emptyArray_sent:
            arrayLength = struct.pack('!i', int(len(array)))
            for sock in socketsArray:
                sock.sendall(arrayLength)
                for elem in array:
                    elem = struct.pack('!f', float(elem))
                    sock.sendall(elem)
            #set event e in order to resetSrv
            e.set()
            #clear event e in order to stop resetSrv
            e.clear()
        mylock.release()
    except socket.error as msg:
        print('Error:', msg.strerror)

#function for reseting server 
def resetSrv():
    global mylock, emptyArray_sent, threads, e, client_count, array, socketsArray
    while True:
        e.wait()
        for t in threads:
            t.join()
        print("all threads are finished now")
        e.clear()
        mylock.acquire()
        threads = []
        emptyArray_sent = False
        client_count = 0
        array.clear()
        socketsArray.clear()
        mylock.release()


if __name__ == '__main__':
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    t = threading.Thread(target=resetSrv, daemon=True)
    t.start()
    while True:
        client_socket, addrc = rs.accept()
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        client_count += 1
        t.start()
