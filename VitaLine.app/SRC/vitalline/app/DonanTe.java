package vitalline.app;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class DonanTe {
    String id;
    String nombre;
    String tipoSangre;
    LocalDate fechaUltimaDonacion;
    DonanTe siguiente;
    public DonanTe(String id, String nombre, String tipoSangre, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.tipoSangre = tipoSangre;
        this.fechaUltimaDonacion = fecha;
        this.siguiente = null;
    }
    @Override
    public String toString() {
        return id + " | " + nombre + " | " + tipoSangre + " | " + fechaUltimaDonacion;
    }
}
class NodoCiudad {
    String nombreCiudad;
    DonanTe cabezaDonantes;
    NodoCiudad izquierda, derecha;
    public NodoCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
        this.cabezaDonantes = null;
        this.izquierda = null;
        this.derecha = null;
    }
}
class ArbolCiudades {
    NodoCiudad raiz;
    public ArbolCiudades() {
        this.raiz = null;
    }
    public void agregarCiudad(String nombreCiudad) {
        raiz = insertarCiudad(raiz, nombreCiudad);
    }
    private NodoCiudad insertarCiudad(NodoCiudad nodo, String nombreCiudad){ 
        if (nodo == null) return new NodoCiudad(nombreCiudad);
        if (nombreCiudad.compareToIgnoreCase(nodo.nombreCiudad) < 0)
            nodo.izquierda = insertarCiudad(nodo.izquierda, nombreCiudad);
        else if (nombreCiudad.compareToIgnoreCase(nodo.nombreCiudad) > 0)
            nodo.derecha = insertarCiudad(nodo.derecha, nombreCiudad);
        return nodo;
    }
    private NodoCiudad buscarCiudad(NodoCiudad nodo, String nombreCiudad) {
        if (nodo == null || nodo.nombreCiudad.equalsIgnoreCase(nombreCiudad)) return nodo;
        if (nombreCiudad.compareToIgnoreCase(nodo.nombreCiudad) < 0){
            return buscarCiudad(nodo.izquierda, nombreCiudad);
        }
        else{
            return buscarCiudad(nodo.derecha, nombreCiudad);
        }
    }
    public NodoCiudad buscarCiudad(String nombreCiudad) {
        return buscarCiudad(raiz, nombreCiudad);
    }
    public void agregarDonante(String ciudad, String id, String nombre, String tipoSangre, LocalDate fecha){
        NodoCiudad nodoCiudad = buscarCiudad(ciudad);
        if (nodoCiudad == null) {
            agregarCiudad(ciudad);
            nodoCiudad = buscarCiudad(ciudad);
        }
        DonanTe nuevo = new DonanTe(id, nombre, tipoSangre, fecha);
        nuevo.siguiente = nodoCiudad.cabezaDonantes;
        nodoCiudad.cabezaDonantes = nuevo;
    }
    public void agregarDonanteDesdeMenu() {
        String continuar;
        do {
            String ciudad = JOptionPane.showInputDialog("Ciudad: ");
            if (ciudad == null) break;

            String id = JOptionPane.showInputDialog("Número de identificación: ");
            if (id == null) break;

            String nombre = JOptionPane.showInputDialog("Nombre: ");
            if (nombre == null) break;

            String tipo = JOptionPane.showInputDialog("Tipo de sangre: ");
            if (tipo == null) break;

            LocalDate fecha = null;
            do {
                try {
                    String fechaInput = JOptionPane.showInputDialog("Fecha de última donación (YYYY-MM-DD): ");
                    if (fechaInput == null) return;
                    fecha = LocalDate.parse(fechaInput);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido.");
                    fecha = null;
                }
            } while (fecha == null);

            agregarDonante(ciudad, id, nombre, tipo, fecha);
            continuar = JOptionPane.showInputDialog("¿Desea agregar otro donante? \n(Digita <s>"
                    + " para agregar, o cualquiera para cancelar):");
            if (continuar == null || !continuar.equalsIgnoreCase("s")) break;
        } while (true);
    }

