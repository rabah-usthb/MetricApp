package application.BackEnd;
import java.util.Random;

public class Hashing {
static String[]ListAlphabetMin= {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
static String[]ListAlphabetMaj= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
static String[]ListSpecialChar= {"-","+","/","\"","@","'","?",":",";","*","^","%","£","!","_","{","}","~","#","<",">"};
static String[]ListNumber= {"0","1","2","3","4","5","6","7","8","9"};
static String MinRegex="[a-z]";
static String MajRegex="[A-Z]";
static String NumRegex="[0-9]";
static String SpecialRegex="[\\-\\+@*&?/^:;'£$%!\\{\\}\\[\\]]";
public static void main(String arg[]) {
	String password = "password";
	System.out.println(GetHash(password));

	}

static String GetHash(String Password) {
	String salt = GetSalting();
	int index = salt.length()/2;
	String FirstHalf=salt.substring(0,index+1);
	String SecondHalf=salt.substring(index+1);
    String Hash = FirstHalf;
    int size = Password.length();
    int i=0;
    int j;
    while(i<size) {
    	String Char = Password.substring(i,i+1);
    	j = i;
    	if(Char.matches(MinRegex)) {
    		
    		while(j>25) {
    		 --j;
    		}
    		Hash = Hash + ListSpecialChar[j];
    	}
    	else if(Char.matches(MajRegex)) {
    		while(j>9) {
       		 --j;
       		}
    		Hash = Hash + ListNumber[j];
    	}
    	else if (Char.matches(SpecialRegex)) {
    		while(j>25) {
       		 --j;
       		}
    		Hash = Hash + ListAlphabetMaj[j];
    	}
    	else if (Char.matches(NumRegex)) {
    		while(j>20) {
       		 --j;
       		}
    		Hash = Hash + ListAlphabetMin[j];
    	}
    	++i;
    }
    Hash = Hash+SecondHalf;
    return Hash;
}

static String GetRandomFunction() {
	int a = 1;
	int b = 4;
	Random random = new Random();
	int RandomNum = a + random.nextInt(b - a + 1);
	switch(RandomNum) {
	case 1:
		return GetRandomMin();
	case 2:
		return GetRandomMaj();
	case 3:
		return GetRandomSpecial();
	case 4:
		return GetRandomMin();
	}
	return "";
}

static String GetSalting() {
	String Salt="";
	int i = 15;
	while(i>0) {
		Salt = Salt+GetRandomFunction();
		--i;
	}
	return Salt;
}

static String GetRandomSpecial() {
int a = 0;
int b = 20;
Random random = new Random();
int RandomIndex = a + random.nextInt(b - a + 1);
return ListSpecialChar[RandomIndex];
}

static String GetRandomNum() {
int a = 0;
int b = 9;
Random random = new Random();
int RandomIndex = a + random.nextInt(b - a + 1);
return ListNumber[RandomIndex];
}

static String GetRandomMaj() {
int a = 0;
int b = 25;
Random random = new Random();
int RandomIndex = a + random.nextInt(b - a + 1);
return ListAlphabetMaj[RandomIndex];
}

static String GetRandomMin() {
int a = 0;
int b = 25;
Random random = new Random();
int RandomIndex = a + random.nextInt(b - a + 1);
return ListAlphabetMin[RandomIndex];
}

}