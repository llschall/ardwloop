
    void fake_delay(unsigned long);
    void fake_write_low(int);
    void fake_write_high(int);
    void fake_pin_out(int);
    void fake_serial_begin(int);
    int fake_available();
    int fake_read(int);
    int fake_write(char);

    void back_print(int log, char* str);
    void back_print(int log, char* str, va_list c);

    jclass findBackEntryClass();