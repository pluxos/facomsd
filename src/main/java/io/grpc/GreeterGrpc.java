package io.grpc;

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
  private static volatile MethodDescriptor<io.grpc.CreateRequest,
      io.grpc.CreateResponse> getCreateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateUser",
      requestType = io.grpc.CreateRequest.class,
      responseType = io.grpc.CreateResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<io.grpc.CreateRequest,
      io.grpc.CreateResponse> getCreateUserMethod() {
    MethodDescriptor<io.grpc.CreateRequest, io.grpc.CreateResponse> getCreateUserMethod;
    if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
          GreeterGrpc.getCreateUserMethod = getCreateUserMethod = 
              MethodDescriptor.<io.grpc.CreateRequest, io.grpc.CreateResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "CreateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.CreateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.CreateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("CreateUser"))
                  .build();
          }
        }
     }
     return getCreateUserMethod;
  }

  private static volatile MethodDescriptor<io.grpc.GetRequest,
      io.grpc.GetResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUser",
      requestType = io.grpc.GetRequest.class,
      responseType = io.grpc.GetResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<io.grpc.GetRequest,
      io.grpc.GetResponse> getGetUserMethod() {
    MethodDescriptor<io.grpc.GetRequest, io.grpc.GetResponse> getGetUserMethod;
    if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
          GreeterGrpc.getGetUserMethod = getGetUserMethod = 
              MethodDescriptor.<io.grpc.GetRequest, io.grpc.GetResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "GetUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.GetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.GetResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("GetUser"))
                  .build();
          }
        }
     }
     return getGetUserMethod;
  }

  private static volatile MethodDescriptor<io.grpc.UpdateRequest,
      io.grpc.UpdateResponse> getUpdateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateUser",
      requestType = io.grpc.UpdateRequest.class,
      responseType = io.grpc.UpdateResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<io.grpc.UpdateRequest,
      io.grpc.UpdateResponse> getUpdateUserMethod() {
    MethodDescriptor<io.grpc.UpdateRequest, io.grpc.UpdateResponse> getUpdateUserMethod;
    if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
          GreeterGrpc.getUpdateUserMethod = getUpdateUserMethod = 
              MethodDescriptor.<io.grpc.UpdateRequest, io.grpc.UpdateResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "UpdateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.UpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.UpdateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("UpdateUser"))
                  .build();
          }
        }
     }
     return getUpdateUserMethod;
  }

  private static volatile MethodDescriptor<io.grpc.DeleteRequest,
      io.grpc.DeleteResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteUser",
      requestType = io.grpc.DeleteRequest.class,
      responseType = io.grpc.DeleteResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<io.grpc.DeleteRequest,
      io.grpc.DeleteResponse> getDeleteUserMethod() {
    MethodDescriptor<io.grpc.DeleteRequest, io.grpc.DeleteResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
          GreeterGrpc.getDeleteUserMethod = getDeleteUserMethod = 
              MethodDescriptor.<io.grpc.DeleteRequest, io.grpc.DeleteResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "DeleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.DeleteResponse.getDefaultInstance()))
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
  public static GreeterStub newStub(Channel channel) {
    return new GreeterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
      Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
      Channel channel) {
    return new GreeterFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class GreeterImplBase implements BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void createUser(io.grpc.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.CreateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateUserMethod(), responseObserver);
    }

    /**
     */
    public void getUser(io.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<io.grpc.GetResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    public void updateUser(io.grpc.UpdateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateUserMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(io.grpc.DeleteRequest request,
        io.grpc.stub.StreamObserver<io.grpc.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @Override public final ServerServiceDefinition bindService() {
      return ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.CreateRequest,
                io.grpc.CreateResponse>(
                  this, METHODID_CREATE_USER)))
          .addMethod(
            getGetUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.GetRequest,
                io.grpc.GetResponse>(
                  this, METHODID_GET_USER)))
          .addMethod(
            getUpdateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.UpdateRequest,
                io.grpc.UpdateResponse>(
                  this, METHODID_UPDATE_USER)))
          .addMethod(
            getDeleteUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.DeleteRequest,
                io.grpc.DeleteResponse>(
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
    private GreeterStub(Channel channel) {
      super(channel);
    }

    private GreeterStub(Channel channel,
                        CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterStub build(Channel channel,
                                CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void createUser(io.grpc.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.CreateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(io.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<io.grpc.GetResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(io.grpc.UpdateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(io.grpc.DeleteRequest request,
        io.grpc.stub.StreamObserver<io.grpc.DeleteResponse> responseObserver) {
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
    private GreeterBlockingStub(Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(Channel channel,
                                CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterBlockingStub build(Channel channel,
                                        CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public io.grpc.CreateResponse createUser(io.grpc.CreateRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.GetResponse getUser(io.grpc.GetRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.UpdateResponse updateUser(io.grpc.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.DeleteResponse deleteUser(io.grpc.DeleteRequest request) {
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
    private GreeterFutureStub(Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(Channel channel,
                              CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterFutureStub build(Channel channel,
                                      CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.CreateResponse> createUser(
        io.grpc.CreateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.GetResponse> getUser(
        io.grpc.GetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.UpdateResponse> updateUser(
        io.grpc.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.DeleteResponse> deleteUser(
        io.grpc.DeleteRequest request) {
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

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_USER:
          serviceImpl.createUser((io.grpc.CreateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.CreateResponse>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((io.grpc.GetRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.GetResponse>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((io.grpc.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.UpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((io.grpc.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.DeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
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

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.HelloWorldProto.getDescriptor();
    }

    @Override
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

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile ServiceDescriptor serviceDescriptor;

  public static ServiceDescriptor getServiceDescriptor() {
    ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME)
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
