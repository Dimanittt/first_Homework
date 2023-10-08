import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Пользовательская реализация динамического массива (не потокобезопасна)
 *
 * @param <T> тип хранимых элементов
 * @author <a href="https://github.com/Dimanittt">Dimanittt</a>
 * @see java.util.ArrayList
 */
public class ArrayList<T> {
    /**
     * @param array - основная единица класса ArrayList,
     * хранящая массив типа Object
     */
    private Object[] array = new Object[16];
    /**
     * @param size длина индексируемой части массива
     */
    private int size = 0;

    /**
     * Конструктор умолчанию создает объект класса {@link ArrayList}
     * с изначальной вместимостью массива {@link ArrayList#array} 16 элементов
     *
     * @return {@link ArrayList}
     */
    public ArrayList() {
    }

    /**
     * Конструктор создает объект класса {@link ArrayList} с указанной вместимостью массива {@link ArrayList#array}
     *
     * @param initialCapacity изначальная вместимость массива {@link ArrayList#array}
     * @return {@link ArrayList}
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("The initial capacity have to be a positive number");
        }
        this.array = new Object[initialCapacity];
    }

    /**
     * Конструктор создает объект класса {@link ArrayList} с помощью другого экземпляра {@link ArrayList}
     *
     * @param initialArrayList экземпляр класса {@link ArrayList} для инициализации
     * @return {@link ArrayList}
     */
    public ArrayList(ArrayList<T> initialArrayList) {
        this.size = initialArrayList.size;
        this.array = initialArrayList.array;
    }

    /**
     * Конструктор создает объект класса {@link ArrayList} с помощью массива
     *
     * @param initialArray массив объектов для инициализации {@link ArrayList#array}
     * @return {@link ArrayList}
     */
    public ArrayList(T[] initialArray) {
        this.array = initialArray;
        this.size = initialArray.length;
    }

    /**
     * Метод реализует алгоритм <a href="https://www.youtube.com/watch?v=4s-aG6yGGLU">быстрой сортировки</a> рекурсивным методом
     *
     * @param comparator экземпляр функционального интерфейса {@link Comparator}
     */
    public void sort(Comparator<T> comparator) {
        quickSort(this.array, 0, this.size - 1, comparator);
    }

    private void quickSort(Object[] array, int firstIndex, int lastIndex, Comparator<T> comparator) {
        if (firstIndex < lastIndex) {
            int wall = wallPosition(array, firstIndex, lastIndex, comparator);
            quickSort(array, firstIndex, wall - 1, comparator);
            quickSort(array, wall + 1, lastIndex, comparator);
        }
    }

    private int wallPosition(Object[] array, int firstIndex, int lastIndex, Comparator<T> comparator) {
        T pivot = (T) array[lastIndex];
        int i = (firstIndex - 1);
        for (int j = firstIndex; j < lastIndex; j++) {
            if (comparator.compare((T) array[j], pivot) <= 0) {
                i++;
                T transitElement = (T) array[i];
                array[i] = array[j];
                array[j] = transitElement;
            }
        }
        T transitElement = (T) array[i + 1];
        array[i + 1] = array[lastIndex];
        array[lastIndex] = transitElement;
        return i + 1;
    }

    /**
     * Добавляет передаваемый элемент в {@link ArrayList#array} в том случае, если позволяет вместимость массива,
     * в противном случае создает новый массив вместимостью в 1,5 раза больше и перезаписывает в него все данные
     * после добавления элемента индексируемость {@link ArrayList} инкрементируется
     *
     * @param element добавляемый элемент в динамический массив
     */
    public void add(T element) {
        if (size >= array.length) {
            Object[] transitArray = new Object[(array.length * 3) / 2 + 1];
            for (int i = 0; i < array.length; i++) {
                transitArray[i] = array[i];
            }
            this.array = transitArray;
        }
        array[size] = element;
        size++;
    }

