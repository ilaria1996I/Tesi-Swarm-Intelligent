package ACOAndCommessoViaggiatore;

public class ACOAndCommessoViaggiatore {
	private double c = 1.0;
	private double alpha = 1;
	private double beta = 5;
	private double evaporation = 0.5;
	private double Q = 500;
	private double antFactor = 0.8;
	private double randomFactor = 0.01;
	
	
	public void visitCity(int currentIndex, int city) {
	    trail[currentIndex + 1] = city;
	    visited[city] = true;
	}

	public boolean visited(int i) {
	    return visited[i];
	}

	public double trailLength(double graph[][]) {
	    double length = graph[trail[trailSize - 1]][trail[0]];
	    for (int i = 0; i < trailSize - 1; i++) {
	        length += graph[trail[i]][trail[i + 1]];
	    }
	    return length;
	}
	
	
	graph = generateRandomMatrix(noOfCities);
	numberOfCities = graph.length;
	numberOfAnts = (int) (numberOfCities * antFactor);

	trails = new double[numberOfCities][numberOfCities];
	probabilities = new double[numberOfCities];
	ants = new Ant[numberOfAnts];
	IntStream.range(0, numberOfAnts).forEach(i -> ants.add(new Ant(numberOfCities)));
	
	
	public void setupAnts() {
	    IntStream.range(0, numberOfAnts)
	      .forEach(i -> {
	          ants.forEach(ant -> {
	              ant.clear();
	              ant.visitCity(-1, random.nextInt(numberOfCities));
	          });
	      });
	    currentIndex = 0;
	}
	
	public void moveAnts() {
	    IntStream.range(currentIndex, numberOfCities - 1).forEach(i -> {
	        ants.forEach(ant -> {
	            ant.visitCity(currentIndex, selectNextCity(ant));
	        });
	        currentIndex++;
	    });
	}
	
	int t = random.nextInt(numberOfCities - currentIndex);
	if (random.nextDouble() < randomFactor) {
	    OptionalInt cityIndex = IntStream.range(0, numberOfCities)
	      .filter(i -> i == t && !ant.visited(i))
	      .findFirst();
	    if (cityIndex.isPresent()) {
	        return cityIndex.getAsInt();
	    }
	}
	
	public void calculateProbabilities(Ant ant) {
	    int i = ant.trail[currentIndex];
	    double pheromone = 0.0;
	    for (int l = 0; l < numberOfCities; l++) {
	        if (!ant.visited(l)){
	            pheromone
	              += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
	        }
	    }
	    for (int j = 0; j < numberOfCities; j++) {
	        if (ant.visited(j)) {
	            probabilities[j] = 0.0;
	        } else {
	            double numerator
	              = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
	            probabilities[j] = numerator / pheromone;
	        }
	    }
	}
	
	double r = random.nextDouble();
	double total = 0;
	for (int i = 0; i < numberOfCities; i++) {
	    total += probabilities[i];
	    if (total >= r) {
	        return i;
	    }
	}
	
	public void updateTrails() {
	    for (int i = 0; i < numberOfCities; i++) {
	        for (int j = 0; j < numberOfCities; j++) {
	            trails[i][j] *= evaporation;
	        }
	    }
	    for (Ant a : ants) {
	        double contribution = Q / a.trailLength(graph);
	        for (int i = 0; i < numberOfCities - 1; i++) {
	            trails[a.trail[i]][a.trail[i + 1]] += contribution;
	        }
	        trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
	    }
	}
	
	private void updateBest() {
	    if (bestTourOrder == null) {
	        bestTourOrder = ants[0].trail;
	        bestTourLength = ants[0].trailLength(graph);
	    }
	    for (Ant a : ants) {
	        if (a.trailLength(graph) < bestTourLength) {
	            bestTourLength = a.trailLength(graph);
	            bestTourOrder = a.trail.clone();
	        }
	    }
	}
}
}
	
