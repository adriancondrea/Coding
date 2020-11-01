import subprocess
import  datetime
months = {'Jan' : 1,
          'Feb' : 2,
          'Mar' : 3,
          'Apr' : 4,
          'May' : 5,
          'June': 6,
          'July': 7,
          'Aug' : 8,
          'Sept': 9,
          'Oct' :10,
          'Nov' :11,
          'Dec' :12}

rebootDates = []
currentYear = int(subprocess.check_output('date | awk \'{print $6}\'', shell=True).decode('utf-8'))
command = 'last reboot | awk \'{print $4, $5,  $6}\''
returned_output = subprocess.check_output(command, shell=True).decode("utf-8").split('\n')
for elem in returned_output:
    date = elem.split(' ')
    # year, month, day, hour, minute, second, microsecond
    rebootDates.append(datetime.datetime(currentYear, int(months[date[0]]), int(date[1]), int(date[2]))
