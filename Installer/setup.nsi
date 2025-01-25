!include "LogicLib.nsh"
!include "StrFunc.nsh"

${StrStr} 

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
    StrCpy $MinFxVersion "21.0.6"
    StrCpy $MinJavaVersion "22.0.2"
    StrCpy $JavaURL "https://download.oracle.com/java/22/archive/jdk-22.0.2_windows-x64_bin.exe"
    StrCpy $JavaFileName "jdk-22.0.2_windows-x64_bin.exe"
    StrCpy $FxURL "https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_windows-x64_bin-sdk.zip"
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

Function Main
    Call InitializeVar
    Call IsJavaInstalled
    Pop $JavaCheckResultCode
    Pop $JavaCheckOuput

    ${If} $JavaCheckResultCode == 0
      Call RetrieveJavaVersion
      MessageBox MB_OK|MB_ICONINFORMATION "Java $UserJavaVersion Is Installed"
      Call GetDownloadsFolder
      MessageBox MB_OK|MB_ICONINFORMATION "Path $DownloadsFolder"
      Call DownloadJava
    ${Else}
        MessageBox MB_OK|MB_ICONEXCLAMATION "Java Isn't Installed In This Machine"
    ${EndIF}

FunctionEnd

Section "Install"
    Call Main ; 
SectionEnd