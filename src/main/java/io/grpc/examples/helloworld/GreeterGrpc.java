package io.grpc.examples.helloworld;

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
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.19.0)",
        comments = "Source: helloworld.proto")
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "helloworld.Greeter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
          io.grpc.examples.helloworld.CreateResponse> getCreateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "CreateUser",
          requestType = io.grpc.examples.helloworld.CreateRequest.class,
          responseType = io.grpc.examples.helloworld.CreateResponse.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
          io.grpc.examples.helloworld.CreateResponse> getCreateUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest, io.grpc.examples.helloworld.CreateResponse> getCreateUserMethod;
    if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
          GreeterGrpc.getCreateUserMethod = getCreateUserMethod =
                  io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.CreateRequest, io.grpc.examples.helloworld.CreateResponse>newBuilder()
                          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                          .setFullMethodName(generateFullMethodName(
                                  "helloworld.Greeter", "CreateUser"))
                          .setSampledToLocalTracing(true)
                          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.CreateRequest.getDefaultInstance()))
                          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.CreateResponse.getDefaultInstance()))
                          .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("CreateUser"))
                          .build();
        }
      }
    }
    return getCreateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.GetRequest,
          io.grpc.examples.helloworld.GetResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "GetUser",
          requestType = io.grpc.examples.helloworld.GetRequest.class,
          responseType = io.grpc.examples.helloworld.GetResponse.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.GetRequest,
          io.grpc.examples.helloworld.GetResponse> getGetUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.GetRequest, io.grpc.examples.helloworld.GetResponse> getGetUserMethod;
    if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
          GreeterGrpc.getGetUserMethod = getGetUserMethod =
                  io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.GetRequest, io.grpc.examples.helloworld.GetResponse>newBuilder()
                          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                          .setFullMethodName(generateFullMethodName(
                                  "helloworld.Greeter", "GetUser"))
                          .setSampledToLocalTracing(true)
                          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.GetRequest.getDefaultInstance()))
                          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.GetResponse.getDefaultInstance()))
                          .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("GetUser"))
                          .build();
        }
      }
    }
    return getGetUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.UpdateRequest,
          io.grpc.examples.helloworld.UpdateResponse> getUpdateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "UpdateUser",
          requestType = io.grpc.examples.helloworld.UpdateRequest.class,
          responseType = io.grpc.examples.helloworld.UpdateResponse.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.UpdateRequest,
          io.grpc.examples.helloworld.UpdateResponse> getUpdateUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.UpdateRequest, io.grpc.examples.helloworld.UpdateResponse> getUpdateUserMethod;
    if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
          GreeterGrpc.getUpdateUserMethod = getUpdateUserMethod =
                  io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.UpdateRequest, io.grpc.examples.helloworld.UpdateResponse>newBuilder()
                          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                          .setFullMethodName(generateFullMethodName(
                                  "helloworld.Greeter", "UpdateUser"))
                          .setSampledToLocalTracing(true)
                          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.UpdateRequest.getDefaultInstance()))
                          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.UpdateResponse.getDefaultInstance()))
                          .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("UpdateUser"))
                          .build();
        }
      }
    }
    return getUpdateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.DeleteRequest,
          io.grpc.examples.helloworld.DeleteResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "DeleteUser",
          requestType = io.grpc.examples.helloworld.DeleteRequest.class,
          responseType = io.grpc.examples.helloworld.DeleteResponse.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.DeleteRequest,
          io.grpc.examples.helloworld.DeleteResponse> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.DeleteRequest, io.grpc.examples.helloworld.DeleteResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
          GreeterGrpc.getDeleteUserMethod = getDeleteUserMethod =
                  io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.DeleteRequest, io.grpc.examples.helloworld.DeleteResponse>newBuilder()
                          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                          .setFullMethodName(generateFullMethodName(
                                  "helloworld.Greeter", "DeleteUser"))
                          .setSampledToLocalTracing(true)
                          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.DeleteRequest.getDefaultInstance()))
                          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                  io.grpc.examples.helloworld.DeleteResponse.getDefaultInstance()))
                          .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("DeleteUser"))
                          .build();
        }
      }
    }
    return getDeleteUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreeterStub newStub(io.grpc.Channel channel) {
    return new GreeterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
          io.grpc.Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
          io.grpc.Channel channel) {
    return new GreeterFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class GreeterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void createUser(io.grpc.examples.helloworld.CreateRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateUserMethod(), responseObserver);
    }

    /**
     */
    public void getUser(io.grpc.examples.helloworld.GetRequest request,
                        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.GetResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    public void updateUser(io.grpc.examples.helloworld.UpdateRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateUserMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(io.grpc.examples.helloworld.DeleteRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
              .addMethod(
                      getCreateUserMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      io.grpc.examples.helloworld.CreateRequest,
                                      io.grpc.examples.helloworld.CreateResponse>(
                                      this, METHODID_CREATE_USER)))
              .addMethod(
                      getGetUserMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      io.grpc.examples.helloworld.GetRequest,
                                      io.grpc.examples.helloworld.GetResponse>(
                                      this, METHODID_GET_USER)))
              .addMethod(
                      getUpdateUserMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      io.grpc.examples.helloworld.UpdateRequest,
                                      io.grpc.examples.helloworld.UpdateResponse>(
                                      this, METHODID_UPDATE_USER)))
              .addMethod(
                      getDeleteUserMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      io.grpc.examples.helloworld.DeleteRequest,
                                      io.grpc.examples.helloworld.DeleteResponse>(
                                      this, METHODID_DELETE_USER)))
              .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterStub extends io.grpc.stub.AbstractStub<GreeterStub> {
    private GreeterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterStub(io.grpc.Channel channel,
                        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterStub build(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void createUser(io.grpc.examples.helloworld.CreateRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateResponse> responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getCreateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(io.grpc.examples.helloworld.GetRequest request,
                        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.GetResponse> responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(io.grpc.examples.helloworld.UpdateRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.UpdateResponse> responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(io.grpc.examples.helloworld.DeleteRequest request,
                           io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.DeleteResponse> responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterBlockingStub extends io.grpc.stub.AbstractStub<GreeterBlockingStub> {
    private GreeterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(io.grpc.Channel channel,
                                io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterBlockingStub build(io.grpc.Channel channel,
                                        io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public io.grpc.examples.helloworld.CreateResponse createUser(io.grpc.examples.helloworld.CreateRequest request) {
      return blockingUnaryCall(
              getChannel(), getCreateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.GetResponse getUser(io.grpc.examples.helloworld.GetRequest request) {
      return blockingUnaryCall(
              getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.UpdateResponse updateUser(io.grpc.examples.helloworld.UpdateRequest request) {
      return blockingUnaryCall(
              getChannel(), getUpdateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.DeleteResponse deleteUser(io.grpc.examples.helloworld.DeleteRequest request) {
      return blockingUnaryCall(
              getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterFutureStub extends io.grpc.stub.AbstractStub<GreeterFutureStub> {
    private GreeterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(io.grpc.Channel channel,
                              io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterFutureStub build(io.grpc.Channel channel,
                                      io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.CreateResponse> createUser(
            io.grpc.examples.helloworld.CreateRequest request) {
      return futureUnaryCall(
              getChannel().newCall(getCreateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.GetResponse> getUser(
            io.grpc.examples.helloworld.GetRequest request) {
      return futureUnaryCall(
              getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.UpdateResponse> updateUser(
            io.grpc.examples.helloworld.UpdateRequest request) {
      return futureUnaryCall(
              getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.DeleteResponse> deleteUser(
            io.grpc.examples.helloworld.DeleteRequest request) {
      return futureUnaryCall(
              getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_USER = 0;
  private static final int METHODID_GET_USER = 1;
  private static final int METHODID_UPDATE_USER = 2;
  private static final int METHODID_DELETE_USER = 3;

  private static final class MethodHandlers<Req, Resp> implements
          io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreeterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_USER:
          serviceImpl.createUser((io.grpc.examples.helloworld.CreateRequest) request,
                  (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateResponse>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((io.grpc.examples.helloworld.GetRequest) request,
                  (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.GetResponse>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((io.grpc.examples.helloworld.UpdateRequest) request,
                  (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.UpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((io.grpc.examples.helloworld.DeleteRequest) request,
                  (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.DeleteResponse>) responseObserver);
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

  private static abstract class GreeterBaseDescriptorSupplier
          implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreeterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.helloworld.HelloWorldProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greeter");
    }
  }

  private static final class GreeterFileDescriptorSupplier
          extends GreeterBaseDescriptorSupplier {
    GreeterFileDescriptorSupplier() {}
  }

  private static final class GreeterMethodDescriptorSupplier
          extends GreeterBaseDescriptorSupplier
          implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreeterMethodDescriptorSupplier(String methodName) {
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
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                  .setSchemaDescriptor(new GreeterFileDescriptorSupplier())
                  .addMethod(getCreateUserMethod())
                  .addMethod(getGetUserMethod())
                  .addMethod(getUpdateUserMethod())
                  .addMethod(getDeleteUserMethod())
                  .build();
        }
      }
    }
    return result;
  }
}
