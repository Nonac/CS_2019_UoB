import java.util.ArrayList;

class OXOController
{
    private OXOModel gameState;
    private int[] coordinator = new int[2];
    private static int currentPlayerByNumber=0;
    public OXOController(OXOModel model)
    {
        gameState=model;
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
        int i;
        if(command.length()!=2) throw new InvalidCellIdentifierException(command,command);

        if(command.charAt(0)<'a'||(command.charAt(0)>((int)'a'+gameState.getNumberOfRows()-1))) throw new InvalidCellIdentifierException(command,command.charAt(0));
        if(command.charAt(1)<'1'||(command.charAt(1)>((int)'1'+gameState.getNumberOfColumns()-1))) throw new InvalidCellIdentifierException(command,command.charAt(1));
        coordinator[0]=command.charAt(0)-'a';
        coordinator[1]=command.charAt(1)-'1';
        for(i=0;i<gameState.getNumberOfPlayers();i++)
        {
            if(gameState.getCellOwner(coordinator[0],coordinator[1])==gameState.getPlayerByNumber(i)) break;
        }
        if(i!=gameState.getNumberOfPlayers()) throw new CellDoesNotExistException(coordinator[0]+1,coordinator[1]+1);

        gameState.setCurrentPlayer(gameState.getPlayerByNumber(currentPlayerByNumber));
        gameState.setCellOwner(coordinator[0], coordinator[1], gameState.getCurrentPlayer());
        currentPlayerByNumber = (currentPlayerByNumber == (gameState.getNumberOfPlayers() - 1)) ? 0 : currentPlayerByNumber + 1;
        isFinish();
    }
}