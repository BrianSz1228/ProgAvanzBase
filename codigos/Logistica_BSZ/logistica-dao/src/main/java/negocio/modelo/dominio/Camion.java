package negocio.modelo.dominio;

public class Camion {

    private int idCamion;
    private String marca;
    private String modelo;
    private String dominio;
    private double toneladasMaximas;
    private double litrosTanque;
    private double consumoLitrosKm;

    public Camion() {
    }

    public Camion(int idCamion, String marca, String modelo, String dominio,
                  double toneladasMaximas, double litrosTanque, double consumoLitrosKm) {
        this.idCamion = idCamion;
        this.marca = marca;
        this.modelo = modelo;
        this.dominio = dominio;
        this.toneladasMaximas = toneladasMaximas;
        this.litrosTanque = litrosTanque;
        this.consumoLitrosKm = consumoLitrosKm;
    }

    // Litros que gasta el camion para recorrer los km indicados
    public double calcularLitros(int km) {
        return km * consumoLitrosKm;
    }

    // Cantidad de tanques que hay que llenar (siempre se redondea para arriba)
    public int calcularTanques(int km) {
        if (litrosTanque <= 0) {
            throw new IllegalStateException("El tanque del camion no tiene capacidad");
        }
        return (int) Math.ceil(calcularLitros(km) / litrosTanque);
    }

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public double getToneladasMaximas() {
        return toneladasMaximas;
    }

    public void setToneladasMaximas(double toneladasMaximas) {
        this.toneladasMaximas = toneladasMaximas;
    }

    public double getLitrosTanque() {
        return litrosTanque;
    }

    public void setLitrosTanque(double litrosTanque) {
        this.litrosTanque = litrosTanque;
    }

    public double getConsumoLitrosKm() {
        return consumoLitrosKm;
    }

    public void setConsumoLitrosKm(double consumoLitrosKm) {
        this.consumoLitrosKm = consumoLitrosKm;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " (" + dominio + ")";
    }
}
