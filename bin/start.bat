@echo off
set "Y=%date:~0,4%"
set "M=%date:~5,2%"
set "D=%date:~8,2%"
set "LOG=log\%Y%%M%%D%.log"
set "JAVA_CMD=%JAVA_HOME%/bin/java"
if "%JAVA_HOME%" == "" goto noJavaHome
if exist "%JAVA_HOME%\bin\java.exe" goto mainEntry
:noJavaHome
echo ---------------------------------------------------
echo WARN: JAVA_HOME environment variable is not set. 
echo ---------------------------------------------------
set "JAVA_CMD=java"
:mainEntry
@echo on
"%JAVA_CMD%" -jar ../abc-0.0.1-SNAPSHOT.war
pause