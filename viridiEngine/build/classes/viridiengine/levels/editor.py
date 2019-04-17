import os
import sys
import subprocess

import numpy
dir_path = os.path.dirname(os.path.realpath(__file__))
out_file = "2.txt"
f = open(dir_path + out_file,"w+")
if sys.platform[:3] == 'win':
    import msvcrt
    def getkey():
        key = msvcrt.getch()
        return key
elif sys.platform[:3] == 'lin':
    import termios
    TERMIOS = termios
 
    def getkey():
        fd = sys.stdin.fileno()
        old = termios.tcgetattr(fd)
        new = termios.tcgetattr(fd)
        new[3] = new[3] & ~TERMIOS.ICANON & ~TERMIOS.ECHO
        new[6][TERMIOS.VMIN] = 1
        new[6][TERMIOS.VTIME] = 0
        termios.tcsetattr(fd, TERMIOS.TCSANOW, new)
        c = None
        try:
            c = os.read(fd, 1)
        finally:
            termios.tcsetattr(fd, TERMIOS.TCSAFLUSH, old)
        return c

done = 0
round = False
va = ["|", "*", "-", "*"]

xd = 15
yd = 15


x = 2
y = 2

velx = 0
vely = 1



def write(y = 0, x = 0, p = numpy.chararray((y, x)), op = ""):
	f = open(op,"w+")
	t = ""
	ins = []
	yp = 0
	xp = 0
	print(p)
	print("found")
	print(zip(*numpy.where(p == "#")))
	ins = zip(*numpy.where(p == "#"))
	for i in ins:
		y = ""
		x = ""
		y = int(i[0]) - 7
		y = y * - 1
		x = int(i[1]) - 7
		d = str(1) + "," + str(y) + ":" + str(x) + "!"
		print(d)
		f.write(d)
	f.close()
	sys.exit()


s = []
pg = numpy.ndarray((yd, xd))
pg[:] = "0"
#pg = numpy.ndarray(shape=(yd,xd), dtype='string', order='F')

#for i in range(xd):
#	pg[i] = "i"

print(type(pg))
def syscmd(cmd, encoding=''):
    """
    Runs a command on the system, waits for the command to finish, and then
    returns the text output of the command. If the command produces no text
    output, the command's return code will be returned instead.
    """
    p = subprocess.Popen(cmd, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT,
        close_fds=True)
    p.wait()
    output = p.stdout.read()
    if len(output) > 1:
        if encoding: return output.decode(encoding)
        else: return output
    return p.returncode

def update(c = "", x = 0):
	if sys.platform[:3] == 'win':
		os.system("cls")
	elif sys.platform[:3] == 'lin':
		os.system("clear")
	a = 0
	print(c)
#	for i in c:
#		for z in i:
#			if a == x:
#				a = 0
#				print(z)
#			else:
#				print(z, end="")
#		a = a + 1
#	print(c)


temp = "0"
#numpy.chararray((yd, xd))
last = numpy.ndarray((yd, xd))
last[:] = "0"
while done == 0:
	
	pg[y, x] = last[y, x]
	
	round = round + 1
	if round >= 4:
		round = 0
	
	input_char = getkey()
	s = list(str(input_char))
	print(s)
	temp = pg[y, x]
	if sys.platform[:3] == 'win':
		s = s[2]
	if sys.platform[:3] == 'lin':
		s = s[0]
#	print(s)
	
	if s == "x":
		done = 1
	elif s == "l":
		pg[y, x] = last[y, x]
		write(yd, xd, pg, dir_path + out_file)
	elif s == "w":
		y = y - 1
	elif s == "s":
		y = y + 1
	elif s == "a":
		x = x - 1
	elif s == "d":
		x = x + 1
	
	if s == "r":
		if last[y, x] == "3":
			last[y, x] = "0"
		else:
			last[y, x] = "3"
	if x < 0:
		x = 0
	if x > xd - 1:
		x = xd -1
	if y < 0:
		y = 0
	if y > yd - 1:
		y = yd - 1
		
		
#	pg[:] = 
#	try:
	pg[y, x] = "5"
#	except Exception as d:
#		print("a wild error appeared: ")
#		print(d)
	update(str(pg), xd)
	print(str(input_char))
	print(vely)

input("exit code(" + str(done) + "), please press ENTER to teminate...")