package com.ufu.jvitoraa.interaction;

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
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: Command.proto")
public final class CommandServiceGrpc {

  private CommandServiceGrpc() {}

  public static final String SERVICE_NAME = "com.ufu.jvitoraa.interaction.CommandService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.CreateCommand,
      com.ufu.jvitoraa.interaction.Response> METHOD_CREATE = getCreateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.CreateCommand,
      com.ufu.jvitoraa.interaction.Response> getCreateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.CreateCommand,
      com.ufu.jvitoraa.interaction.Response> getCreateMethod() {
    return getCreateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.CreateCommand,
      com.ufu.jvitoraa.interaction.Response> getCreateMethodHelper() {
    io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.CreateCommand, com.ufu.jvitoraa.interaction.Response> getCreateMethod;
    if ((getCreateMethod = CommandServiceGrpc.getCreateMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getCreateMethod = CommandServiceGrpc.getCreateMethod) == null) {
          CommandServiceGrpc.getCreateMethod = getCreateMethod = 
              io.grpc.MethodDescriptor.<com.ufu.jvitoraa.interaction.CreateCommand, com.ufu.jvitoraa.interaction.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.ufu.jvitoraa.interaction.CommandService", "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.CreateCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new CommandServiceMethodDescriptorSupplier("create"))
                  .build();
          }
        }
     }
     return getCreateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getReadMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.ReadCommand,
      com.ufu.jvitoraa.interaction.Response> METHOD_READ = getReadMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.ReadCommand,
      com.ufu.jvitoraa.interaction.Response> getReadMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.ReadCommand,
      com.ufu.jvitoraa.interaction.Response> getReadMethod() {
    return getReadMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.ReadCommand,
      com.ufu.jvitoraa.interaction.Response> getReadMethodHelper() {
    io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.ReadCommand, com.ufu.jvitoraa.interaction.Response> getReadMethod;
    if ((getReadMethod = CommandServiceGrpc.getReadMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getReadMethod = CommandServiceGrpc.getReadMethod) == null) {
          CommandServiceGrpc.getReadMethod = getReadMethod = 
              io.grpc.MethodDescriptor.<com.ufu.jvitoraa.interaction.ReadCommand, com.ufu.jvitoraa.interaction.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.ufu.jvitoraa.interaction.CommandService", "read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.ReadCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new CommandServiceMethodDescriptorSupplier("read"))
                  .build();
          }
        }
     }
     return getReadMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.UpdateCommand,
      com.ufu.jvitoraa.interaction.Response> METHOD_UPDATE = getUpdateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.UpdateCommand,
      com.ufu.jvitoraa.interaction.Response> getUpdateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.UpdateCommand,
      com.ufu.jvitoraa.interaction.Response> getUpdateMethod() {
    return getUpdateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.UpdateCommand,
      com.ufu.jvitoraa.interaction.Response> getUpdateMethodHelper() {
    io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.UpdateCommand, com.ufu.jvitoraa.interaction.Response> getUpdateMethod;
    if ((getUpdateMethod = CommandServiceGrpc.getUpdateMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getUpdateMethod = CommandServiceGrpc.getUpdateMethod) == null) {
          CommandServiceGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<com.ufu.jvitoraa.interaction.UpdateCommand, com.ufu.jvitoraa.interaction.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.ufu.jvitoraa.interaction.CommandService", "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.UpdateCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new CommandServiceMethodDescriptorSupplier("update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDeleteMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.DeleteCommand,
      com.ufu.jvitoraa.interaction.Response> METHOD_DELETE = getDeleteMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.DeleteCommand,
      com.ufu.jvitoraa.interaction.Response> getDeleteMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.DeleteCommand,
      com.ufu.jvitoraa.interaction.Response> getDeleteMethod() {
    return getDeleteMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.DeleteCommand,
      com.ufu.jvitoraa.interaction.Response> getDeleteMethodHelper() {
    io.grpc.MethodDescriptor<com.ufu.jvitoraa.interaction.DeleteCommand, com.ufu.jvitoraa.interaction.Response> getDeleteMethod;
    if ((getDeleteMethod = CommandServiceGrpc.getDeleteMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getDeleteMethod = CommandServiceGrpc.getDeleteMethod) == null) {
          CommandServiceGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<com.ufu.jvitoraa.interaction.DeleteCommand, com.ufu.jvitoraa.interaction.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.ufu.jvitoraa.interaction.CommandService", "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.DeleteCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ufu.jvitoraa.interaction.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new CommandServiceMethodDescriptorSupplier("delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CommandServiceStub newStub(io.grpc.Channel channel) {
    return new CommandServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CommandServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CommandServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CommandServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CommandServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CommandServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(com.ufu.jvitoraa.interaction.CreateCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMethodHelper(), responseObserver);
    }

    /**
     */
    public void read(com.ufu.jvitoraa.interaction.ReadCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMethodHelper(), responseObserver);
    }

    /**
     */
    public void update(com.ufu.jvitoraa.interaction.UpdateCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethodHelper(), responseObserver);
    }

    /**
     */
    public void delete(com.ufu.jvitoraa.interaction.DeleteCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ufu.jvitoraa.interaction.CreateCommand,
                com.ufu.jvitoraa.interaction.Response>(
                  this, METHODID_CREATE)))
          .addMethod(
            getReadMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ufu.jvitoraa.interaction.ReadCommand,
                com.ufu.jvitoraa.interaction.Response>(
                  this, METHODID_READ)))
          .addMethod(
            getUpdateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ufu.jvitoraa.interaction.UpdateCommand,
                com.ufu.jvitoraa.interaction.Response>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ufu.jvitoraa.interaction.DeleteCommand,
                com.ufu.jvitoraa.interaction.Response>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class CommandServiceStub extends io.grpc.stub.AbstractStub<CommandServiceStub> {
    private CommandServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceStub(channel, callOptions);
    }

    /**
     */
    public void create(com.ufu.jvitoraa.interaction.CreateCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(com.ufu.jvitoraa.interaction.ReadCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(com.ufu.jvitoraa.interaction.UpdateCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.ufu.jvitoraa.interaction.DeleteCommand request,
        io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CommandServiceBlockingStub extends io.grpc.stub.AbstractStub<CommandServiceBlockingStub> {
    private CommandServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.ufu.jvitoraa.interaction.Response create(com.ufu.jvitoraa.interaction.CreateCommand request) {
      return blockingUnaryCall(
          getChannel(), getCreateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public com.ufu.jvitoraa.interaction.Response read(com.ufu.jvitoraa.interaction.ReadCommand request) {
      return blockingUnaryCall(
          getChannel(), getReadMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public com.ufu.jvitoraa.interaction.Response update(com.ufu.jvitoraa.interaction.UpdateCommand request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public com.ufu.jvitoraa.interaction.Response delete(com.ufu.jvitoraa.interaction.DeleteCommand request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CommandServiceFutureStub extends io.grpc.stub.AbstractStub<CommandServiceFutureStub> {
    private CommandServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ufu.jvitoraa.interaction.Response> create(
        com.ufu.jvitoraa.interaction.CreateCommand request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ufu.jvitoraa.interaction.Response> read(
        com.ufu.jvitoraa.interaction.ReadCommand request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ufu.jvitoraa.interaction.Response> update(
        com.ufu.jvitoraa.interaction.UpdateCommand request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ufu.jvitoraa.interaction.Response> delete(
        com.ufu.jvitoraa.interaction.DeleteCommand request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request);
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
    private final CommandServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CommandServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((com.ufu.jvitoraa.interaction.CreateCommand) request,
              (io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((com.ufu.jvitoraa.interaction.ReadCommand) request,
              (io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((com.ufu.jvitoraa.interaction.UpdateCommand) request,
              (io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.ufu.jvitoraa.interaction.DeleteCommand) request,
              (io.grpc.stub.StreamObserver<com.ufu.jvitoraa.interaction.Response>) responseObserver);
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

  private static abstract class CommandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CommandServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ufu.jvitoraa.interaction.Command.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CommandService");
    }
  }

  private static final class CommandServiceFileDescriptorSupplier
      extends CommandServiceBaseDescriptorSupplier {
    CommandServiceFileDescriptorSupplier() {}
  }

  private static final class CommandServiceMethodDescriptorSupplier
      extends CommandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CommandServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CommandServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CommandServiceFileDescriptorSupplier())
              .addMethod(getCreateMethodHelper())
              .addMethod(getReadMethodHelper())
              .addMethod(getUpdateMethodHelper())
              .addMethod(getDeleteMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
