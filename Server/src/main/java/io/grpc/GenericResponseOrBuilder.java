// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GrpcServer.proto

package io.grpc;

public interface GenericResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:io.grpc.GenericResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string status = 1;</code>
   */
  String getStatus();
  /**
   * <code>string status = 1;</code>
   */
  com.google.protobuf.ByteString
      getStatusBytes();

  /**
   * <code>string message = 2;</code>
   */
  String getMessage();
  /**
   * <code>string message = 2;</code>
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>string data = 3;</code>
   */
  String getData();
  /**
   * <code>string data = 3;</code>
   */
  com.google.protobuf.ByteString
      getDataBytes();
}
