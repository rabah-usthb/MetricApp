!include "LogicLib.nsh"
!include "StrFunc.nsh"
!include "nsDialogs.nsh"
!include "MUI2.nsh"
!include "FileFunc.nsh"

${StrRep}
${StrStr} 
${StrTok} 

Var CreateShortcutCheckbox
Var LaunchAppCheckbox
var AppPath

Name "Java Metrics"
OutFile "metricsInstaller.exe"
InstallDir "$PROGRAMFILES\Java Metrics"

!insertmacro MUI_LANGUAGE "English"

LangString MUI_TEXT_WELCOME_INFO_TITLE ${LANG_ENGLISH} "Welcome to the Installer"
LangString MUI_TEXT_WELCOME_INFO_TEXT ${LANG_ENGLISH} "This wizard will guide you through the installation process."
LangString MUI_TEXT_DIRECTORY_TITLE ${LANG_ENGLISH} "Choose Installation Directory"
LangString MUI_TEXT_DIRECTORY_SUBTITLE ${LANG_ENGLISH} "Specify where the program will be installed."

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY 
!insertmacro MUI_PAGE_INSTFILES
Page custom CheckBoxPage CheckBoxIf
!insertmacro MUI_PAGE_FINISH




Function CheckBoxPage
    # Create the dialog
    nsDialogs::Create 1018
    Pop $0 # Retrieve the dialog handle (even if unused)

    # Add a checkbox for "Create Desktop Shortcut"
    ${NSD_CreateCheckbox} 10 10 100% 12u "Create Desktop Shortcut"
    Pop $CreateShortcutCheckbox
    ${NSD_SetState} $CreateShortcutCheckbox 0 # Set checkbox to unchecked by default

    # Add a checkbox for "Launch Application After Installation"
    ${NSD_CreateCheckbox} 10 40 100% 12u "Launch Application After Installation"
    Pop $LaunchAppCheckbox
    ${NSD_SetState} $LaunchAppCheckbox 1 # Set checkbox to checked by default

    # Show the dialog
    nsDialogs::Show
FunctionEnd


Function CheckBoxIf
    # Get the state of the "Create Desktop Shortcut" checkbox
    ${NSD_GetState} $CreateShortcutCheckbox $0
    ${If} $0 == 1
        # Create a desktop shortcut
        CreateShortcut "$DESKTOP\MetricApp.lnk" "$AppPath\run.exe" "" "$AppPath\run.exe" "" "" "" "$AppPath"
    ${EndIf}

    # Get the state of the "Launch Application After Installation" checkbox
    ${NSD_GetState} $LaunchAppCheckbox $1
    ${If} $1 == 1
        # Launch the application after installation
        Exec '"$AppPath\run.exe"'
    ${EndIf}
FunctionEnd



Section "Install Files"
    SetOutPath "$INSTDIR" 
    StrCpy $AppPath "$INSTDIR" 
    File "app.jar"
    File "run.exe"
     
    SetOutPath "$INSTDIR\custom-jre"
    File /r "custom-jre\*" 
    SetOutPath "$AppPath"

SectionEnd