    public void mostrarDonantesPorCiudad() {
        mostrarDonantesPorCiudadRec(raiz);
    }
    private void mostrarDonantesPorCiudadRec(NodoCiudad nodo) {
        if (nodo == null) return;
        mostrarDonantesPorCiudadRec(nodo.izquierda);
        JOptionPane.showMessageDialog(null,"--- Donantes por Ciudad ---\nCiudad: " + nodo.nombreCiudad);
        Map<String, Integer> conteo = new TreeMap<>();
        DonanTe actual = nodo.cabezaDonantes;
        while (actual != null) {
            conteo.put(actual.tipoSangre, conteo.getOrDefault(actual.tipoSangre, 0) + 1);
            actual = actual.siguiente;
        }
        for (Map.Entry<String, Integer> e : conteo.entrySet()) {
            JOptionPane.showMessageDialog(null,"Tipo " + e.getKey() + ": " + e.getValue() + " donantes");
        }
        mostrarDonantesPorCiudadRec(nodo.derecha);
    }
    public void buscarDonantesPorCiudadYTipo() {
        String ciudad = JOptionPane.showInputDialog("Ciudad: ");
        String tipo = JOptionPane.showInputDialog("Tipo de sangre: ");
        NodoCiudad nodo = buscarCiudad(ciudad);
        if (nodo == null) {
            JOptionPane.showMessageDialog(null,"Ciudad no encontrada.");
            return;
        }
        List<DonanTe> filtrados = new ArrayList<>();
        DonanTe actual = nodo.cabezaDonantes;
        while (actual != null) {
            if (actual.tipoSangre.equalsIgnoreCase(tipo)) {
                filtrados.add(actual);
            }
            actual = actual.siguiente;
        }
        filtrados.sort(Comparator.comparing(d -> d.nombre));
        JOptionPane.showMessageDialog(null,"\nDonantes con sangre tipo " + tipo + " en " + ciudad + ":");
        for (DonanTe d : filtrados) {
            JOptionPane.showMessageDialog(null,d);
        }
    }
    public void ordenarDonantesPorFecha() {
        ordenarDonantesPorFechaRec(raiz);
    }
    private void ordenarDonantesPorFechaRec(NodoCiudad nodo) {
        if (nodo == null) return;
        ordenarDonantesPorFechaRec(nodo.izquierda);
        nodo.cabezaDonantes = ordenarListaPorFecha(nodo.cabezaDonantes);
        ordenarDonantesPorFechaRec(nodo.derecha);
    }
    private DonanTe ordenarListaPorFecha(DonanTe cabeza) {
        if (cabeza == null || cabeza.siguiente == null) return cabeza;
        boolean swapped;
        do {
            swapped = false;
            DonanTe actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.fechaUltimaDonacion.isBefore(actual.siguiente.fechaUltimaDonacion)){
                    intercambiarDatos(actual, actual.siguiente);
                    swapped = true;
                }
                actual = actual.siguiente;
            }
        } while (swapped);
        return cabeza;
    }
    private void intercambiarDatos(DonanTe d1, DonanTe d2) {
        String tempId = d1.id;
        String tempNombre = d1.nombre;
        String tempTipo = d1.tipoSangre;
        LocalDate tempFecha = d1.fechaUltimaDonacion;
        d1.id = d2.id;
        d1.nombre = d2.nombre;
        d1.tipoSangre = d2.tipoSangre;
        d1.fechaUltimaDonacion = d2.fechaUltimaDonacion;
        d2.id = tempId;
        d2.nombre = tempNombre;
        d2.tipoSangre = tempTipo;
        d2.fechaUltimaDonacion = tempFecha;
    }
    public void eliminarDonantePorNombre() {
        String continuar;
        do {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del donante a eliminar: ");
            if(nombre==null)break;
            boolean eliminado = eliminarDonanteRec(raiz, nombre);
            if (eliminado) {
                JOptionPane.showMessageDialog(null,"Donante eliminado.");
            } else {
                JOptionPane.showMessageDialog(null,"Donante no encontrado.");
            }
            
            continuar = JOptionPane.showInputDialog("¿Desea eliminar otro donante?  \n(Digita <s> para agregar, o cualquiera para cancelar): ");
            if(!continuar.equalsIgnoreCase("s") || continuar==null)break;
        } while (true);
    }

    private boolean eliminarDonanteRec(NodoCiudad nodo, String nombre) {
        if (nodo == null) return false;
        DonanTe actual = nodo.cabezaDonantes;
        DonanTe anterior = null;
        while (actual != null && !actual.nombre.equalsIgnoreCase(nombre)) {
            anterior = actual;
            actual = actual.siguiente;
        }
        if (actual != null) {
            if (anterior == null) {
                nodo.cabezaDonantes = actual.siguiente;
            } else {
                anterior.siguiente = actual.siguiente;
            }
            if (nodo.cabezaDonantes == null) {
                raiz = eliminarCiudad(raiz, nodo.nombreCiudad);
            }
            return true;
        }
        return eliminarDonanteRec(nodo.izquierda, nombre) || eliminarDonanteRec(nodo.derecha, nombre);
    }
    private NodoCiudad eliminarCiudad(NodoCiudad nodo, String nombreCiudad) {
        if (nodo == null) return null;

        if (nombreCiudad.compareToIgnoreCase(nodo.nombreCiudad) < 0) {
            nodo.izquierda = eliminarCiudad(nodo.izquierda, nombreCiudad);
        } else if (nombreCiudad.compareToIgnoreCase(nodo.nombreCiudad) > 0) {
            nodo.derecha = eliminarCiudad(nodo.derecha, nombreCiudad);
        } else {
            if (nodo.izquierda == null) return nodo.derecha;
            if (nodo.derecha == null) return nodo.izquierda;
            NodoCiudad sucesor = nodo.derecha;
            while (sucesor.izquierda != null) {
                sucesor = sucesor.izquierda;
            }

            nodo.nombreCiudad = sucesor.nombreCiudad;
            nodo.derecha = eliminarCiudad(nodo.derecha, sucesor.nombreCiudad);
        }

        return nodo;
    }
    public void actualizarFechaDonacion() {
        String nombre = JOptionPane.showInputDialog("Nombre del donante: ");
        LocalDate nuevaFecha = null;
        do{
            try{
                nuevaFecha = LocalDate.parse(JOptionPane.showInputDialog("Nueva fecha de última donación (YYYY-MM-DD): "));
            }catch(DateTimeParseException e){
                JOptionPane.showMessageDialog(null,"Por favor, ingrese un valor válido.");
                nuevaFecha = null;
            }
        }while(nuevaFecha==null);
        boolean actualizado = actualizarFechaRec(raiz, nombre, nuevaFecha);
        if (actualizado) {
            JOptionPane.showMessageDialog(null,"Fecha actualizada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null,"Donante no encontrado.");
        }
    }

    private boolean actualizarFechaRec(NodoCiudad nodo, String nombre, LocalDate nuevaFecha){ 
        if (nodo == null) return false;
        DonanTe actual = nodo.cabezaDonantes;
        while (actual != null) {
            if (actual.nombre.equalsIgnoreCase(nombre)) {
                actual.fechaUltimaDonacion = nuevaFecha;
                return true;
            }
            actual = actual.siguiente;
        }
        return actualizarFechaRec(nodo.izquierda, nombre, nuevaFecha) || actualizarFechaRec(nodo.derecha, nombre, nuevaFecha);
    }
}