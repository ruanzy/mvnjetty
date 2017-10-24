#!/bin/sh

$EXECUTABLE=../abc-0.0.1-SNAPSHOT.war
PID="`ps -ef|grep java|grep $EXECUTABLE|grep -v grep|awk '{print $2}'`";
if [ -n "$PID" ]; then
    echo Stop PID $PID;
    kill -9 $PID;
    sleep 1;
fi