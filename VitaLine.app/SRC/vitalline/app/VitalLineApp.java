package vitalline.app;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Image;

public class VitalLineApp {

    public static void main(String[] args) {
        ArbolCiudades sistema = new ArbolCiudades();
        int opcion = 0;
        ImageIcon iconoOriginal = new ImageIcon("image.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        
        do {            
            try { 
                String input = (String) JOptionPane.showInputDialog(
                    null,
                    "--- MENÚ PRINCIPAL ---\n"
                    + "1. Agregar donante\n"
                    + "2. Mostrar donantes por ciudad\n"
                    + "3. Buscar donante por ciudad y tipo de sangre\n"
                    + "4. Ordenar donantes por fecha (más reciente)\n"
                    + "5. Eliminar donante\n"
                    + "6. Actualizar fecha de última donación del donante\n"
                    + "7. Salir\n"
                    + "Seleccione una opción y ingrese el valor:",
                    "Vital Line",
                    JOptionPane.PLAIN_MESSAGE,
                    iconoEscalado,
                    null,
                    ""
                );
                
                if (input != null && !input.isEmpty()) {
                    opcion = Integer.parseInt(input);
                } else {
                    opcion = 0;
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido.", "Error", JOptionPane.ERROR_MESSAGE);
                opcion = 0;
            }
            
            switch (opcion) {
                case 1:
                    sistema.agregarDonanteDesdeMenu();
                    break;
                case 2:
                    sistema.mostrarDonantesPorCiudad();
                    break;
                case 3:
                    sistema.buscarDonantesPorCiudadYTipo();
                    break;
                case 4:
                    sistema.ordenarDonantesPorFecha();
                    JOptionPane.showMessageDialog(null, "Donantes ordenados por fecha de última donación (más reciente primero).");
                    break;
                case 5:
                    sistema.eliminarDonantePorNombre();
                    break;
                case 6:
                    sistema.actualizarFechaDonacion();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Gracias por usar Vital Line!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
            }
            
        } while (opcion != 7);
    }
}
