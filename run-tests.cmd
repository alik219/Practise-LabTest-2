@echo off
cd /d "%~dp0"
java tools/PracticeRunner.java %*
exit /b %errorlevel%
