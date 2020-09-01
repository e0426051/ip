@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
C:\Users\admin\Downloads\openjdk-11.0.2_windows-x64_bin\jdk-11.0.2\bin\javac.exe -cp C:\Users\admin\Desktop\iP\src -Xlint:none -d C:\Users\admin\Desktop\iP\bin C:\Users\admin\Desktop\iP\src\main\java\Duke.java C:\Users\admin\Desktop\iP\src\main\java\Deadline.java C:\Users\admin\Desktop\iP\src\main\java\Event.java C:\Users\admin\Desktop\iP\src\main\java\Task.java C:\Users\admin\Desktop\iP\src\main\java\Todo.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
	pause
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -Dfile.encoding=UTF-8 -classpath C:\Users\admin\Desktop\iP\bin Duke < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

pause

