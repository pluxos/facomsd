# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
import grpc

import interface_pb2 as interface__pb2


class ManipulaMapaStub(object):
  # missing associated documentation comment in .proto file
  pass

  def __init__(self, channel):
    """Constructor.

    Args:
      channel: A grpc.Channel.
    """
    self.CriaItem = channel.unary_unary(
        '/ManipulaMapa/CriaItem',
        request_serializer=interface__pb2.msgItem.SerializeToString,
        response_deserializer=interface__pb2.status.FromString,
        )
    self.LeItem = channel.unary_unary(
        '/ManipulaMapa/LeItem',
        request_serializer=interface__pb2.msgItem.SerializeToString,
        response_deserializer=interface__pb2.status.FromString,
        )
    self.AtualizaItem = channel.unary_unary(
        '/ManipulaMapa/AtualizaItem',
        request_serializer=interface__pb2.msgItem.SerializeToString,
        response_deserializer=interface__pb2.status.FromString,
        )
    self.DeletaItem = channel.unary_unary(
        '/ManipulaMapa/DeletaItem',
        request_serializer=interface__pb2.msgItem.SerializeToString,
        response_deserializer=interface__pb2.status.FromString,
        )


class ManipulaMapaServicer(object):
  # missing associated documentation comment in .proto file
  pass

  def CriaItem(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def LeItem(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def AtualizaItem(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def DeletaItem(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')


def add_ManipulaMapaServicer_to_server(servicer, server):
  rpc_method_handlers = {
      'CriaItem': grpc.unary_unary_rpc_method_handler(
          servicer.CriaItem,
          request_deserializer=interface__pb2.msgItem.FromString,
          response_serializer=interface__pb2.status.SerializeToString,
      ),
      'LeItem': grpc.unary_unary_rpc_method_handler(
          servicer.LeItem,
          request_deserializer=interface__pb2.msgItem.FromString,
          response_serializer=interface__pb2.status.SerializeToString,
      ),
      'AtualizaItem': grpc.unary_unary_rpc_method_handler(
          servicer.AtualizaItem,
          request_deserializer=interface__pb2.msgItem.FromString,
          response_serializer=interface__pb2.status.SerializeToString,
      ),
      'DeletaItem': grpc.unary_unary_rpc_method_handler(
          servicer.DeletaItem,
          request_deserializer=interface__pb2.msgItem.FromString,
          response_serializer=interface__pb2.status.SerializeToString,
      ),
  }
  generic_handler = grpc.method_handlers_generic_handler(
      'ManipulaMapa', rpc_method_handlers)
  server.add_generic_rpc_handlers((generic_handler,))
