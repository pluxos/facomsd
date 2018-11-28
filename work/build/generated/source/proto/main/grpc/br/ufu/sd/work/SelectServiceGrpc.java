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
public final class SelectServiceGrpc {

  private SelectServiceGrpc() {}

  public static final String SERVICE_NAME = "br.ufu.sd.work.SelectService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.ufu.sd.work.SelectRequest,
      br.ufu.sd.work.Response> getSelectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "select",
      requestType = br.ufu.sd.work.SelectRequest.class,
      responseType = br.ufu.sd.work.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.ufu.sd.work.SelectRequest,
      br.ufu.sd.work.Response> getSelectMethod() {
    io.grpc.MethodDescriptor<br.ufu.sd.work.SelectRequest, br.ufu.sd.work.Response> getSelectMethod;
    if ((getSelectMethod = SelectServiceGrpc.getSelectMethod) == null) {
      synchronized (SelectServiceGrpc.class) {
        if ((getSelectMethod = SelectServiceGrpc.getSelectMethod) == null) {
          SelectServiceGrpc.getSelectMethod = getSelectMethod = 
              io.grpc.MethodDescriptor.<br.ufu.sd.work.SelectRequest, br.ufu.sd.work.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.ufu.sd.work.SelectService", "select"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.SelectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new SelectServiceMethodDescriptorSupplier("select"))
                  .build();
          }
        }
     }
     return getSelectMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SelectServiceStub newStub(io.grpc.Channel channel) {
    return new SelectServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SelectServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SelectServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SelectServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SelectServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SelectServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void select(br.ufu.sd.work.SelectRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getSelectMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSelectMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.ufu.sd.work.SelectRequest,
                br.ufu.sd.work.Response>(
                  this, METHODID_SELECT)))
          .build();
    }
  }

  /**
   */
  public static final class SelectServiceStub extends io.grpc.stub.AbstractStub<SelectServiceStub> {
    private SelectServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectServiceStub(channel, callOptions);
    }

    /**
     */
    public void select(br.ufu.sd.work.SelectRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSelectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SelectServiceBlockingStub extends io.grpc.stub.AbstractStub<SelectServiceBlockingStub> {
    private SelectServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.ufu.sd.work.Response select(br.ufu.sd.work.SelectRequest request) {
      return blockingUnaryCall(
          getChannel(), getSelectMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SelectServiceFutureStub extends io.grpc.stub.AbstractStub<SelectServiceFutureStub> {
    private SelectServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SelectServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SelectServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SelectServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.ufu.sd.work.Response> select(
        br.ufu.sd.work.SelectRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSelectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SelectServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SelectServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT:
          serviceImpl.select((br.ufu.sd.work.SelectRequest) request,
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

  private static abstract class SelectServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SelectServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.ufu.sd.work.CRUDService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SelectService");
    }
  }

  private static final class SelectServiceFileDescriptorSupplier
      extends SelectServiceBaseDescriptorSupplier {
    SelectServiceFileDescriptorSupplier() {}
  }

  private static final class SelectServiceMethodDescriptorSupplier
      extends SelectServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SelectServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SelectServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SelectServiceFileDescriptorSupplier())
              .addMethod(getSelectMethod())
              .build();
        }
      }
    }
    return result;
  }
}
