import java.io.*;

public class OXOTextOnlyView extends OXOView
{
    private OXOController controller;

    public static void main(String args[])
    {
        new OXOTextOnlyView();
    }

    public OXOTextOnlyView()
    {
        OXOModel gameModel = new OXOModel(3,3,3,this);
        gameModel.addPlayer(new OXOPlayer('X'));
        gameModel.addPlayer(new OXOPlayer('O'));
        controller = new OXOController(gameModel);
        while(true)
        {
            processNextCommand();
        }
    }

    public void drawBoard(OXOModel boardState)
    {
        System.out.println("");
        System.out.println("   1 2 3");
        System.out.println("  -------");
        for(int y=0; y<boardState.getNumberOfRows(); y++) {
            System.out.print((char)('a'+y) + " ");
            for(int x=0; x<boardState.getNumberOfColumns(); x++) {
                System.out.print("|");
                if(boardState.getCellOwner(y,x) == null) System.out.print(" ");
                else System.out.print(boardState.getCellOwner(y,x).getPlayingLetter());
            }
            System.out.print("|\n  -------\n");
        }
        System.out.print("\n");
        if(boardState.getWinner() != null) System.out.println("Player " + boardState.getWinner().getPlayingLetter() + " is the winner !");
        else if(boardState.isGameDrawn()) System.out.println("Stalemate - game is a draw !");
        else if (boardState.getCurrentPlayer() != null) System.out.print("Player " + boardState.getCurrentPlayer().getPlayingLetter() + "'s turn: ");
    }

    public void processNextCommand()
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = reader.readLine();
            controller.handleIncomingCommand(command);
        } catch(IOException ioe) {
            System.out.println(ioe);
        } catch(InvalidCellIdentifierException icie) {
            System.out.println(icie);
        } catch(CellAlreadyTakenException cnae) {
            System.out.println(cnae);
        } catch(CellDoesNotExistException cdnee) {
            System.out.println(cdnee);
        }
    }

}
