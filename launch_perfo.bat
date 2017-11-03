@echo off
ECHO Starting Performance Testing...
ECHO IMPLEMENTATION 'ORIGINAL'
java -jar perfo.jar 1
ECHO IMPLEMENTATION 'NOKEYCHAR'
java -jar perfo.jar 2
ECHO IMPLEMENTATION 'NOMUTABLE'
java -jar perfo.jar 3
ECHO IMPLEMENTATION 'NOKEYCHAR_NOMUTABLE'
java -jar perfo.jar 4
pause