package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.List;

public class AsIntStream implements IntStream {
    List<Integer> elementsList = new ArrayList<>();

    public boolean hasNext(int now) {
        return now + 1 < elementsList.size();
    }

    public Integer getNext(int now) {
        if (hasNext(now))
            return elementsList.get(now + 1);
        throw new IllegalArgumentException("Array out of boundaries");
    }

    private AsIntStream(List<Integer> lst) {
        elementsList = lst;
    }

    public AsIntStream(int[] values) {
        for (Integer element : values) {
            elementsList.add(element);
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    private void isEmptyError() {
        if (size() == 0) {
            throw new IllegalArgumentException("List size of zero");
        }
    }

    @Override
    public Double average() {
        isEmptyError();
        return (double) sum() / size();
    }

    private Integer size() {
        return elementsList.size();
    }

    private Integer findMinMax(int multiplier) {
        isEmptyError();
        Integer now = getNext(-1);
        int index = 0;
        while (hasNext(index)) {
            int value = getNext(index);
            if (value * multiplier > now) {
                now = value * multiplier;
            }
            index++;
        }
        return now * multiplier;
    }

    @Override
    public Integer max() {
        return findMinMax(1);
    }

    @Override
    public Integer min() {
        return findMinMax(-1);
    }

    @Override
    public long count() {
        return size();
    }

    @Override
    public Integer sum() {
        isEmptyError();
        Integer suma = 0;
        int index = -1;
        while (hasNext(index)) {
            suma += getNext(index);
            index++;
        }
        return suma;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List<Integer> elementsList = new ArrayList<>();
        int now = -1;
        while (hasNext(now)) {
            int x = getNext(now);
            if (predicate.test(x)) {
                elementsList.add(x);
            }
            now += 1;
        }
        return new AsIntStream(elementsList);
    }


    @Override
    public AsIntStream forEach(IntConsumer action) {
        int arr[] = new int[size()];
        int now = -1;
        while (hasNext(now)) {
            arr[now + 1] = getNext(now);
            action.accept(arr[now + 1]);
            now++;
        }
        return new AsIntStream(arr);
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(elementsList) {
            @Override
            public Integer getNext(int now) {
                return mapper.apply(super.getNext(now));

            }
        };
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        int now = -1;
        List<Integer> elements = new ArrayList<>();

        while (hasNext(now)) {
            IntStream arr = func.applyAsIntStream(getNext(now));
            int now2 = -1;
            while (arr.hasNext(now2)) {
                elements.add(arr.getNext(now2));
                now2++;
            }
            now++;
        }
        return new AsIntStream(elements);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int now = -1;
        while (hasNext(now)) {
            identity = op.apply(identity, getNext(now));
            now += 1;
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        int arr[] = new int[size()];
        int now = -1;
        while (hasNext(now)) {
            arr[now + 1] = getNext(now);
            now++;
        }
        return arr;
    }


}