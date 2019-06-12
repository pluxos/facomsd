// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GrpcServer.proto

package io.grpc;

public final class ServerProto {
  private ServerProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_GenericRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_GenericRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_GenericResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_GenericResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_FindMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_FindMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_FindResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_FindResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_GetRangeRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_GetRangeRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_GetRangeResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_GetRangeResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_UpdateFTRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_UpdateFTRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_UpdateFTResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_UpdateFTResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_NewNodeRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_NewNodeRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_io_grpc_NewNodeResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_io_grpc_NewNodeResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\020GrpcServer.proto\022\007io.grpc\",\n\016GenericRe" +
      "quest\022\014\n\004code\030\001 \001(\005\022\014\n\004data\030\002 \001(\t\"@\n\017Gen" +
      "ericResponse\022\016\n\006status\030\001 \001(\t\022\017\n\007message\030" +
      "\002 \001(\t\022\014\n\004data\030\003 \001(\t\"\032\n\013FindMessage\022\013\n\003ke" +
      "y\030\001 \001(\005\":\n\014FindResponse\022\020\n\010response\030\001 \001(" +
      "\010\022\n\n\002ip\030\002 \001(\t\022\014\n\004port\030\003 \001(\005\"\037\n\017GetRangeR" +
      "equest\022\014\n\004node\030\001 \001(\t\"N\n\020GetRangeResponse" +
      "\022\014\n\004node\030\001 \001(\t\022\017\n\007fingerT\030\002 \001(\t\022\r\n\005range" +
      "\030\003 \001(\t\022\014\n\004data\030\004 \001(\t\"\"\n\017UpdateFTRequest\022" +
      "\017\n\007fingerT\030\001 \001(\t\"3\n\020UpdateFTResponse\022\017\n\007" +
      "fingerT\030\001 \001(\t\022\016\n\006update\030\002 \001(\010\"/\n\016NewNode" +
      "Request\022\014\n\004node\030\001 \001(\t\022\017\n\007newNode\030\002 \001(\t\"2" +
      "\n\017NewNodeResponse\022\017\n\007fingerT\030\001 \001(\t\022\016\n\006up" +
      "date\030\002 \001(\0102\223\004\n\007Greeter\022A\n\nCreateUser\022\027.i" +
      "o.grpc.GenericRequest\032\030.io.grpc.GenericR" +
      "esponse\"\000\022>\n\007GetUser\022\027.io.grpc.GenericRe" +
      "quest\032\030.io.grpc.GenericResponse\"\000\022A\n\nUpd" +
      "ateUser\022\027.io.grpc.GenericRequest\032\030.io.gr" +
      "pc.GenericResponse\"\000\022A\n\nDeleteUser\022\027.io." +
      "grpc.GenericRequest\032\030.io.grpc.GenericRes" +
      "ponse\"\000\0229\n\010FindNode\022\024.io.grpc.FindMessag" +
      "e\032\025.io.grpc.FindResponse\"\000\022A\n\010GetRange\022\030" +
      ".io.grpc.GetRangeRequest\032\031.io.grpc.GetRa" +
      "ngeResponse\"\000\022A\n\010UpdateFT\022\030.io.grpc.Upda" +
      "teFTRequest\032\031.io.grpc.UpdateFTResponse\"\000" +
      "\022>\n\007NewNode\022\027.io.grpc.NewNodeRequest\032\030.i" +
      "o.grpc.NewNodeResponse\"\000B\036\n\007io.grpcB\013Ser" +
      "verProtoP\001\242\002\003HLWb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_io_grpc_GenericRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_io_grpc_GenericRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_GenericRequest_descriptor,
        new String[] { "Code", "Data", });
    internal_static_io_grpc_GenericResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_io_grpc_GenericResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_GenericResponse_descriptor,
        new String[] { "Status", "Message", "Data", });
    internal_static_io_grpc_FindMessage_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_io_grpc_FindMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_FindMessage_descriptor,
        new String[] { "Key", });
    internal_static_io_grpc_FindResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_io_grpc_FindResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_FindResponse_descriptor,
        new String[] { "Response", "Ip", "Port", });
    internal_static_io_grpc_GetRangeRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_io_grpc_GetRangeRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_GetRangeRequest_descriptor,
        new String[] { "Node", });
    internal_static_io_grpc_GetRangeResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_io_grpc_GetRangeResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_GetRangeResponse_descriptor,
        new String[] { "Node", "FingerT", "Range", "Data", });
    internal_static_io_grpc_UpdateFTRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_io_grpc_UpdateFTRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_UpdateFTRequest_descriptor,
        new String[] { "FingerT", });
    internal_static_io_grpc_UpdateFTResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_io_grpc_UpdateFTResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_UpdateFTResponse_descriptor,
        new String[] { "FingerT", "Update", });
    internal_static_io_grpc_NewNodeRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_io_grpc_NewNodeRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_NewNodeRequest_descriptor,
        new String[] { "Node", "NewNode", });
    internal_static_io_grpc_NewNodeResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_io_grpc_NewNodeResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_io_grpc_NewNodeResponse_descriptor,
        new String[] { "FingerT", "Update", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
