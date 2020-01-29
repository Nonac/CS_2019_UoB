import java.io.*;

enum Type {SPACE,O,X}

public class OXO {
    private Board board;
    public static void main(String[] args) {
        OXO oxo=new OXO();
        oxo.run(args);
    }
    void run(String[] args)
    {
        String next;
        board = new Board();
        Dispaly dispaly =new Dispaly();
        dispaly.begin();
        dispaly.printout(board.show());
        do {
            next=null;
            do {
                if(!board.isValid(next))
                {
                    dispaly.errorInput();
                }
                next = dispaly.turnbegin(board.showTurn());
            }while (!board.isValid(next));

            board.move(next);
            dispaly.printout(board.show());
            board.nextTurn();
        } while ((!board.isWin())&&(!board.isDraw()));
        if(board.isWin())
        {
            dispaly.winEnding(board.showWinner());
        }else if(board.isDraw())
        {
            dispaly.drawEnding();
        }
    }
}

class Dispaly
{

    void begin()
    {
        System.out.println("Let us begin the game!");
    }
    void printout(Type[][] board)
    {
        int i,j;
        String row0,col0;
        row0 ="  A B C ";
        System.out.println(row0);
        for(i=0;i<3;i++)
        {
            col0=(i+1)+" ";
            System.out.print(col0);
            for (j=0;j<3;j++)
            {
                switch (board[i][j]){
                    case SPACE:System.out.print(". ");break;
                    case O:System.out.print("0 ");break;
                    case X:System.out.print("X ");break;
                }
            }
            System.out.println();
        }
    }
    String turnbegin(boolean playerO){
        String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String player=playerO?"Player O":"Player X";
        System.out.println("Now is "+player+"'s turn.");
        System.out.print(player+"'s move:");

        try{
            str = br.readLine();
        }catch (Exception e){ throw new Error(e); }

        while (!isValid(str))
        {
            System.out.println("It is a un valid place. Enter in a new one.");
            try{
                str=br.readLine();
            }catch (Exception e){ throw new Error(e); }
        }
        return str;
    }
    boolean isValid(String str)
    {
        if(str.length()!=2) return false;
        if((str.charAt(0) < 'A') || (str.charAt(0) > ('A' + 2))) return false;
        if((str.charAt(1) < '1') || (str.charAt(1) > ('1' + 2))) return false;
        return true;
    }
    void errorInput()
    {
        System.out.println("It is a un valid place. Enter in a new one.");
    }
    void winEnding(Type winner)
    {
        String player=winner==Type.O?"Player O":"Player X";
        System.out.println("Congratulations！"+player+" win the game!");
    }
    void drawEnding()
    {
        System.out.println("This is a draw!");
    }
}

class Board
{
    private boolean playerO=true;
    private Type[][] board = new Type[3][3];
    private Type winner;
    Board()
    {
        int i,j;
        for(i=0;i<3;i++)
        {
            for (j=0;j<3;j++)
            {
                board[i][j]=Type.SPACE;
            }
        }
    }
    Type[][] show()
    {
        return board;
    }
    Type showWinner()
    {
        return winner;
    }
    void nextTurn()
    {
        playerO=!playerO;
    }
    boolean showTurn()
    {
        return playerO;
    }
    boolean isValid(String next)
    {
        if (next==null)return true;
        return board[next.charAt(1) - '1'][next.charAt(0) - 'A'] == Type.SPACE;
    }
    boolean rowWin()
    {
        int i,j;
        for(i=0;i<3;i++) {
            for(j=0;j<3;j++) {
                if(board[i][j]!=Type.O) {
                    break;
                }
                if(j==2) {
                    winner=Type.O;
                    return true;
                }
            }
        }
        for(i=0;i<3;i++) {
            for(j=0;j<3;j++) {
                if(board[i][j]!=Type.X) {
                    break;
                }
                if(j==2) {
                    winner=Type.X;
                    return true;
                }
            }
        }
        return false;
    }
    boolean colWin()
    {
        int i,j;
        for(i=0;i<3;i++) {
            for(j=0;j<3;j++) {
                if(board[j][i]!=Type.O) {
                    break;
                }
                if(j==2) {
                    winner=Type.O;
                    return true;
                }
            }
        }
        for(i=0;i<3;i++) {
            for(j=0;j<3;j++) {
                if(board[j][i]!=Type.X) {
                    break;
                }
                if(j==2) {
                    winner=Type.X;
                    return true;
                }
            }
        }
        return false;
    }
    boolean slashwin()
    {
        int i;
        for(i=0;i<3;i++){
            if(board[i][i]!=Type.O) {
                break;
            }
            if(i==2) {
                winner=Type.O;
                return true;
            }
        }
        for(i=0;i<3;i++){
            if(board[i][i]!=Type.X) {
                break;
            }
            if(i==2) {
                winner=Type.X;
                return true;
            }
        }
        for(i=0;i<3;i++){
            if(board[2-i][i]!=Type.X) {
                break;
            }
            if(i==2) {
                winner=Type.X;
                return true;
            }
        }
        for(i=0;i<3;i++){
            if(board[2-i][i]!=Type.O) {
                break;
            }
            if(i==2) {
                winner=Type.O;
                return true;
            }
        }
        return false;
    }
    boolean isWin()
    {
        return rowWin()||colWin()||slashwin();

    }
    boolean isDraw()
    {
        int i,j;
        if(!isWin())
        {
            for (i=0;i<3;i++){
                for(j=0;j<3;j++)
                {
                    if(board[i][j]==Type.SPACE)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    void move(String next)
    {
        board[next.charAt(1) - '1'][next.charAt(0) - 'A']=playerO?Type.O:Type.X;
    }
}
