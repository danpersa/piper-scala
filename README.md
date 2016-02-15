
## Build the Docker image

    make
    make docker-run

## Deploy

    make push
    make tick
    
## Code

Format the code:
    
    scalariformFormat
    
Static code analysis:

    scapegoat

Scala:
wrk -t12 -c200 -d30s http://localhost:8085/template
Running 30s test @ http://localhost:8085/template
  12 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    41.63ms   61.93ms 955.22ms   93.67%
    Req/Sec   499.30    284.05     3.50k    83.67%
  171068 requests in 30.09s, 232.97MB read
  Socket errors: connect 0, read 9, write 0, timeout 0
Requests/sec:   5685.64
Transfer/sec:      7.74MB

Node:
wrk -t12 -c200 -d30s http://localhost:8080/index
Running 30s test @ http://localhost:8080/index
  12 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   674.97ms  110.81ms   1.20s    65.88%
    Req/Sec    41.65     41.44   151.00     83.13%
  8211 requests in 30.09s, 13.53MB read
  Socket errors: connect 0, read 33, write 2, timeout 0
Requests/sec:    272.88
Transfer/sec:    460.53KB
