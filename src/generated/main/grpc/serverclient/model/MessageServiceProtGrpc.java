package serverclient.model;

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
    value = "by gRPC proto compiler (version 1.21.0)",
    comments = "Source: message.proto")
public final class MessageServiceProtGrpc {

  private MessageServiceProtGrpc() {}

  public static final String SERVICE_NAME = "model.MessageServiceProt";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getCreateMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createMessage",
      requestType = serverclient.model.Message.class,
      responseType = serverclient.model.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getCreateMessageMethod() {
    io.grpc.MethodDescriptor<serverclient.model.Message, serverclient.model.Message> getCreateMessageMethod;
    if ((getCreateMessageMethod = MessageServiceProtGrpc.getCreateMessageMethod) == null) {
      synchronized (MessageServiceProtGrpc.class) {
        if ((getCreateMessageMethod = MessageServiceProtGrpc.getCreateMessageMethod) == null) {
          MessageServiceProtGrpc.getCreateMessageMethod = getCreateMessageMethod = 
              io.grpc.MethodDescriptor.<serverclient.model.Message, serverclient.model.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.MessageServiceProt", "createMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
                  .setSchemaDescriptor(new MessageServiceProtMethodDescriptorSupplier("createMessage"))
                  .build();
          }
        }
     }
     return getCreateMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getReadMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readMessage",
      requestType = serverclient.model.Message.class,
      responseType = serverclient.model.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getReadMessageMethod() {
    io.grpc.MethodDescriptor<serverclient.model.Message, serverclient.model.Message> getReadMessageMethod;
    if ((getReadMessageMethod = MessageServiceProtGrpc.getReadMessageMethod) == null) {
      synchronized (MessageServiceProtGrpc.class) {
        if ((getReadMessageMethod = MessageServiceProtGrpc.getReadMessageMethod) == null) {
          MessageServiceProtGrpc.getReadMessageMethod = getReadMessageMethod = 
              io.grpc.MethodDescriptor.<serverclient.model.Message, serverclient.model.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.MessageServiceProt", "readMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
                  .setSchemaDescriptor(new MessageServiceProtMethodDescriptorSupplier("readMessage"))
                  .build();
          }
        }
     }
     return getReadMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getUpdateMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateMessage",
      requestType = serverclient.model.Message.class,
      responseType = serverclient.model.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getUpdateMessageMethod() {
    io.grpc.MethodDescriptor<serverclient.model.Message, serverclient.model.Message> getUpdateMessageMethod;
    if ((getUpdateMessageMethod = MessageServiceProtGrpc.getUpdateMessageMethod) == null) {
      synchronized (MessageServiceProtGrpc.class) {
        if ((getUpdateMessageMethod = MessageServiceProtGrpc.getUpdateMessageMethod) == null) {
          MessageServiceProtGrpc.getUpdateMessageMethod = getUpdateMessageMethod = 
              io.grpc.MethodDescriptor.<serverclient.model.Message, serverclient.model.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.MessageServiceProt", "updateMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
                  .setSchemaDescriptor(new MessageServiceProtMethodDescriptorSupplier("updateMessage"))
                  .build();
          }
        }
     }
     return getUpdateMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getDeleteMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteMessage",
      requestType = serverclient.model.Message.class,
      responseType = serverclient.model.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<serverclient.model.Message,
      serverclient.model.Message> getDeleteMessageMethod() {
    io.grpc.MethodDescriptor<serverclient.model.Message, serverclient.model.Message> getDeleteMessageMethod;
    if ((getDeleteMessageMethod = MessageServiceProtGrpc.getDeleteMessageMethod) == null) {
      synchronized (MessageServiceProtGrpc.class) {
        if ((getDeleteMessageMethod = MessageServiceProtGrpc.getDeleteMessageMethod) == null) {
          MessageServiceProtGrpc.getDeleteMessageMethod = getDeleteMessageMethod = 
              io.grpc.MethodDescriptor.<serverclient.model.Message, serverclient.model.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "model.MessageServiceProt", "deleteMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  serverclient.model.Message.getDefaultInstance()))
                  .setSchemaDescriptor(new MessageServiceProtMethodDescriptorSupplier("deleteMessage"))
                  .build();
          }
        }
     }
     return getDeleteMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessageServiceProtStub newStub(io.grpc.Channel channel) {
    return new MessageServiceProtStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessageServiceProtBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MessageServiceProtBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessageServiceProtFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MessageServiceProtFutureStub(channel);
  }

  /**
   */
  public static abstract class MessageServiceProtImplBase implements io.grpc.BindableService {

    /**
     */
    public void createMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMessageMethod(), responseObserver);
    }

    /**
     */
    public void readMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMessageMethod(), responseObserver);
    }

    /**
     */
    public void updateMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMessageMethod(), responseObserver);
    }

    /**
     */
    public void deleteMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMessageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                serverclient.model.Message,
                serverclient.model.Message>(
                  this, METHODID_CREATE_MESSAGE)))
          .addMethod(
            getReadMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                serverclient.model.Message,
                serverclient.model.Message>(
                  this, METHODID_READ_MESSAGE)))
          .addMethod(
            getUpdateMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                serverclient.model.Message,
                serverclient.model.Message>(
                  this, METHODID_UPDATE_MESSAGE)))
          .addMethod(
            getDeleteMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                serverclient.model.Message,
                serverclient.model.Message>(
                  this, METHODID_DELETE_MESSAGE)))
          .build();
    }
  }

  /**
   */
  public static final class MessageServiceProtStub extends io.grpc.stub.AbstractStub<MessageServiceProtStub> {
    private MessageServiceProtStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceProtStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceProtStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceProtStub(channel, callOptions);
    }

    /**
     */
    public void createMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteMessage(serverclient.model.Message request,
        io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMessageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MessageServiceProtBlockingStub extends io.grpc.stub.AbstractStub<MessageServiceProtBlockingStub> {
    private MessageServiceProtBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceProtBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceProtBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceProtBlockingStub(channel, callOptions);
    }

    /**
     */
    public serverclient.model.Message createMessage(serverclient.model.Message request) {
      return blockingUnaryCall(
          getChannel(), getCreateMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public serverclient.model.Message readMessage(serverclient.model.Message request) {
      return blockingUnaryCall(
          getChannel(), getReadMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public serverclient.model.Message updateMessage(serverclient.model.Message request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public serverclient.model.Message deleteMessage(serverclient.model.Message request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessageServiceProtFutureStub extends io.grpc.stub.AbstractStub<MessageServiceProtFutureStub> {
    private MessageServiceProtFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceProtFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceProtFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceProtFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<serverclient.model.Message> createMessage(
        serverclient.model.Message request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<serverclient.model.Message> readMessage(
        serverclient.model.Message request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<serverclient.model.Message> updateMessage(
        serverclient.model.Message request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<serverclient.model.Message> deleteMessage(
        serverclient.model.Message request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_MESSAGE = 0;
  private static final int METHODID_READ_MESSAGE = 1;
  private static final int METHODID_UPDATE_MESSAGE = 2;
  private static final int METHODID_DELETE_MESSAGE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessageServiceProtImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessageServiceProtImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_MESSAGE:
          serviceImpl.createMessage((serverclient.model.Message) request,
              (io.grpc.stub.StreamObserver<serverclient.model.Message>) responseObserver);
          break;
        case METHODID_READ_MESSAGE:
          serviceImpl.readMessage((serverclient.model.Message) request,
              (io.grpc.stub.StreamObserver<serverclient.model.Message>) responseObserver);
          break;
        case METHODID_UPDATE_MESSAGE:
          serviceImpl.updateMessage((serverclient.model.Message) request,
              (io.grpc.stub.StreamObserver<serverclient.model.Message>) responseObserver);
          break;
        case METHODID_DELETE_MESSAGE:
          serviceImpl.deleteMessage((serverclient.model.Message) request,
              (io.grpc.stub.StreamObserver<serverclient.model.Message>) responseObserver);
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

  private static abstract class MessageServiceProtBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessageServiceProtBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return serverclient.model.MessageProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MessageServiceProt");
    }
  }

  private static final class MessageServiceProtFileDescriptorSupplier
      extends MessageServiceProtBaseDescriptorSupplier {
    MessageServiceProtFileDescriptorSupplier() {}
  }

  private static final class MessageServiceProtMethodDescriptorSupplier
      extends MessageServiceProtBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MessageServiceProtMethodDescriptorSupplier(String methodName) {
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
      synchronized (MessageServiceProtGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessageServiceProtFileDescriptorSupplier())
              .addMethod(getCreateMessageMethod())
              .addMethod(getReadMessageMethod())
              .addMethod(getUpdateMessageMethod())
              .addMethod(getDeleteMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
