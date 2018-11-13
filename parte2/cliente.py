import grpc
import definition_pb2
import definition_pb2_grpc
    


    
def run3():

    channel = grpc.insecure_channel('localhost:50000') 
    stub = definition_pb2_grpc.OperacaoStub(channel)
    mostra_menu(stub)
    



def mostra_menu(stub) :
    
    while True:
        print("-----MENU-------\n")
        print("1 - create data")
        print("2 - search data")
        print("3 - update data")
        print("4 - delete data")
        print("5 - quit system")
        response = ""
        resposta = input("Digite a opcao:")
        resposta = resposta.lower()

        if resposta == "sair" or resposta == '5':
            print("saindo...")
            break
        
        elif resposta == 'create' or resposta == '1':
            key = input("Digite a chave:")
            if not key.isdecimal() :
                print("A chave deve ser um número inteiro!")
                
            else:
                value = input("Digite os dados:")
                response = stub.Create(definition_pb2.CreateUpdateRequest(chave=key, dados=value))
    
            
        elif resposta == 'read' or resposta == '2':
            key = input("Digite a chave:")
            if not key.isdecimal :
                print("A chave deve ser um número inteiro!")
            else:
                response = stub.Read(definition_pb2.ReadDeleteRequest(chave=key))
            
        elif resposta == 'update' or resposta == '3':
            key = input("Digite a chave:")
            if not key.isdecimal() :
                print("A chave deve ser um número inteiro!")
            else:  
                value = input("Digite o novo dado:")
                response = stub.Update(definition_pb2.CreateUpdateRequest(chave=key,dados=value))
                
        elif resposta == 'delete' or resposta == '4':
            key = input("Digite a chave:")
            if not key.isdecimal() :
                print("A chave deve ser um número inteiro!")
            else :
                response = stub.Delete(definition_pb2.ReadDeleteRequest(chave=key))
        else :
            response = "Opcao Invalida"

        print(response)
   




    
if __name__ == "__main__":

    run3()


    
    
    













    

