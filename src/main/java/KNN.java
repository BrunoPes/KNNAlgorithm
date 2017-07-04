import java.util.*;
import java.io.*;

public class KNN {
	private int k;
	private DatasetCar data;
    private ArrayList<Car> nearestsK;

	public KNN(DatasetCar data, int k) {
		this.k = k;
		this.data = data;
	}

	public String getNearestsNeighbour(int[] vector) {
		Car closest = new Car();
        Car farther = new Car();
        this.nearestsK = new ArrayList<Car>();

		double distance = 0;
        double distance2 = 0;
		for(Car car : this.data.getCars()) {
            distance = this.getDistance(vector, car.getAttributes());
			if(this.nearestsK.size() < this.k) {
                this.nearestsK.add(car);
			} else {
				closest = this.getClosestCar(vector);
                farther = this.getFartherCar(vector);
				distance2 = this.getDistance(vector, farther.getAttributes());
                if(distance < distance2) {
                    this.nearestsK.remove(farther);
                    this.nearestsK.add(car);
                }
            }
		}

        int dominant = 0;
        int[] countClass = new int[4];
        for(Car car : this.nearestsK) {
            countClass[car.getCarClass().getValue()]++;
        }
        for(int i=1; i<countClass.length; i++) {
            if(countClass[i] > countClass[dominant])
                dominant = i;
        }

		CarClass dominantClass = CarClass.get(dominant);
		this.logCars(vector);
		System.out.println("Classified as: " + dominantClass.toString());
		return dominantClass.toString();
	}

    private Car getFartherCar(int vector[]) {
        Car fartherCar = new Car();
        double distance = 0;
        double majorDist = 0;
        for(Car car : this.nearestsK) {
            distance = this.getDistance(vector, car.getAttributes());
            if(majorDist < distance) {
                majorDist = distance;
                fartherCar = car;
            }
        }

        return fartherCar;
    }

	private Car getClosestCar(int vector[]) {
        Car closestCar = new Car();
        double distance = 0;
        double minorDist = -1;
        for(Car car : this.nearestsK) {
            distance = this.getDistance(vector, car.getAttributes());
            if(minorDist > distance || minorDist == -1) {
                minorDist = distance;
                closestCar = car;
            }
        }

        return closestCar;
    }

	private double getDistance(int[] vetA, int[] vetB) {
		try {
			if(vetA.length == vetB.length) {
				double diff = 0;
				double sum = 0;
				for(int i=0; i<vetA.length; i++) {
					diff = Integer.valueOf(Math.abs(vetA[i]-vetB[i])).doubleValue();
					sum += Math.pow(diff, new Double("2"));
				}

				return Math.sqrt(sum);
			}
		} catch(NumberFormatException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private void logCars(int[] vector) {
		System.out.println("------------- LOGGER -------------");
		for(Car car : this.nearestsK) {
			int[] attr = car.getAttributes();
			System.out.print("Car: {"+attr[0]);
			for(int i=1; i<6; i++) System.out.print(","+attr[i]);
			System.out.print("} Dist: " + String.format(java.util.Locale.US, "%.3f", this.getDistance(attr, vector)));
			System.out.println(" C: " + car.getCarClass().toString());
		}
	}

	public static void main(String[] args) {
		File currDir = new File(".");
		String dir = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length()-1);
		String file = (dir.split("knnalgorithm"))[0]+"knnalgorithm/car.data.txt";
		System.out.println(file);

		KNN knn = new KNN(new DatasetCar(file), 5);
		System.out.println(knn.getNearestsNeighbour(new int[]{2,1,1,0,2,1}));
	}
}
