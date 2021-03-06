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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-10-07")
public class ReferenceSlot implements org.apache.thrift.TBase<ReferenceSlot, ReferenceSlot._Fields>, java.io.Serializable, Cloneable, Comparable<ReferenceSlot> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReferenceSlot");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField POSITION_FIELD_DESC = new org.apache.thrift.protocol.TField("position", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField POSITIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("positions", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("ids", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField MIXED_FIELD_DESC = new org.apache.thrift.protocol.TField("mixed", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReferenceSlotStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReferenceSlotTupleSchemeFactory());
  }

  public String name; // required
  public int position; // optional
  public List<Integer> positions; // optional
  public String id; // optional
  public List<String> ids; // optional
  public List<MixedReference> mixed; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    POSITION((short)2, "position"),
    POSITIONS((short)3, "positions"),
    ID((short)4, "id"),
    IDS((short)5, "ids"),
    MIXED((short)6, "mixed");

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
        case 2: // POSITION
          return POSITION;
        case 3: // POSITIONS
          return POSITIONS;
        case 4: // ID
          return ID;
        case 5: // IDS
          return IDS;
        case 6: // MIXED
          return MIXED;
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
  private static final int __POSITION_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.POSITION,_Fields.POSITIONS,_Fields.ID,_Fields.IDS,_Fields.MIXED};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POSITION, new org.apache.thrift.meta_data.FieldMetaData("position", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.POSITIONS, new org.apache.thrift.meta_data.FieldMetaData("positions", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IDS, new org.apache.thrift.meta_data.FieldMetaData("ids", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.MIXED, new org.apache.thrift.meta_data.FieldMetaData("mixed", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MixedReference.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReferenceSlot.class, metaDataMap);
  }

  public ReferenceSlot() {
  }

  public ReferenceSlot(
    String name)
  {
    this();
    this.name = name;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReferenceSlot(ReferenceSlot other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.position = other.position;
    if (other.isSetPositions()) {
      List<Integer> __this__positions = new ArrayList<Integer>(other.positions);
      this.positions = __this__positions;
    }
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetIds()) {
      List<String> __this__ids = new ArrayList<String>(other.ids);
      this.ids = __this__ids;
    }
    if (other.isSetMixed()) {
      List<MixedReference> __this__mixed = new ArrayList<MixedReference>(other.mixed.size());
      for (MixedReference other_element : other.mixed) {
        __this__mixed.add(new MixedReference(other_element));
      }
      this.mixed = __this__mixed;
    }
  }

  public ReferenceSlot deepCopy() {
    return new ReferenceSlot(this);
  }

  @Override
  public void clear() {
    this.name = null;
    setPositionIsSet(false);
    this.position = 0;
    this.positions = null;
    this.id = null;
    this.ids = null;
    this.mixed = null;
  }

  public String getName() {
    return this.name;
  }

  public ReferenceSlot setName(String name) {
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

  public int getPosition() {
    return this.position;
  }

  public ReferenceSlot setPosition(int position) {
    this.position = position;
    setPositionIsSet(true);
    return this;
  }

  public void unsetPosition() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __POSITION_ISSET_ID);
  }

  /** Returns true if field position is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition() {
    return EncodingUtils.testBit(__isset_bitfield, __POSITION_ISSET_ID);
  }

  public void setPositionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __POSITION_ISSET_ID, value);
  }

  public int getPositionsSize() {
    return (this.positions == null) ? 0 : this.positions.size();
  }

  public java.util.Iterator<Integer> getPositionsIterator() {
    return (this.positions == null) ? null : this.positions.iterator();
  }

  public void addToPositions(int elem) {
    if (this.positions == null) {
      this.positions = new ArrayList<Integer>();
    }
    this.positions.add(elem);
  }

  public List<Integer> getPositions() {
    return this.positions;
  }

  public ReferenceSlot setPositions(List<Integer> positions) {
    this.positions = positions;
    return this;
  }

  public void unsetPositions() {
    this.positions = null;
  }

  /** Returns true if field positions is set (has been assigned a value) and false otherwise */
  public boolean isSetPositions() {
    return this.positions != null;
  }

  public void setPositionsIsSet(boolean value) {
    if (!value) {
      this.positions = null;
    }
  }

  public String getId() {
    return this.id;
  }

  public ReferenceSlot setId(String id) {
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

  public int getIdsSize() {
    return (this.ids == null) ? 0 : this.ids.size();
  }

  public java.util.Iterator<String> getIdsIterator() {
    return (this.ids == null) ? null : this.ids.iterator();
  }

  public void addToIds(String elem) {
    if (this.ids == null) {
      this.ids = new ArrayList<String>();
    }
    this.ids.add(elem);
  }

  public List<String> getIds() {
    return this.ids;
  }

  public ReferenceSlot setIds(List<String> ids) {
    this.ids = ids;
    return this;
  }

  public void unsetIds() {
    this.ids = null;
  }

  /** Returns true if field ids is set (has been assigned a value) and false otherwise */
  public boolean isSetIds() {
    return this.ids != null;
  }

  public void setIdsIsSet(boolean value) {
    if (!value) {
      this.ids = null;
    }
  }

  public int getMixedSize() {
    return (this.mixed == null) ? 0 : this.mixed.size();
  }

  public java.util.Iterator<MixedReference> getMixedIterator() {
    return (this.mixed == null) ? null : this.mixed.iterator();
  }

  public void addToMixed(MixedReference elem) {
    if (this.mixed == null) {
      this.mixed = new ArrayList<MixedReference>();
    }
    this.mixed.add(elem);
  }

  public List<MixedReference> getMixed() {
    return this.mixed;
  }

  public ReferenceSlot setMixed(List<MixedReference> mixed) {
    this.mixed = mixed;
    return this;
  }

  public void unsetMixed() {
    this.mixed = null;
  }

  /** Returns true if field mixed is set (has been assigned a value) and false otherwise */
  public boolean isSetMixed() {
    return this.mixed != null;
  }

  public void setMixedIsSet(boolean value) {
    if (!value) {
      this.mixed = null;
    }
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

    case POSITION:
      if (value == null) {
        unsetPosition();
      } else {
        setPosition((Integer)value);
      }
      break;

    case POSITIONS:
      if (value == null) {
        unsetPositions();
      } else {
        setPositions((List<Integer>)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case IDS:
      if (value == null) {
        unsetIds();
      } else {
        setIds((List<String>)value);
      }
      break;

    case MIXED:
      if (value == null) {
        unsetMixed();
      } else {
        setMixed((List<MixedReference>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case POSITION:
      return getPosition();

    case POSITIONS:
      return getPositions();

    case ID:
      return getId();

    case IDS:
      return getIds();

    case MIXED:
      return getMixed();

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
    case POSITION:
      return isSetPosition();
    case POSITIONS:
      return isSetPositions();
    case ID:
      return isSetId();
    case IDS:
      return isSetIds();
    case MIXED:
      return isSetMixed();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReferenceSlot)
      return this.equals((ReferenceSlot)that);
    return false;
  }

  public boolean equals(ReferenceSlot that) {
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

    boolean this_present_position = true && this.isSetPosition();
    boolean that_present_position = true && that.isSetPosition();
    if (this_present_position || that_present_position) {
      if (!(this_present_position && that_present_position))
        return false;
      if (this.position != that.position)
        return false;
    }

    boolean this_present_positions = true && this.isSetPositions();
    boolean that_present_positions = true && that.isSetPositions();
    if (this_present_positions || that_present_positions) {
      if (!(this_present_positions && that_present_positions))
        return false;
      if (!this.positions.equals(that.positions))
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

    boolean this_present_ids = true && this.isSetIds();
    boolean that_present_ids = true && that.isSetIds();
    if (this_present_ids || that_present_ids) {
      if (!(this_present_ids && that_present_ids))
        return false;
      if (!this.ids.equals(that.ids))
        return false;
    }

    boolean this_present_mixed = true && this.isSetMixed();
    boolean that_present_mixed = true && that.isSetMixed();
    if (this_present_mixed || that_present_mixed) {
      if (!(this_present_mixed && that_present_mixed))
        return false;
      if (!this.mixed.equals(that.mixed))
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

    boolean present_position = true && (isSetPosition());
    list.add(present_position);
    if (present_position)
      list.add(position);

    boolean present_positions = true && (isSetPositions());
    list.add(present_positions);
    if (present_positions)
      list.add(positions);

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_ids = true && (isSetIds());
    list.add(present_ids);
    if (present_ids)
      list.add(ids);

    boolean present_mixed = true && (isSetMixed());
    list.add(present_mixed);
    if (present_mixed)
      list.add(mixed);

    return list.hashCode();
  }

  @Override
  public int compareTo(ReferenceSlot other) {
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
    lastComparison = Boolean.valueOf(isSetPosition()).compareTo(other.isSetPosition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position, other.position);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositions()).compareTo(other.isSetPositions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positions, other.positions);
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
    lastComparison = Boolean.valueOf(isSetIds()).compareTo(other.isSetIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ids, other.ids);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMixed()).compareTo(other.isSetMixed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMixed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mixed, other.mixed);
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
    StringBuilder sb = new StringBuilder("ReferenceSlot(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (isSetPosition()) {
      if (!first) sb.append(", ");
      sb.append("position:");
      sb.append(this.position);
      first = false;
    }
    if (isSetPositions()) {
      if (!first) sb.append(", ");
      sb.append("positions:");
      if (this.positions == null) {
        sb.append("null");
      } else {
        sb.append(this.positions);
      }
      first = false;
    }
    if (isSetId()) {
      if (!first) sb.append(", ");
      sb.append("id:");
      if (this.id == null) {
        sb.append("null");
      } else {
        sb.append(this.id);
      }
      first = false;
    }
    if (isSetIds()) {
      if (!first) sb.append(", ");
      sb.append("ids:");
      if (this.ids == null) {
        sb.append("null");
      } else {
        sb.append(this.ids);
      }
      first = false;
    }
    if (isSetMixed()) {
      if (!first) sb.append(", ");
      sb.append("mixed:");
      if (this.mixed == null) {
        sb.append("null");
      } else {
        sb.append(this.mixed);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' was not present! Struct: " + toString());
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReferenceSlotStandardSchemeFactory implements SchemeFactory {
    public ReferenceSlotStandardScheme getScheme() {
      return new ReferenceSlotStandardScheme();
    }
  }

  private static class ReferenceSlotStandardScheme extends StandardScheme<ReferenceSlot> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReferenceSlot struct) throws org.apache.thrift.TException {
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
          case 2: // POSITION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.position = iprot.readI32();
              struct.setPositionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // POSITIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list88 = iprot.readListBegin();
                struct.positions = new ArrayList<Integer>(_list88.size);
                int _elem89;
                for (int _i90 = 0; _i90 < _list88.size; ++_i90)
                {
                  _elem89 = iprot.readI32();
                  struct.positions.add(_elem89);
                }
                iprot.readListEnd();
              }
              struct.setPositionsIsSet(true);
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
          case 5: // IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list91 = iprot.readListBegin();
                struct.ids = new ArrayList<String>(_list91.size);
                String _elem92;
                for (int _i93 = 0; _i93 < _list91.size; ++_i93)
                {
                  _elem92 = iprot.readString();
                  struct.ids.add(_elem92);
                }
                iprot.readListEnd();
              }
              struct.setIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // MIXED
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list94 = iprot.readListBegin();
                struct.mixed = new ArrayList<MixedReference>(_list94.size);
                MixedReference _elem95;
                for (int _i96 = 0; _i96 < _list94.size; ++_i96)
                {
                  _elem95 = new MixedReference();
                  _elem95.read(iprot);
                  struct.mixed.add(_elem95);
                }
                iprot.readListEnd();
              }
              struct.setMixedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReferenceSlot struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPosition()) {
        oprot.writeFieldBegin(POSITION_FIELD_DESC);
        oprot.writeI32(struct.position);
        oprot.writeFieldEnd();
      }
      if (struct.positions != null) {
        if (struct.isSetPositions()) {
          oprot.writeFieldBegin(POSITIONS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.positions.size()));
            for (int _iter97 : struct.positions)
            {
              oprot.writeI32(_iter97);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.id != null) {
        if (struct.isSetId()) {
          oprot.writeFieldBegin(ID_FIELD_DESC);
          oprot.writeString(struct.id);
          oprot.writeFieldEnd();
        }
      }
      if (struct.ids != null) {
        if (struct.isSetIds()) {
          oprot.writeFieldBegin(IDS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.ids.size()));
            for (String _iter98 : struct.ids)
            {
              oprot.writeString(_iter98);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.mixed != null) {
        if (struct.isSetMixed()) {
          oprot.writeFieldBegin(MIXED_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.mixed.size()));
            for (MixedReference _iter99 : struct.mixed)
            {
              _iter99.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReferenceSlotTupleSchemeFactory implements SchemeFactory {
    public ReferenceSlotTupleScheme getScheme() {
      return new ReferenceSlotTupleScheme();
    }
  }

  private static class ReferenceSlotTupleScheme extends TupleScheme<ReferenceSlot> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReferenceSlot struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.name);
      BitSet optionals = new BitSet();
      if (struct.isSetPosition()) {
        optionals.set(0);
      }
      if (struct.isSetPositions()) {
        optionals.set(1);
      }
      if (struct.isSetId()) {
        optionals.set(2);
      }
      if (struct.isSetIds()) {
        optionals.set(3);
      }
      if (struct.isSetMixed()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPosition()) {
        oprot.writeI32(struct.position);
      }
      if (struct.isSetPositions()) {
        {
          oprot.writeI32(struct.positions.size());
          for (int _iter100 : struct.positions)
          {
            oprot.writeI32(_iter100);
          }
        }
      }
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetIds()) {
        {
          oprot.writeI32(struct.ids.size());
          for (String _iter101 : struct.ids)
          {
            oprot.writeString(_iter101);
          }
        }
      }
      if (struct.isSetMixed()) {
        {
          oprot.writeI32(struct.mixed.size());
          for (MixedReference _iter102 : struct.mixed)
          {
            _iter102.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReferenceSlot struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.name = iprot.readString();
      struct.setNameIsSet(true);
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.position = iprot.readI32();
        struct.setPositionIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list103 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.positions = new ArrayList<Integer>(_list103.size);
          int _elem104;
          for (int _i105 = 0; _i105 < _list103.size; ++_i105)
          {
            _elem104 = iprot.readI32();
            struct.positions.add(_elem104);
          }
        }
        struct.setPositionsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list106 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.ids = new ArrayList<String>(_list106.size);
          String _elem107;
          for (int _i108 = 0; _i108 < _list106.size; ++_i108)
          {
            _elem107 = iprot.readString();
            struct.ids.add(_elem107);
          }
        }
        struct.setIdsIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list109 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.mixed = new ArrayList<MixedReference>(_list109.size);
          MixedReference _elem110;
          for (int _i111 = 0; _i111 < _list109.size; ++_i111)
          {
            _elem110 = new MixedReference();
            _elem110.read(iprot);
            struct.mixed.add(_elem110);
          }
        }
        struct.setMixedIsSet(true);
      }
    }
  }

}

