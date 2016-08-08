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
public class SlotMetadata implements org.apache.thrift.TBase<SlotMetadata, SlotMetadata._Fields>, java.io.Serializable, Cloneable, Comparable<SlotMetadata> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SlotMetadata");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField IS_MANY_FIELD_DESC = new org.apache.thrift.protocol.TField("isMany", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField IS_ORDERED_FIELD_DESC = new org.apache.thrift.protocol.TField("isOrdered", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField IS_UNIQUE_FIELD_DESC = new org.apache.thrift.protocol.TField("isUnique", org.apache.thrift.protocol.TType.BOOL, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SlotMetadataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SlotMetadataTupleSchemeFactory());
  }

  public String name; // required
  public String type; // required
  public boolean isMany; // required
  public boolean isOrdered; // required
  public boolean isUnique; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    TYPE((short)2, "type"),
    IS_MANY((short)3, "isMany"),
    IS_ORDERED((short)4, "isOrdered"),
    IS_UNIQUE((short)5, "isUnique");

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
        case 1: // NAME
          return NAME;
        case 2: // TYPE
          return TYPE;
        case 3: // IS_MANY
          return IS_MANY;
        case 4: // IS_ORDERED
          return IS_ORDERED;
        case 5: // IS_UNIQUE
          return IS_UNIQUE;
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
  private static final int __ISMANY_ISSET_ID = 0;
  private static final int __ISORDERED_ISSET_ID = 1;
  private static final int __ISUNIQUE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_MANY, new org.apache.thrift.meta_data.FieldMetaData("isMany", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_ORDERED, new org.apache.thrift.meta_data.FieldMetaData("isOrdered", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_UNIQUE, new org.apache.thrift.meta_data.FieldMetaData("isUnique", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SlotMetadata.class, metaDataMap);
  }

  public SlotMetadata() {
  }

  public SlotMetadata(
    String name,
    String type,
    boolean isMany,
    boolean isOrdered,
    boolean isUnique)
  {
    this();
    this.name = name;
    this.type = type;
    this.isMany = isMany;
    setIsManyIsSet(true);
    this.isOrdered = isOrdered;
    setIsOrderedIsSet(true);
    this.isUnique = isUnique;
    setIsUniqueIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SlotMetadata(SlotMetadata other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.isMany = other.isMany;
    this.isOrdered = other.isOrdered;
    this.isUnique = other.isUnique;
  }

  public SlotMetadata deepCopy() {
    return new SlotMetadata(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.type = null;
    setIsManyIsSet(false);
    this.isMany = false;
    setIsOrderedIsSet(false);
    this.isOrdered = false;
    setIsUniqueIsSet(false);
    this.isUnique = false;
  }

  public String getName() {
    return this.name;
  }

  public SlotMetadata setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getType() {
    return this.type;
  }

  public SlotMetadata setType(String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public boolean isIsMany() {
    return this.isMany;
  }

  public SlotMetadata setIsMany(boolean isMany) {
    this.isMany = isMany;
    setIsManyIsSet(true);
    return this;
  }

  public void unsetIsMany() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISMANY_ISSET_ID);
  }

  /** Returns true if field isMany is set (has been assigned a value) and false otherwise */
  public boolean isSetIsMany() {
    return EncodingUtils.testBit(__isset_bitfield, __ISMANY_ISSET_ID);
  }

  public void setIsManyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISMANY_ISSET_ID, value);
  }

  public boolean isIsOrdered() {
    return this.isOrdered;
  }

  public SlotMetadata setIsOrdered(boolean isOrdered) {
    this.isOrdered = isOrdered;
    setIsOrderedIsSet(true);
    return this;
  }

  public void unsetIsOrdered() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISORDERED_ISSET_ID);
  }

  /** Returns true if field isOrdered is set (has been assigned a value) and false otherwise */
  public boolean isSetIsOrdered() {
    return EncodingUtils.testBit(__isset_bitfield, __ISORDERED_ISSET_ID);
  }

  public void setIsOrderedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISORDERED_ISSET_ID, value);
  }

  public boolean isIsUnique() {
    return this.isUnique;
  }

  public SlotMetadata setIsUnique(boolean isUnique) {
    this.isUnique = isUnique;
    setIsUniqueIsSet(true);
    return this;
  }

  public void unsetIsUnique() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISUNIQUE_ISSET_ID);
  }

  /** Returns true if field isUnique is set (has been assigned a value) and false otherwise */
  public boolean isSetIsUnique() {
    return EncodingUtils.testBit(__isset_bitfield, __ISUNIQUE_ISSET_ID);
  }

  public void setIsUniqueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISUNIQUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case IS_MANY:
      if (value == null) {
        unsetIsMany();
      } else {
        setIsMany((Boolean)value);
      }
      break;

    case IS_ORDERED:
      if (value == null) {
        unsetIsOrdered();
      } else {
        setIsOrdered((Boolean)value);
      }
      break;

    case IS_UNIQUE:
      if (value == null) {
        unsetIsUnique();
      } else {
        setIsUnique((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case TYPE:
      return getType();

    case IS_MANY:
      return isIsMany();

    case IS_ORDERED:
      return isIsOrdered();

    case IS_UNIQUE:
      return isIsUnique();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case TYPE:
      return isSetType();
    case IS_MANY:
      return isSetIsMany();
    case IS_ORDERED:
      return isSetIsOrdered();
    case IS_UNIQUE:
      return isSetIsUnique();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SlotMetadata)
      return this.equals((SlotMetadata)that);
    return false;
  }

  public boolean equals(SlotMetadata that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_isMany = true;
    boolean that_present_isMany = true;
    if (this_present_isMany || that_present_isMany) {
      if (!(this_present_isMany && that_present_isMany))
        return false;
      if (this.isMany != that.isMany)
        return false;
    }

    boolean this_present_isOrdered = true;
    boolean that_present_isOrdered = true;
    if (this_present_isOrdered || that_present_isOrdered) {
      if (!(this_present_isOrdered && that_present_isOrdered))
        return false;
      if (this.isOrdered != that.isOrdered)
        return false;
    }

    boolean this_present_isUnique = true;
    boolean that_present_isUnique = true;
    if (this_present_isUnique || that_present_isUnique) {
      if (!(this_present_isUnique && that_present_isUnique))
        return false;
      if (this.isUnique != that.isUnique)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type);

    boolean present_isMany = true;
    list.add(present_isMany);
    if (present_isMany)
      list.add(isMany);

    boolean present_isOrdered = true;
    list.add(present_isOrdered);
    if (present_isOrdered)
      list.add(isOrdered);

    boolean present_isUnique = true;
    list.add(present_isUnique);
    if (present_isUnique)
      list.add(isUnique);

    return list.hashCode();
  }

  @Override
  public int compareTo(SlotMetadata other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsMany()).compareTo(other.isSetIsMany());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsMany()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isMany, other.isMany);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsOrdered()).compareTo(other.isSetIsOrdered());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsOrdered()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isOrdered, other.isOrdered);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsUnique()).compareTo(other.isSetIsUnique());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsUnique()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isUnique, other.isUnique);
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
    StringBuilder sb = new StringBuilder("SlotMetadata(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isMany:");
    sb.append(this.isMany);
    first = false;
    if (!first) sb.append(", ");
    sb.append("isOrdered:");
    sb.append(this.isOrdered);
    first = false;
    if (!first) sb.append(", ");
    sb.append("isUnique:");
    sb.append(this.isUnique);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' was not present! Struct: " + toString());
    }
    if (type == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'type' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'isMany' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'isOrdered' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'isUnique' because it's a primitive and you chose the non-beans generator.
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SlotMetadataStandardSchemeFactory implements SchemeFactory {
    public SlotMetadataStandardScheme getScheme() {
      return new SlotMetadataStandardScheme();
    }
  }

  private static class SlotMetadataStandardScheme extends StandardScheme<SlotMetadata> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SlotMetadata struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IS_MANY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isMany = iprot.readBool();
              struct.setIsManyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IS_ORDERED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isOrdered = iprot.readBool();
              struct.setIsOrderedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_UNIQUE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isUnique = iprot.readBool();
              struct.setIsUniqueIsSet(true);
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
      if (!struct.isSetIsMany()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'isMany' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetIsOrdered()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'isOrdered' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetIsUnique()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'isUnique' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SlotMetadata struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_MANY_FIELD_DESC);
      oprot.writeBool(struct.isMany);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_ORDERED_FIELD_DESC);
      oprot.writeBool(struct.isOrdered);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_UNIQUE_FIELD_DESC);
      oprot.writeBool(struct.isUnique);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SlotMetadataTupleSchemeFactory implements SchemeFactory {
    public SlotMetadataTupleScheme getScheme() {
      return new SlotMetadataTupleScheme();
    }
  }

  private static class SlotMetadataTupleScheme extends TupleScheme<SlotMetadata> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SlotMetadata struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.name);
      oprot.writeString(struct.type);
      oprot.writeBool(struct.isMany);
      oprot.writeBool(struct.isOrdered);
      oprot.writeBool(struct.isUnique);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SlotMetadata struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.name = iprot.readString();
      struct.setNameIsSet(true);
      struct.type = iprot.readString();
      struct.setTypeIsSet(true);
      struct.isMany = iprot.readBool();
      struct.setIsManyIsSet(true);
      struct.isOrdered = iprot.readBool();
      struct.setIsOrderedIsSet(true);
      struct.isUnique = iprot.readBool();
      struct.setIsUniqueIsSet(true);
    }
  }

}

