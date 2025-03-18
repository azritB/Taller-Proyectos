import com.fazecast.jSerialComm.SerialPort;

public class ConexionSerial {
    
    private SerialPort puerto;

    public boolean conectar(String nombrePuerto) {
        puerto = SerialPort.getCommPort(nombrePuerto);
        puerto.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (puerto.openPort()) {
            System.out.println("Conectado al puerto " + nombrePuerto);
            return true;
        } else {
            System.out.println("No se pudo conectar al puerto " + nombrePuerto);
            return false;
        }
    }

    public void desconectar() {
        if (puerto != null && puerto.isOpen()) {
            puerto.closePort();
            System.out.println("Desconectado del puerto");
        }
    }

    public void enviarDato(String dato) {
        if (puerto != null && puerto.isOpen()) {
            byte[] buffer = dato.getBytes();
            puerto.writeBytes(buffer, buffer.length);
            System.out.println("Dato enviado: " + dato);
        } else {
            System.out.println("Puerto no conectado o no disponible.");
        }
    }
    
}
