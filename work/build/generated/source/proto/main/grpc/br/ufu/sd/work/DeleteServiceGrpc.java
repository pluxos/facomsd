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
public final class DeleteServiceGrpc {

  private DeleteServiceGrpc() {}

  public static final String SERVICE_NAME = "br.ufu.sd.work.DeleteService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.ufu.sd.work.DeleteRequest,
      br.ufu.sd.work.Response> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = br.ufu.sd.work.DeleteRequest.class,
      responseType = br.ufu.sd.work.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.ufu.sd.work.DeleteRequest,
      br.ufu.sd.work.Response> getDeleteMethod() {
    io.grpc.MethodDescriptor<br.ufu.sd.work.DeleteRequest, br.ufu.sd.work.Response> getDeleteMethod;
    if ((getDeleteMethod = DeleteServiceGrpc.getDeleteMethod) == null) {
      synchronized (DeleteServiceGrpc.class) {
        if ((getDeleteMethod = DeleteServiceGrpc.getDeleteMethod) == null) {
          DeleteServiceGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<br.ufu.sd.work.DeleteRequest, br.ufu.sd.work.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.ufu.sd.work.DeleteService", "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new DeleteServiceMethodDescriptorSupplier("delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DeleteServiceStub newStub(io.grpc.Channel channel) {
    return new DeleteServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DeleteServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DeleteServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DeleteServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DeleteServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DeleteServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void delete(br.ufu.sd.work.DeleteRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.ufu.sd.work.DeleteRequest,
                br.ufu.sd.work.Response>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class DeleteServiceStub extends io.grpc.stub.AbstractStub<DeleteServiceStub> {
    private DeleteServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeleteServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeleteServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeleteServiceStub(channel, callOptions);
    }

    /**
     */
    public void delete(br.ufu.sd.work.DeleteRequest request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DeleteServiceBlockingStub extends io.grpc.stub.AbstractStub<DeleteServiceBlockingStub> {
    private DeleteServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeleteServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeleteServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeleteServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.ufu.sd.work.Response delete(br.ufu.sd.work.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DeleteServiceFutureStub extends io.grpc.stub.AbstractStub<DeleteServiceFutureStub> {
    private DeleteServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeleteServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeleteServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeleteServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.ufu.sd.work.Response> delete(
        br.ufu.sd.work.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DELETE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DeleteServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DeleteServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DELETE:
          serviceImpl.delete((br.ufu.sd.work.DeleteRequest) request,
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

  private static abstract class DeleteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DeleteServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.ufu.sd.work.CRUDService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DeleteService");
    }
  }

  private static final class DeleteServiceFileDescriptorSupplier
      extends DeleteServiceBaseDescriptorSupplier {
    DeleteServiceFileDescriptorSupplier() {}
  }

  private static final class DeleteServiceMethodDescriptorSupplier
      extends DeleteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DeleteServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DeleteServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DeleteServiceFileDescriptorSupplier())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
