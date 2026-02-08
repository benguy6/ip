@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
(
  javac -cp ..\src\main\java -Xlint:none -d ..\bin ^
  ..\src\main\java\orion\*.java ^
  ..\src\main\java\orion\task\*.java ^
  ..\src\main\java\orion\storage\*.java ^
  ..\src\main\java\orion\ui\*.java
)
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt and redirect output to ACTUAL.TXT
java -classpath ..\bin orion.Orion < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
