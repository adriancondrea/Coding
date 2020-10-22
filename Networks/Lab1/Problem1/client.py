__author__ = 'adi'
#import socket for all socket related primitives
import socket
# we need struct in order to be able to pack data in
# a stream of bytes so that we can actually send
# an integer as a binary four byte sequence - instead
# of a string
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# input return actually a string and we need an int
size=int(input('size= '))
elements = []
for i in range(size):
    element=int(input('element' + str(i) + '= '))
    elements.append(element)

# The obscure struct_addr is elegantly replaced by
# a simple pair - very convenient. Replace the IP Address with
# the one of your server
s.connect( ("192.168.100.4",1234) )

# pack the value of a as a short int (16 bits) in network representation
res = s.send(struct.pack("!H", size))
for i in range(size):
    res = s.send( struct.pack('!H', elements[i]))
c = s.recv(2)

# unpack the content read from the network into a short int
# and convert from network representation back to host
c = struct.unpack('!H',c)
print('sum of elements of array is=' + c[0].__format__('d'))

s.close()