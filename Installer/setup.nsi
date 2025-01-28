!include "LogicLib.nsh"
!include "StrFunc.nsh"
!include "nsDialogs.nsh"
!include "MUI2.nsh"
!include "FileFunc.nsh"

${StrRep}
${StrStr} 
${StrTok} 

Var MinJavaVersion
var MinFxVersion
var JavaURL
var FxURL
var JavaCheckResultCode
var JavaCheckOuput
var UserJavaVersion
var DownloadsFolder
var JavaFileName
var JavaBinPath
var JavaSDKPath
Var CreateShortcutCheckbox
Var LaunchAppCheckbox
var AppPath

Name "Java Metrics"
OutFile "MetricsInstaller.exe"
InstallDir "$PROGRAMFILES\Java Metrics"

!insertmacro MUI_LANGUAGE "English"

LangString MUI_TEXT_WELCOME_INFO_TITLE ${LANG_ENGLISH} "Welcome to the Installer"
LangString MUI_TEXT_WELCOME_INFO_TEXT ${LANG_ENGLISH} "This wizard will guide you through the installation process."
LangString MUI_TEXT_DIRECTORY_TITLE ${LANG_ENGLISH} "Choose Installation Directory"
LangString MUI_TEXT_DIRECTORY_SUBTITLE ${LANG_ENGLISH} "Specify where the program will be installed."

!insertmacro MUI_PAGE_WELCOME
Page custom PreMainPage ""
!insertmacro MUI_PAGE_DIRECTORY 
!insertmacro MUI_PAGE_INSTFILES
Page custom CheckBoxPage CheckBoxIf
!insertmacro MUI_PAGE_FINISH


Function DownloadJava
    StrCpy $JavaBinPath "$DownloadsFolder\$JavaFileName"
    inetc::get /POPUP "Downloading..." /CAPTION "Download Progress" $JavaURL $JavaBinPath

    ; Check the result of the download
    Pop $0 ; Result code (OK, Cancel, or error message)
    ${If} $0 == "OK"
        MessageBox MB_OK "Download completed successfully!"
    ${Else}
        MessageBox MB_OK|MB_ICONSTOP "Download failed: $0"
    ${EndIf}
FunctionEnd

Function InstallJava    
    ; Run the Java installer interactively
    ;StrCpy $JavaLogPath "$DownloadsFolder\JavaInstall.log"
    ExecWait '"$JavaBinPath"' $0

    ; Check if the installer finished successfully
    ${If} $0 == 0
        MessageBox MB_OK "Java installation completed successfully!"
    ${Else}
        MessageBox MB_OK|MB_ICONSTOP "Java installation failed with error code: $0"
    ${EndIf}
    Delete "$JavaBinPath"
FunctionEnd


Function GetDownloadsFolder
    ; Define the GUID for the Downloads folder
    System::Call 'shell32::SHGetKnownFolderPath(g "{374DE290-123F-4565-9164-39C4925E467B}", i 0, i 0, *i .r0)i.r1'

    ; Check if the function succeeded
    ${If} $1 == 0
        ; Debug: Check if $0 has a valid pointer
        System::Call '*$0(&w1024 .r2)' ; Retrieve the Unicode path
        ${If} $2 != ""
            MessageBox MB_OK "Successfully retrieved Unicode path: $2"
            StrCpy $DownloadsFolder $2  ; Convert to NSIS-compatible string
        ${Else}
            MessageBox MB_OK|MB_ICONSTOP "Unicode path is empty or invalid."
            StrCpy $DownloadsFolder "$DESKTOP"
        ${EndIf}

        ; Free the allocated memory
        System::Call 'ole32::CoTaskMemFree(i r0)'
    ${Else}
        MessageBox MB_OK|MB_ICONSTOP "Failed to retrieve the Downloads folder path."
        StrCpy $DownloadsFolder "$DESKTOP" ; Fallback to the Desktop folder
    ${EndIf}
FunctionEnd


Function InitializeVar
    StrCpy $JavaSDKPath "C:\Program Files\Java\jdk-22"
    StrCpy $MinFxVersion "21.0.6"
    StrCpy $MinJavaVersion "22.0.2"
    StrCpy $JavaURL "https://download.oracle.com/java/22/archive/jdk-22.0.2_windows-x64_bin.exe"
    ;StrCpy $JavaURL "https://download.oracle.com/java/23/latest/jdk-23_windows-x64_bin.exe"
    StrCpy $JavaFileName "jdk-22.0.2_windows-x64_bin.exe"
    StrCpy $FxURL "https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_windows-x64_bin-sdk.zip"
