import java.io.*;
import java.util.*;

public class DatasetCar {
	private String path = "../../../../car.data.txt";
	private ArrayList<Car> cars = new ArrayList<Car>();

	public DatasetCar() {
		File currDir = new File(".");
		String dir = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length()-1);
		this.path = (dir.split("knnalgorithm"))[0]+"knnalgorithm/car.data.txt";
		System.out.println("Dataset path is: " + this.path);
		this.readDatasetFile();
	}

	public DatasetCar(String pathDataset) {
		if(pathDataset != null && pathDataset.length() > 0) {
			this.path = pathDataset;
		}
		System.out.println("Dataset path is: " + this.path);
		this.readDatasetFile();
	}

	public void readDatasetFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.path));
		    String line = br.readLine();

		    while (line != null) {
		        cars.add(new Car(line.split(",")));
		        line = br.readLine();
		    }
		    br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void removeCar(Car car) {
		this.cars.remove(car);
	}

	public Car getCarWithAttrs(int[] attrs) {
		for(Car car : this.cars) {
			if(Arrays.equals(car.getAttributes(), attrs)) {
				return car;
			}
		}
		return null;
	}

	public ArrayList<Car> getCars() {
		return this.cars;
	}

	public static void main(String[] args) {
		DatasetCar dataset = new DatasetCar();
	}
}


class Car {
	private int[] attributes;
	private CarClass carClass;

	public Car() {}
	public Car(String[] attrs) {
		for(int i=0; i < attrs.length; i++) {
			attrs[i] = attrs[i].toUpperCase().trim();
			if(i==2) attrs[i] = "D"+attrs[i];
			if(i==3) attrs[i] = "P"+attrs[i];
		}
		int buying = Buying.getByName(attrs[0]);
		int maint = Maint.getByName(attrs[1]);
		int doors = Doors.getByName(attrs[2]);
		int persons = Persons.getByName(attrs[3]);
		int luggage = Luggage.getByName(attrs[4]);
		int safety = Safety.getByName(attrs[5]);

		this.carClass = CarClass.get(CarClass.getByName(attrs[6]));
		this.attributes = new int[]{buying, maint, doors, persons, luggage, safety};
	}

	public int[] getAttributes() {
		return this.attributes;
	}

	public CarClass getCarClass() {
		return this.carClass;
	}
}


enum Buying {
	VHIGH(0), HIGH(1), MED(2), LOW(3);
	private final int value;
    private Buying(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Buying buying : Buying.values()) {
			if(buying.toString().equals(name)) {
				return buying.getValue();
			}
		}

		return -1;
	}
	public static Buying get(int index) {return Buying.values()[index];}
}

enum Maint {
	VHIGH(0), HIGH(1), MED(2), LOW(3);
	private final int value;
    private Maint(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Maint maint : Maint.values()) {
			if(maint.toString().equals(name)) return maint.getValue();
		}
		return -1;
	}
	public static Maint get(int index) {return Maint.values()[index];}
}

enum Doors {
	D2(0), D3(1), D4(2), D5MORE(3);
	private final int value;
    private Doors(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Doors doors : Doors.values()) {
			if(doors.toString().equals(name)) return doors.getValue();
		}
		return -1;
	}
	public static Doors get(int index) {return Doors.values()[index];}
}

enum Persons {
	P2(0), P4(1), PMORE(2);
	private final int value;
    private Persons(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Persons persons : Persons.values()) {
			if(persons.toString().equals(name)) return persons.getValue();
		}
		return -1;
	}
	public static Persons get(int index) {return Persons.values()[index];}
}

enum Luggage {
	SMALL(0), MED(1), BIG(2);
	private final int value;
    private Luggage(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Luggage luggage : Luggage.values()) {
			if(luggage.toString().equals(name)) return luggage.getValue();
		}
		return -1;
	}
	public static Luggage get(int index) {return Luggage.values()[index];}
}

enum Safety {
	LOW(0), MED(1), HIGH(2);
	private final int value;
    private Safety(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(Safety safety : Safety.values()) {
			if(safety.toString().equals(name)) return safety.getValue();
		}
		return -1;
	}
	public static Safety get(int index) {return Safety.values()[index];}
}

enum CarClass {
	UNACC(0), ACC(1), GOOD(2), VGOOD(3);
	private final int value;
    private CarClass(int value) { this.value = value; }
    public int getValue() { return value; }

	public static int getByName(String name) {
		for(CarClass carclass : CarClass.values()) {
			if(carclass.toString().equals(name)) return carclass.getValue();
		}
		return -1;
	}
	public static CarClass get(int index) {return CarClass.values()[index];}
}
