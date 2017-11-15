@echo off
set "Y=%date:~0,4%"
set "M=%date:~5,2%"
set "D=%date:~8,2%"
set "LOGDIR=../log"
set "LOG=%LOGDIR%/%Y%%M%%D%.log"
set "JAVA_CMD=%JAVA_HOME%/bin/java"
set "EXECUTABLE=../abc-0.0.1-SNAPSHOT.war"
if "%JAVA_HOME%" == "" goto noJavaHome
if exist "%JAVA_HOME%\bin\java.exe" goto mainEntry
:noJavaHome
echo ---------------------------------------------------
echo WARN: JAVA_HOME environment variable is not set. 
echo ---------------------------------------------------
:mainEntry
set "JVM_ARGS=-Dcom.sun.management.jmxremote.port=9401 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
set "JVM_XDEBUG=-Xdebug -Xrunjdwp:transport=dt_socket,address=6999,server=y,suspend=n"
md "%LOGDIR%"
java %JVM_ARGS% %JVM_XDEBUG% -jar %EXECUTABLE% > %LOG%