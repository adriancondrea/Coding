import sys
import socket
import select
group_port = int(sys.argv[1])
group_leader = int(sys.argv[2])

if group_leader == 1:
    pass
else:
