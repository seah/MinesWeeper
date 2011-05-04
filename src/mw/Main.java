package mw;

<<<<<<< HEAD
import mw.controller.BoardController;
import mw.controller.GameController;
import mw.model.BoardModel;
import mw.model.Difficulty;
import mw.view.BoardPanel;
import mw.view.GameFrame;

public class Main {

	/**
	 * 
	 */
	public static void main(String[] args) {
		// Setzt die Schwierigkeitsstufe
		Difficulty diff = Difficulty.EINFACH;
		// setzt die Spiel einstellungen
		BoardModel boardModel = new BoardModel(diff);
		BoardPanel boardPanel = new BoardPanel(boardModel);
		GameFrame gameFrame = new GameFrame(boardPanel, boardModel);

		boardModel.addObserver(gameFrame);
		
		// Erstellt das SpielFeld
		new BoardController(boardModel, boardPanel);
		new GameController(boardModel, gameFrame, boardPanel);

		gameFrame.pack();
		gameFrame.repaint();
		gameFrame.validate();
=======
import mw.controller.GameController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * 
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		GameController gameController = (GameController) context.getBean("gameController");
		
//		Das muss in die Main noch irgenwie mit rein ;) mit rein
		
//		BoardModel boardModel = new BoardModel();
//		BoardPanel boardPanel = new BoardPanel();
//		GameFrame gameFrame = new GameFrame(boardPanel, boardModel);
//
//		// Erstellt das SpielFeld
//
//		new GameController(gameFrame, boardPanel);
>>>>>>> refs/heads/DevMitSpring

	}

}
