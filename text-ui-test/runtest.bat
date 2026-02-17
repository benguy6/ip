@ECHO OFF
setlocal EnableExtensions EnableDelayedExpansion

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL-SAVE.TXT del ACTUAL-SAVE.TXT
if exist ACTUAL-LOAD.TXT del ACTUAL-LOAD.TXT

REM delete saved data so test is repeatable
if exist ..\data\orion.txt del ..\data\orion.txt

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

REM RUN 1: create tasks + save to disk
java -classpath ..\bin orion.Orion < input-save.txt > ACTUAL-SAVE.TXT
IF ERRORLEVEL 1 (
    echo ********** RUN 1 FAILED **********
    exit /b 1
)

REM RUN 2: restart app + load from disk + list
java -classpath ..\bin orion.Orion < input-load.txt > ACTUAL-LOAD.TXT
IF ERRORLEVEL 1 (
    echo ********** RUN 2 FAILED **********
    exit /b 1
)

REM compare outputs
FC ACTUAL-SAVE.TXT EXPECTED-SAVE.TXT
IF ERRORLEVEL 1 (
    echo ********** SAVE OUTPUT MISMATCH **********
    exit /b 1
)

FC ACTUAL-LOAD.TXT EXPECTED-LOAD.TXT
IF ERRORLEVEL 1 (
    echo ********** LOAD OUTPUT MISMATCH **********
    exit /b 1
)

echo ********** ALL TESTS PASSED **********
