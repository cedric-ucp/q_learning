package q_learning.configuration;

public class GameConfig {
	/**
	 * Hauteur et largeur de la fenetre codages en dur.
	 */
	public static final int WINDOW_WIDTH = 600; // On definit la largeur de la fenetre
	public static final int WINDOW_HEIGHT = 600;// On definit la hauteur de notre fenetre
	/**
	 * taille d'un block codage en dur.
	 */
	public static final int GRILL_SIZE = 100;

	public static final int LINE_COUNT = WINDOW_HEIGHT / 100; // hauteur de la fenetre divisee la taille d'un
																		// block.
	public static final int COLUMN_COUNT = WINDOW_WIDTH / 100; // largeur de la fenetre divisee par la taille
																		// d'un block.

	public static final int GAME_SPEED = 1000;
	public static int BUILDING_SPEED = 0 ;

	public static final int reward = 100;
	public static final int danger = -10;
	public static final int dangerMax = 12;
	public static final int states = LINE_COUNT * COLUMN_COUNT + 1;
	public static final double learningRate = 0.3;
	public static final double discountFactor = 0.9;
	public static final double explorationGreedy = 0.7;
	public static final int roundCount = 500;
}
