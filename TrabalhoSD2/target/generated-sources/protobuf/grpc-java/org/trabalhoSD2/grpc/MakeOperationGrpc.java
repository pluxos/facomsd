package org.trabalhoSD2.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: Service.proto")
public final class MakeOperationGrpc {

  private MakeOperationGrpc() {}

  public static final String SERVICE_NAME = "org.trabalhoSD2.grpc.MakeOperation";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<org.trabalhoSD2.grpc.Operation,
      org.trabalhoSD2.grpc.OperationResponse> METHOD_MAKE_OPERATION =
      io.grpc.MethodDescriptor.<org.trabalhoSD2.grpc.Operation, org.trabalhoSD2.grpc.OperationResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "org.trabalhoSD2.grpc.MakeOperation", "makeOperation"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              org.trabalhoSD2.grpc.Operation.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              org.trabalhoSD2.grpc.OperationResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MakeOperationStub newStub(io.grpc.Channel channel) {
    return new MakeOperationStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MakeOperationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MakeOperationBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MakeOperationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MakeOperationFutureStub(channel);
  }

  /**
   */
  public static abstract class MakeOperationImplBase implements io.grpc.BindableService {

    /**
     */
    public void makeOperation(org.trabalhoSD2.grpc.Operation request,
        io.grpc.stub.StreamObserver<org.trabalhoSD2.grpc.OperationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MAKE_OPERATION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_MAKE_OPERATION,
            asyncServerStreamingCall(
              new MethodHandlers<
                org.trabalhoSD2.grpc.Operation,
                org.trabalhoSD2.grpc.OperationResponse>(
                  this, METHODID_MAKE_OPERATION)))
          .build();
    }
  }

  /**
   */
  public static final class MakeOperationStub extends io.grpc.stub.AbstractStub<MakeOperationStub> {
    private MakeOperationStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MakeOperationStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MakeOperationStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MakeOperationStub(channel, callOptions);
    }

    /**
     */
    public void makeOperation(org.trabalhoSD2.grpc.Operation request,
        io.grpc.stub.StreamObserver<org.trabalhoSD2.grpc.OperationResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_MAKE_OPERATION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MakeOperationBlockingStub extends io.grpc.stub.AbstractStub<MakeOperationBlockingStub> {
    private MakeOperationBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MakeOperationBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MakeOperationBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MakeOperationBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<org.trabalhoSD2.grpc.OperationResponse> makeOperation(
        org.trabalhoSD2.grpc.Operation request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_MAKE_OPERATION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MakeOperationFutureStub extends io.grpc.stub.AbstractStub<MakeOperationFutureStub> {
    private MakeOperationFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MakeOperationFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MakeOperationFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MakeOperationFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_MAKE_OPERATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MakeOperationImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MakeOperationImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MAKE_OPERATION:
          serviceImpl.makeOperation((org.trabalhoSD2.grpc.Operation) request,
              (io.grpc.stub.StreamObserver<org.trabalhoSD2.grpc.OperationResponse>) responseObserver);
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

  private static final class MakeOperationDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.trabalhoSD2.grpc.Service.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MakeOperationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MakeOperationDescriptorSupplier())
              .addMethod(METHOD_MAKE_OPERATION)
              .build();
        }
      }
    }
    return result;
  }
}
