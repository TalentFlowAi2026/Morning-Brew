@echo off
cd /d "%~dp0"
set GCM_INTERACTIVE=
set GIT_TERMINAL_PROMPT=1
echo Sign in to GitHub as YahiaNasrallah when prompted.
git -c credential.https://github.com.username=YahiaNasrallah push -u origin master
if errorlevel 1 (
    echo Publication failed. Review the message above and verify repository access.
) else (
    echo Published successfully to https://github.com/YahiaNasrallah/Morning-Brew
)
pause
