/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package uk.ac.york.mondo.integration.api;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-08-08")
public class HawkReferenceRemovalEvent implements org.apache.thrift.TBase<HawkReferenceRemovalEvent, HawkReferenceRemovalEvent._Fields>, java.io.Serializable, Cloneable, Comparable<HawkReferenceRemovalEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HawkReferenceRemovalEvent");

  private static final org.apache.thrift.protocol.TField VCS_ITEM_FIELD_DESC = new org.apache.thrift.protocol.TField("vcsItem", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SOURCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sourceId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TARGET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("targetId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField REF_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("refName", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HawkReferenceRemovalEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HawkReferenceRemovalEventTupleSchemeFactory());
  }

  public CommitItem vcsItem; // required
  public String sourceId; // required
  public String targetId; // required
  public String refName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VCS_ITEM((short)1, "vcsItem"),
    SOURCE_ID((short)2, "sourceId"),
    TARGET_ID((short)3, "targetId"),
    REF_NAME((short)4, "refName");

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
        case 1: // VCS_ITEM
          return VCS_ITEM;
        case 2: // SOURCE_ID
          return SOURCE_ID;
        case 3: // TARGET_ID
          return TARGET_ID;
        case 4: // REF_NAME
          return REF_NAME;
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
    tmpMap.put(_Fields.VCS_ITEM, new org.apache.thrift.meta_data.FieldMetaData("vcsItem", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CommitItem.class)));
    tmpMap.put(_Fields.SOURCE_ID, new org.apache.thrift.meta_data.FieldMetaData("sourceId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TARGET_ID, new org.apache.thrift.meta_data.FieldMetaData("targetId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REF_NAME, new org.apache.thrift.meta_data.FieldMetaData("refName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HawkReferenceRemovalEvent.class, metaDataMap);
  }

  public HawkReferenceRemovalEvent() {
  }

  public HawkReferenceRemovalEvent(
    CommitItem vcsItem,
    String sourceId,
    String targetId,
    String refName)
  {
    this();
    this.vcsItem = vcsItem;
    this.sourceId = sourceId;
    this.targetId = targetId;
    this.refName = refName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HawkReferenceRemovalEvent(HawkReferenceRemovalEvent other) {
    if (other.isSetVcsItem()) {
      this.vcsItem = new CommitItem(other.vcsItem);
    }
    if (other.isSetSourceId()) {
      this.sourceId = other.sourceId;
    }
    if (other.isSetTargetId()) {
      this.targetId = other.targetId;
    }
    if (other.isSetRefName()) {
      this.refName = other.refName;
    }
  }

  public HawkReferenceRemovalEvent deepCopy() {
    return new HawkReferenceRemovalEvent(this);
  }

  @Override
  public void clear() {
    this.vcsItem = null;
    this.sourceId = null;
    this.targetId = null;
    this.refName = null;
  }

  public CommitItem getVcsItem() {
    return this.vcsItem;
  }

  public HawkReferenceRemovalEvent setVcsItem(CommitItem vcsItem) {
    this.vcsItem = vcsItem;
    return this;
  }

  public void unsetVcsItem() {
    this.vcsItem = null;
  }

  /** Returns true if field vcsItem is set (has been assigned a value) and false otherwise */
  public boolean isSetVcsItem() {
    return this.vcsItem != null;
  }

  public void setVcsItemIsSet(boolean value) {
    if (!value) {
      this.vcsItem = null;
    }
  }

  public String getSourceId() {
    return this.sourceId;
  }

  public HawkReferenceRemovalEvent setSourceId(String sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  public void unsetSourceId() {
    this.sourceId = null;
  }

  /** Returns true if field sourceId is set (has been assigned a value) and false otherwise */
  public boolean isSetSourceId() {
    return this.sourceId != null;
  }

  public void setSourceIdIsSet(boolean value) {
    if (!value) {
      this.sourceId = null;
    }
  }

  public String getTargetId() {
    return this.targetId;
  }

  public HawkReferenceRemovalEvent setTargetId(String targetId) {
    this.targetId = targetId;
    return this;
  }

  public void unsetTargetId() {
    this.targetId = null;
  }

  /** Returns true if field targetId is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetId() {
    return this.targetId != null;
  }

  public void setTargetIdIsSet(boolean value) {
    if (!value) {
      this.targetId = null;
    }
  }

  public String getRefName() {
    return this.refName;
  }

  public HawkReferenceRemovalEvent setRefName(String refName) {
    this.refName = refName;
    return this;
  }

  public void unsetRefName() {
    this.refName = null;
  }

  /** Returns true if field refName is set (has been assigned a value) and false otherwise */
  public boolean isSetRefName() {
    return this.refName != null;
  }

  public void setRefNameIsSet(boolean value) {
    if (!value) {
      this.refName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VCS_ITEM:
      if (value == null) {
        unsetVcsItem();
      } else {
        setVcsItem((CommitItem)value);
      }
      break;

    case SOURCE_ID:
      if (value == null) {
        unsetSourceId();
      } else {
        setSourceId((String)value);
      }
      break;

    case TARGET_ID:
      if (value == null) {
        unsetTargetId();
      } else {
        setTargetId((String)value);
      }
      break;

    case REF_NAME:
      if (value == null) {
        unsetRefName();
      } else {
        setRefName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VCS_ITEM:
      return getVcsItem();

    case SOURCE_ID:
      return getSourceId();

    case TARGET_ID:
      return getTargetId();

    case REF_NAME:
      return getRefName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VCS_ITEM:
      return isSetVcsItem();
    case SOURCE_ID:
      return isSetSourceId();
    case TARGET_ID:
      return isSetTargetId();
    case REF_NAME:
      return isSetRefName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HawkReferenceRemovalEvent)
      return this.equals((HawkReferenceRemovalEvent)that);
    return false;
  }

  public boolean equals(HawkReferenceRemovalEvent that) {
    if (that == null)
      return false;

    boolean this_present_vcsItem = true && this.isSetVcsItem();
    boolean that_present_vcsItem = true && that.isSetVcsItem();
    if (this_present_vcsItem || that_present_vcsItem) {
      if (!(this_present_vcsItem && that_present_vcsItem))
        return false;
      if (!this.vcsItem.equals(that.vcsItem))
        return false;
    }

    boolean this_present_sourceId = true && this.isSetSourceId();
    boolean that_present_sourceId = true && that.isSetSourceId();
    if (this_present_sourceId || that_present_sourceId) {
      if (!(this_present_sourceId && that_present_sourceId))
        return false;
      if (!this.sourceId.equals(that.sourceId))
        return false;
    }

    boolean this_present_targetId = true && this.isSetTargetId();
    boolean that_present_targetId = true && that.isSetTargetId();
    if (this_present_targetId || that_present_targetId) {
      if (!(this_present_targetId && that_present_targetId))
        return false;
      if (!this.targetId.equals(that.targetId))
        return false;
    }

    boolean this_present_refName = true && this.isSetRefName();
    boolean that_present_refName = true && that.isSetRefName();
    if (this_present_refName || that_present_refName) {
      if (!(this_present_refName && that_present_refName))
        return false;
      if (!this.refName.equals(that.refName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_vcsItem = true && (isSetVcsItem());
    list.add(present_vcsItem);
    if (present_vcsItem)
      list.add(vcsItem);

    boolean present_sourceId = true && (isSetSourceId());
    list.add(present_sourceId);
    if (present_sourceId)
      list.add(sourceId);

    boolean present_targetId = true && (isSetTargetId());
    list.add(present_targetId);
    if (present_targetId)
      list.add(targetId);

    boolean present_refName = true && (isSetRefName());
    list.add(present_refName);
    if (present_refName)
      list.add(refName);

    return list.hashCode();
  }

  @Override
  public int compareTo(HawkReferenceRemovalEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetVcsItem()).compareTo(other.isSetVcsItem());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVcsItem()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vcsItem, other.vcsItem);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSourceId()).compareTo(other.isSetSourceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSourceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sourceId, other.sourceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTargetId()).compareTo(other.isSetTargetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetId, other.targetId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRefName()).compareTo(other.isSetRefName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRefName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.refName, other.refName);
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
    StringBuilder sb = new StringBuilder("HawkReferenceRemovalEvent(");
    boolean first = true;

    sb.append("vcsItem:");
    if (this.vcsItem == null) {
      sb.append("null");
    } else {
      sb.append(this.vcsItem);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sourceId:");
    if (this.sourceId == null) {
      sb.append("null");
    } else {
      sb.append(this.sourceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("targetId:");
    if (this.targetId == null) {
      sb.append("null");
    } else {
      sb.append(this.targetId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("refName:");
    if (this.refName == null) {
      sb.append("null");
    } else {
      sb.append(this.refName);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (vcsItem == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'vcsItem' was not present! Struct: " + toString());
    }
    if (sourceId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'sourceId' was not present! Struct: " + toString());
    }
    if (targetId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'targetId' was not present! Struct: " + toString());
    }
    if (refName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'refName' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (vcsItem != null) {
      vcsItem.validate();
    }
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

  private static class HawkReferenceRemovalEventStandardSchemeFactory implements SchemeFactory {
    public HawkReferenceRemovalEventStandardScheme getScheme() {
      return new HawkReferenceRemovalEventStandardScheme();
    }
  }

  private static class HawkReferenceRemovalEventStandardScheme extends StandardScheme<HawkReferenceRemovalEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HawkReferenceRemovalEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VCS_ITEM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.vcsItem = new CommitItem();
              struct.vcsItem.read(iprot);
              struct.setVcsItemIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SOURCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sourceId = iprot.readString();
              struct.setSourceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TARGET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.targetId = iprot.readString();
              struct.setTargetIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REF_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.refName = iprot.readString();
              struct.setRefNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HawkReferenceRemovalEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.vcsItem != null) {
        oprot.writeFieldBegin(VCS_ITEM_FIELD_DESC);
        struct.vcsItem.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.sourceId != null) {
        oprot.writeFieldBegin(SOURCE_ID_FIELD_DESC);
        oprot.writeString(struct.sourceId);
        oprot.writeFieldEnd();
      }
      if (struct.targetId != null) {
        oprot.writeFieldBegin(TARGET_ID_FIELD_DESC);
        oprot.writeString(struct.targetId);
        oprot.writeFieldEnd();
      }
      if (struct.refName != null) {
        oprot.writeFieldBegin(REF_NAME_FIELD_DESC);
        oprot.writeString(struct.refName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HawkReferenceRemovalEventTupleSchemeFactory implements SchemeFactory {
    public HawkReferenceRemovalEventTupleScheme getScheme() {
      return new HawkReferenceRemovalEventTupleScheme();
    }
  }

  private static class HawkReferenceRemovalEventTupleScheme extends TupleScheme<HawkReferenceRemovalEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HawkReferenceRemovalEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.vcsItem.write(oprot);
      oprot.writeString(struct.sourceId);
      oprot.writeString(struct.targetId);
      oprot.writeString(struct.refName);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HawkReferenceRemovalEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.vcsItem = new CommitItem();
      struct.vcsItem.read(iprot);
      struct.setVcsItemIsSet(true);
      struct.sourceId = iprot.readString();
      struct.setSourceIdIsSet(true);
      struct.targetId = iprot.readString();
      struct.setTargetIdIsSet(true);
      struct.refName = iprot.readString();
      struct.setRefNameIsSet(true);
    }
  }

}

