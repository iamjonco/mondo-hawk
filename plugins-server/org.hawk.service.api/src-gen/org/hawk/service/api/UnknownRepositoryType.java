/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.hawk.service.api;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2019-02-12")
public class UnknownRepositoryType extends TException implements org.apache.thrift.TBase<UnknownRepositoryType, UnknownRepositoryType._Fields>, java.io.Serializable, Cloneable, Comparable<UnknownRepositoryType> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UnknownRepositoryType");

  private static final org.apache.thrift.protocol.TField REPOSITORY_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("repositoryType", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UnknownRepositoryTypeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UnknownRepositoryTypeTupleSchemeFactory());
  }

  public String repositoryType; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    REPOSITORY_TYPE((short)1, "repositoryType");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // REPOSITORY_TYPE
          return REPOSITORY_TYPE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REPOSITORY_TYPE, new org.apache.thrift.meta_data.FieldMetaData("repositoryType", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UnknownRepositoryType.class, metaDataMap);
  }

  public UnknownRepositoryType() {
  }

  public UnknownRepositoryType(
    String repositoryType)
  {
    this();
    this.repositoryType = repositoryType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UnknownRepositoryType(UnknownRepositoryType other) {
    if (other.isSetRepositoryType()) {
      this.repositoryType = other.repositoryType;
    }
  }

  public UnknownRepositoryType deepCopy() {
    return new UnknownRepositoryType(this);
  }

  @Override
  public void clear() {
    this.repositoryType = null;
  }

  public String getRepositoryType() {
    return this.repositoryType;
  }

  public UnknownRepositoryType setRepositoryType(String repositoryType) {
    this.repositoryType = repositoryType;
    return this;
  }

  public void unsetRepositoryType() {
    this.repositoryType = null;
  }

  /** Returns true if field repositoryType is set (has been assigned a value) and false otherwise */
  public boolean isSetRepositoryType() {
    return this.repositoryType != null;
  }

  public void setRepositoryTypeIsSet(boolean value) {
    if (!value) {
      this.repositoryType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REPOSITORY_TYPE:
      if (value == null) {
        unsetRepositoryType();
      } else {
        setRepositoryType((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REPOSITORY_TYPE:
      return getRepositoryType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REPOSITORY_TYPE:
      return isSetRepositoryType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UnknownRepositoryType)
      return this.equals((UnknownRepositoryType)that);
    return false;
  }

  public boolean equals(UnknownRepositoryType that) {
    if (that == null)
      return false;

    boolean this_present_repositoryType = true && this.isSetRepositoryType();
    boolean that_present_repositoryType = true && that.isSetRepositoryType();
    if (this_present_repositoryType || that_present_repositoryType) {
      if (!(this_present_repositoryType && that_present_repositoryType))
        return false;
      if (!this.repositoryType.equals(that.repositoryType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_repositoryType = true && (isSetRepositoryType());
    list.add(present_repositoryType);
    if (present_repositoryType)
      list.add(repositoryType);

    return list.hashCode();
  }

  @Override
  public int compareTo(UnknownRepositoryType other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRepositoryType()).compareTo(other.isSetRepositoryType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRepositoryType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.repositoryType, other.repositoryType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("UnknownRepositoryType(");
    boolean first = true;

    sb.append("repositoryType:");
    if (this.repositoryType == null) {
      sb.append("null");
    } else {
      sb.append(this.repositoryType);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (repositoryType == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'repositoryType' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class UnknownRepositoryTypeStandardSchemeFactory implements SchemeFactory {
    public UnknownRepositoryTypeStandardScheme getScheme() {
      return new UnknownRepositoryTypeStandardScheme();
    }
  }

  private static class UnknownRepositoryTypeStandardScheme extends StandardScheme<UnknownRepositoryType> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UnknownRepositoryType struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REPOSITORY_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.repositoryType = iprot.readString();
              struct.setRepositoryTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, UnknownRepositoryType struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.repositoryType != null) {
        oprot.writeFieldBegin(REPOSITORY_TYPE_FIELD_DESC);
        oprot.writeString(struct.repositoryType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UnknownRepositoryTypeTupleSchemeFactory implements SchemeFactory {
    public UnknownRepositoryTypeTupleScheme getScheme() {
      return new UnknownRepositoryTypeTupleScheme();
    }
  }

  private static class UnknownRepositoryTypeTupleScheme extends TupleScheme<UnknownRepositoryType> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UnknownRepositoryType struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.repositoryType);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UnknownRepositoryType struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.repositoryType = iprot.readString();
      struct.setRepositoryTypeIsSet(true);
    }
  }

}

