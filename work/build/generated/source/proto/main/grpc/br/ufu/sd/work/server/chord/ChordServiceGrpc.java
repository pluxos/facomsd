package br.ufu.sd.work.server.chord;

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
    comments = "Source: ChordService.proto")
public final class ChordServiceGrpc {

  private ChordServiceGrpc() {}

  public static final String SERVICE_NAME = "br.ufu.sd.work.server.chord.ChordService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChordNode,
      br.ufu.sd.work.server.chord.ChannelNode> getHeyListenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "heyListen",
      requestType = br.ufu.sd.work.server.chord.ChordNode.class,
      responseType = br.ufu.sd.work.server.chord.ChannelNode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChordNode,
      br.ufu.sd.work.server.chord.ChannelNode> getHeyListenMethod() {
    io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChordNode, br.ufu.sd.work.server.chord.ChannelNode> getHeyListenMethod;
    if ((getHeyListenMethod = ChordServiceGrpc.getHeyListenMethod) == null) {
      synchronized (ChordServiceGrpc.class) {
        if ((getHeyListenMethod = ChordServiceGrpc.getHeyListenMethod) == null) {
          ChordServiceGrpc.getHeyListenMethod = getHeyListenMethod = 
              io.grpc.MethodDescriptor.<br.ufu.sd.work.server.chord.ChordNode, br.ufu.sd.work.server.chord.ChannelNode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.ufu.sd.work.server.chord.ChordService", "heyListen"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.server.chord.ChordNode.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.server.chord.ChannelNode.getDefaultInstance()))
                  .setSchemaDescriptor(new ChordServiceMethodDescriptorSupplier("heyListen"))
                  .build();
          }
        }
     }
     return getHeyListenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChannelNode,
      br.ufu.sd.work.server.chord.ChannelNode> getSetPreviousMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "setPrevious",
      requestType = br.ufu.sd.work.server.chord.ChannelNode.class,
      responseType = br.ufu.sd.work.server.chord.ChannelNode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChannelNode,
      br.ufu.sd.work.server.chord.ChannelNode> getSetPreviousMethod() {
    io.grpc.MethodDescriptor<br.ufu.sd.work.server.chord.ChannelNode, br.ufu.sd.work.server.chord.ChannelNode> getSetPreviousMethod;
    if ((getSetPreviousMethod = ChordServiceGrpc.getSetPreviousMethod) == null) {
      synchronized (ChordServiceGrpc.class) {
        if ((getSetPreviousMethod = ChordServiceGrpc.getSetPreviousMethod) == null) {
          ChordServiceGrpc.getSetPreviousMethod = getSetPreviousMethod = 
              io.grpc.MethodDescriptor.<br.ufu.sd.work.server.chord.ChannelNode, br.ufu.sd.work.server.chord.ChannelNode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "br.ufu.sd.work.server.chord.ChordService", "setPrevious"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.server.chord.ChannelNode.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.ufu.sd.work.server.chord.ChannelNode.getDefaultInstance()))
                  .setSchemaDescriptor(new ChordServiceMethodDescriptorSupplier("setPrevious"))
                  .build();
          }
        }
     }
     return getSetPreviousMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChordServiceStub newStub(io.grpc.Channel channel) {
    return new ChordServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChordServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChordServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChordServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChordServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChordServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void heyListen(br.ufu.sd.work.server.chord.ChordNode request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode> responseObserver) {
      asyncUnimplementedUnaryCall(getHeyListenMethod(), responseObserver);
    }

    /**
     */
    public void setPrevious(br.ufu.sd.work.server.chord.ChannelNode request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode> responseObserver) {
      asyncUnimplementedUnaryCall(getSetPreviousMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHeyListenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.ufu.sd.work.server.chord.ChordNode,
                br.ufu.sd.work.server.chord.ChannelNode>(
                  this, METHODID_HEY_LISTEN)))
          .addMethod(
            getSetPreviousMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                br.ufu.sd.work.server.chord.ChannelNode,
                br.ufu.sd.work.server.chord.ChannelNode>(
                  this, METHODID_SET_PREVIOUS)))
          .build();
    }
  }

  /**
   */
  public static final class ChordServiceStub extends io.grpc.stub.AbstractStub<ChordServiceStub> {
    private ChordServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceStub(channel, callOptions);
    }

    /**
     */
    public void heyListen(br.ufu.sd.work.server.chord.ChordNode request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHeyListenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setPrevious(br.ufu.sd.work.server.chord.ChannelNode request,
        io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetPreviousMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChordServiceBlockingStub extends io.grpc.stub.AbstractStub<ChordServiceBlockingStub> {
    private ChordServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.ufu.sd.work.server.chord.ChannelNode heyListen(br.ufu.sd.work.server.chord.ChordNode request) {
      return blockingUnaryCall(
          getChannel(), getHeyListenMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.ufu.sd.work.server.chord.ChannelNode setPrevious(br.ufu.sd.work.server.chord.ChannelNode request) {
      return blockingUnaryCall(
          getChannel(), getSetPreviousMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChordServiceFutureStub extends io.grpc.stub.AbstractStub<ChordServiceFutureStub> {
    private ChordServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.ufu.sd.work.server.chord.ChannelNode> heyListen(
        br.ufu.sd.work.server.chord.ChordNode request) {
      return futureUnaryCall(
          getChannel().newCall(getHeyListenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.ufu.sd.work.server.chord.ChannelNode> setPrevious(
        br.ufu.sd.work.server.chord.ChannelNode request) {
      return futureUnaryCall(
          getChannel().newCall(getSetPreviousMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HEY_LISTEN = 0;
  private static final int METHODID_SET_PREVIOUS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChordServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChordServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HEY_LISTEN:
          serviceImpl.heyListen((br.ufu.sd.work.server.chord.ChordNode) request,
              (io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode>) responseObserver);
          break;
        case METHODID_SET_PREVIOUS:
          serviceImpl.setPrevious((br.ufu.sd.work.server.chord.ChannelNode) request,
              (io.grpc.stub.StreamObserver<br.ufu.sd.work.server.chord.ChannelNode>) responseObserver);
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

  private static abstract class ChordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChordServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.ufu.sd.work.server.chord.ChordServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChordService");
    }
  }

  private static final class ChordServiceFileDescriptorSupplier
      extends ChordServiceBaseDescriptorSupplier {
    ChordServiceFileDescriptorSupplier() {}
  }

  private static final class ChordServiceMethodDescriptorSupplier
      extends ChordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChordServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChordServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChordServiceFileDescriptorSupplier())
              .addMethod(getHeyListenMethod())
              .addMethod(getSetPreviousMethod())
              .build();
        }
      }
    }
    return result;
  }
}
