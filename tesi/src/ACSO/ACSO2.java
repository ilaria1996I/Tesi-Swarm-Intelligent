package ACOAndCommessoViaggiatore;


public class Swarm {
	private Cat[] cats;
	private long[] bestPosition;
	private double bestFitness = Double.NEGATIVE_INFINITY;
				    
	public Swarm(int numCat) {
		cats = new Cat[numCats];
		for (int i = 0; i < numCats; i++) {
		long[] initialCatPosition = { 
		random.nextInt(Constants.Cat_UPPER_BOUND),
		random.nextInt(Constants.Cat_UPPER_BOUND) 
		};
				      
		long[] initialCatspeed = { 
		random.nextInt(Constants.Cat_UPPER_BOUND),
		random.nextInt(Constants.Cat_UPPER_BOUND) 
		};
		Cats[i] = new Cat(
		initialCatsPosition, initialCatSpeed);
			}
		}
				    
	public interface FitnessFunction {
		public double getFitness(long[] CatPosition);
		}
					
	public class LolFitnessFunction implements FitnessFunction {
		@Override
		public double getFitness(long[] particlePosition) {
			long health = particlePosition[0];
			long armor = particlePosition[1];
					 
			if (health < 0 && armor < 0) {
				return -(health * armor);
				} else if (health < 0) {
					return health;
				} else if (armor < 0) {
					return armor;
					}
					 
			double cost = (health * 2.5) + (armor * 18);
			
			if (cost > 3600) {
				return 3600 - cost;
				} else {
				
					long fitness = (health * (100 + armor)) / 100;
					return fitness;
					}
			}
		}	
	}


		


}
