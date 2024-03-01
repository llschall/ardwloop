package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.llschall.ardwloop.structure.StructureException;
import org.llschall.ardwloop.structure.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.llschall.ardwloop.serial.Serial.*;

public class MsgEntry implements IBackEntry {

    final List<Msg> msgs; // list of all messages
    int m = 0; // index of the current message
    boolean writing; // false if reading message should be made available
    int i = 0; // index of the current char
    int av = 0; // how many successive times available() is called

    MsgEntry(char program, int rc, int sc, int read, int post) {
        this.msgs = new ArrayList<>();
        this.msgs.add(new Msg("", K_ + P + J + K + P));
        this.msgs.add(new Msg(J_, J_));
        this.msgs.add(new Msg("", K_));
        this.msgs.add(new Msg(K_, C_ + program + C_ + rc + C_ + sc + C_ + read + C_ + post));
    }

    MsgEntry(char program, int rc, int sc) {
        this(program, rc, sc, 0, 0);
    }

    void addMsg(String a2c, String c2a) {
        msgs.add(new Msg(a2c, c2a));
    }

    @Override
    public int available() {
        if (writing) {
            return 0;
        }

        Msg msg = msgs.get(m);
        String buf = msg.c2a();
        if (buf.isEmpty()) {
            incM();
            writing = false;
            return 0;
        }

        int size = buf.length() - i;
        if (size > 0) {
            av = 0;
            return size;
        }
        av++;
        if (av > 9) {
            Logger.err("Too many available() calls", new StructureException("Too many available() calls !"));
            System.exit(0);
        }
        return size;
    }

    @Override
    public char read() {
        av = 0;
        String buf = msgs.get(m).c2a();
        char c = buf.charAt(i);
        i++;
        if (i == buf.length()) {
            Logger.msg("[" + m + "] All read !");
            incM();
            boolean nextMsgHasNothingToWrite = m < msgs.size() && msgs.get(m).a2c().isEmpty();
            writing = !nextMsgHasNothingToWrite;
        }
        return c;
    }

    private void incM() {
        m++;
        i = 0;
    }

    @Override
    public void write(char c) {
        av = 0;
        Msg msg = msgs.get(m);
        String str = msg.a2c();
        char c0 = str.charAt(i);

        if (c0 == c) {
            i++;
        } else {
            Assertions.fail("[" + m + "] " + c + " instead of " + c0 + " in (" + i + ")" + str);
        }
        if (i == str.length()) {
            Logger.msg("[" + m + "] == Got " + msg + " ==");
            // switch to reading
            i = 0;
            writing = false;
        }
    }

}

record Msg(String a2c, String c2a) {
    public static final String EMPTY_A = "av+aw+ax+ay+az+";
    public static final String EMPTY_B = "bv+bw+bx+by+bz+";
    public static final String EMPTY_C = "cv+cw+cx+cy+cz+";
    public static final String EMPTY_D = "dv+dw+dx+dy+dz+";
    public static final String EMPTY_E = "ev+ew+ex+ey+ez+";
    public static final String EMPTY_F = "fv+fw+fx+fy+fz+";
    public static final String EMPTY_G = "gv+gw+gx+gy+gz+";
    public static final String EMPTY_H = "hv+hw+hx+hy+hz+";
    public static final String EMPTY_I = "iv+iw+ix+iy+iz+";
    public static final String EMPTY_MSG = EMPTY_A + EMPTY_B + EMPTY_C + EMPTY_D +
            EMPTY_E + EMPTY_F + EMPTY_G + EMPTY_H + EMPTY_I;
    public static final String EMPTY_B_I = EMPTY_B + EMPTY_C + EMPTY_D +
            EMPTY_E + EMPTY_F + EMPTY_G + EMPTY_H + EMPTY_I;
}