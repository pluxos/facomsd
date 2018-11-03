from __future__ import print_function
import sys
import threading
import time
import grpc
import servicos_pb2
import servicos_pb2_grpc
import fila
import config

class Client:
    def __init__(self):
        self.configuracao = config.Config()
        self.host = self.configuracao.getHost().strip("\n")
        self.filaRespostas = fila.Fila()
        
    def main(self):
        try:
            porta = input("Porta do Servidor: ")
            self.channel = grpc.insecure_channel(self.host+":"+porta)
            crud = threading.Thread(target=self.crud_thread, name="crud")
            crud.start()
        except:
            print("Erro ao iniciar crud_thread")

    def print_menu(self):
        print("1 - Create Ex: 1 123 ABCD")
        print("2 - Read   Ex: 2 123")
        print("3 - Update Ex: 3 123 EFGH")
        print("4 - Delete Ex: 4 123")
        print("5 - Sair   Ex: 5")

    def crud_thread(self):
        ativo = True
        self.print_menu()
        stub = servicos_pb2_grpc.RequisicaoStub(self.channel)
        ret = stub.Conectado(servicos_pb2.Hello(chave = "teste"))
        print(ret.resposta)
        while ativo:
            entrada = input("Comando: ")
            temp = entrada.split(' ',2)
            while temp[0] != '5':
                if self.validar_temp(entrada):
                    if temp[0] == '1':
                        retorno = stub.Create(servicos_pb2.CreateUpdate(chave=temp[1], valor=temp[2]))
                    elif temp[0] == '2':
                        retorno = stub.Read(servicos_pb2.ReadDelete(chave=temp[1]))
                    elif temp[0] == '3':
                        retorno = stub.Update(servicos_pb2.CreateUpdate(chave=temp[1], valor=temp[2]))
                    elif temp[0] == '4':
                        retorno = stub.Delete(servicos_pb2.ReadDelete(chave=temp[1]))
                    self.filaRespostas.insere(retorno.resposta)
                    resposta = threading.Thread(target=self.resposta_thread, name="resposta")
                    resposta.start()
                else:
                    print("Comando não reconhecido")
                entrada = input("Comando: ")
                temp = entrada.split(' ',2)
            ativo = False

    def validar_temp(self,entrada):
        msg = entrada.split(' ',2)  
        try:
            if int(msg[0]) >= 1 and int(msg[0]) <= 5 and isinstance(int(msg[1]),int):          
                return True
            else:
                return False
        except:
            return False

    def resposta_thread(self):
        while not self.filaRespostas.vazia():
            try:
                msg = self.filaRespostas.retira()
                print(msg)
            except:
                pass

if __name__ == '__main__':
    client = Client()
    client.main()