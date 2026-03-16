@echo off
setlocal
set "APP_HOME=%~dp0"
"%APP_HOME%runtime\bin\java.exe" -cp "%APP_HOME%app\app.jar;%APP_HOME%lib\jfreechart-1.5.4.jar" org.example.Main
endlocal
