import java.io.*;

// Class to convert unit marks to grades
class GradeConverter
{
    // Converts a numerical mark (0 to 100) into a textual grade
    // Returns "Invalid" if the number is invalid
    String convertMarkToGrade(int mark)
    {
        if((mark>=70)&&(mark<=100))
        {
            return "Distinction";
        }else if ((mark>=60)&&(mark<70))
        {
            return "Merit";
        }
        else if((mark>=50)&&(mark<60))
        {
            return "Pass";
        }else if((mark>=0)&&(mark<50))
        {
            return "Fail";
        }else {
            return "Invalid";
        }
    }

    // Reads a mark from a String and returns the mark as an int (0 to 100)
    // Returns -1 if the string is invalid
    int convertStringToMark(String text)
    {
        int len=text.length();
        int i,temp,res=0;
        boolean flag1=true,flag2=true;
        if(len==0||(text.charAt(0)=='0'&&len>1))
        {
            return -1;
        }
        if (isValid(text)){
            return -1;
        }
        for(i=0;i<len;i++)
        {
            if(text.charAt(i)=='%')
            {
                return ((res>=0)&&(res<=100))?res:-1;
            }else if(text.charAt(i)=='.')
            {
                res+=carryVaild(text);
                return ((res>=0)&&(res<=100))?res:-1;
            }else {
                res *=10;
            }
            temp=convertCharToInt(text.charAt(i));
            if(temp==-1)
            {
                return -1;
            }
            res+=temp;
        }
        return ((res>=0)&&(res<=100))?res:-1;

    }
    boolean isValid(String text)
    {
        boolean flag1=true,flag2=true;
        int i,len=text.length();
        for(i=0;i<len;i++)
        {
            switch (text.charAt(i)){
                case '.':
                    if(flag1){
                        flag1=false;
                    }else {
                        return true;
                    }
                    break;
                case '%':
                    if(flag2){
                        flag2=false;
                    }else {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    int carryVaild(String text)
    {
        int n,flag=1,len=text.length();
        int carry=0;
        for(n=0;((n<len)&&(flag==1));n++)
        {
            if(text.charAt(n)=='.'){
                flag=0;
            }
        }
        if(convertCharToInt(text.charAt(n))+carry>4)
        {
            carry=1;
        }
        return carry;
    }
    // Convert a single character to an int (0 to 9)
    // Returns -1 if char is not numerical
    int convertCharToInt(char c)
    {
        if ((c>='0')&&(c<='9'))
        {
            return c-'0';
        }
        return -1;
    }

    public static void main(String[] args) throws IOException
    {
        GradeConverter converter = new GradeConverter();
        while(true) {
            System.out.print("Please enter your mark: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            int mark = converter.convertStringToMark(input);
            String grade = converter.convertMarkToGrade(mark);
            System.out.println("A mark of " + input + " is " + grade);
        }
    }
}
