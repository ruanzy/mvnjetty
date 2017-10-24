#!/bin/sh

#check JAVA_HOME & java

$EXECUTABLE=../abc-0.0.1-SNAPSHOT.war
LOG=../log/"`date +%Y%m%d`".log
noJavaHome=false
	if [ -z "$JAVA_HOME" ] ; then
    	noJavaHome=true
    fi
    if [ ! -e "$JAVA_HOME/bin/java" ] ; then
        noJavaHome=true
	fi
	if $noJavaHome ; then
	    echo
	    echo "Error: JAVA_HOME environment variable is not set."
		echo
		exit 1
	fi
	
	JVM_MEM_OPTS="-Xms1024m
        -Xmx1024m
        -XX:PermSize=256m
        -XX:MaxPermSize=256m"

	echo "Java memory options: ${JVM_MEM_OPTS}"
	
    JVM_OPTIONS="${JVM_MEM_OPTS}
        -XX:-UseGCOverheadLimit -XX:+HeapDumpOnOutOfMemoryError";

    JVM_DEFINES=" 
         -Djava.io.tmpdir=../work
         -Xdebug -Xrunjdwp:transport=dt_socket,address=6999,server=y,suspend=n
        ";
    mkdir -p ../log ../work;

	nohup java $JVM_OPTIONS $JVM_DEFINES  -jar $EXECUTABLE > $LOG 2>&1 &