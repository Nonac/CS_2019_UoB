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
        ArrayList<OXOPlayer> slash1;
        ArrayList<OXOPlayer> slash2;

        for(cuti=0;cuti<(gameState.getNumberOfRows()-gameState.getWinThreshold()+1);cuti++){
            for(cutj=0;cutj<(gameState.getNumberOfColumns()-gameState.getWinThreshold()+1);cutj++){
                slash1=new ArrayList<OXOPlayer>();
                slash2=new ArrayList<OXOPlayer>();
                for(i=cuti;i<gameState.getWinThreshold()+cuti;i++){
                    ArrayList<OXOPlayer> row=gameState.getRow(i);
                    ArrayList<OXOPlayer> col=new ArrayList<OXOPlayer>();
                    for(j=cutj;j<gameState.getWinThreshold()+cutj;j++){
                        col.add(gameState.getCellOwner(j,i));
                    }
                    if(checkArrayForWin(row)||checkArrayForWin(col)) return;
                    slash1.add(gameState.getCellOwner(i,i));
                    slash2.add(gameState.getCellOwner(gameState.getNumberOfRows()-1-i,i));
                }
                if(checkArrayForWin(slash1)||checkArrayForWin(slash2)) return;
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

    boolean checkArrayForWin(ArrayList<OXOPlayer> array){
       for(int i=0;i<(array.size()-gameState.getWinThreshold()+1);i++){
           OXOPlayer target=null;
           if(array.get(i)!=null) target=array.get(i);
           int j;
           for(j=i;j<gameState.getWinThreshold()+i;j++){
               if(array.get(j)==null||target==null||array.get(j).getPlayingLetter()!=target.getPlayingLetter()) break;
           }
           if(j==gameState.getWinThreshold()+i){
               gameState.setWinner(array.get(j-1));
               return true;
           }
       }
        return false;
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
            testCheckArrayForWin();
            testIsDraw();
            testIsFinishForWin();
            testNonFixedSizeBoardForDraw();
            testNonFixedSizeBoardForWin();
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

    private static void testCheckArrayForWin()
    {
        OXOModel gameModel = new OXOModel(3,3,3,null);
        OXOController controller=new OXOController(gameModel);
        ArrayList<OXOPlayer> array1=new ArrayList<OXOPlayer>();
        ArrayList<OXOPlayer> array2=new ArrayList<OXOPlayer>();
        for(int i=0;i<gameModel.getWinThreshold();i++){
            array1.add(new OXOPlayer('X'));
        }
        for(int i=0;i<gameModel.getWinThreshold()-1;i++){
            array2.add(new OXOPlayer('X'));
        }
        array2.add(new OXOPlayer('O'));
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));

        assert controller.checkArrayForWin(array2) || (gameModel.getWinner() == null);
        assert !controller.checkArrayForWin(array1) || (gameModel.getWinner().getPlayingLetter() == 'X');

    }

    private static void testIsDraw()
    {
        OXOModel gameModel = new OXOModel(3,3,3,null);
        OXOController controller=new OXOController(gameModel);
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));

        try {
            controller.handleIncomingCommand("a1");
            controller.handleIncomingCommand("a2");
            controller.handleIncomingCommand("a3");
            controller.handleIncomingCommand("b2");
            controller.handleIncomingCommand("b1");
            controller.handleIncomingCommand("b3");
            controller.handleIncomingCommand("c2");
            controller.handleIncomingCommand("c1");
            assert (gameModel.isGameDrawn()==false);
            controller.handleIncomingCommand("c3");
            assert (gameModel.isGameDrawn()==true);
        }catch(InvalidCellIdentifierException icie) {
            System.out.println(icie);
        } catch(CellAlreadyTakenException cnae) {
            System.out.println(cnae);
        } catch(CellDoesNotExistException cdnee) {
            System.out.println(cdnee);
        }
    }

    private static void testIsFinishForWin()
    {
        OXOModel gameModel = new OXOModel(3,3,3,null);
        OXOController controller=new OXOController(gameModel);
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));
        try {
            controller.handleIncomingCommand("a1");
            controller.handleIncomingCommand("b1");
            controller.handleIncomingCommand("a2");
            controller.handleIncomingCommand("b2");
            assert (gameModel.getWinner()==null);
            controller.handleIncomingCommand("a3");
            assert (gameModel.getWinner().getPlayingLetter()=='O');
            assert (gameModel.isGameDrawn()==false);
        }catch(InvalidCellIdentifierException icie) {
            System.out.println(icie);
        } catch(CellAlreadyTakenException cnae) {
            System.out.println(cnae);
        } catch(CellDoesNotExistException cdnee) {
            System.out.println(cdnee);
        }
    }
    private static void testNonFixedSizeBoardForDraw()
    {
        OXOModel gameModel = new OXOModel(4,4,3,null);
        OXOController controller=new OXOController(gameModel);
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));
        try {
            for(int i=0;i<2;i++) {
                for(int j=0;j<gameModel.getNumberOfColumns();j++) {
                    controller.handleIncomingCommand(""+(char)('a'+i)+(char)('1'+j));
                }
            }
            for(int i=2;i<4;i++){
                for(int j=gameModel.getNumberOfColumns()-1;j>=0;j--) {
                    controller.handleIncomingCommand(""+(char)('a'+i)+(char)('1'+j));
                }
            }

            assert (gameModel.getWinner()==null);
            assert (gameModel.isGameDrawn()==true);

        }catch(InvalidCellIdentifierException icie) {
            System.out.println(icie);
        } catch(CellAlreadyTakenException cnae) {
            System.out.println(cnae);
        } catch(CellDoesNotExistException cdnee) {
            System.out.println(cdnee);
        }
    }
    private static void testNonFixedSizeBoardForWin()
    {
        OXOModel gameModel = new OXOModel(4,4,3,null);
        OXOController controller=new OXOController(gameModel);
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));
        try {
            for(int i=0;i<2;i++) {
                for(int j=0;j<gameModel.getNumberOfColumns();j++) {
                    controller.handleIncomingCommand(""+(char)('a'+i)+(char)('1'+j));
                }
            }
            assert (gameModel.getWinner()==null);
            controller.handleIncomingCommand("c1");
            assert (gameModel.getWinner().getPlayingLetter()=='X');

        }catch(InvalidCellIdentifierException icie) {
            System.out.println(icie);
        } catch(CellAlreadyTakenException cnae) {
            System.out.println(cnae);
        } catch(CellDoesNotExistException cdnee) {
            System.out.println(cdnee);
        }
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