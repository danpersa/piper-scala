
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



scala first run
wrk -t12 -c200 -d30s http://localhost:8085/template
Running 30s test @ http://localhost:8085/template
  12 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    68.05ms   72.74ms 897.49ms   91.54%
    Req/Sec   281.00    140.21     1.94k    73.56%
  99063 requests in 30.07s, 285.31MB read
  Socket errors: connect 0, read 148, write 0, timeout 0
Requests/sec:   3294.04
Transfer/sec:      9.49MB

scala second run
wrk -t12 -c200 -d30s http://localhost:8085/template
Running 30s test @ http://localhost:8085/template
  12 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    42.92ms   43.67ms 760.08ms   88.09%
    Req/Sec   439.11    304.89     4.41k    87.97%
  152976 requests in 30.05s, 440.59MB read
  Socket errors: connect 0, read 35, write 2, timeout 0
Requests/sec:   5091.17
Transfer/sec:     14.66MB

third run -> after jit comes into play :)
wrk -t12 -c200 -d30s http://localhost:8085/template
Running 30s test @ http://localhost:8085/template
  12 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    34.08ms   32.44ms 586.33ms   85.02%
    Req/Sec   539.36    354.58     4.41k    85.83%
  187142 requests in 30.10s, 538.99MB read
  Socket errors: connect 0, read 86, write 0, timeout 0
Requests/sec:   6217.89
Transfer/sec:     17.91MB