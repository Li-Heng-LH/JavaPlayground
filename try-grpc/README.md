#  gRPC


## Some Learning Notes ##

### gRPC and protobuf ###
* gRPC uses protobuf as both its Interface Definition Language (IDL) and as its underlying message interchange format. 

&nbsp;

### 4 kinds of service methods ###
* **simple RPC**: client sends a request to the server using the stub and waits for a response to come back. 
* **server-side streaming RPC**: client sends a request to the server and gets a stream to read a sequence of messages back.
* **client-side streaming RPC**: client writes a sequence of messages and sends them to the server. 
* **bidirectional streaming RPC**: both sides send a sequence of messages using a read-write stream.   
  * The two streams operate independently, so clients and servers can read and write in whatever order they like. 
  * in any combination of reads and writes
  * The order of messages in each stream is preserved.


&nbsp;


&nbsp;
----
### Useful links ###
* []()
