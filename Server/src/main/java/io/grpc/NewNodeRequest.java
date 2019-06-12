// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GrpcServer.proto

package io.grpc;

/**
 * Protobuf type {@code io.grpc.NewNodeRequest}
 */
public  final class NewNodeRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.grpc.NewNodeRequest)
    NewNodeRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NewNodeRequest.newBuilder() to construct.
  private NewNodeRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NewNodeRequest() {
    node_ = "";
    newNode_ = "";
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private NewNodeRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            String s = input.readStringRequireUtf8();

            node_ = s;
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            newNode_ = s;
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ServerProto.internal_static_io_grpc_NewNodeRequest_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ServerProto.internal_static_io_grpc_NewNodeRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            NewNodeRequest.class, Builder.class);
  }

  public static final int NODE_FIELD_NUMBER = 1;
  private volatile Object node_;
  /**
   * <code>string node = 1;</code>
   */
  public String getNode() {
    Object ref = node_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      node_ = s;
      return s;
    }
  }
  /**
   * <code>string node = 1;</code>
   */
  public com.google.protobuf.ByteString
      getNodeBytes() {
    Object ref = node_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      node_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NEWNODE_FIELD_NUMBER = 2;
  private volatile Object newNode_;
  /**
   * <code>string newNode = 2;</code>
   */
  public String getNewNode() {
    Object ref = newNode_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      newNode_ = s;
      return s;
    }
  }
  /**
   * <code>string newNode = 2;</code>
   */
  public com.google.protobuf.ByteString
      getNewNodeBytes() {
    Object ref = newNode_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      newNode_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getNodeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, node_);
    }
    if (!getNewNodeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, newNode_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getNodeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, node_);
    }
    if (!getNewNodeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, newNode_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof NewNodeRequest)) {
      return super.equals(obj);
    }
    NewNodeRequest other = (NewNodeRequest) obj;

    boolean result = true;
    result = result && getNode()
        .equals(other.getNode());
    result = result && getNewNode()
        .equals(other.getNewNode());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NODE_FIELD_NUMBER;
    hash = (53 * hash) + getNode().hashCode();
    hash = (37 * hash) + NEWNODE_FIELD_NUMBER;
    hash = (53 * hash) + getNewNode().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static NewNodeRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static NewNodeRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static NewNodeRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static NewNodeRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static NewNodeRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static NewNodeRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static NewNodeRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static NewNodeRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static NewNodeRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static NewNodeRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static NewNodeRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static NewNodeRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(NewNodeRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code io.grpc.NewNodeRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.grpc.NewNodeRequest)
      NewNodeRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ServerProto.internal_static_io_grpc_NewNodeRequest_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ServerProto.internal_static_io_grpc_NewNodeRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              NewNodeRequest.class, Builder.class);
    }

    // Construct using io.grpc.NewNodeRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      node_ = "";

      newNode_ = "";

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ServerProto.internal_static_io_grpc_NewNodeRequest_descriptor;
    }

    @Override
    public NewNodeRequest getDefaultInstanceForType() {
      return NewNodeRequest.getDefaultInstance();
    }

    @Override
    public NewNodeRequest build() {
      NewNodeRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public NewNodeRequest buildPartial() {
      NewNodeRequest result = new NewNodeRequest(this);
      result.node_ = node_;
      result.newNode_ = newNode_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof NewNodeRequest) {
        return mergeFrom((NewNodeRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(NewNodeRequest other) {
      if (other == NewNodeRequest.getDefaultInstance()) return this;
      if (!other.getNode().isEmpty()) {
        node_ = other.node_;
        onChanged();
      }
      if (!other.getNewNode().isEmpty()) {
        newNode_ = other.newNode_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      NewNodeRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (NewNodeRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private Object node_ = "";
    /**
     * <code>string node = 1;</code>
     */
    public String getNode() {
      Object ref = node_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        node_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string node = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNodeBytes() {
      Object ref = node_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        node_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string node = 1;</code>
     */
    public Builder setNode(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      node_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string node = 1;</code>
     */
    public Builder clearNode() {
      
      node_ = getDefaultInstance().getNode();
      onChanged();
      return this;
    }
    /**
     * <code>string node = 1;</code>
     */
    public Builder setNodeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      node_ = value;
      onChanged();
      return this;
    }

    private Object newNode_ = "";
    /**
     * <code>string newNode = 2;</code>
     */
    public String getNewNode() {
      Object ref = newNode_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        newNode_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string newNode = 2;</code>
     */
    public com.google.protobuf.ByteString
        getNewNodeBytes() {
      Object ref = newNode_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        newNode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string newNode = 2;</code>
     */
    public Builder setNewNode(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      newNode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string newNode = 2;</code>
     */
    public Builder clearNewNode() {
      
      newNode_ = getDefaultInstance().getNewNode();
      onChanged();
      return this;
    }
    /**
     * <code>string newNode = 2;</code>
     */
    public Builder setNewNodeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      newNode_ = value;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:io.grpc.NewNodeRequest)
  }

  // @@protoc_insertion_point(class_scope:io.grpc.NewNodeRequest)
  private static final NewNodeRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new NewNodeRequest();
  }

  public static NewNodeRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NewNodeRequest>
      PARSER = new com.google.protobuf.AbstractParser<NewNodeRequest>() {
    @Override
    public NewNodeRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new NewNodeRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NewNodeRequest> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<NewNodeRequest> getParserForType() {
    return PARSER;
  }

  @Override
  public NewNodeRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

