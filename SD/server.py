#!/usr/bin/python
# -*- coding: utf-8 -*-

import socket
import threading
import os
from collections import deque

commands_list = deque()

bd_list = deque()
log_list = deque()

bd_dict = {}


def listen_to_client(udp_ip, udp_port):

	sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	sock.bind((udp_ip, udp_port))

	while True:
		data, addr = sock.recvfrom(1024)
		data = data.decode()
		try:
			if data == 'sair':
				print('>>' + addr[0] + ' Disconnected =(')

			else:
				if data.split()[0] in ['Create', 'Retrieve', 'Update', 'Delete']:
					commands_list.append((addr, data))
				else:
					print(str(addr[0]) + ': ' + data)
		except:
			sock.sendto(('-*-  Nani?  -*-').encode(), addr)


def choose_queue():
	while True:
		if commands_list != deque([]):
			addr_data = commands_list.pop()
			data = addr_data[1]
			if data.split()[0] in ['Create', 'Update', 'Delete']:
				bd_list.append(addr_data)
				log_list.append(data)
			if data.split()[0] in ['Retrieve']:
				bd_list.append(addr_data)


def log_handle():
	while True:
		if log_list != deque([]):
			command = log_list.pop()
			command_log = open("../SD/system_log.txt", "a")
			command_log.write(command + '\n')
			command_log.close()


def file_handle(udp_ip, udp_port):

	sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	sock.bind((udp_ip, udp_port))

	while True:

		if bd_list != deque([]):

			addr_command = bd_list.pop()
			client_addr = addr_command[0]

			try:
				next_command = addr_command[1]
				string = str(next_command)
				command = str(string.split()[0])
				data = str(string.split(" ", 1)[1])

				if command in ['Create', 'Retrieve', 'Update', 'Delete']:
					if command == 'Create':

						try:
							f = open("../SD/config.txt", "r")
							file = f.readlines()
							key = int(file[3])
							f.close()
							file[3] = str(key + 1)

							bd_dict[key] = data
							f = open("../SD/config.txt", "w")
							f.writelines(file)
							f.close()

							sock.sendto(('-*-  ' + data + ' Created' + 'on key ' + str(key) + '  -*-').encode(), client_addr)
							print('>> ' + data + ' Created')
							print(bd_dict)

						except:
							sock.sendto(('-*-  Create Error  -*-').encode(), client_addr)
							print(bd_dict)

					if command == 'Update':
						try:
							update_key = data.split(":")[0]
							data_new = data.split(":", 1)[1]

							if bd_dict[int(update_key)] != None:
								bd_dict[int(update_key)] = data_new
							else:
								raise Exception('Missing Key!')

							print('>> ' + update_key + ' Updated to ' + data_new)
							sock.sendto(('-*-  ' + data + ' Updated  -*-').encode(), client_addr)
							print(bd_dict)

						except:
							sock.sendto(('-*- Update Error -*-').encode(), client_addr)
							print(bd_dict)

					if command == 'Delete':

						try:
							del bd_dict[int(data)]
							print('>> ' + data + ' Deleted')
							sock.sendto(('-*-  ' + data + ' Deleted  -*-').encode(), client_addr)
							print(bd_dict)

						except:
							sock.sendto(('-*-  Delete Error  -*-').encode(), client_addr)
							print(bd_dict)

					if command == 'Retrieve':
						print('retrieve')
						try:
							message = bd_dict[int(data.split()[0])]
							sock.sendto(('Found: \n' + message).encode(), client_addr)
							print(bd_dict)

						except:
							sock.sendto(('-*-  Retrieve Error  -*-').encode(), client_addr)
							print(bd_dict)

			except:
				sock.sendto(('-*-  File Handle Error  -*-').encode(), client_addr)
				print(bd_dict)


def system_restart():
	reset_key()
	command_log = open("../SD/system_log.txt", "r+")
	for next_command in command_log.readlines():
		string = str(next_command)
		command = str(string.split()[0])
		data = str(string.split(" ", 1)[1])

		if command in ['Create', 'Remove', 'Update', 'Delete']:
			print(bd_dict)
			if command == 'Create':
				f = open("../SD/config.txt", "r")
				file = f.readlines()
				key = int(file[3])
				f.close()
				file[3] = str(key + 1)

				bd_dict[key] = data.split('\n')[0]
				f = open("../SD/config.txt", "w")
				f.writelines(file)
				f.close()

			if command == 'Delete':
				del bd_dict[int(data)]

			if command == 'Update':
				update_key = data.split(":")[0]
				data_new = data.split(":", 1)[1]
				if bd_dict[int(update_key)] != None:
					bd_dict[int(update_key)] = data_new.split('\n')[0]

def reset_key():
	f = open("../SD/config.txt", "r")
	file = f.readlines()
	f.close()
	file[3] = '0'

	f = open("../SD/config.txt", "w")
	f.writelines(file)
	f.close()

if __name__ == "__main__":
	config = open("../SD/config.txt", "r")
	f_read = config.readlines()
	config.close()
	UDP_PORT = int(f_read[0])
	UDP_HOST = f_read[1]
	UDP_IP = f_read[2]

	print('UDP PORT: ' + str(UDP_PORT))
	print('UDP_ IP: ' + str(UDP_IP))

	commands_list = deque()

	system_restart()
	print(bd_dict)

	threading.Thread(target=listen_to_client, args=(UDP_IP, UDP_PORT)).start()
	threading.Thread(target=choose_queue, args=()).start()
	threading.Thread(target=log_handle, args=()).start()
	threading.Thread(target=file_handle, args=('127.0.0.2', UDP_PORT)).start()
