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
  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getCreateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateUser",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getCreateUserMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getCreateUserMethod;
    if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCreateUserMethod = GreeterGrpc.getCreateUserMethod) == null) {
          GreeterGrpc.getCreateUserMethod = getCreateUserMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "CreateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("CreateUser"))
                  .build();
          }
        }
     }
     return getCreateUserMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getGetUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUser",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getGetUserMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getGetUserMethod;
    if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGetUserMethod = GreeterGrpc.getGetUserMethod) == null) {
          GreeterGrpc.getGetUserMethod = getGetUserMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "GetUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("GetUser"))
                  .build();
          }
        }
     }
     return getGetUserMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getUpdateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateUser",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getUpdateUserMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getUpdateUserMethod;
    if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpdateUserMethod = GreeterGrpc.getUpdateUserMethod) == null) {
          GreeterGrpc.getUpdateUserMethod = getUpdateUserMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "UpdateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("UpdateUser"))
                  .build();
          }
        }
     }
     return getUpdateUserMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteUser",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getDeleteUserMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getDeleteUserMethod = GreeterGrpc.getDeleteUserMethod) == null) {
          GreeterGrpc.getDeleteUserMethod = getDeleteUserMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "DeleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("DeleteUser"))
                  .build();
          }
        }
     }
     return getDeleteUserMethod;
  }

  private static volatile MethodDescriptor<FindMessage,
      FindResponse> getFindNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindNode",
      requestType = FindMessage.class,
      responseType = FindResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<FindMessage,
      FindResponse> getFindNodeMethod() {
    MethodDescriptor<FindMessage, FindResponse> getFindNodeMethod;
    if ((getFindNodeMethod = GreeterGrpc.getFindNodeMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getFindNodeMethod = GreeterGrpc.getFindNodeMethod) == null) {
          GreeterGrpc.getFindNodeMethod = getFindNodeMethod = 
              MethodDescriptor.<FindMessage, FindResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "FindNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  FindMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  FindResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("FindNode"))
                  .build();
          }
        }
     }
     return getFindNodeMethod;
  }

  private static volatile MethodDescriptor<GetRangeRequest,
      GetRangeResponse> getGetRangeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRange",
      requestType = GetRangeRequest.class,
      responseType = GetRangeResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GetRangeRequest,
      GetRangeResponse> getGetRangeMethod() {
    MethodDescriptor<GetRangeRequest, GetRangeResponse> getGetRangeMethod;
    if ((getGetRangeMethod = GreeterGrpc.getGetRangeMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGetRangeMethod = GreeterGrpc.getGetRangeMethod) == null) {
          GreeterGrpc.getGetRangeMethod = getGetRangeMethod = 
              MethodDescriptor.<GetRangeRequest, GetRangeResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "GetRange"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GetRangeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GetRangeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("GetRange"))
                  .build();
          }
        }
     }
     return getGetRangeMethod;
  }

  private static volatile MethodDescriptor<UpdateFTRequest,
      UpdateFTResponse> getUpdateFTMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateFT",
      requestType = UpdateFTRequest.class,
      responseType = UpdateFTResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<UpdateFTRequest,
      UpdateFTResponse> getUpdateFTMethod() {
    MethodDescriptor<UpdateFTRequest, UpdateFTResponse> getUpdateFTMethod;
    if ((getUpdateFTMethod = GreeterGrpc.getUpdateFTMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpdateFTMethod = GreeterGrpc.getUpdateFTMethod) == null) {
          GreeterGrpc.getUpdateFTMethod = getUpdateFTMethod = 
              MethodDescriptor.<UpdateFTRequest, UpdateFTResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "UpdateFT"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  UpdateFTRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  UpdateFTResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("UpdateFT"))
                  .build();
          }
        }
     }
     return getUpdateFTMethod;
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
    public void createUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateUserMethod(), responseObserver);
    }

    /**
     */
    public void getUser(GenericRequest request,
                        io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserMethod(), responseObserver);
    }

    /**
     */
    public void updateUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateUserMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    /**
     */
    public void findNode(FindMessage request,
                         io.grpc.stub.StreamObserver<FindResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFindNodeMethod(), responseObserver);
    }

    /**
     */
    public void getRange(GetRangeRequest request,
                         io.grpc.stub.StreamObserver<GetRangeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRangeMethod(), responseObserver);
    }

    /**
     */
    public void updateFT(UpdateFTRequest request,
                         io.grpc.stub.StreamObserver<UpdateFTResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateFTMethod(), responseObserver);
    }

    @Override public final ServerServiceDefinition bindService() {
      return ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_CREATE_USER)))
          .addMethod(
            getGetUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_GET_USER)))
          .addMethod(
            getUpdateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_UPDATE_USER)))
          .addMethod(
            getDeleteUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_DELETE_USER)))
          .addMethod(
            getFindNodeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                FindMessage,
                FindResponse>(
                  this, METHODID_FIND_NODE)))
          .addMethod(
            getGetRangeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GetRangeRequest,
                GetRangeResponse>(
                  this, METHODID_GET_RANGE)))
          .addMethod(
            getUpdateFTMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                UpdateFTRequest,
                UpdateFTResponse>(
                  this, METHODID_UPDATE_FT)))
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
    public void createUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUser(GenericRequest request,
                        io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(GenericRequest request,
                           io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findNode(FindMessage request,
                         io.grpc.stub.StreamObserver<FindResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRange(GetRangeRequest request,
                         io.grpc.stub.StreamObserver<GetRangeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRangeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateFT(UpdateFTRequest request,
                         io.grpc.stub.StreamObserver<UpdateFTResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateFTMethod(), getCallOptions()), request, responseObserver);
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
    public GenericResponse createUser(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse getUser(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse updateUser(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse deleteUser(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public FindResponse findNode(FindMessage request) {
      return blockingUnaryCall(
          getChannel(), getFindNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public GetRangeResponse getRange(GetRangeRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRangeMethod(), getCallOptions(), request);
    }

    /**
     */
    public UpdateFTResponse updateFT(UpdateFTRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateFTMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> createUser(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> getUser(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> updateUser(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> deleteUser(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<FindResponse> findNode(
        FindMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getFindNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GetRangeResponse> getRange(
        GetRangeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRangeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<UpdateFTResponse> updateFT(
        UpdateFTRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateFTMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_USER = 0;
  private static final int METHODID_GET_USER = 1;
  private static final int METHODID_UPDATE_USER = 2;
  private static final int METHODID_DELETE_USER = 3;
  private static final int METHODID_FIND_NODE = 4;
  private static final int METHODID_GET_RANGE = 5;
  private static final int METHODID_UPDATE_FT = 6;

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
          serviceImpl.createUser((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_GET_USER:
          serviceImpl.getUser((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_FIND_NODE:
          serviceImpl.findNode((FindMessage) request,
              (io.grpc.stub.StreamObserver<FindResponse>) responseObserver);
          break;
        case METHODID_GET_RANGE:
          serviceImpl.getRange((GetRangeRequest) request,
              (io.grpc.stub.StreamObserver<GetRangeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_FT:
          serviceImpl.updateFT((UpdateFTRequest) request,
              (io.grpc.stub.StreamObserver<UpdateFTResponse>) responseObserver);
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
      return GrpcProto.getDescriptor();
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
              .addMethod(getFindNodeMethod())
              .addMethod(getGetRangeMethod())
              .addMethod(getUpdateFTMethod())
              .build();
        }
      }
    }
    return result;
  }
}
