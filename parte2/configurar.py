class Config:
    
    SERVIDORES_CONECTADOS = int()
    

    def __init__(self):
        with open("config", "r") as fh:
            readed = fh.read()
            read = readed.split("\n")
            aux = read[0].split(":")
            self._NUMERO_BITS = int(aux[1])
            aux = read[1].split(":")
            self._NUMERO_SERVIDORES = int(aux[1])
            aux = read[2].split(":") 
            self.host = aux[1]
            aux = read[3].split(":")
            self.portaBase = int(aux[1])
        
        self._NUMERO_MAXIMO_CHAVE = (2 **self._NUMERO_BITS) - 1
        self.espectro_responsabilidade = (self._NUMERO_MAXIMO_CHAVE // self._NUMERO_SERVIDORES)
            
    def conectar(self,num_server):
        '''retorna três parâmetros, a conexao(host:port), o ID , a tabela de roteamento e o espectro de responsabilidade'''
        
        meu_ID = num_server
        
        
        if(meu_ID >= self._NUMERO_SERVIDORES):
            print("Nao eh possivel adicionar um novo servidor!!!")
            return None
        else:
            identificador = self._NUMERO_MAXIMO_CHAVE - (self.espectro_responsabilidade * meu_ID)
            connection = self.host + ":" + str(self.portaBase + meu_ID)
            
            id_next = (meu_ID+1) % self._NUMERO_SERVIDORES
            id_prev = (meu_ID-1) % self._NUMERO_SERVIDORES
            #identificador_next = self._NUMERO_MAXIMO_CHAVE - (self.espectro_responsabilidade * id_next)
            #identificador_prev = self._NUMERO_MAXIMO_CHAVE - (self.espectro_responsabilidade * id_prev)
            tabela_roteamento = {}
            tabela_roteamento[0] = self.host + ":" + str(self.portaBase + id_prev)
            tabela_roteamento[1] = self.host + ":" + str(self.portaBase + id_next)

            return connection, identificador , tabela_roteamento, self.espectro_responsabilidade, self._NUMERO_MAXIMO_CHAVE







    
    

                
    