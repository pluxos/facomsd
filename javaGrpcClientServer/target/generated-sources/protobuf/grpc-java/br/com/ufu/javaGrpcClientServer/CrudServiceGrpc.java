package br.com.ufu.javaGrpcClientServer;

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
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: Server.proto")
public final class CrudServiceGrpc {

  private CrudServiceGrpc() {}

  public static final String SERVICE_NAME = "br.com.ufu.javaGrpcClientServer.CrudService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.InsertRequest,
      br.com.ufu.javaGrpcClientServer.InsertResponse> getInsertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "insert",
      requestType = br.com.ufu.javaGrpcClientServer.InsertRequest.class,
      responseType = br.com.ufu.javaGrpcClientServer.InsertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.InsertRequest,
      br.com.ufu.javaGrpcClientServer.InsertResponse> getInsertMethod() {
    io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.InsertRequest, br.com.ufu.javaGrpcClientServer.InsertResponse> getInsertMethod;
    if ((getInsertMethod = CrudServiceGrpc.getInsertMethod) == null) {
      synchronized (CrudServiceGrpc.class) {
        if ((getInsertMethod = CrudServiceGrpc.getInsertMethod) == null) {
          CrudServiceGrpc.getInsertMethod = getInsertMethod = 
              io.grpc.MethodDescriptor.<br.com.ufu.javaGrpcClientServer.InsertRequest, br.com.ufu.javaGrpcClientServer.InsertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.com.ufu.javaGrpcClientServer.CrudService", "insert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.InsertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.InsertResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CrudServiceMethodDescriptorSupplier("insert"))
                  .build();
          }
        }
     }
     return getInsertMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.SelectRequest,
      br.com.ufu.javaGrpcClientServer.SelectResponse> getSelectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "select",
      requestType = br.com.ufu.javaGrpcClientServer.SelectRequest.class,
      responseType = br.com.ufu.javaGrpcClientServer.SelectResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.SelectRequest,
      br.com.ufu.javaGrpcClientServer.SelectResponse> getSelectMethod() {
    io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.SelectRequest, br.com.ufu.javaGrpcClientServer.SelectResponse> getSelectMethod;
    if ((getSelectMethod = CrudServiceGrpc.getSelectMethod) == null) {
      synchronized (CrudServiceGrpc.class) {
        if ((getSelectMethod = CrudServiceGrpc.getSelectMethod) == null) {
          CrudServiceGrpc.getSelectMethod = getSelectMethod = 
              io.grpc.MethodDescriptor.<br.com.ufu.javaGrpcClientServer.SelectRequest, br.com.ufu.javaGrpcClientServer.SelectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.com.ufu.javaGrpcClientServer.CrudService", "select"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.SelectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.SelectResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CrudServiceMethodDescriptorSupplier("select"))
                  .build();
          }
        }
     }
     return getSelectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.UpdateRequest,
      br.com.ufu.javaGrpcClientServer.UpdateResponse> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = br.com.ufu.javaGrpcClientServer.UpdateRequest.class,
      responseType = br.com.ufu.javaGrpcClientServer.UpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.UpdateRequest,
      br.com.ufu.javaGrpcClientServer.UpdateResponse> getUpdateMethod() {
    io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.UpdateRequest, br.com.ufu.javaGrpcClientServer.UpdateResponse> getUpdateMethod;
    if ((getUpdateMethod = CrudServiceGrpc.getUpdateMethod) == null) {
      synchronized (CrudServiceGrpc.class) {
        if ((getUpdateMethod = CrudServiceGrpc.getUpdateMethod) == null) {
          CrudServiceGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<br.com.ufu.javaGrpcClientServer.UpdateRequest, br.com.ufu.javaGrpcClientServer.UpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.com.ufu.javaGrpcClientServer.CrudService", "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.UpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.UpdateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CrudServiceMethodDescriptorSupplier("update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.DeleteRequest,
      br.com.ufu.javaGrpcClientServer.DeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = br.com.ufu.javaGrpcClientServer.DeleteRequest.class,
      responseType = br.com.ufu.javaGrpcClientServer.DeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.DeleteRequest,
      br.com.ufu.javaGrpcClientServer.DeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<br.com.ufu.javaGrpcClientServer.DeleteRequest, br.com.ufu.javaGrpcClientServer.DeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = CrudServiceGrpc.getDeleteMethod) == null) {
      synchronized (CrudServiceGrpc.class) {
        if ((getDeleteMethod = CrudServiceGrpc.getDeleteMethod) == null) {
          CrudServiceGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<br.com.ufu.javaGrpcClientServer.DeleteRequest, br.com.ufu.javaGrpcClientServer.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.com.ufu.javaGrpcClientServer.CrudService", "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.ufu.javaGrpcClientServer.DeleteResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CrudServiceMethodDescriptorSupplier("delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CrudServiceStub newStub(io.grpc.Channel channel) {
    return new CrudServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CrudServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CrudServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CrudServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CrudServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CrudServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void insert(br.com.ufu.javaGrpcClientServer.InsertRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.InsertResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInsertMethod(), responseObserver);
    }

    /**
     */
    public void select(br.com.ufu.javaGrpcClientServer.SelectRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.SelectResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSelectMethod(), responseObserver);
    }

    /**
     */
    public void update(br.com.ufu.javaGrpcClientServer.UpdateRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(br.com.ufu.javaGrpcClientServer.DeleteRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInsertMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.com.ufu.javaGrpcClientServer.InsertRequest,
                br.com.ufu.javaGrpcClientServer.InsertResponse>(
                  this, METHODID_INSERT)))
          .addMethod(
            getSelectMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.com.ufu.javaGrpcClientServer.SelectRequest,
                br.com.ufu.javaGrpcClientServer.SelectResponse>(
                  this, METHODID_SELECT)))
          .addMethod(
            getUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.com.ufu.javaGrpcClientServer.UpdateRequest,
                br.com.ufu.javaGrpcClientServer.UpdateResponse>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.com.ufu.javaGrpcClientServer.DeleteRequest,
                br.com.ufu.javaGrpcClientServer.DeleteResponse>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class CrudServiceStub extends io.grpc.stub.AbstractStub<CrudServiceStub> {
    private CrudServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CrudServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CrudServiceStub(channel, callOptions);
    }

    /**
     */
    public void insert(br.com.ufu.javaGrpcClientServer.InsertRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.InsertResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInsertMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void select(br.com.ufu.javaGrpcClientServer.SelectRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.SelectResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSelectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(br.com.ufu.javaGrpcClientServer.UpdateRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(br.com.ufu.javaGrpcClientServer.DeleteRequest request,
        io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.DeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CrudServiceBlockingStub extends io.grpc.stub.AbstractStub<CrudServiceBlockingStub> {
    private CrudServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CrudServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CrudServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.com.ufu.javaGrpcClientServer.InsertResponse insert(br.com.ufu.javaGrpcClientServer.InsertRequest request) {
      return blockingUnaryCall(
          getChannel(), getInsertMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.ufu.javaGrpcClientServer.SelectResponse select(br.com.ufu.javaGrpcClientServer.SelectRequest request) {
      return blockingUnaryCall(
          getChannel(), getSelectMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.ufu.javaGrpcClientServer.UpdateResponse update(br.com.ufu.javaGrpcClientServer.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.ufu.javaGrpcClientServer.DeleteResponse delete(br.com.ufu.javaGrpcClientServer.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CrudServiceFutureStub extends io.grpc.stub.AbstractStub<CrudServiceFutureStub> {
    private CrudServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CrudServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CrudServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CrudServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.ufu.javaGrpcClientServer.InsertResponse> insert(
        br.com.ufu.javaGrpcClientServer.InsertRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getInsertMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.ufu.javaGrpcClientServer.SelectResponse> select(
        br.com.ufu.javaGrpcClientServer.SelectRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSelectMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.ufu.javaGrpcClientServer.UpdateResponse> update(
        br.com.ufu.javaGrpcClientServer.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.ufu.javaGrpcClientServer.DeleteResponse> delete(
        br.com.ufu.javaGrpcClientServer.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT = 0;
  private static final int METHODID_SELECT = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CrudServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CrudServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT:
          serviceImpl.insert((br.com.ufu.javaGrpcClientServer.InsertRequest) request,
              (io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.InsertResponse>) responseObserver);
          break;
        case METHODID_SELECT:
          serviceImpl.select((br.com.ufu.javaGrpcClientServer.SelectRequest) request,
              (io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.SelectResponse>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((br.com.ufu.javaGrpcClientServer.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.UpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((br.com.ufu.javaGrpcClientServer.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<br.com.ufu.javaGrpcClientServer.DeleteResponse>) responseObserver);
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

  private static abstract class CrudServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CrudServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.com.ufu.javaGrpcClientServer.Server.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CrudService");
    }
  }

  private static final class CrudServiceFileDescriptorSupplier
      extends CrudServiceBaseDescriptorSupplier {
    CrudServiceFileDescriptorSupplier() {}
  }

  private static final class CrudServiceMethodDescriptorSupplier
      extends CrudServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CrudServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CrudServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CrudServiceFileDescriptorSupplier())
              .addMethod(getInsertMethod())
              .addMethod(getSelectMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
