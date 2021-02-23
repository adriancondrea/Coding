"""4.   The clients send an integer number N and an array of N float values. The server will merge sort the numbers
received from all clients until it gets an empty array of floats (N=0). The server returns to each client the size of
the merge-sorted array followed by the merge-sorted arrays of all floats from all clients. """

__author__ = 'adi'

from copy import deepcopy
import socket
import threading
import random
import struct
import sys
import time

array = []
socketsArray = []
mylock = threading.Lock()
emptyArray_sent = False
e = threading.Event()
e.clear()
threads = []
client_count = 0


# Python program to merge
# two sorted arrays

# Merge arr1[0..n1-1] and
# arr2[0..n2-1] into
# arr3[0..n1+n2-1]
def mergeArrays(arr1, arr2, n1, n2):
    arr3 = [None] * (n1 + n2)
    i = 0
    j = 0
    k = 0
    # Traverse both array
    while i < n1 and j < n2:

        # Check if current element
        # of first array is smaller
        # than current element of
        # second array. If yes,
        # store first array element
        # and increment first array
        # index. Otherwise do same
        # with second array
        if arr1[i] < arr2[j]:
            arr3[k] = arr1[i]
            k = k + 1
            i = i + 1
        else:
            arr3[k] = arr2[j]
            k = k + 1
            j = j + 1

    # Store remaining elements
    # of first array
    while i < n1:
        arr3[k] = arr1[i]
        k = k + 1
        i = i + 1
    # Store remaining elements
    # of second array
    while j < n2:
        arr3[k] = arr2[j]
        k = k + 1
        j = j + 1
    return deepcopy(arr3)


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
            e.set()
            e.clear()
        mylock.release()
    except socket.error as msg:
        print('Error:', msg.strerror)


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
