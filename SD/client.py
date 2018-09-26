#!/usr/bin/python
# -*- coding: utf-8 -*-

import socket
import threading
from time import sleep

config = open("./config.txt", "r")
f_read = config.readlines()

UDP_PORT = int(f_read[0])
UDP_HOST = str(f_read[1])
UDP_SERVER_IP = str(f_read[2])

usr_config = open("./usr_config.txt", "r")
USER_OFFSET = usr_config.readlines()[0]
USER_IP = '127.0.0.' + USER_OFFSET

usr_config = open("./usr_config.txt", "w")
usr_config.write(str(int(USER_OFFSET)+1))
usr_config.close()

print('UDP PORT: ' + str(UDP_PORT))
print('UDP_HOST: ' + str(UDP_HOST))
print('UDP_SERVER_IP: ' + str(UDP_SERVER_IP))

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((USER_IP, UDP_PORT))

sock.sendto('Joined in'.encode(), (UDP_SERVER_IP, UDP_PORT))
	
# Envia dados para o servidor
# "exit()" para sair - verificar


def speak():
	print('Bem Vindo!')
	while True:

		print('\n====================== Lista de Comandos ======================')
		print(' "Escolha uma das opções abaixo para continuar:')
		print(' "Create << dado a ser criado >>"')
		print(' "Retrieve << dado a ser recuperado >>"')
		print(' "Update << dado a ser atualizado : novo dado >>"')
		print(' "Delete << dado a ser apagado >>"')
		print(' "sair" - Sair')
		print('================================================================\n')

		print('Digite o seu comando: ')

		data = input()

		if len(data) > 0:
			if data.split()[0] in ['Create', 'Retrieve', 'Update', 'Delete', 'sair']:
				sock.sendto(data.encode(), (UDP_SERVER_IP, UDP_PORT))
				sleep(0.5)
				print('Aperte Enter Para Continuar')
				input()

		else:
			print('Comando inválido! Digite novamente a opção desejada!')
			sleep(0.5)
			print("Digite enter para continuar...")
			input()

		# sock.sendto(data.encode(), (UDP_SERVER_IP, UDP_PORT))

		if data == 'exit()':
			break


def listen():
	while True:
		data, addr = sock.recvfrom(1024)
		data = data.decode()
		if data == 'exit()':
			print(addr[0] + ' disconnected.')
			break
		else:
			print(data)


threading.Thread(target=listen, args=()).start()
threading.Thread(target=speak, args=()).start()
