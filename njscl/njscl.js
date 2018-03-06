var PROTO_PATH = 'protos/mdr.proto';
var grpc = require('grpc');
var protoDescriptor = grpc.load(PROTO_PATH);
// the protoDescriptor object has the full package hierarchy
var routeguide = protoDescriptor.routeguide;

var Server = new grpc.Server();

print Server;
print routeguide;

throw 'i was here';


