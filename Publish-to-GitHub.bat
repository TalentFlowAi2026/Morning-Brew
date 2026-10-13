@echo off

cd /d "%~dp0"

set GCM_INTERACTIVE=

set GIT_TERMINAL_PROMPT=1

echo Sign in to GitHub as TalentFlowAi2026 when prompted.

git -c credential.https://github.com.username=TalentFlowAi2026 push -u origin master

if errorlevel 1 (

    echo Publication failed. Review the message above and verify repository access.

) else (

    echo Published successfully to https://github.com/TalentFlowAi2026/Morning-Brew

)

pause

