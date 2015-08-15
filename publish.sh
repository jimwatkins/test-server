scp -o ProxyCommand="ssh -A centos@10.34.112.203 nc 192.168.2.11 22" src/main/java/TestServer.java centos@192.168.2.11:~/test-server
