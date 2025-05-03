package org.llschall.ardwloop.serial.bus;

import org.llschall.ardwloop.structure.utils.Logger;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

class Cable {

    final ArrayBlockingQueue<Character> input = new ArrayBlockingQueue<>(2000);

    final ArrayBlockingQueue<Character> available = new ArrayBlockingQueue<>(2000);
    final ArrayBlockingQueue<Character> output = new ArrayBlockingQueue<>(2000);

    void push(char c) {
        input.add(c);
    }

    String check() {
        StringWriter writer = new StringWriter();
        List<Character> list = input.stream().toList();
        list.stream().map(c -> "" + c).forEach(writer::append);
        return writer.toString();
    }

    String check(int n) {
        Logger.msg("check " + n);
        StringWriter writer = new StringWriter();
        for (int i = 0; i < n; i++) {
            try {
                Character c = input.poll(1, java.util.concurrent.TimeUnit.SECONDS);
                if (c == null) {
                    dumpThd();
                    throw new RuntimeException("Time out");
                }
                writer.append(c);
                available.add(c);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
        try {
            Character c = available.take();
            if (c == '*') return 0;
            output.add(c);
            return 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    char pull() {
        try {
            return output.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void dumpThd() {

        var names = new HashSet<String>();
        names.add(AbstractBusTest.COMPUTER_THD);
        names.add(AbstractBusTest.ARDUINO_THD);

        StringWriter writer = new StringWriter();

        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
            Thread thread = entry.getKey();
            if (!names.contains(thread.getName())) continue;

            writer.append("\n").append(thread.getName()).append(":\n");
            for (StackTraceElement element : entry.getValue()) {
                writer.append("\t").append(element.toString()).append("\n");
            }
        }
        Logger.msg(writer.toString());
    }


}
