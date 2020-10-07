/* Calculate a grade from a mark. */
class Grade {

    // Convert the mark into a grade   TODO: complete this
    String grade(int mark) {
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

    // Convert string to int, or -1 if invalid TODO: complete this
    int convert(String mark) {
        int len=mark.length();
        int i,temp,res=0;
        if(len==0||(mark.charAt(0)=='0'&&len>1))
        {
            return -1;
        }
        for(i=0;i<len;i++)
        {
            res *=10;
            temp=convertCharToInt(mark.charAt(i));
            if(temp==-1)
            {
                return -1;
            }
            res+=temp;
        }
        return ((res>=0)&&(res<=100))?res:-1;
    }

    int convertCharToInt(char c)
    {
        if ((c>='0')&&(c<='9'))
        {
            return c-'0';
        }
        return -1;
    }
    // Check each grade
    void testGrades() {
        assert(grade(45).equals("Fail"));
        assert(grade(55).equals("Pass"));
        assert(grade(65).equals("Merit"));
        assert(grade(75).equals("Distinction"));
    }

    // Check boundaries: 0..49, 50..59, 60..69, 70..100
    void testBoundaries() {
        assert(grade(0).equals("Fail"));
        assert(grade(49).equals("Fail"));
        assert(grade(50).equals("Pass"));
        assert(grade(59).equals("Pass"));
        assert(grade(60).equals("Merit"));
        assert(grade(69).equals("Merit"));
        assert(grade(70).equals("Distinction"));
        assert(grade(100).equals("Distinction"));
    }

    // Check out of range gives Invalid
    void testRange() {
        assert(grade(-1).equals("Invalid"));
        assert(grade(101).equals("Invalid"));
    }

    // Check convert works on numbers in range
    void testConvert() {
        assert(convert("0") == 0);
        assert(convert("53") == 53);
        assert(convert("100") == 100);
    }

    // Check non-digits or number out of range or leading zero gives -1
    // (Note: leading zeros are potentially ambiguous because they
    // indicate octal in some circumstances)
    void testInvalid() {
        assert(convert("x") == -1);
        assert(convert("5x") == -1);
        assert(convert("5x6") == -1);
        assert(convert("40.5") == -1);
        assert(convert("-1") == -1);
        assert(convert("101") == -1);
        assert(convert("01") == -1);
        assert(convert("099") == -1);
    }

    // Run the tests.
    void test() {
        boolean testing = false;
        assert(testing = true);
        if (! testing) throw new Error("To test, use java -ea Grade");
        testGrades();
        testBoundaries();
        testRange();
        testConvert();
        testInvalid();
        System.out.println("All tests passed");
    }

    void run(String[] args) {
        if (args.length == 0) test();
        else if (args.length == 1) System.out.println(grade(convert(args[0])));
        else {
            System.err.println("Use:  java -ea Grade   or  java Grade m");
            System.err.println("where m is an integer mark from 0 to 100");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Grade program = new Grade();
        program.run(args);
    }
}