FunctionEnd


Function AddJavaToPath
   
    StrCpy $1 "$JavaSDKPath\bin" ; Construct the bin directory path
    ; Add the Java bin directory to the system PATH
    ReadEnvStr $0 "PATH" ; Read the current PATH value
      ${StrRep} $0 $0 $1 "" 
        ; Prepend the bin directory to the PATH
        StrCpy $0 "$1;$0"

        ; Update the PATH in the registry
        WriteRegStr HKLM "SYSTEM\CurrentControlSet\Control\Session Manager\Environment" "PATH" "$0"

        ; Update the PATH in the current environment
        System::Call 'Kernel32::SetEnvironmentVariable(t "PATH", t "$0")'

        ; Display a success message
        MessageBox MB_OK "Added Java to the system PATH: $1"
    
FunctionEnd


Function RetrieveJavaVersion
    ${StrStr} $0 $JavaCheckOuput '"'
     ;MessageBox MB_OK|MB_ICONINFORMATION "String 0 : $0"
    StrCpy $1 $0 "" 1
      ;MessageBox MB_OK|MB_ICONINFORMATION "String 1 : $1"
    ${StrStr} $2 $1 '"'
      ;MessageBox MB_OK|MB_ICONINFORMATION "String 2 : $2"
    StrCpy $UserJavaVersion $1 -$2
    
    StrLen $3 $2 
    StrLen $4 $1 
    IntOp $5 $4 - $3 

    StrCpy $UserJavaVersion $1 $5
    
    ;MessageBox MB_OK|MB_ICONINFORMATION "String 3 : $UserJavaVersion"


    
FunctionEnd

Function IsJavaInstalled
    nsExec::ExecToStack 'cmd.exe /c java -version'
FunctionEnd


Function CheckUserVersion
    StrCpy $5 0
    StrCpy $0 0

    Loop:
     ${If} $0 <= 3

     ${StrTok} $1 $UserJavaVersion "." $0 1
     ${StrTok} $2 $MinJavaVersion "." $0 1
     IntOp $3 $1 + 0
     IntOp $4 $2 + 0
     
     ${If} $3 > $4 
       IntOp $0 $0 + 4

        ${ElseIf} $3 < $4 
           IntOp $0 $0 + 4
           StrCpy $5 1
     ${EndIf}

      IntOp $0 $0 + 1
    Goto Loop
     ${EndIf}
 Push $5
FunctionEnd

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
        CreateShortcut "$DESKTOP\MetricApp.lnk" "$AppPath\MetricApp.exe" "" "$AppPath\MetricApp.exe" "" "" "" "$AppPath"
    ${EndIf}

    # Get the state of the "Launch Application After Installation" checkbox
    ${NSD_GetState} $LaunchAppCheckbox $1
    ${If} $1 == 1
        # Launch the application after installation
        Exec '"$AppPath\MetricApp.exe"'
    ${EndIf}
FunctionEnd

Function Main
    Call InitializeVar
    Call IsJavaInstalled
    Pop $JavaCheckResultCode
    Pop $JavaCheckOuput

    ${If} $JavaCheckResultCode == 0
      Call RetrieveJavaVersion
      Call CheckUserVersion
      Pop $0
       ${If} $0 = 0 
         MessageBox MB_OK|MB_ICONINFORMATION "Java $UserJavaVersion Is Already Installed"
        ${Else}
         MessageBox MB_OK|MB_ICONINFORMATION "An Older Java Version Is Installed In Your Machine $UserJavaVersion , We Will Install The Correct Version For You Java $MinJavaVersion"
         Call GetDownloadsFolder
         ;MessageBox MB_OK|MB_ICONINFORMATION "Path $DownloadsFolder"
         Call DownloadJava
         Call InstallJava
         Call AddJavaToPath
       ${EndIf}

    ${Else}
        MessageBox MB_OK|MB_ICONEXCLAMATION "Java Isn't Installed In Your Machine , Java $MinJavaVersion Will Be Installed"
        Call GetDownloadsFolder
        Call DownloadJava
        Call InstallJava
         Call AddJavaToPath
    ${EndIF}
FunctionEnd

Function PreMainPage
    Call Main
FunctionEnd

Section "Install Files"
    SetOutPath "$INSTDIR" 
    StrCpy $AppPath "$INSTDIR" 
    File "app.jar"
    File "MetricApp.exe"
     
    SetOutPath "$INSTDIR\Dependencies"
    File /r "Dependencies\*" 
    SetOutPath "$AppPath"

SectionEnd