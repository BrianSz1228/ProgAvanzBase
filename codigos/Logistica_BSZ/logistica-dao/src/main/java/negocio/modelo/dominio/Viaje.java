package negocio.modelo.dominio;

import java.time.LocalDateTime;

public class Viaje {

    // Kilometros que se recorren por dia
    public static final int KM_POR_DIA = 200;

    private int idViaje;
    private Chofer chofer;
    private Camion camion;
    private Destino origen;
    private Destino destino;
    private int km;
    private int dias;
    private int tanques;
    private EstadoViaje estado = EstadoViaje.ASIGNADO;
    private LocalDateTime fechaCarga;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Viaje() {
    }

    public Viaje(Chofer chofer, Camion camion, Destino origen, Destino destino) {
        this.chofer = chofer;
        this.camion = camion;
        this.origen = origen;
        this.destino = destino;
    }

    // Dias de viaje segun los km (se redondea para arriba)
    public static int calcularDias(int km) {
        return (int) Math.ceil((double) km / KM_POR_DIA);
    }

    // Carga los km y deja calculados los dias y los tanques
    public void calcularRecorrido(int km) {
        this.km = km;
        this.dias = calcularDias(km);
        this.tanques = camion.calcularTanques(km);
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Destino getOrigen() {
        return origen;
    }

    public void setOrigen(Destino origen) {
        this.origen = origen;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getTanques() {
        return tanques;
    }

    public void setTanques(int tanques) {
        this.tanques = tanques;
    }

    public EstadoViaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoViaje estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "idViaje=" + idViaje +
                ", origen=" + origen +
                ", destino=" + destino +
                ", km=" + km +
                ", estado=" + estado +
                '}';
    }
}
