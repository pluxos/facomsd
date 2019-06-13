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
    comments = "Source: GrpcServer.proto")
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "io.grpc.Greeter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Create",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getCreateMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getCreateMethod;
    if ((getCreateMethod = GreeterGrpc.getCreateMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCreateMethod = GreeterGrpc.getCreateMethod) == null) {
          GreeterGrpc.getCreateMethod = getCreateMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.Greeter", "Create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("Create"))
                  .build();
          }
        }
     }
     return getCreateMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getGetMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getGetMethod;
    if ((getGetMethod = GreeterGrpc.getGetMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getGetMethod = GreeterGrpc.getGetMethod) == null) {
          GreeterGrpc.getGetMethod = getGetMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.Greeter", "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("Get"))
                  .build();
          }
        }
     }
     return getGetMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Update",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getUpdateMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getUpdateMethod;
    if ((getUpdateMethod = GreeterGrpc.getUpdateMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getUpdateMethod = GreeterGrpc.getUpdateMethod) == null) {
          GreeterGrpc.getUpdateMethod = getUpdateMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.Greeter", "Update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("Update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }

  private static volatile MethodDescriptor<GenericRequest,
      GenericResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = GenericRequest.class,
      responseType = GenericResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<GenericRequest,
      GenericResponse> getDeleteMethod() {
    MethodDescriptor<GenericRequest, GenericResponse> getDeleteMethod;
    if ((getDeleteMethod = GreeterGrpc.getDeleteMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getDeleteMethod = GreeterGrpc.getDeleteMethod) == null) {
          GreeterGrpc.getDeleteMethod = getDeleteMethod = 
              MethodDescriptor.<GenericRequest, GenericResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.Greeter", "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("Delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
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
                  "io.grpc.Greeter", "FindNode"))
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
                  "io.grpc.Greeter", "GetRange"))
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
                  "io.grpc.Greeter", "UpdateFT"))
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

  private static volatile MethodDescriptor<NewNodeRequest,
      NewNodeResponse> getNewNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewNode",
      requestType = NewNodeRequest.class,
      responseType = NewNodeResponse.class,
      methodType = MethodDescriptor.MethodType.UNARY)
  public static MethodDescriptor<NewNodeRequest,
      NewNodeResponse> getNewNodeMethod() {
    MethodDescriptor<NewNodeRequest, NewNodeResponse> getNewNodeMethod;
    if ((getNewNodeMethod = GreeterGrpc.getNewNodeMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getNewNodeMethod = GreeterGrpc.getNewNodeMethod) == null) {
          GreeterGrpc.getNewNodeMethod = getNewNodeMethod = 
              MethodDescriptor.<NewNodeRequest, NewNodeResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.Greeter", "NewNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  NewNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  NewNodeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("NewNode"))
                  .build();
          }
        }
     }
     return getNewNodeMethod;
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
    public void create(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void get(GenericRequest request,
                    io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void update(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
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

    /**
     */
    public void newNode(NewNodeRequest request,
                        io.grpc.stub.StreamObserver<NewNodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getNewNodeMethod(), responseObserver);
    }

    @Override public final ServerServiceDefinition bindService() {
      return ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_CREATE)))
          .addMethod(
            getGetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_GET)))
          .addMethod(
            getUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GenericRequest,
                GenericResponse>(
                  this, METHODID_DELETE)))
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
          .addMethod(
            getNewNodeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                NewNodeRequest,
                NewNodeResponse>(
                  this, METHODID_NEW_NODE)))
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
    public void create(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(GenericRequest request,
                    io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(GenericRequest request,
                       io.grpc.stub.StreamObserver<GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
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

    /**
     */
    public void newNode(NewNodeRequest request,
                        io.grpc.stub.StreamObserver<NewNodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNewNodeMethod(), getCallOptions()), request, responseObserver);
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
    public GenericResponse create(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse get(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse update(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public GenericResponse delete(GenericRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
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

    /**
     */
    public NewNodeResponse newNode(NewNodeRequest request) {
      return blockingUnaryCall(
          getChannel(), getNewNodeMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> create(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> get(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> update(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GenericResponse> delete(
        GenericRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
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

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<NewNodeResponse> newNode(
        NewNodeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNewNodeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;
  private static final int METHODID_FIND_NODE = 4;
  private static final int METHODID_GET_RANGE = 5;
  private static final int METHODID_UPDATE_FT = 6;
  private static final int METHODID_NEW_NODE = 7;

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
        case METHODID_CREATE:
          serviceImpl.create((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((GenericRequest) request,
              (io.grpc.stub.StreamObserver<GenericResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((GenericRequest) request,
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
        case METHODID_NEW_NODE:
          serviceImpl.newNode((NewNodeRequest) request,
              (io.grpc.stub.StreamObserver<NewNodeResponse>) responseObserver);
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
      return ServerProto.getDescriptor();
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
              .addMethod(getCreateMethod())
              .addMethod(getGetMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getFindNodeMethod())
              .addMethod(getGetRangeMethod())
              .addMethod(getUpdateFTMethod())
              .addMethod(getNewNodeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
