package br.ufu.sd.work;

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
    value = "by gRPC proto compiler (version 1.16.0)",
    comments = "Source: CRUDService.proto")
public final class InsertServiceGrpc {

  private InsertServiceGrpc() {}

  public static final String SERVICE_NAME = "br.ufu.sd.work.InsertService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.ufu.sd.work.InsertRequest,
      br.ufu.sd.work.Response> getInsertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "insert",
      requestType = br.ufu.sd.work.InsertRequest.class,
      responseType = br.ufu.sd.work.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.ufu.sd.work.InsertRequest,
      br.ufu.sd.work.Response> getInsertMethod() {
    io.grpc.MethodDescriptor<br.ufu.sd.work.InsertRequest, br.ufu.sd.work.Response> getInsertMethod;
    if ((getInsertMethod = InsertServiceGrpc.getInsertMethod) == null) {
      synchronized (InsertServiceGrpc.class) {
        if ((getInsertMethod = InsertServiceGrpc.getInsertMethod) == null) {
          InsertServiceGrpc.getInsertMethod = getInsertMethod = 
              io.grpc.MethodDescriptor.<br.ufu.sd.work.InsertRequest, br.ufu.sd.work.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.ufu.sd.work.InsertService", "insert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.InsertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new InsertServiceMethodDescriptorSupplier("insert"))
                  .build();
          }
        }
     }
     return getInsertMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InsertServiceStub newStub(io.grpc.Channel channel) {
    return new InsertServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InsertServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InsertServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InsertServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InsertServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InsertServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void insert(br.ufu.sd.work.InsertRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getInsertMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInsertMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.ufu.sd.work.InsertRequest,
                br.ufu.sd.work.Response>(
                  this, METHODID_INSERT)))
          .build();
    }
  }

  /**
   */
  public static final class InsertServiceStub extends io.grpc.stub.AbstractStub<InsertServiceStub> {
    private InsertServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertServiceStub(channel, callOptions);
    }

    /**
     */
    public void insert(br.ufu.sd.work.InsertRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInsertMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InsertServiceBlockingStub extends io.grpc.stub.AbstractStub<InsertServiceBlockingStub> {
    private InsertServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.ufu.sd.work.Response insert(br.ufu.sd.work.InsertRequest request) {
      return blockingUnaryCall(
          getChannel(), getInsertMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InsertServiceFutureStub extends io.grpc.stub.AbstractStub<InsertServiceFutureStub> {
    private InsertServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.ufu.sd.work.Response> insert(
        br.ufu.sd.work.InsertRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getInsertMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InsertServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InsertServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT:
          serviceImpl.insert((br.ufu.sd.work.InsertRequest) request,
              (io.grpc.stub.StreamObserver<br.ufu.sd.work.Response>) responseObserver);
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

  private static abstract class InsertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InsertServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.ufu.sd.work.CRUDService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InsertService");
    }
  }

  private static final class InsertServiceFileDescriptorSupplier
      extends InsertServiceBaseDescriptorSupplier {
    InsertServiceFileDescriptorSupplier() {}
  }

  private static final class InsertServiceMethodDescriptorSupplier
      extends InsertServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    InsertServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (InsertServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InsertServiceFileDescriptorSupplier())
              .addMethod(getInsertMethod())
              .build();
        }
      }
    }
    return result;
  }
}
