This is my project to create a minimal client-server usage of gRPC in several languages.

Problems yet to be solved:

# How to normalize the folder structure to best allow "sharing" of "common" resources
  (such as the gRPC "proto" files)?  Currently (for example), the "proto" file must be
  copied between the Java and the Node.js projects since the gradle plugin appears to
  REQUIRE the proto file to be located in the specific "src/main/proto" directory.

