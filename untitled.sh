
#!/bin/sh
### BEGIN INIT INFO
# Provides:          vsftpdg
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# X-Interactive:     true
# Short-Description: Start/stop HeimdallBot server
### END INIT INFO

#Java alterações
JAVA_HOME=/home/ubuntu/HeimdallBot/tools/java
JDK_HOME=/home/ubuntu/HeimdallBot/tools/java
CLASSPATH=.:$JAVA_HOME/jre/lib/ext/pgjdbc2.jar:$JAVA_HOME/lib/tools.jar:$JAVA_H$
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/home/ubuntu/HeimdallBot/tools/java/bin/:/home/ubuntu/HeimdallBot/tools/maven/bin/

M2=/home/ubuntu/HeimdallBot/tools/maven/bin
M2_HOME=/home/ubuntu/HeimdallBot/tools/maven


case $1 in
    start)
        echo "Starting HeimdallBot ..."
        if [ ! -f /home/ubuntu/HeimdallBot/pid ]; then
            nohup java -jar /home/ubuntu/HeimdallBot/git/HeimdallBot/target/HeimdallBot-SuperSaiyajin2.5.jar >> /home/ubuntu/HeimdallBot/logs/log.txt &
            echo $! > /home/ubuntu/HeimdallBot/pid
            echo "HeimdallBot started ..."
        else
            echo "HeimdallBot is already running ..."
        fi
    ;;
    stop)
        if [ -f /home/ubuntu/HeimdallBot/pid ]; then
            PID=$(cat /home/ubuntu/HeimdallBot/pid);
            echo "Stopping HeimdallBot ..."
            kill $PID;
            echo "HeimdallBot stopped ..."
            rm /home/ubuntu/HeimdallBot/pid
        else
            echo "HeimdallBot is not running ..."
        fi
    ;;
    restart)
        if [ -f /home/ubuntu/HeimdallBot/pid ]; then
            PID=$(cat /home/ubuntu/HeimdallBot/pid);
            echo "Stopping HeimdallBot ...";
            kill $PID;
            echo "HeimdallBot stopped ...";
            rm /home/ubuntu/HeimdallBot/pid

            echo "Starting HeimdallBot ..."
            nohup java -jar /home/ubuntu/HeimdallBot/git/HeimdallBot/target/HeimdallBot-SuperSaiyajin2.5.jar >> /home/ubuntu/HeimdallBot/logs/log.txt &
            echo $! > /home/ubuntu/HeimdallBot/pid
            echo "HeimdallBot started ..."
        else
            echo "HeimdallBot is not running ..."
        fi
    ;;
esac