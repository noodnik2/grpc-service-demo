# Simple Cross-Platform gRPC Client/Server

## Problems Yet to be Solved

- How to normalize the folder structure to best allow "sharing" of "common" resources
  (such as the gRPC "proto" files)?  Currently (for example), the "proto" file must be
  copied between the Java and the Node.js projects since the gradle plugin appears to
  REQUIRE the proto file to be located in the specific "src/main/proto" directory.

