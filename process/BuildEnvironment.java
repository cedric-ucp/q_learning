package q_learning.process;

import q_learning.configuration.GameConfig;
import q_learning.data_base.elements.Agent;
import q_learning.data_base.elements.Danger;
import q_learning.data_base.elements.Goal;
import q_learning.data_base.environment.Environment;
import q_learning.data_base.environment.Grill;

import java.util.ArrayList;
import java.util.Random;

public class BuildEnvironment {
	private final Environment map;
	private final Agent agent;
	private Danger danger;
	private ArrayList<Danger> dangers = new ArrayList<>();
	private final Goal goal;

	public BuildEnvironment(int lineNumber, int columnNumber, int states) {
		this.map = new Environment(lineNumber, columnNumber, states);

		Grill agentPosition = map.getGrill(0, 0);
		this.agent = new Agent(agentPosition);

		int line = map.getLineNumber();
		int column = map.getColumnNumber();

		Grill goalPosition = map.getGrill(line - 1, column - 1);
		this.goal = new Goal(goalPosition);

		this.putDangerOnMap();

		this.fillReward();
	}

	private void fillReward() {
		for (Danger warning : this.dangers) {
			warning.getPosition().setReward(-10);
		}
		for (int i = 0; i < getMap().getLineNumber(); i++) {
			for (int j = 0; j < getMap().getColumnNumber(); j++) {
				if (!(this.goal.isGoal(this.getMap().getGrill(i, j)))
						&& !this.agent.isAgent(this.getMap().getGrill(i, j))) {
					this.getMap().getGrill(i, j).setReward(0);
				} else {
					for (Danger warning : this.dangers) {
						if (!warning.isDanger(this.map.getGrill(i, j))) {
							warning.getPosition().setReward(-10);
							;
						} else
							i++;
					}
				}
			}
		}
		this.goal.getPosition().setReward(100);
	}

	public Environment getMap() {
		return this.map;
	}

	public Agent getAgent() {
		return this.agent;
	}

	public Danger getDanger() {
		return this.danger;
	}

	public ArrayList<Danger> getDangers() {
		return this.dangers;
	}

	public Goal getGoal() {
		return this.goal;
	}

	private void putDangerOnMap() {
		for (int i = 0; i < GameConfig.dangerMax; i++) {
			Random random = new Random () ;
			int line = random.nextInt(map.getLineNumber());
			int column = random.nextInt(map.getColumnNumber());
			Grill position = this.map.getGrill (line, column);
			if (!this.agent.isAgent (position) && !this.goal.isGoal (position)) {
				this.danger = new Danger (position);
				this.dangers.add (this.danger);
			} else {
				i++;
			}
		}
	}
	public void resetDangersOnMap (){
		this.dangers = new ArrayList <> () ;
		this.putDangerOnMap () ;
		this.fillReward () ;
	}
}
