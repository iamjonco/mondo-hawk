//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


//HELPER FUNCTIONS AND STRUCTURES

IFCExport_exportAsSTEP_args = function(args) {
  this.hawkInstance = null;
  this.options = null;
  if (args) {
    if (args.hawkInstance !== undefined && args.hawkInstance !== null) {
      this.hawkInstance = args.hawkInstance;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field hawkInstance is unset!');
    }
    if (args.options !== undefined && args.options !== null) {
      this.options = new IFCExportOptions(args.options);
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field options is unset!');
    }
  }
};
IFCExport_exportAsSTEP_args.prototype = {};
IFCExport_exportAsSTEP_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.hawkInstance = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.options = new IFCExportOptions();
        this.options.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_exportAsSTEP_args.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_exportAsSTEP_args');
  if (this.hawkInstance !== null && this.hawkInstance !== undefined) {
    output.writeFieldBegin('hawkInstance', Thrift.Type.STRING, 1);
    output.writeString(this.hawkInstance);
    output.writeFieldEnd();
  }
  if (this.options !== null && this.options !== undefined) {
    output.writeFieldBegin('options', Thrift.Type.STRUCT, 2);
    this.options.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_exportAsSTEP_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new IFCExportJob(args.success);
    }
  }
};
IFCExport_exportAsSTEP_result.prototype = {};
IFCExport_exportAsSTEP_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new IFCExportJob();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_exportAsSTEP_result.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_exportAsSTEP_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_getJobs_args = function(args) {
};
IFCExport_getJobs_args.prototype = {};
IFCExport_getJobs_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_getJobs_args.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_getJobs_args');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_getJobs_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = Thrift.copyList(args.success, [IFCExportJob]);
    }
  }
};
IFCExport_getJobs_result.prototype = {};
IFCExport_getJobs_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.LIST) {
        var _size424 = 0;
        var _rtmp3428;
        this.success = [];
        var _etype427 = 0;
        _rtmp3428 = input.readListBegin();
        _etype427 = _rtmp3428.etype;
        _size424 = _rtmp3428.size;
        for (var _i429 = 0; _i429 < _size424; ++_i429)
        {
          var elem430 = null;
          elem430 = new IFCExportJob();
          elem430.read(input);
          this.success.push(elem430);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_getJobs_result.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_getJobs_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.LIST, 0);
    output.writeListBegin(Thrift.Type.STRUCT, this.success.length);
    for (var iter431 in this.success)
    {
      if (this.success.hasOwnProperty(iter431))
      {
        iter431 = this.success[iter431];
        iter431.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_getJobStatus_args = function(args) {
  this.jobID = null;
  if (args) {
    if (args.jobID !== undefined && args.jobID !== null) {
      this.jobID = args.jobID;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field jobID is unset!');
    }
  }
};
IFCExport_getJobStatus_args.prototype = {};
IFCExport_getJobStatus_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.jobID = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_getJobStatus_args.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_getJobStatus_args');
  if (this.jobID !== null && this.jobID !== undefined) {
    output.writeFieldBegin('jobID', Thrift.Type.STRING, 1);
    output.writeString(this.jobID);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_getJobStatus_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new IFCExportJob(args.success);
    }
  }
};
IFCExport_getJobStatus_result.prototype = {};
IFCExport_getJobStatus_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new IFCExportJob();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_getJobStatus_result.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_getJobStatus_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_killJob_args = function(args) {
  this.jobID = null;
  if (args) {
    if (args.jobID !== undefined && args.jobID !== null) {
      this.jobID = args.jobID;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field jobID is unset!');
    }
  }
};
IFCExport_killJob_args.prototype = {};
IFCExport_killJob_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.jobID = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_killJob_args.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_killJob_args');
  if (this.jobID !== null && this.jobID !== undefined) {
    output.writeFieldBegin('jobID', Thrift.Type.STRING, 1);
    output.writeString(this.jobID);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExport_killJob_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = args.success;
    }
  }
};
IFCExport_killJob_result.prototype = {};
IFCExport_killJob_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.BOOL) {
        this.success = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

IFCExport_killJob_result.prototype.write = function(output) {
  output.writeStructBegin('IFCExport_killJob_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.BOOL, 0);
    output.writeBool(this.success);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

IFCExportClient = function(input, output) {
    this.input = input;
    this.output = (!output) ? input : output;
    this.seqid = 0;
};
IFCExportClient.prototype = {};
IFCExportClient.prototype.exportAsSTEP = function(hawkInstance, options, callback) {
  this.send_exportAsSTEP(hawkInstance, options, callback); 
  if (!callback) {
    return this.recv_exportAsSTEP();
  }
};

IFCExportClient.prototype.send_exportAsSTEP = function(hawkInstance, options, callback) {
  this.output.writeMessageBegin('exportAsSTEP', Thrift.MessageType.CALL, this.seqid);
  var args = new IFCExport_exportAsSTEP_args();
  args.hawkInstance = hawkInstance;
  args.options = options;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_exportAsSTEP();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

IFCExportClient.prototype.recv_exportAsSTEP = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new IFCExport_exportAsSTEP_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'exportAsSTEP failed: unknown result';
};
IFCExportClient.prototype.getJobs = function(callback) {
  this.send_getJobs(callback); 
  if (!callback) {
    return this.recv_getJobs();
  }
};

IFCExportClient.prototype.send_getJobs = function(callback) {
  this.output.writeMessageBegin('getJobs', Thrift.MessageType.CALL, this.seqid);
  var args = new IFCExport_getJobs_args();
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_getJobs();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

IFCExportClient.prototype.recv_getJobs = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new IFCExport_getJobs_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'getJobs failed: unknown result';
};
IFCExportClient.prototype.getJobStatus = function(jobID, callback) {
  this.send_getJobStatus(jobID, callback); 
  if (!callback) {
    return this.recv_getJobStatus();
  }
};

IFCExportClient.prototype.send_getJobStatus = function(jobID, callback) {
  this.output.writeMessageBegin('getJobStatus', Thrift.MessageType.CALL, this.seqid);
  var args = new IFCExport_getJobStatus_args();
  args.jobID = jobID;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_getJobStatus();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

IFCExportClient.prototype.recv_getJobStatus = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new IFCExport_getJobStatus_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'getJobStatus failed: unknown result';
};
IFCExportClient.prototype.killJob = function(jobID, callback) {
  this.send_killJob(jobID, callback); 
  if (!callback) {
    return this.recv_killJob();
  }
};

IFCExportClient.prototype.send_killJob = function(jobID, callback) {
  this.output.writeMessageBegin('killJob', Thrift.MessageType.CALL, this.seqid);
  var args = new IFCExport_killJob_args();
  args.jobID = jobID;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_killJob();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

IFCExportClient.prototype.recv_killJob = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new IFCExport_killJob_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'killJob failed: unknown result';
};
