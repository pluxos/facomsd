class Banco(object):
    def __init__(self):
        self.dicionario = []
        
    def create(self, chave, valor):
        try:
            for ind in range(0,len(self.dicionario)): #percorre a lista
                if chave == self.dicionario[ind][0]: #se a chave já existe retorna false
                    return False
            self.dicionario.append([chave, valor]) 
            return True
        except:
            return False
        
    def read(self, chave):
        try:
            for ind in range(0,len(self.dicionario)): 
                if chave == self.dicionario[ind][0]:
                    return self.dicionario[ind][1]
            return None
        except:
            return False
    
    def update(self, chave, novoValor):
        try:
            for ind in range(0,len(self.dicionario)):
                if chave == self.dicionario[ind][0]:
                    self.dicionario[ind][1] = novoValor
                    return True
            return False
        except:
            return False

    def delete(self, chave):
        try:
            for ind in range(0,len(self.dicionario)):
                if chave == self.dicionario[ind][0]:
                    del(self.dicionario[ind])
                    return True
            return False 
        except:
            return False