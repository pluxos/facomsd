import map_pb2


def create(db, key, data=None):
    if key not in db:
        db[key] = data
        return map_pb2.Response(ack=True, data=map_pb2.Data(key=key, value=db[key]))
    else:
        return map_pb2.Response(ack=False, data={})


def retrieve(db, key, data=None):
    if key in db:
        print('key: '+str(key))
        return map_pb2.Response(ack=True, data=map_pb2.Data(key=key, value=db[key]))
    else:
        return map_pb2.Response(ack=False, data={})


def update(db, key, data=None):
    if key in db:
        db[key] = data
        return map_pb2.Response(ack=True, data=map_pb2.Data(key=key, value=db[key]))
    else:
        return map_pb2.Response(ack=False, data={})


def delete(db, key, data=None):
    if key in db:
        del db[key]
        return map_pb2.Response(ack=True, data={})
    else:
        return map_pb2.Response(ack=False, data={})
