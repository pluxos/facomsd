package com.sd.etapa2.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: ServerRequest.proto")
public final class ServerRequestServiceGrpc {

  private ServerRequestServiceGrpc() {}

  public static final String SERVICE_NAME = "com.sd.etapa2.grpc.ServerRequestService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.CreateRequest,
      com.sd.etapa2.grpc.ServerRequest.CreateResponse> getCreatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "creat",
      requestType = com.sd.etapa2.grpc.ServerRequest.CreateRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.CreateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.CreateRequest,
      com.sd.etapa2.grpc.ServerRequest.CreateResponse> getCreatMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.CreateRequest, com.sd.etapa2.grpc.ServerRequest.CreateResponse> getCreatMethod;
    if ((getCreatMethod = ServerRequestServiceGrpc.getCreatMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getCreatMethod = ServerRequestServiceGrpc.getCreatMethod) == null) {
          ServerRequestServiceGrpc.getCreatMethod = getCreatMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.CreateRequest, com.sd.etapa2.grpc.ServerRequest.CreateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "creat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.CreateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.CreateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("creat"))
                  .build();
          }
        }
     }
     return getCreatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadRequest,
      com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "read",
      requestType = com.sd.etapa2.grpc.ServerRequest.ReadRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.ReadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadRequest,
      com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadRequest, com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadMethod;
    if ((getReadMethod = ServerRequestServiceGrpc.getReadMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getReadMethod = ServerRequestServiceGrpc.getReadMethod) == null) {
          ServerRequestServiceGrpc.getReadMethod = getReadMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.ReadRequest, com.sd.etapa2.grpc.ServerRequest.ReadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.ReadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.ReadResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("read"))
                  .build();
          }
        }
     }
     return getReadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.UpdateRequest,
      com.sd.etapa2.grpc.ServerRequest.UpdateResponse> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = com.sd.etapa2.grpc.ServerRequest.UpdateRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.UpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.UpdateRequest,
      com.sd.etapa2.grpc.ServerRequest.UpdateResponse> getUpdateMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.UpdateRequest, com.sd.etapa2.grpc.ServerRequest.UpdateResponse> getUpdateMethod;
    if ((getUpdateMethod = ServerRequestServiceGrpc.getUpdateMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getUpdateMethod = ServerRequestServiceGrpc.getUpdateMethod) == null) {
          ServerRequestServiceGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.UpdateRequest, com.sd.etapa2.grpc.ServerRequest.UpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.UpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.UpdateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.DeleteRequest,
      com.sd.etapa2.grpc.ServerRequest.DeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = com.sd.etapa2.grpc.ServerRequest.DeleteRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.DeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.DeleteRequest,
      com.sd.etapa2.grpc.ServerRequest.DeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.DeleteRequest, com.sd.etapa2.grpc.ServerRequest.DeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = ServerRequestServiceGrpc.getDeleteMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getDeleteMethod = ServerRequestServiceGrpc.getDeleteMethod) == null) {
          ServerRequestServiceGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.DeleteRequest, com.sd.etapa2.grpc.ServerRequest.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.DeleteResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest,
      com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadvaluesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readvalues",
      requestType = com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.ReadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest,
      com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadvaluesMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest, com.sd.etapa2.grpc.ServerRequest.ReadResponse> getReadvaluesMethod;
    if ((getReadvaluesMethod = ServerRequestServiceGrpc.getReadvaluesMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getReadvaluesMethod = ServerRequestServiceGrpc.getReadvaluesMethod) == null) {
          ServerRequestServiceGrpc.getReadvaluesMethod = getReadvaluesMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest, com.sd.etapa2.grpc.ServerRequest.ReadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "readvalues"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.ReadResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("readvalues"))
                  .build();
          }
        }
     }
     return getReadvaluesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.SairRequest,
      com.sd.etapa2.grpc.ServerRequest.SairResponse> getSairMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sair",
      requestType = com.sd.etapa2.grpc.ServerRequest.SairRequest.class,
      responseType = com.sd.etapa2.grpc.ServerRequest.SairResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.SairRequest,
      com.sd.etapa2.grpc.ServerRequest.SairResponse> getSairMethod() {
    io.grpc.MethodDescriptor<com.sd.etapa2.grpc.ServerRequest.SairRequest, com.sd.etapa2.grpc.ServerRequest.SairResponse> getSairMethod;
    if ((getSairMethod = ServerRequestServiceGrpc.getSairMethod) == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        if ((getSairMethod = ServerRequestServiceGrpc.getSairMethod) == null) {
          ServerRequestServiceGrpc.getSairMethod = getSairMethod = 
              io.grpc.MethodDescriptor.<com.sd.etapa2.grpc.ServerRequest.SairRequest, com.sd.etapa2.grpc.ServerRequest.SairResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.sd.etapa2.grpc.ServerRequestService", "sair"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.SairRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.sd.etapa2.grpc.ServerRequest.SairResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerRequestServiceMethodDescriptorSupplier("sair"))
                  .build();
          }
        }
     }
     return getSairMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerRequestServiceStub newStub(io.grpc.Channel channel) {
    return new ServerRequestServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerRequestServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ServerRequestServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerRequestServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ServerRequestServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ServerRequestServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void creat(com.sd.etapa2.grpc.ServerRequest.CreateRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.CreateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreatMethod(), responseObserver);
    }

    /**
     */
    public void read(com.sd.etapa2.grpc.ServerRequest.ReadRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMethod(), responseObserver);
    }

    /**
     */
    public void update(com.sd.etapa2.grpc.ServerRequest.UpdateRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(com.sd.etapa2.grpc.ServerRequest.DeleteRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void readvalues(com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReadvaluesMethod(), responseObserver);
    }

    /**
     */
    public void sair(com.sd.etapa2.grpc.ServerRequest.SairRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.SairResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSairMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreatMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.CreateRequest,
                com.sd.etapa2.grpc.ServerRequest.CreateResponse>(
                  this, METHODID_CREAT)))
          .addMethod(
            getReadMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.ReadRequest,
                com.sd.etapa2.grpc.ServerRequest.ReadResponse>(
                  this, METHODID_READ)))
          .addMethod(
            getUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.UpdateRequest,
                com.sd.etapa2.grpc.ServerRequest.UpdateResponse>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.DeleteRequest,
                com.sd.etapa2.grpc.ServerRequest.DeleteResponse>(
                  this, METHODID_DELETE)))
          .addMethod(
            getReadvaluesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest,
                com.sd.etapa2.grpc.ServerRequest.ReadResponse>(
                  this, METHODID_READVALUES)))
          .addMethod(
            getSairMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.sd.etapa2.grpc.ServerRequest.SairRequest,
                com.sd.etapa2.grpc.ServerRequest.SairResponse>(
                  this, METHODID_SAIR)))
          .build();
    }
  }

  /**
   */
  public static final class ServerRequestServiceStub extends io.grpc.stub.AbstractStub<ServerRequestServiceStub> {
    private ServerRequestServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerRequestServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerRequestServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerRequestServiceStub(channel, callOptions);
    }

    /**
     */
    public void creat(com.sd.etapa2.grpc.ServerRequest.CreateRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.CreateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(com.sd.etapa2.grpc.ServerRequest.ReadRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(com.sd.etapa2.grpc.ServerRequest.UpdateRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.sd.etapa2.grpc.ServerRequest.DeleteRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.DeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readvalues(com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getReadvaluesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sair(com.sd.etapa2.grpc.ServerRequest.SairRequest request,
        io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.SairResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSairMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServerRequestServiceBlockingStub extends io.grpc.stub.AbstractStub<ServerRequestServiceBlockingStub> {
    private ServerRequestServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerRequestServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerRequestServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerRequestServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.sd.etapa2.grpc.ServerRequest.CreateResponse creat(com.sd.etapa2.grpc.ServerRequest.CreateRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreatMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sd.etapa2.grpc.ServerRequest.ReadResponse read(com.sd.etapa2.grpc.ServerRequest.ReadRequest request) {
      return blockingUnaryCall(
          getChannel(), getReadMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sd.etapa2.grpc.ServerRequest.UpdateResponse update(com.sd.etapa2.grpc.ServerRequest.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sd.etapa2.grpc.ServerRequest.DeleteResponse delete(com.sd.etapa2.grpc.ServerRequest.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.sd.etapa2.grpc.ServerRequest.ReadResponse> readvalues(
        com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getReadvaluesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.sd.etapa2.grpc.ServerRequest.SairResponse sair(com.sd.etapa2.grpc.ServerRequest.SairRequest request) {
      return blockingUnaryCall(
          getChannel(), getSairMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServerRequestServiceFutureStub extends io.grpc.stub.AbstractStub<ServerRequestServiceFutureStub> {
    private ServerRequestServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerRequestServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerRequestServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerRequestServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sd.etapa2.grpc.ServerRequest.CreateResponse> creat(
        com.sd.etapa2.grpc.ServerRequest.CreateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sd.etapa2.grpc.ServerRequest.ReadResponse> read(
        com.sd.etapa2.grpc.ServerRequest.ReadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sd.etapa2.grpc.ServerRequest.UpdateResponse> update(
        com.sd.etapa2.grpc.ServerRequest.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sd.etapa2.grpc.ServerRequest.DeleteResponse> delete(
        com.sd.etapa2.grpc.ServerRequest.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sd.etapa2.grpc.ServerRequest.SairResponse> sair(
        com.sd.etapa2.grpc.ServerRequest.SairRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSairMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREAT = 0;
  private static final int METHODID_READ = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;
  private static final int METHODID_READVALUES = 4;
  private static final int METHODID_SAIR = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerRequestServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerRequestServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREAT:
          serviceImpl.creat((com.sd.etapa2.grpc.ServerRequest.CreateRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.CreateResponse>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((com.sd.etapa2.grpc.ServerRequest.ReadRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((com.sd.etapa2.grpc.ServerRequest.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.UpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.sd.etapa2.grpc.ServerRequest.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.DeleteResponse>) responseObserver);
          break;
        case METHODID_READVALUES:
          serviceImpl.readvalues((com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.ReadResponse>) responseObserver);
          break;
        case METHODID_SAIR:
          serviceImpl.sair((com.sd.etapa2.grpc.ServerRequest.SairRequest) request,
              (io.grpc.stub.StreamObserver<com.sd.etapa2.grpc.ServerRequest.SairResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServerRequestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerRequestServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.sd.etapa2.grpc.ServerRequest.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServerRequestService");
    }
  }

  private static final class ServerRequestServiceFileDescriptorSupplier
      extends ServerRequestServiceBaseDescriptorSupplier {
    ServerRequestServiceFileDescriptorSupplier() {}
  }

  private static final class ServerRequestServiceMethodDescriptorSupplier
      extends ServerRequestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerRequestServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServerRequestServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerRequestServiceFileDescriptorSupplier())
              .addMethod(getCreatMethod())
              .addMethod(getReadMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getReadvaluesMethod())
              .addMethod(getSairMethod())
              .build();
        }
      }
    }
    return result;
  }
}
