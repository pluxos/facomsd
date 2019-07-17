package com.example.grpc;

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
    comments = "Source: crud.proto")
public final class CRUDGrpc {

  private CRUDGrpc() {}

  public static final String SERVICE_NAME = "CRUD";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Create",
      requestType = com.example.grpc.Comando.class,
      responseType = com.example.grpc.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getCreateMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.Comando, com.example.grpc.Result> getCreateMethod;
    if ((getCreateMethod = CRUDGrpc.getCreateMethod) == null) {
      synchronized (CRUDGrpc.class) {
        if ((getCreateMethod = CRUDGrpc.getCreateMethod) == null) {
          CRUDGrpc.getCreateMethod = getCreateMethod = 
              io.grpc.MethodDescriptor.<com.example.grpc.Comando, com.example.grpc.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CRUD", "Create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Comando.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new CRUDMethodDescriptorSupplier("Create"))
                  .build();
          }
        }
     }
     return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Read",
      requestType = com.example.grpc.Comando.class,
      responseType = com.example.grpc.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getReadMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.Comando, com.example.grpc.Result> getReadMethod;
    if ((getReadMethod = CRUDGrpc.getReadMethod) == null) {
      synchronized (CRUDGrpc.class) {
        if ((getReadMethod = CRUDGrpc.getReadMethod) == null) {
          CRUDGrpc.getReadMethod = getReadMethod = 
              io.grpc.MethodDescriptor.<com.example.grpc.Comando, com.example.grpc.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CRUD", "Read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Comando.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new CRUDMethodDescriptorSupplier("Read"))
                  .build();
          }
        }
     }
     return getReadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Update",
      requestType = com.example.grpc.Comando.class,
      responseType = com.example.grpc.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getUpdateMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.Comando, com.example.grpc.Result> getUpdateMethod;
    if ((getUpdateMethod = CRUDGrpc.getUpdateMethod) == null) {
      synchronized (CRUDGrpc.class) {
        if ((getUpdateMethod = CRUDGrpc.getUpdateMethod) == null) {
          CRUDGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<com.example.grpc.Comando, com.example.grpc.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CRUD", "Update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Comando.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new CRUDMethodDescriptorSupplier("Update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = com.example.grpc.Comando.class,
      responseType = com.example.grpc.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.Comando,
      com.example.grpc.Result> getDeleteMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.Comando, com.example.grpc.Result> getDeleteMethod;
    if ((getDeleteMethod = CRUDGrpc.getDeleteMethod) == null) {
      synchronized (CRUDGrpc.class) {
        if ((getDeleteMethod = CRUDGrpc.getDeleteMethod) == null) {
          CRUDGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<com.example.grpc.Comando, com.example.grpc.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CRUD", "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Comando.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new CRUDMethodDescriptorSupplier("Delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CRUDStub newStub(io.grpc.Channel channel) {
    return new CRUDStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CRUDBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CRUDBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CRUDFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CRUDFutureStub(channel);
  }

  /**
   */
  public static abstract class CRUDImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void read(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMethod(), responseObserver);
    }

    /**
     */
    public void update(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.Comando,
                com.example.grpc.Result>(
                  this, METHODID_CREATE)))
          .addMethod(
            getReadMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.Comando,
                com.example.grpc.Result>(
                  this, METHODID_READ)))
          .addMethod(
            getUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.Comando,
                com.example.grpc.Result>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.Comando,
                com.example.grpc.Result>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class CRUDStub extends io.grpc.stub.AbstractStub<CRUDStub> {
    private CRUDStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CRUDStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRUDStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CRUDStub(channel, callOptions);
    }

    /**
     */
    public void create(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.example.grpc.Comando request,
        io.grpc.stub.StreamObserver<com.example.grpc.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CRUDBlockingStub extends io.grpc.stub.AbstractStub<CRUDBlockingStub> {
    private CRUDBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CRUDBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRUDBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CRUDBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.grpc.Result create(com.example.grpc.Comando request) {
      return blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.Result read(com.example.grpc.Comando request) {
      return blockingUnaryCall(
          getChannel(), getReadMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.Result update(com.example.grpc.Comando request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.Result delete(com.example.grpc.Comando request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CRUDFutureStub extends io.grpc.stub.AbstractStub<CRUDFutureStub> {
    private CRUDFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CRUDFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CRUDFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CRUDFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.Result> create(
        com.example.grpc.Comando request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.Result> read(
        com.example.grpc.Comando request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.Result> update(
        com.example.grpc.Comando request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.Result> delete(
        com.example.grpc.Comando request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_READ = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CRUDImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CRUDImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((com.example.grpc.Comando) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.Result>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((com.example.grpc.Comando) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.Result>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((com.example.grpc.Comando) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.Result>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.example.grpc.Comando) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.Result>) responseObserver);
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

  private static abstract class CRUDBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CRUDBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.grpc.Crud.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CRUD");
    }
  }

  private static final class CRUDFileDescriptorSupplier
      extends CRUDBaseDescriptorSupplier {
    CRUDFileDescriptorSupplier() {}
  }

  private static final class CRUDMethodDescriptorSupplier
      extends CRUDBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CRUDMethodDescriptorSupplier(String methodName) {
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
      synchronized (CRUDGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CRUDFileDescriptorSupplier())
              .addMethod(getCreateMethod())
              .addMethod(getReadMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
