package org.llschall.ardwloop.serial.bus;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

class Cable {

    final ArrayBlockingQueue<Character> input = new ArrayBlockingQueue<>(2000);
    final ArrayBlockingQueue<Character> output = new ArrayBlockingQueue<>(2000);

    CountDownLatch latch = new CountDownLatch(1);

    void push(char c) {
        input.add(c);
    }

    String check() {
        StringWriter writer = new StringWriter();
        List<Character> list = input.stream().toList();
        list.stream().map(c -> "" + c).forEach(writer::append);
        return writer.toString();
    }

    String dump() {
        StringWriter writer = new StringWriter();
        ArrayList<Character> list;
        list = new ArrayList<>(this.input);
        for (int i = list.size() - 1; i >= 0; i--) {
            writer.append(list.get(i));
        }
        writer.append(" >>> ");
        list = new ArrayList<>(this.output);
        for (int i = list.size() - 1; i >= 0; i--) {
            writer.append(list.get(i));
        }
        return writer.toString();
    }

    void release(int n) {
        for (int i = 0; i < n; i++) {
            Character c = input.remove();
            output.add(c);
        }
    }

    void releaseAll() {
        while (!input.isEmpty()) {
            Character c = input.remove();
            output.add(c);
        }
    }

    int available() {
        return output.size();
    }

    char pull() {
        try {
            return output.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