    /**
     * Добавляет передаваемый элемент в {@link ArrayList#array} на указанный {@code index},
     * сдвигая все элементы вправо
     *
     * @param element добавляемый элемент в динамический массив
     * @param index   место вставки элемента
     * @throws IndexOutOfBoundsException в случае если передаваемый {@code index}
     *                                   выходит за пределы индексации {@link ArrayList}
     * @see ArrayList#add(Object) ArrayList.add(T element)
     */
    public void add(int index, T element) {
        if (index > size || size == 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        if (size >= array.length) {
            Object[] transitArray = new Object[(array.length * 3) / 2 + 1];
            for (int i = 0; i < array.length; i++) {
                transitArray[i] = array[i];
            }
            this.array = transitArray;
        }
        if (index == 0) {
            ArrayList<T> transitList = new ArrayList<>();
            transitList.add(element);
            transitList.addAll(this);
            array = transitList.array;
        } else {
            for (int i = size; i >= index; i--) {
                array[i] = array[i - 1];
            }
        }
        array[index] = element;
        size++;
    }

    /**
     * Добавляет все элементы передаваемого {@code secondList} в конец массива изначального {@link ArrayList}
     *
     * @param secondList экземпляр класса {@link ArrayList}
     * @throws NullPointerException в случае, если {@code secondList} неинициализирован
     */
    public void addAll(ArrayList<T> secondList) {
        if (secondList == null) {
            throw new NullPointerException("Cannot invoke method addAll(ArrayList<T> secondList) because the secondList is null");
        }
        for (int i = 0; i < secondList.size; i++) {
            this.add(secondList.get(i));
        }
    }

    /**
     * Метод возвращает элемент массива {@link ArrayList#array} на указанной позиции
     *
     * @param index индекс требуемого элемента массива {@link ArrayList#array}
     * @return элемент массива {@link ArrayList#array} на указанной позиции {@code index}
     * @throws IndexOutOfBoundsException в случае если передаваемый {@code index}
     *                                   выходит за пределы индексации {@link ArrayList}
     */
    public T get(int index) {
        if (index > size || size == 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        return (T) array[index];
    }

    /**
     * Изменяет значение элемента в массиве {@link ArrayList#array} на указанной позиции
     *
     * @param index   индекс элемента, который требуется изменить
     * @param element значение элемента, на который требуется заменить
     * @throws IndexOutOfBoundsException в случае если передаваемый {@code index}
     *                                   выходит за пределы индексации {@link ArrayList}
     */
    public void set(int index, T element) {
        if (index > size || size == 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        array[index] = element;
    }

    /**
     * Удаляет из массива {@link ArrayList#array} элемент по индексу,
     * сдвигая все элементы влево, при этом декрементируя индексируемость {@link ArrayList}
     *
     * @param index индекс элемента для удаления
     * @return значение удаленного элемента
     * @throws IndexOutOfBoundsException в случае если передаваемый {@code index}
     *                                   выходит за пределы индексации {@link ArrayList}
     */
    public T remove(int index) {
        if (index > size || size == 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        T deletedElement = (T) array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return deletedElement;
    }

    /**
     * Удаляет из массива {@link ArrayList#array} элемент по его значению,
     * сдвигая все элементы влево, при этом декрементируя индексируемость {@link ArrayList}.
     * Если в массиве {@link ArrayList#array} элемент отсутствует, то ничего не происходит
     *
     * @param element значение элемента для удаления
     * @return {@code true}, если элемент был найден и удален, в противном случае {@code false}
     */
    public boolean remove(T element) {
        boolean checkForElement = false;
        int position = 0;
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                checkForElement = true;
                position = i;
                break;
            }
        }
        if (checkForElement) {
            for (int i = position; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            size--;
        }
        return checkForElement;
    }

    /**
     * Удаляет из массива {@link ArrayList#array} все элементы,
     * удовлетворяющие фильтру передаваемого экземпляра {@code Predicate},
     * используя механизм {@link ArrayList#remove(int) ArrayList.remove(int index)}
     *
     * @param filter экземпляр функционального интерфейса {@link Predicate}
     * @return {@code true}, если элемент(ы) был(и) найден(ы) и удален(ы), в противном случае {@code false}
     */
    public boolean removeIf(Predicate<T> filter) {
        boolean checkForElement = false;
        int position = 0;
        for (int i = 0; i < size; i++) {
            if (filter.test((T) array[i])) {
                checkForElement = true;
                position = i;
                for (int j = position; j < size - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                i--;
            }
        }
        return checkForElement;
    }

    /**
     * Возвращает индекс первого найденного передаваемого элемента в массиве {@link ArrayList#array},
     * или {@code -1} если такого элемента нет
     *
     * @param element элемент, индекс которого требуется получить
     * @return индекс элемента, если он содержится в массиве {@link ArrayList#array}, в противном случае {@code -1}
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Возвращает индекс последнего найденного передаваемого элемента в массиве {@link ArrayList#array},
     * или {@code -1} если такого элемента нет
     *
     * @param element элемент, индекс которого требуется получить
     * @return индекс элемента, если он содержится в массиве {@link ArrayList#array}, в противном случае {@code -1}
     */
    public int lastIndexOf(T element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return значение длины индексируемой части массива {@link ArrayList#array}
     */
    public int length() {
        return size;
    }

    /**
     * Присваивает всем элементам массива {@link ArrayList#array} значение {@code null}
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * @return {@code true} если длина индексируемой части массива {@link ArrayList#array} меньше вместимости массива,
     * В противном случае вернет {@code false}
     */
    public boolean ensureCapacity() {
        return (array.length > size);
    }

    /**
     * Обрезает длину массива {@link ArrayList#array} до размера индексируемой части
     * {@link ArrayList#size} путем его перезаписи
     */
    public void trimToSize() {
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = this.array[i];
        }
        this.array = array;
    }

    /**
     * Устанавливает вместимость массива {@link ArrayList#array} указанному значению путем его перезаписи,
     * при этом если {@code capacity} меньше индексируемой части массива {@link ArrayList#array},
     * то происходит потеря этих значений
     *
     * @param capacity новое значение вместимости массива {@link ArrayList#array}
     * @throws IllegalArgumentException в случае если {@code capacity} меньше 1
     */
    public void setCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity has to be greater or equal to 1");
        }
        Object[] array = new Object[capacity];
        for (int i = 0; i < size; i++) {
            array[i] = this.array[i];
        }
        this.array = array;
    }

    /**
     * @return {@code String} в квадратных скобках со значениями элементов индексируемой части
     * массива {@link ArrayList#array}, перечисленных через запятую
     */
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(array[i].toString()).append(", ");
        }
        result.delete(result.length() - 2, result.length()).append(']');
        return result.toString();
    }

    /**
     * @return массив {@code Object[]} с элементами индексируемой части массива {@link ArrayList#array}
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        return result;
    }

    @Override
    public int hashCode() {
        if (this.size == 0) {
            return super.hashCode();
        } else {
            int result = 17;
            result = 31 * result + size;
            result = 31 * result + array.length;
            result = 31 * result + this.getClass().getName().length();
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ArrayList<?>))
            return false;
        if (this.array.length == ((ArrayList<?>) o).array.length &&
                this.size == ((ArrayList<?>) o).size) {
            for (int i = 0; i < this.size; i++) {
                if (!this.array[i].equals(((ArrayList<?>) o).array[i])) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}

