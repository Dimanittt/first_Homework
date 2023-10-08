import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArrayListTest {

    @Test
    void toStringTest(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        assertTrue("[]".equals(arrayList.toString()));
        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
        }
        assertTrue("[0, 1, 2, 3, 4]".equals(arrayList.toString()));
    }

    @Test
    void toArrayTest() {
        Integer[] verify = {1,2,3,4,5};
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            arrayList.add(i);
        }
        Object[] given = arrayList.toArray();
        assertThat(given).isEqualTo(verify);
    }

    @ParameterizedTest(name = "{arguments} test")
    @MethodSource("ArrayListTest#argumentsForSortMethodTest")
    @DisplayName("ArrayList.sort(Comparator<T>)")
    void sortTest(ArrayList given, ArrayList verify, Comparator comparator) {
        given.sort(comparator);
        assertThat(given).isEqualTo(verify);
    }

    Stream<Arguments> argumentsForSortMethodTest() {
        Comparator<String> stringComparator = String::compareTo;
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<String> varifyStringArrayList = new ArrayList<>();
        Comparator<Integer> integerComparator = Integer::compareTo;
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Integer> varifyIntegerArrayList = new ArrayList<>();
        Comparator<Double> doubleComparator = Double::compareTo;
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        ArrayList<Double> varifyDoubleArrayList = new ArrayList<>();
        for (int i = 9, j = 0; i >= 0; i--, j++) {
            integerArrayList.add(i);
            varifyIntegerArrayList.add(j);
            doubleArrayList.add(i + 0.00001d);
            varifyDoubleArrayList.add(j + 0.00001d);
            stringArrayList.add(String.valueOf(i));
            varifyStringArrayList.add(String.valueOf(j));
        }
        return Stream.of(
                Arguments.of(integerArrayList, varifyIntegerArrayList, integerComparator),
                Arguments.of(doubleArrayList, varifyDoubleArrayList, doubleComparator),
                Arguments.of(stringArrayList, varifyStringArrayList, stringComparator)
        );
    }

    @Test
    void addMethodsTest() {
        ArrayList<Integer> arrayList = new ArrayList();
        assertAll(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        arrayList.add(i);
                    }
                    assertThat(arrayList.length()).isEqualTo(20);
                    assertThat(arrayList.get(15)).isEqualTo(15);
                    assertThat(arrayList.get(0)).isEqualTo(0);
                    arrayList.clear();
                },
                () -> {
                    for (int i = 0; i < 20; i++) {
                        arrayList.add(i);
                    }
                    arrayList.add(0, 100);
                    arrayList.add(10, 200);
                    for (int i = 0; i < 100; i++) {
                        arrayList.add(i);
                    }
                    arrayList.add(arrayList.length(), 300);
                    assertThat(arrayList.get(0)).isEqualTo(100);
                    assertThat(arrayList.get(10)).isEqualTo(200);
                    assertThat(arrayList.get(arrayList.length() - 1)).isEqualTo(300);
                    arrayList.clear();
                },
                () -> {
                    ArrayList<Integer> secondList = new ArrayList();
                    for (int i = 0; i < 5; i++) {
                        arrayList.add(i);
                        secondList.add(i);
                    }
                    arrayList.addAll(secondList);
                    assertThat(arrayList.length()).isEqualTo(10);
                    assertThat(arrayList.toString()).isEqualTo("[0, 1, 2, 3, 4, 0, 1, 2, 3, 4]");
                }
        );
    }

    @Test
    void constructorsTest() {
        Integer[] integerArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> arrayConstructorArrayList = new ArrayList<>(integerArray);
        ArrayList<Integer> arrayListConstructorArrayList = new ArrayList<>(arrayConstructorArrayList);
        ArrayList<Integer> noArgsConstructorArrayList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            noArgsConstructorArrayList.add(i);
        }
        int initialCapacity = 5;
        ArrayList<Integer> initcapacityConstructorArrayList = new ArrayList<>(initialCapacity);
        for (int i = 0; i < 5; i++) {
            initcapacityConstructorArrayList.add(i);
        }
        assertThat(arrayConstructorArrayList.toArray()).isEqualTo(integerArray);
        assertThat(arrayListConstructorArrayList.toArray()).isEqualTo(integerArray);
        assertThat(noArgsConstructorArrayList.toArray()).isEqualTo(integerArray);
        assertFalse(initcapacityConstructorArrayList.ensureCapacity());
        initcapacityConstructorArrayList.add(5);
        assertTrue(initcapacityConstructorArrayList.ensureCapacity());
        assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(0));
    }

    @Test
    void setTest() {
        int initialCapacity = 5;
        ArrayList<Integer> arrayList = new ArrayList<>(initialCapacity);
        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
            arrayList.set(i, 0);
            assertThat(arrayList.get(i)).isEqualTo(0);
        }
        assertThat(arrayList.length()).isEqualTo(initialCapacity);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(6, 0));
        arrayList.add(0);
        assertThat(arrayList.length()).isNotEqualTo(initialCapacity);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(7, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.set(-1, 0));
    }

    @Test
    void removeMethodsTest() {
        String[] strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayList<String> arrayList = new ArrayList<>(strings);
        assertThat(arrayList.remove(5)).isEqualTo("5");
        assertThat(arrayList.toString()).isEqualTo("[0, 1, 2, 3, 4, 6, 7, 8, 9, 10]");
        assertTrue(arrayList.remove("4"));
        assertThat(arrayList.toString()).isEqualTo("[0, 1, 2, 3, 6, 7, 8, 9, 10]");
        assertFalse(arrayList.remove("11"));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(10));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.remove(-1));
        arrayList.clear();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                arrayList.add("1");
            } else {
                arrayList.add("0");
            }
        }
        Predicate<String> predicate = (x) -> {
            return x.equals("1");
        };
        arrayList.removeIf(predicate);
        assertThat(arrayList.toString()).isEqualTo("[0, 0, 0, 0, 0]");
    }

    @Test
    void indexOfMethodsTest() {
        Integer[] integers = {1, 2, 3, 3, 5, 6, 7, 3, 3, 10};
        ArrayList<Integer> arrayList = new ArrayList<>(integers);
        assertThat(arrayList.indexOf(3)).isEqualTo(2);
        assertThat(arrayList.lastIndexOf(3)).isEqualTo(8);
        assertThat(arrayList.indexOf(4)).isEqualTo(-1);
        assertThat(arrayList.lastIndexOf(4)).isEqualTo(-1);
    }

    @Test
    void equalsTest() {
        Integer[] integers = {1,2,3,4,5,6,7,8,9,10};
        ArrayList<Integer> arrayConstructorArrayList = new ArrayList<>(integers);
        ArrayList<Integer> initCapacityConstructorArrayList = new ArrayList<>(10);
        Object object = new Object();
        for (int i = 1; i < 11; i++) {
            initCapacityConstructorArrayList.add(i);
        }
        assertTrue(arrayConstructorArrayList.equals(initCapacityConstructorArrayList));
        arrayConstructorArrayList.setCapacity(11);
        arrayConstructorArrayList.add(11);
        assertFalse(arrayConstructorArrayList.equals(initCapacityConstructorArrayList));
        initCapacityConstructorArrayList.add(11);
        assertFalse(arrayConstructorArrayList.equals(initCapacityConstructorArrayList));
        assertFalse(arrayConstructorArrayList.equals(object));
        initCapacityConstructorArrayList = null;
        assertFalse(arrayConstructorArrayList.equals(initCapacityConstructorArrayList));
    }

}
