from copy import deepcopy
class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None


class Lista:
    def __init__(self):
        self.prim = None
        


def creareLista():
    lista = Lista()
    lista.prim = creareLista_rec()
    return lista

def creareLista_rec():
    x = int(input("x="))
    if x == 0:
        return None
    else:
        nod = Nod(x)
        nod.urm = creareLista_rec()
        return nod
    

def tipar(lista):
    tipar_rec(lista.prim)
    
def tipar_rec(nod):
    if nod != None:
        print (nod.e)
        tipar_rec(nod.urm)

def removeElement(list):
    newList = deepcopy(list)
    if newList.prim is not None:
        newList.prim = newList.prim.urm
    return newList


def addElement(list, e):
    newList = deepcopy(list)
    newNode = Nod(e)
    newNode.urm = newList.prim
    newList.prim = newNode
    return newList


def substituteElement(lista, i, v):
    if lista.prim is None:
        return Lista()
    elif i == 1:
        return addElement(removeElement(lista), v)
    else:
        return addElement(substituteElement(removeElement(lista), i-1, v),lista.prim.e)


def belongs(lista, element):
    if lista.prim is None:
        return False
    elif lista.prim.e == element:
        return True
    return belongs(removeElement(lista), element)


def difference(list1, list2):
    if list1.prim is None:
        return Lista()
    elif belongs(list2, list1.prim.e):
        return difference(removeElement(list1), list2)
    else:
        return addElement(difference(removeElement(list1), list2), list1.prim.e)


def testSubstitute():
    list = creareLista()
    i = int(input("enter substitution index:"))
    v = int(input("enter substitute value:"))
    tipar(substituteElement(list, i, v))
    print("END!")


def testDifference():
    list1 = creareLista()
    list2 = creareLista()
    tipar(difference(list1, list2))
    print('END!')


def main():
    testSubstitute()
    testDifference()

main() 
    
    
    
    
    