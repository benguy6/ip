@ECHO OFF
setlocal EnableExtensions EnableDelayedExpansion

REM clean bin to avoid stale class files
if exist ..\bin rmdir /s /q ..\bin
mkdir ..\bin

REM delete outputs from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ACTUAL-SAVE.TXT del ACTUAL-SAVE.TXT
if exist ACTUAL-LOAD.TXT del ACTUAL-LOAD.TXT

REM reset data so persistence test is repeatable
if exist ..\data rmdir /s /q ..\data
if exist data rmdir /s /q data

REM compile (include parser!)
(
  javac -cp ..\src\main\java -Xlint:none -d ..\bin ^
  ..\src\main\java\orion\*.java ^
  ..\src\main\java\orion\parser\*.java ^
  ..\src\main\java\orion\task\*.java ^
  ..\src\main\java\orion\storage\*.java ^
  ..\src\main\java\orion\ui\*.java
)
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM =========================
REM TEST 1: Normal behaviour
REM =========================
java -classpath ..\bin orion.Orion < input.txt > ACTUAL.TXT
FC ACTUAL.TXT EXPECTED.TXT
IF ERRORLEVEL 1 (
    echo ********** NORMAL TEST MISMATCH **********
    exit /b 1
)

REM =========================
REM TEST 2: Persistence (save/load)
REM =========================
if exist ..\data\orion.txt del ..\data\orion.txt

java -classpath ..\bin orion.Orion < input-save.txt > ACTUAL-SAVE.TXT
IF ERRORLEVEL 1 (
    echo ********** RUN 1 FAILED **********
    exit /b 1
)

java -classpath ..\bin orion.Orion < input-load.txt > ACTUAL-LOAD.TXT
IF ERRORLEVEL 1 (
    echo ********** RUN 2 FAILED **********
    exit /b 1
)

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