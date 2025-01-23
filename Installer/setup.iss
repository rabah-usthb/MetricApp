[Setup]
AppName=MetricApp
AppVersion=1.0
DefaultDirName={pf}\MetricApp
DefaultGroupName=MetricApp
OutputDir=.
OutputBaseFilename=MetricInstaller

[Files]
Source: "app.jar"; DestDir: "{app}"; Flags: ignoreversion

[Code]
function IsJavaInstalled(): Boolean;
var
  ResultCode: Integer;
begin
 
  if Exec('cmd.exe', '/c java -version', '', SW_HIDE, ewWaitUntilTerminated, ResultCode) then
  begin
    Result := (ResultCode = 0);
  end
  else
  begin
    Result := False;
  end;
end;

//function IsRightVersion(): boolean

//begin

//end; 

function PosEx(SubString: String;WholeString: String; StartIndex: Integer): Integer;
var
SearchString: String;
begin
SearchString:= Copy(WholeString,StartIndex,Length(WholeString)-StartIndex+1);
//MsgBox('SearchString '+SearchString, mbError, MB_OK);
if Pos(SubString,SearchString) = 0 then
begin
Result:= 0;
//MsgBox('SearchString I Not Found '+IntToStr(Result), mbError, MB_OK);
end
else
begin
Result:=Pos(SubString,SearchString)+StartIndex-1;
//MsgBox('SearchString I '+IntToStr(Result), mbError, MB_OK);
end
end;



function SplitString (SubString: String;WholeString: String) : TArrayOfString;

var
StringArray: TArrayOfString;
LoopString: String;
Size: Integer;
StartIndex: Integer;
SplitIndex :Integer;

begin
Size:=0;

StartIndex:=1;
SplitIndex:=PosEx(SubString,WholeString,StartIndex);

while (SplitIndex > 0) do
begin
Size:=Size+1;
SetArrayLength(StringArray, Size);
StringArray[Size-1]:=Copy(WholeString,StartIndex,SplitIndex-StartIndex);

StartIndex:=SplitIndex+1;

SplitIndex:=PosEx(SubString,WholeString,StartIndex);
end;

Size:=Size+1;
SetArrayLength(StringArray, Size);
StringArray[Size-1]:=Copy(WholeString,StartIndex,Length(WholeString)-StartIndex+1);

begin
Result:= StringArray; 
end
end;


function Max(a,b: Integer):Integer;

begin

if (a>=b) then
begin 
Result:=a;
end
else
begin
Result:=b;
end
end;


function CheckJavaVersion(MinVersion, UserVersion: String) : Boolean;

var 
MinArray: TArrayOfString;
UserArray: TArrayOfString;
i: Integer;
loop: Integer;
B: Boolean;

begin
MinArray:= SplitString('.',MinVersion);
UserArray:= SplitString('.',UserVersion);

loop:=GetArrayLength(UserArray)-1;

for i := 0 to loop do
begin

if(MinArray[i]<UserArray[i]) then
begin
B:= True;
Break;
end
else if (MinArray[i]>UserArray[i]) then
begin
B:=False;
Break;
end
end;

Result:=B;

end; 

procedure printSplit (sub,str: String);

var
ar: TArrayOfString;
i: Integer;

begin
ar:=SplitString(sub,str);
 
 for i := 0 to GetArrayLength(ar) - 1 do
 begin
 MsgBox('Split '+ar[i], mbError, MB_OK);
 end;
end;

function FetchJavaVersion(): String;
var
  TempFile: String;
  Output: TArrayOfString;
  ResultCode: Integer;
  StartIndex: Integer;
  EndIndex: Integer;
begin  
  TempFile := ExpandConstant('{tmp}\java_version.txt');

  Exec('cmd.exe', '/c java -version > "' + TempFile + '" 2>&1', '', SW_HIDE, ewWaitUntilTerminated, ResultCode);

  // Read the output from the temporary file
  if LoadStringsFromFile(TempFile, Output) and (GetArrayLength(Output) > 0) then
  begin
  MsgBox('String '+Output[0], mbError, MB_OK);
  StartIndex:= Pos('"', Output[0])+1;
  EndIndex:= PosEx('"', Output[0], StartIndex)-1;
  MsgBox('I '+IntToStr(StartIndex)+' J '+IntToStr(EndIndex), mbError, MB_OK);
  Result := Copy(Output[0],StartIndex,EndIndex-StartIndex+1);
  end
  else 
  begin
  Result:='Failed';
  end;
 end;

function InitializeSetup(): Boolean;
begin
  if not IsJavaInstalled then
  begin
    MsgBox('Java is not installed. Please install Java before proceeding.', mbError, MB_OK);
    Result := False; 
  end
  else
  begin
    MsgBox('Java '+FetchJavaVersion()+' is installed.', mbError, MB_OK);
    //printSplit('.','22.032.21.212');
    
    if not CheckJavaVersion('22.0.1.2','23.0.1.1') then 
    
    begin 
    MsgBox('Java Not Right Version', mbError, MB_OK);
    end;
    
    Result := True; 
  end;
end;
