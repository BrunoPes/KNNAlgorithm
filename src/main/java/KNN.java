import java.util.*;

public class KNN {
	private DatasetCar data;
    private ArrayList<Car> nearestsK;

	public KNN(DatasetCar data) {
		this.data = data;
	}

	public String getNearestNeighbour(int[] vector, int k) {
		Car nearest = new Car();
        Car farther = new Car();
        this.nearestsK = new ArrayList<Car>();

		double distance = 0;
        double distance2 = 0;
        double majorDiff = -1;
		for(Car car : this.data.getDataset()) {
            distance = this.getDistance(vector, car.getAttributes());
			if(this.nearestsK.size() < k) {
                this.nearestsK.add(car);
			} else {
                farther = this.getFartherCar(vector);
                if(distance < this.getDistance(vector, farther.getAttributes())){
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
		return nearest.getCarClass().toString();
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


	public static void main(String[] args) {
		DatasetCar data = new DatasetCar();
		KNN knn = new KNN(data);
		knn.getNearestNeighbour(new int[]{2,2,2,2,2,0}, 5);
	}
}
