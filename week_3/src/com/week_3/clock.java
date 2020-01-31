package com.week_3;

class Counter
{
    private int limit,count;
    Counter next;
    Counter(int limit0,int count0, Counter next0)
    {
        this.limit=limit0;
        this.count=count0;
        this.next=next0;
    }
    void tick()
    {
        count++;
        if(count==limit)
        {
            count=0;
            if(next!=null)
            {
                next.tick();
            }
        }
    }
    String display()
    {
        if(count<=9)
        {
            return "0"+count;
        }else return ""+count;
    }
}

class Clock
{
    private Counter hour, minute, second;

    public static void main(String[] args) {
        Clock clock= new Clock();
        clock.run(args);

    }
    void run(String[] args)
    {
        int[] begin=getTime(args);
        setup(begin);
        start();
    }

    void setup(int[] begin)
    {
        hour = new Counter(24,begin[0],null);
        minute = new Counter(60,begin[1],hour);
        second = new Counter(60,begin[2],minute);
    }
    void start(){
        while (true){
            String time=hour.display()+":"+minute.display()+":"+second.display();
            System.out.println(time);
            try { Thread.sleep(1000); }
            catch (Exception e) { throw new Error(e); }
            second.tick();
        }
    }
    int[] getTime(String[] args)
    {
        int[] begin= {0,0,0};
        if(args.length>3) fail();
        if(args.length>=1){
            begin[0]=Integer.parseInt(args[0]);
        }
        if(args.length>=2)
        {
            begin[1]=Integer.parseInt(args[1]);
        }
        if(args.length>=3)
        {
            begin[2]=Integer.parseInt(args[2]);
        }
        return begin;
    }
    void fail()
    {
        System.err.println("Use: java Clock [h] [m] [s]");
        System.exit(1);
    }
}