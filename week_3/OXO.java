import java.io.*;
import java.util.ArrayList;
import java.util.List;

enum Type {SPACE,O,X}
enum Status {PROGRESS,DRAW,XWIN,OWIN}

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

        if(board.isWin()!=Status.DRAW) {
            dispaly.winEnding(board.isWin());
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
                    next = machine.easyPVE();
                } while (!board.isValid(next));
            }

            board.move(next);
            dispaly.printout(board.show());
            board.nextTurn();
        } while (board.isWin()==Status.PROGRESS);
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
    void winEnding(Status winner)
    {
        String player=winner==Status.OWIN?"Player O":"Player X";
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

    Status isWin()
    {
        int i,j;
        Type[] slash1=new Type[3];
        Type[] slash2=new Type[3];
        for(i=0;i<3;i++) {
            Type[] row = board[i];
            Type[] col = new Type[3];
            for (j = 0; j < 3; j++) {
                col[j] = board[j][i];
            }

            Status checkRowForWin = checkForWin(row);
            if(checkRowForWin!=Status.PROGRESS){
                return checkRowForWin;
            }
            Status checkColForWin = checkForWin(col);
            if(checkColForWin!=Status.PROGRESS){
                return checkColForWin;
            }
            slash1[i]=board[i][i];
            slash2[i]=board[2-i][i];
        }
        Status checkSlash1ForWin = checkForWin(slash1);
        if(checkSlash1ForWin!=Status.PROGRESS){
            return checkSlash1ForWin;
        }
        Status checkSlash2ForWin = checkForWin(slash2);
        if(checkSlash2ForWin!=Status.PROGRESS){
            return checkSlash2ForWin;
        }
        return isDraw()?Status.DRAW:Status.PROGRESS;

    }

    Status checkForWin(Type[] array)
    {
        int i;
        for(i=0;i<3;i++)
        {
            if((array[i]==Type.SPACE)||(array[i]!=array[0])) {
                return Status.PROGRESS;
            }
        }
        return array[0]==Type.O?Status.OWIN:Status.XWIN;
    }
    boolean isDraw()
    {
        int i,j;
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
        final double d = Math.random();
        return (int)(d * 3);
    }
    String easyPVE()
    {
        char a = (char)((int)'A'+random());
        char b = (char)((int)'1'+random());
        return ""+a+b;
    }
}
/*
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
public class State {
    private BoardAI board;
    private int playerNo;
    private int visitCount;
    private double winScore;

    public State() {
        board = new BoardAI();
    }

    public State(State state) {
        this.board = new Board(state.getBoard());
        this.playerNo = state.getPlayerNo();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public State(BoardAI board) {
        this.board = new BoardAI(board);
    }

    Board getBoard() {
        return board;
    }

    void setBoard(BoardAI board) {
        this.board = board;
    }

    int getPlayerNo() {
        return playerNo;
    }

    void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    int getOpponent() {
        return 3 - playerNo;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    double getWinScore() {
        return winScore;
    }

    void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        List<Position> availablePositions = this.board.getEmptyPositions();
        availablePositions.forEach(p -> {
            State newState = new State(this.board);
            newState.setPlayerNo(3 - this.playerNo);
            newState.getBoard().performMove(newState.getPlayerNo(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    void incrementVisit() {
        this.visitCount++;
    }

    void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    void randomPlay() {
        List<Position> availablePositions = this.board.getEmptyPositions();
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * totalPossibilities);
        this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
    }

    void togglePlayer() {
        this.playerNo = 3 - this.playerNo;
    }
}

class MonteCarloTreeSearch{
    static final int WIN_SCORE=10;
    int level;
    int opppnent;

    private int getMillisForCurrentLevel() {
        return 2 * (this.level - 1) + 1;
    }

    BoardAI findNextMove(BoardAI board, int playerNo){

        long start = System.currentTimeMillis();
        long end = start + 60 * getMillisForCurrentLevel();
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
    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayerNo(node.getState().getOpponent());
            node.getChildArray().add(newNode);
        });
    }

    private void backPropogation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayerNo() == playerNo)
                tempNode.getState().addScore(WIN_SCORE);
            tempNode = tempNode.getParent();
        }
    }
    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().checkStatus();

        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().checkStatus();
        }

        return boardStatus;
    }

}
*/