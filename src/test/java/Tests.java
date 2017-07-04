
import org.junit.rules.ErrorCollector;
import org.junit.*;
import org.junit.rules.*;
import org.hamcrest.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class Tests {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

	public void testEntry(int[] vector, String checkClass) {
        DatasetCar data = new DatasetCar();
		KNN nn = new KNN(data, 9);
		String carClass = "";
        try {
            data.removeCar(data.getCarWithAttrs(vector));
            carClass = nn.getNearestsNeighbour(vector);
            assertThat(carClass, equalTo(checkClass));
        } catch (Throwable t) {
            collector.addError(t);
        }
	}

    @Test
    public void testOne() {
        testEntry(new int[]{0,0,0,1,1,1},"UNACC");
    }

    @Test
    public void testTwo() {
        testEntry(new int[]{0,0,1,1,1,0},"UNACC");

    }

    @Test
    public void testThree() {
        testEntry(new int[]{0,0,3,2,0,2},"UNACC");

    }

    @Test
    public void testFour() {
        testEntry(new int[]{3,3,3,1,2,2},"VGOOD");

    }

    @Test
    public void testFive() {
        testEntry(new int[]{2,0,0,1,1,2},"ACC");

    }

    @Test
    public void testSix() {
        testEntry(new int[]{2,2,0,1,2,2},"VGOOD");

    }

    @Test
    public void testSeven() {
        testEntry(new int[]{2,3,0,1,0,2},"GOOD");

    }
}
