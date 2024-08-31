package hex;

import org.llschall.ardwloop.hex.ArdwHexUploader;

import java.io.IOException;

public class HexUploader {

    public static void main(String[] args) throws IOException {

        var path = "/home/laurent/Factory/git/arduino_sw/ArduinoSw/hex/pin_low.ino.hex";
        var port = "/dev/ttyUSB0";

        //path = "/home/laurent/Factory/git/arduino_sw/ArduinoSw/hex/pin_high.ino.hex"

        var uploader = new ArdwHexUploader();
        uploader.setAvrdudePath("");
        uploader.upload(path, port);
    }

}
