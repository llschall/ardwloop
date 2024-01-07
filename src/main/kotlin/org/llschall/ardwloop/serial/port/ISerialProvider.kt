package serial.port

interface ISerialProvider {
    fun listPorts(): List<ISerialPort>
}
