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
public class HawkModelElementAdditionEvent implements org.apache.thrift.TBase<HawkModelElementAdditionEvent, HawkModelElementAdditionEvent._Fields>, java.io.Serializable, Cloneable, Comparable<HawkModelElementAdditionEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HawkModelElementAdditionEvent");

  private static final org.apache.thrift.protocol.TField VCS_ITEM_FIELD_DESC = new org.apache.thrift.protocol.TField("vcsItem", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField METAMODEL_URI_FIELD_DESC = new org.apache.thrift.protocol.TField("metamodelURI", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("typeName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HawkModelElementAdditionEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HawkModelElementAdditionEventTupleSchemeFactory());
  }

  public CommitItem vcsItem; // required
  public String metamodelURI; // required
  public String typeName; // required
  public String id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VCS_ITEM((short)1, "vcsItem"),
    METAMODEL_URI((short)2, "metamodelURI"),
    TYPE_NAME((short)3, "typeName"),
    ID((short)4, "id");

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
        case 2: // METAMODEL_URI
          return METAMODEL_URI;
        case 3: // TYPE_NAME
          return TYPE_NAME;
        case 4: // ID
          return ID;
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
    tmpMap.put(_Fields.METAMODEL_URI, new org.apache.thrift.meta_data.FieldMetaData("metamodelURI", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE_NAME, new org.apache.thrift.meta_data.FieldMetaData("typeName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HawkModelElementAdditionEvent.class, metaDataMap);
  }

  public HawkModelElementAdditionEvent() {
  }

  public HawkModelElementAdditionEvent(
    CommitItem vcsItem,
    String metamodelURI,
    String typeName,
    String id)
  {
    this();
    this.vcsItem = vcsItem;
    this.metamodelURI = metamodelURI;
    this.typeName = typeName;
    this.id = id;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HawkModelElementAdditionEvent(HawkModelElementAdditionEvent other) {
    if (other.isSetVcsItem()) {
      this.vcsItem = new CommitItem(other.vcsItem);
    }
    if (other.isSetMetamodelURI()) {
      this.metamodelURI = other.metamodelURI;
    }
    if (other.isSetTypeName()) {
      this.typeName = other.typeName;
    }
    if (other.isSetId()) {
      this.id = other.id;
    }
  }

  public HawkModelElementAdditionEvent deepCopy() {
    return new HawkModelElementAdditionEvent(this);
  }

  @Override
  public void clear() {
    this.vcsItem = null;
    this.metamodelURI = null;
    this.typeName = null;
    this.id = null;
  }

  public CommitItem getVcsItem() {
    return this.vcsItem;
  }

  public HawkModelElementAdditionEvent setVcsItem(CommitItem vcsItem) {
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

  public String getMetamodelURI() {
    return this.metamodelURI;
  }

  public HawkModelElementAdditionEvent setMetamodelURI(String metamodelURI) {
    this.metamodelURI = metamodelURI;
    return this;
  }

  public void unsetMetamodelURI() {
    this.metamodelURI = null;
  }

  /** Returns true if field metamodelURI is set (has been assigned a value) and false otherwise */
  public boolean isSetMetamodelURI() {
    return this.metamodelURI != null;
  }

  public void setMetamodelURIIsSet(boolean value) {
    if (!value) {
      this.metamodelURI = null;
    }
  }

  public String getTypeName() {
    return this.typeName;
  }

  public HawkModelElementAdditionEvent setTypeName(String typeName) {
    this.typeName = typeName;
    return this;
  }

  public void unsetTypeName() {
    this.typeName = null;
  }

  /** Returns true if field typeName is set (has been assigned a value) and false otherwise */
  public boolean isSetTypeName() {
    return this.typeName != null;
  }

  public void setTypeNameIsSet(boolean value) {
    if (!value) {
      this.typeName = null;
    }
  }

  public String getId() {
    return this.id;
  }

  public HawkModelElementAdditionEvent setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
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

    case METAMODEL_URI:
      if (value == null) {
        unsetMetamodelURI();
      } else {
        setMetamodelURI((String)value);
      }
      break;

    case TYPE_NAME:
      if (value == null) {
        unsetTypeName();
      } else {
        setTypeName((String)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VCS_ITEM:
      return getVcsItem();

    case METAMODEL_URI:
      return getMetamodelURI();

    case TYPE_NAME:
      return getTypeName();

    case ID:
      return getId();

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
    case METAMODEL_URI:
      return isSetMetamodelURI();
    case TYPE_NAME:
      return isSetTypeName();
    case ID:
      return isSetId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HawkModelElementAdditionEvent)
      return this.equals((HawkModelElementAdditionEvent)that);
    return false;
  }

  public boolean equals(HawkModelElementAdditionEvent that) {
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

    boolean this_present_metamodelURI = true && this.isSetMetamodelURI();
    boolean that_present_metamodelURI = true && that.isSetMetamodelURI();
    if (this_present_metamodelURI || that_present_metamodelURI) {
      if (!(this_present_metamodelURI && that_present_metamodelURI))
        return false;
      if (!this.metamodelURI.equals(that.metamodelURI))
        return false;
    }

    boolean this_present_typeName = true && this.isSetTypeName();
    boolean that_present_typeName = true && that.isSetTypeName();
    if (this_present_typeName || that_present_typeName) {
      if (!(this_present_typeName && that_present_typeName))
        return false;
      if (!this.typeName.equals(that.typeName))
        return false;
    }

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
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

    boolean present_metamodelURI = true && (isSetMetamodelURI());
    list.add(present_metamodelURI);
    if (present_metamodelURI)
      list.add(metamodelURI);

    boolean present_typeName = true && (isSetTypeName());
    list.add(present_typeName);
    if (present_typeName)
      list.add(typeName);

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    return list.hashCode();
  }

  @Override
  public int compareTo(HawkModelElementAdditionEvent other) {
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
    lastComparison = Boolean.valueOf(isSetMetamodelURI()).compareTo(other.isSetMetamodelURI());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMetamodelURI()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metamodelURI, other.metamodelURI);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTypeName()).compareTo(other.isSetTypeName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTypeName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.typeName, other.typeName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
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
    StringBuilder sb = new StringBuilder("HawkModelElementAdditionEvent(");
    boolean first = true;

    sb.append("vcsItem:");
    if (this.vcsItem == null) {
      sb.append("null");
    } else {
      sb.append(this.vcsItem);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("metamodelURI:");
    if (this.metamodelURI == null) {
      sb.append("null");
    } else {
      sb.append(this.metamodelURI);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("typeName:");
    if (this.typeName == null) {
      sb.append("null");
    } else {
      sb.append(this.typeName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
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
    if (metamodelURI == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'metamodelURI' was not present! Struct: " + toString());
    }
    if (typeName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'typeName' was not present! Struct: " + toString());
    }
    if (id == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not present! Struct: " + toString());
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

  private static class HawkModelElementAdditionEventStandardSchemeFactory implements SchemeFactory {
    public HawkModelElementAdditionEventStandardScheme getScheme() {
      return new HawkModelElementAdditionEventStandardScheme();
    }
  }

  private static class HawkModelElementAdditionEventStandardScheme extends StandardScheme<HawkModelElementAdditionEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HawkModelElementAdditionEvent struct) throws org.apache.thrift.TException {
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
          case 2: // METAMODEL_URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.metamodelURI = iprot.readString();
              struct.setMetamodelURIIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.typeName = iprot.readString();
              struct.setTypeNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HawkModelElementAdditionEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.vcsItem != null) {
        oprot.writeFieldBegin(VCS_ITEM_FIELD_DESC);
        struct.vcsItem.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.metamodelURI != null) {
        oprot.writeFieldBegin(METAMODEL_URI_FIELD_DESC);
        oprot.writeString(struct.metamodelURI);
        oprot.writeFieldEnd();
      }
      if (struct.typeName != null) {
        oprot.writeFieldBegin(TYPE_NAME_FIELD_DESC);
        oprot.writeString(struct.typeName);
        oprot.writeFieldEnd();
      }
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HawkModelElementAdditionEventTupleSchemeFactory implements SchemeFactory {
    public HawkModelElementAdditionEventTupleScheme getScheme() {
      return new HawkModelElementAdditionEventTupleScheme();
    }
  }

  private static class HawkModelElementAdditionEventTupleScheme extends TupleScheme<HawkModelElementAdditionEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HawkModelElementAdditionEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.vcsItem.write(oprot);
      oprot.writeString(struct.metamodelURI);
      oprot.writeString(struct.typeName);
      oprot.writeString(struct.id);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HawkModelElementAdditionEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.vcsItem = new CommitItem();
      struct.vcsItem.read(iprot);
      struct.setVcsItemIsSet(true);
      struct.metamodelURI = iprot.readString();
      struct.setMetamodelURIIsSet(true);
      struct.typeName = iprot.readString();
      struct.setTypeNameIsSet(true);
      struct.id = iprot.readString();
      struct.setIdIsSet(true);
    }
  }

}

