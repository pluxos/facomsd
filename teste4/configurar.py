class Config():

    
    def config_connection(self,hostC='localhost',portC='12345'):
        #configura um arquivo para fazer conexões locais
        assert (str.isnumeric(portC)), "Incorrect port type"
        
        try :
            fh = open("config", 'w')
        except :
            print("Error - please try again")
        else :
            configs ="host:"+ hostC + '\n' +"port:" +portC
            fh.write(configs)
            print("Configuration file is ready")
            fh.close()


    def set_environment(self):
        #le os dados do arquivo e retorna o host e a porta configurados
        try :
            fh = open("config", 'r')
            read = fh.read()
            fh.close()
        except :
            print("File error - try to use config_connection() method")
        else :
            read = read.split('\n')
            aux = read[0].split(':')
            host = aux[1]
            aux = read[1].split(':')
            port = int(aux[1])
            return host,port  
