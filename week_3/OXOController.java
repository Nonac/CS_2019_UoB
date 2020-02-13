import java.util.ArrayList;

class OXOController
{
    private OXOModel gameState;
    private int[] coordinator = new int[2];
    private static int currentPlayerByNumber=0;
    private Machine machine;
    public OXOController(OXOModel model)
    {
        gameState=model;
        this.machine=new Machine(gameState.getNumberOfRows(),gameState.getNumberOfColumns());
    }

    void isFinish()
    {
        int i,j,cuti,cutj;
        ArrayList<OXOPlayer> slash1=new ArrayList<OXOPlayer>();
        ArrayList<OXOPlayer> slash2=new ArrayList<OXOPlayer>();

        for(cuti=0;cuti<(gameState.getNumberOfRows()-gameState.getWinThreshold()+1);cuti++){
            for(cutj=0;cutj<(gameState.getNumberOfColumns()-gameState.getWinThreshold()+1);cutj++){
                for(i=cuti;i<gameState.getWinThreshold();i++){
                    ArrayList<OXOPlayer> row=gameState.getRow(i);
                    ArrayList<OXOPlayer> col=new ArrayList<OXOPlayer>();
                    for(j=cutj;j<gameState.getWinThreshold();j++){
                        col.add(gameState.getCellOwner(i,j));
                    }
                    if(checkForWin(row)||checkForWin(col)) return;
                    slash1.add(gameState.getCellOwner(i,i));
                    slash2.add(gameState.getCellOwner(gameState.getNumberOfRows()-1-i,i));
                }
                if(checkForWin(slash1)||checkForWin(slash2)) return;
                isDraw();
            }
        }
    }

    void isDraw()
    {
        for (int i=0;i<gameState.getNumberOfRows();i++){
            for(int j=0;j<gameState.getNumberOfColumns();j++) {
                if(gameState.getCellOwner(i,j)==null) return;
            }
        }
        gameState.setGameDrawn();
    }

    boolean checkForWin(ArrayList<OXOPlayer> array){
        for(int i=0;i<gameState.getWinThreshold();i++){
            if(array.get(i)==null||array.get(i).getPlayingLetter()!=array.get(0).getPlayingLetter()){
                return false;
            }
        }
        for(int i=0;i<gameState.getNumberOfPlayers();i++){
            if(array.get(0).getPlayingLetter()==gameState.getPlayerByNumber(i).getPlayingLetter()) gameState.setWinner(array.get(0));
        }
        return true;
    }

    public void handleIncomingCommand(String command) throws InvalidCellIdentifierException, CellAlreadyTakenException, CellDoesNotExistException
    {
        if(command.equals("machine")) {
            do {
                command = machine.easyPVE();
            }while (gameState.getCellOwner(command.charAt(0)-'a',command.charAt(1)-'1')!=null);
        }
        if(command.length()!=2) throw new InvalidCellIdentifierException(command,command);
        if(command.charAt(0)<'a'||(command.charAt(0)>((int)'z'))) throw new InvalidCellIdentifierException(command,command.charAt(0));
        if(command.charAt(1)<'1'||(command.charAt(1)>((int)'9'))) throw new InvalidCellIdentifierException(command,command.charAt(1));

        coordinator[0]=command.charAt(0)-'a';
        coordinator[1]=command.charAt(1)-'1';

        if(command.charAt(0)>((int)'a'+gameState.getNumberOfRows()-1)) throw new CellDoesNotExistException(coordinator[0]+1,coordinator[1]+1);
        if(command.charAt(1)>((int)'1'+gameState.getNumberOfColumns()-1)) throw new CellDoesNotExistException(coordinator[0]+1,coordinator[1]+1);
        if(gameState.getCellOwner(coordinator[0],coordinator[1])!=null) throw new CellAlreadyTakenException(coordinator[0]+1,coordinator[1]+1);

        run();
    }
    void run()
    {
        gameState.setCurrentPlayer(gameState.getPlayerByNumber(currentPlayerByNumber));
        gameState.setCellOwner(coordinator[0], coordinator[1], gameState.getCurrentPlayer());
        currentPlayerByNumber = (currentPlayerByNumber == (gameState.getNumberOfPlayers() - 1)) ? 0 : currentPlayerByNumber + 1;
        isFinish();
    }
}

class Machine
{
    private int row,col;
    public Machine(int row,int col)
    {
        this.row=row;
        this.col=col;
    }
    int random(int seed)
    {
        final double d = Math.random();
        return (int)(d*seed);
    }
    String easyPVE()
    {
        char a = (char)((int)'a'+random(row));
        char b = (char)((int)'1'+random(col));
        return ""+a+b;
    }
}

class test
{
    public static void main(String[] args) {
        boolean assertionsEnabled = false;
        assert(assertionsEnabled = true);
        if (assertionsEnabled) {
            testException();
            testcheckForWin();
            System.out.println("SUCCESS: All tests passed !!!");
        }
        else {
            System.out.println("You MUST run java with assertions enabled (-ea) to test your program !");
        }
    }
    private static void testException()
    {
        checkTestException("{1");
        checkTestException("a{");
        checkTestException("a11");
        checkTestException("z1");
        checkTestException("a9");
        checkTestException("a1");
    }

    private static void testcheckForWin()
    {
        OXOModel gameModel = new OXOModel(3,3,3,null);
        OXOController controller=new OXOController(gameModel);
        ArrayList<OXOPlayer> array=new ArrayList<OXOPlayer>();
        for(int i=0;i<gameModel.getWinThreshold();i++){
            array.add(new OXOPlayer('X'));
        }
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));

        assert !controller.checkForWin(array) || (gameModel.getWinner().getPlayingLetter() == 'X');
    }

/*set a new board with (0,0) a X on it.*/
    private static void checkTestException(String command)
    {
        OXOModel gameModel = new OXOModel(3,3,3,null);
        OXOController controller=new OXOController(gameModel);
        gameModel.setCellOwner(0,0,new OXOPlayer('X'));
        try{
            controller.handleIncomingCommand(command);
        }catch (InvalidCellIdentifierException icie){
            assert(icie.toString().contains("InvalidCellIdentifierException"));
        }catch (CellDoesNotExistException cdne){
            assert(cdne.toString().contains("CellDoesNotExistException"));
        }catch (CellAlreadyTakenException cate){
            assert(cate.toString().contains("CellAlreadyTakenException"));
        }
    }
}