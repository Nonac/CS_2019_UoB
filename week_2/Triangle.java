class Triangle
{
    private int firstLength;
    private int secondLength;
    private int thirdLength;
    private TriangleType type;

    // Class to represent trinagles
    Triangle(int first, int second, int third)
    {
        firstLength = first;
        secondLength = second;
        thirdLength = third;
        type = identifyTriangleType(first, second, third);
    }

    // Returns the (previously indentified) type of this triangle
    TriangleType getType()
    {
        return type;
    }

    // Returns a printable string that describes this triangle
    public String toString()
    {
        return "(" + firstLength + "," + secondLength + "," + thirdLength + ")";
    }

    // Works out what kind of triangle this is !
    static TriangleType identifyTriangleType(int first, int second, int third)
    {
        int temp;
        if(first<second) {temp=first;first=second;second=temp;}
        if(second<third) {temp=second;second=third;third=temp;}
        if(first<second) {temp=first;first=second;second=temp;}
        if((first<=0)||(second<=0)||(third<=0))
        {
            return TriangleType.Illegal;
        }
        if(triangleVaild(first,second,third))
        {
            if((first==second)&&(first==third))
            {
                return TriangleType.Equilateral;
            }else if((first==second)||(second==third))
            {
                return TriangleType.Isosceles;
            }
            if(ifRight(first,second,third))
            {
                return TriangleType.Right;
            }
            return TriangleType.Scalene;
        }else if((second+third)==first)
        {
            return TriangleType.Flat;
        }
        return TriangleType.Impossible;
    }

    static boolean triangleVaild(int first, int second, int third)
    {
        long x=Integer.toUnsignedLong(second)+Integer.toUnsignedLong(third);
        long y=Integer.toUnsignedLong(first);
        if(x>y)
        {
            return true;
        }
        return false;
    }

    static boolean ifRight(int first, int second, int third)
    {
        double x=Integer.toUnsignedLong(first)*Integer.toUnsignedLong(first);
        double y=(Integer.toUnsignedLong(second)*Integer.toUnsignedLong(second))+
                (Integer.toUnsignedLong(third)*Integer.toUnsignedLong(third));
        if ((x==y)&&(x<=Integer.MAX_VALUE)&&(y<=Integer.MAX_VALUE))
        {
            return true;
        }
        return false;
    }
}
