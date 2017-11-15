@echo off
set javahome=.\jdk1.6.0_45
set cp=.\bin
set cp=%cp%;.\lib\postgresql-9.4-1203.jdbc4.jar

%javahome%\bin\javac.exe -d .\bin\ -cp %cp% .\src\stevens\cs562\SQLKnokout\output\Output.java

%javahome%\bin\java.exe -cp %cp% stevens.cs562.SQLKnokout.output.Output

pause