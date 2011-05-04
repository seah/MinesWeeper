package mw.controller;

<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import mw.model.BoardModel;
import mw.model.ButtonStatus;
import mw.view.BoardPanel;
import mw.view.FeldButton;

public class BoardController {

	private BoardModel boardModel;
	private BoardPanel boardPanel;

	/**
	 * Initiates the board variable with the appropriate difficulty setting
	 * 
	 * @param boardModel
	 * @param boardPanel
	 */
	public BoardController(BoardModel boardModel, BoardPanel boardPanel) {
		this.boardModel = boardModel;
		this.boardPanel = boardPanel;

		this.boardModel.newGame();
		this.boardPanel.build();
		this.boardPanel.repaint();

		// Fügt den Buttons auf dem BoardGrid den Listener hinzu
		this.boardPanel.addClickListener(new ToggleButtonListner());
	}

	/**
	 * Zeigt die aktuelle Zelle
	 * 
	 * @param e - <code>MouseEvent</code>
	 */
//	@SuppressWarnings("unused")
	private void showCell(MouseEvent e) {
		FeldButton feldButton = (FeldButton) e.getSource();
		int[] coords = new int[2];

		coords = feldButton.getCoords();

		if (feldButton.getButtonStatus() == ButtonStatus.DEFAULT || feldButton.getButtonStatus() == ButtonStatus.FLAG) {
			if (feldButton.isMine()) {
				
				feldButton.setButtonStatus(ButtonStatus.MINE_EXPLODED);
				feldButton.getMineExplodeIcon();
				
				boardModel.endGame();
				boardPanel.redraw();
				
				int timePlayed = boardModel.getTimePlayed();
				JOptionPane.showOptionDialog(
                		null, 
                		"Glückwunsch, Du hast Verloren! Und nur "+timePlayed+" Sekunden dafür benötigt. \n", 
                		"Du hast Verloren!", 
                		JOptionPane.CLOSED_OPTION, 
                		JOptionPane.INFORMATION_MESSAGE, null, null, null);
				
			} else {
				boardModel.openField(coords[1], coords[0]);
				boardPanel.redraw();
				
				if(boardModel.checkWin()){
					boardModel.endGame();
					boardPanel.redraw();
					
					int timePlayed = boardModel.getTimePlayed();
                	Object[] options = {"Yeah", "Nöö!"};

                    JOptionPane.showOptionDialog(
                    		null, 
                    		"Glückwunsch, Du hast Gewonnen! Du brauchtest "+timePlayed+" Sekunden um das Spiel zu beenden. \n " +
                				"Möchtest du deinen Highscore eintragen?", 
                    		"Gewonnen!", 
                    		JOptionPane.YES_NO_OPTION, 
                    		JOptionPane.INFORMATION_MESSAGE, 
                    		null, 
                    		options,
                    	    options[0]);
				}
			}
		}
		
	}

	/**
	 * MouseListener für die Felder (rechts klick/ links klick)
	 */
	private class ToggleButtonListner extends MouseAdapter {		
		// Wenn die Maus gedrück ist, dann
		public void mousePressed(MouseEvent e) {
			FeldButton feldButton = (FeldButton) e.getSource();

			if (e.getButton() == MouseEvent.BUTTON3) {
				// Entweder noch offen oder schon eine Flagge
				if (feldButton.getButtonStatus() == ButtonStatus.DEFAULT) {
					// Toggel der Flagge
					// Und des Status des Enum Wertes
					feldButton.toggleFlagButton();
					boardModel.setRestMinen(boardModel.getRestMinen() -1 );
				}else if(feldButton.isFlagged()){
					feldButton.toggleFlagButton();
					boardModel.setRestMinen(boardModel.getRestMinen() +1 );
				}

			} else if (e.getButton() == MouseEvent.BUTTON1) {
				if (feldButton.isFlagged()) {
					// Wenn das eine Flagge ist Entferne die Flagge
					feldButton.toggleFlagButton();	
					boardModel.setRestMinen(boardModel.getRestMinen() +1 );
				} else {
					// Sonst zeige die Zelle
					showCell(e);				
				}
			}
		}
	}

}
=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import mw.backstage.GameLogic;
import mw.model.BoardModel;
import mw.model.ButtonStatus;
import mw.model.Difficulty;
import mw.view.BoardPanel;
import mw.view.FeldButton;
import mw.view.GameFrame;
import mw.view.IBoardPanel;

public class BoardController extends Observable {

	private BoardModel boardModel;
	private IBoardPanel boardPanel;
	private GameLogic gameLogic;
	public String winMessage,loseMessage,winMessageTitle,loseMessageTitle;

