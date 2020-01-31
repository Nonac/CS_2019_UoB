import java.io.*;
import java.util.ArrayList;
import java.util.List;

enum Type {SPACE,O,X}

public class OXO {
    private Board board;
    public static void main(String[] args) {
        OXO oxo=new OXO();
        oxo.run(args);
    }
    void run(String[] args)
    {

        board = new Board();
        Dispaly dispaly =new Dispaly();
        dispaly.begin();
        if(!dispaly.isPVP()) {
            board.changeToPVE();
            if(!dispaly.isFirst()) {
                board.changePlayerfirst();
            }
        }
        dispaly.printout(board.show());

        play(board,dispaly);

        if(board.isWin()) {
            dispaly.winEnding(board.showWinner());
        }else if(board.isDraw()) {
            dispaly.drawEnding();
        }
    }
    void play(Board board,Dispaly dispaly)
    {
        String next;
        Machine machine=new Machine();
        do {
            next=null;
            if((!board.showPlayMode())||((board.showPlayMode())&&
                    (((board.showPlayerfirst())&&(board.showTurn()))||
                            (!board.showPlayerfirst())&&(!board.showTurn())))) {
                do {
                    if (!board.isValid(next)) {
                        dispaly.errorInput();
                    }
                    next = dispaly.turnbegin(board.showTurn());
                } while (!board.isValid(next));
            }else{
                do {
                    if (!board.isValid(next)) {
                        dispaly.errorInput();
                    }
                    next = machine.easyPVE();
                } while (!board.isValid(next));
            }

            board.move(next);
            dispaly.printout(board.show());
            board.nextTurn();
        } while ((!board.isWin())&&(!board.isDraw()));
    }

}

class Dispaly
{

    void begin()
    {
        System.out.println("Let us begin the game!");
    }
    boolean isPVP()
    {
        String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("You want a PVP or PVE? (y for PVP, n for PVE): ");
            try {
                str = br.readLine();
            } catch (Exception e) {
                throw new Error(e);
            }
        }while (!isPVPInputValid(str));

        return str.charAt(0) == 'y';
    }

    boolean isFirst()
    {
        String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("You want first? (y for first, n for second): ");
            try {
                str = br.readLine();
            } catch (Exception e) {
                throw new Error(e);
            }
        }while (!isPVPInputValid(str));
        return str.charAt(0) == 'y';
    }

    boolean isPVPInputValid(String str)
    {
        if(str.length()!=1) return false;
        return (str.charAt(0) == 'y') || (str.charAt(0) == 'n');
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
    private boolean PVE = false;
    private boolean playerFirst = true;
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
    void changeToPVE() {
        PVE = true;
    }
    boolean showPlayMode() {
        return PVE;
    }
    void changePlayerfirst() {
        playerFirst =false;
    }
    boolean showPlayerfirst() {
        return playerFirst;
    }
}

class Machine
{
    int random()
    {
        final long l = System.currentTimeMillis();
        return (int)(l % 3);
    }
    String easyPVE()
    {
        char a = (char)((int)'A'+random());
        char b = (char)((int)'1'+random());
        return ""+a+b;
    }
}

class Node{
    State state;
    Node parent;
    List<Node> childArray;

    State getState() {
        return state;
    }
}
class Tree{
    Node root;
    Node getRoot(){
        return root;
    }
}
class State{
    BoardAI board;
    int playerNo;
    int visitCount;
    double winScore;

    BoardAI getBoard(){
        return board;
    }
    void setBoard(BoardAI board){
        this.board=board;
    }
    void setPlayerNo(int playerNo){
        this.playerNo=playerNo;
    }

    public List<State> getAllPossibleStates(){


    }

    public void randomPlay(){

    }
}

class MonteCarloTreeSearch{
    static final int WIN_SCORE=10;
    int level;
    int opppnent;

    BoardAI findNextMove(BoardAI board, int playerNo){

        final int end=1000;
        opppnent=3-playerNo;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        while (System.currentTimeMillis()<end){
            Node promisingNode =selectPromisingNode(rootNode);
            if(promisingNode.getState().getBoard().checkStatus()==BoardAI.IN_PROGRESS){
                expandNode(promisingNode);
            }
            Node nodeToExplore=promisingNode;
            if(promisingNode.getChildArray().size()>0){
                nodeToExplore=promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            backProgogation(nodeToExplore,playoutResult);
        }
        Node winnerNode = rootNode.getChildWithScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();
    }
    private Node selectPromisingNode()
}

class BoardAI{
    int[][] boardValues;
    static final int DEFAULT_BOARD_SIZE=3;
    static final int IN_PROGRESS=-1;
    static final int DREW =0;
    static final int P1=1;
    static final int P2=2;

    void performMove(int player,Position p){
        this.totalMoves++;
        boardValues[p.getX()][p.getY()] = player;
    }
    int checkStatus(){

    }
    List<Position> getEmptyPositions(){
        int size=this.boardValues.length;
        List<Position> emptyPositions = new ArrayList<>();
        for(int i = 0;i<size;i++) {
            for(int j =0;j<size;j++){
                if(boardValues[i][j]==0){
                    emptyPositions.add(new Position(i,j));
                }
            }
        }
        return emptyPositions;
    }
}