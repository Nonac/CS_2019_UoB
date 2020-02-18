/************************************************************************************************************************
 * Code by ff19085 Yinan Yang.
 * OXO controller can fit any size board, according to the oxoview set, the max size of board is 26*9.
 * And it can fit any number of players.
 * The input command can only accept one lower case letter and one number, ie. a1 any other letters will be illegal.
 * Geometry algorithm used to determine win or draw, in the way the code is not redundant.
 ***********************************************************************************************************************/

import java.util.ArrayList;

class OXOController
{
    private OXOModel gameState;
    private int[] coordinator = new int[2];
    private static int currentPlayerByNumber=0;
    public OXOController(OXOModel model)
    {
        gameState=model;
        gameState.setCurrentPlayer(gameState.getPlayerByNumber(currentPlayerByNumber));
        currentPlayerByNumber = (currentPlayerByNumber == (gameState.getNumberOfPlayers() - 1)) ? 0 :
                currentPlayerByNumber + 1;
    }

    void isFinish()
    {
        int i,j,segmentationOriginI,segmentationOriginJ;
        ArrayList<OXOPlayer> slash1;
        ArrayList<OXOPlayer> slash2;

        for(segmentationOriginI=0;segmentationOriginI<(gameState.getNumberOfRows()-gameState.getWinThreshold()+1);
            segmentationOriginI++){
            for(segmentationOriginJ=0;segmentationOriginJ<(gameState.getNumberOfColumns()-gameState.getWinThreshold()+1);
                segmentationOriginJ++){
                slash1=new ArrayList<OXOPlayer>();
                slash2=new ArrayList<OXOPlayer>();
                for(i=segmentationOriginI;i<gameState.getWinThreshold()+segmentationOriginI;i++){
                    ArrayList<OXOPlayer> row=gameState.getRow(i);
                    ArrayList<OXOPlayer> col=new ArrayList<OXOPlayer>();
                    for(j=segmentationOriginJ;j<gameState.getWinThreshold()+segmentationOriginJ;j++){
                        col.add(gameState.getCellOwner(j,i));
                    }
                    if(checkArrayForWin(row)||checkArrayForWin(col)) return;
                    slash1.add(gameState.getCellOwner(i,i-segmentationOriginI+segmentationOriginJ));
                    slash2.add(gameState.getCellOwner(gameState.getNumberOfRows()-gameState.getWinThreshold()+
                            segmentationOriginI-i+segmentationOriginI, i-segmentationOriginI+segmentationOriginJ));
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
               if(array.get(j)==null||target==null||array.get(j).getPlayingLetter()!= target.getPlayingLetter())
                   break;
           }
           if(j==gameState.getWinThreshold()+i){
               gameState.setWinner(array.get(j-1));
               return true;
           }
       }
        return false;
    }

    public void handleIncomingCommand(String command) throws InvalidCellIdentifierException, CellAlreadyTakenException,
            CellDoesNotExistException
    {
        if(command.length()!=2) throw new InvalidCellIdentifierException(command,command);
        if(command.charAt(0)<'a'||(command.charAt(0)>((int)'z')))
            throw new InvalidCellIdentifierException(command,command.charAt(0));
        if(command.charAt(1)<'1'||(command.charAt(1)>((int)'9')))
            throw new InvalidCellIdentifierException(command,command.charAt(1));

        coordinator[0]=command.charAt(0)-'a';
        coordinator[1]=command.charAt(1)-'1';

        if(command.charAt(0)>((int)'a'+gameState.getNumberOfRows()-1))
            throw new CellDoesNotExistException(coordinator[0]+1,coordinator[1]+1);
        if(command.charAt(1)>((int)'1'+gameState.getNumberOfColumns()-1))
            throw new CellDoesNotExistException(coordinator[0]+1,coordinator[1]+1);
        if(gameState.getCellOwner(coordinator[0],coordinator[1])!=null)
            throw new CellAlreadyTakenException(coordinator[0]+1,coordinator[1]+1);

        if((gameState.getWinner()==null)&&(!gameState.isGameDrawn())) OXOMoves();
    }
    void OXOMoves()
    {
        gameState.setCellOwner(coordinator[0], coordinator[1], gameState.getCurrentPlayer());
        isFinish();
        gameState.setCurrentPlayer(gameState.getPlayerByNumber(currentPlayerByNumber));
        currentPlayerByNumber = (currentPlayerByNumber == (gameState.getNumberOfPlayers() - 1)) ?
                0 : currentPlayerByNumber + 1;
    }
}