	/**
	 * Initiates the board variable with the appropriate difficulty setting
	 * 
	 * @param gameFrame
	 * @param boardPanel
	 * 
	 * @param boardModel
	 * @param boardPanel
	 */
	public BoardController(GameFrame gameFrame, BoardPanel boardPanel) {
		this.boardModel = new BoardModel();
		this.boardPanel = boardPanel;
		this.gameLogic = new GameLogic(boardModel);

		boardPanel.setGameLogic(gameLogic);

		addObserver(gameFrame);

		// // Fügt den Buttons auf dem BoardGrid den Listener hinzu
		this.boardPanel.addClickListener(new ToggleButtonListner());

		gameLogic.setTimer(new Timer(1000, new TimerAction()));
	}

	/**
	 * Zähler
	 * 
	 * @author niwe
	 * 
	 */
	class TimerAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			gameLogic.setTimePlayed(gameLogic.getTimePlayed() + 1);

			setChanged();
			notifyObservers(gameLogic);
		}
	}

	public void init() {
		Difficulty diff = Difficulty.EINFACH;
		gameLogic.setDifficulty(diff);
		newGame();
	}

	/**
	 * MouseListener für die Felder (rechts klick/ links klick)
	 */
	private class ToggleButtonListner extends MouseAdapter {
		// Wenn die Maus gedrück ist, dann
		public void mousePressed(MouseEvent e) {
			FeldButton feldButton = (FeldButton) e.getSource();

			if (e.getButton() == MouseEvent.BUTTON3) {
				// Entweder noch offen oder schon eine Flagge
				if (feldButton.getButtonStatus() == ButtonStatus.DEFAULT) {
					// Toggel der Flagge
					// Und des Status des Enum Wertes
					gameLogic.setRestMinen(-1);
				} else if (feldButton.isFlagged()) {
					gameLogic.setRestMinen(+1);
				}
				feldButton.toggleFlagButton();

			} else if (e.getButton() == MouseEvent.BUTTON1) {
				if (feldButton.isFlagged()) {
					// Wenn das eine Flagge ist Entferne die Flagge
					feldButton.toggleFlagButton();
					gameLogic.setRestMinen(+1);
				} else {
					// Sonst zeige die Zelle
					showCell(e);
					boardPanel.redraw();
				}
			}
			setChanged();
			notifyObservers(gameLogic);
		}
	}
	
	public void setWinMessage(String winMessage) {
    this.winMessage = winMessage;
  }

  public void setLoseMessage(String loseMessage) {
    this.loseMessage = loseMessage;
  }

  public void setWinMessageTitle(String winMessageTitle) {
    this.winMessageTitle = winMessageTitle;
  }

  public void setLoseMessageTitle(String loseMessageTitle) {
    this.loseMessageTitle = loseMessageTitle;
  }

  public void showCell(MouseEvent e) {
		FeldButton feldButton = (FeldButton) e.getSource();
		int[] coords = new int[2];

		coords = feldButton.getCoords();
		int y = coords[0];
		int x = coords[1];
		// Sonst öffne Feld
		gameLogic.openField(y, x);

		if (gameLogic.checkLoose(y, x)) {
			gameLogic.endGame();
			feldButton.setButtonStatus(ButtonStatus.MINE_EXPLODED);			

			int timePlayed = boardModel.getTimePlayed();
			Object message = loseMessage + timePlayed + " Sekunden. \n";
			String title = loseMessageTitle;
			showMessage(message, title);
		}

		if (gameLogic.checkWin()) {
			gameLogic.endGame();
			int timePlayed = boardModel.getTimePlayed();
			Object message = winMessage + timePlayed + " Sekunden. \n";
			String title = winMessageTitle;
			showMessage(message, title);
		}

	}

	private void showMessage(Object message, String title) {
		JOptionPane.showOptionDialog(null, message, title, JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	}

	public void newGame() {
		gameLogic.newGame();
		// rebuild the gamefield with MineButtons
		boardPanel.setRebuild(true);
		boardPanel.setCols(getCols());
		boardPanel.setRows(getRows());

		boardPanel.build();

	}

	public void setDifficulty(Difficulty difficulty) {
		gameLogic.setDifficulty(difficulty);
		setChanged();
		notifyObservers(gameLogic);
	}

	public void setDifficultyUser(int rows, int cols, int anzahlMinen) {
		gameLogic.setDifficultyUser(rows, cols, anzahlMinen);
		setChanged();
		notifyObservers(gameLogic);
	}

	public int getRows() {
		return boardModel.getRows();
	}

	public int getCols() {
		return boardModel.getCols();
	}

	public int getAnzahlMinen() {
		return boardModel.getAnzahlMinen();
	}

}
>>>>>>> refs/heads/DevMitSpring
