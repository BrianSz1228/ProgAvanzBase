package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.ViajeDAO;
import negocio.modelo.dominio.*;
import negocio.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViajeDAOImpl implements ViajeDAO {

    // Trae el viaje con los datos basicos del chofer, camion, origen y destino
    private static final String SELECT =
            "SELECT v.id_viaje, v.km, v.dias, v.tanques, v.estado, " +
            "v.fecha_carga, v.fecha_inicio, v.fecha_fin, " +
            "ch.id_chofer, ch.nombre AS nombre_chofer, ch.apellido, ch.dni, " +
            "ca.id_camion, ca.marca, ca.modelo, ca.dominio, " +
            "o.id_destino AS id_origen, o.nombre AS nombre_origen, " +
            "d.id_destino AS id_destino, d.nombre AS nombre_destino " +
            "FROM viajes v " +
            "JOIN choferes ch ON ch.id_chofer = v.id_chofer " +
            "JOIN camiones ca ON ca.id_camion = v.id_camion " +
            "JOIN destinos o ON o.id_destino = v.id_origen " +
            "JOIN destinos d ON d.id_destino = v.id_destino ";

    @Override
    public int crear(Connection con, Viaje viaje) throws SQLException {
        String sql = "INSERT INTO viajes (id_chofer, id_camion, id_origen, id_destino, km, dias, " +
                "tanques, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, viaje.getChofer().getIdChofer());
            ps.setInt(2, viaje.getCamion().getIdCamion());
            ps.setInt(3, viaje.getOrigen().getIdDestino());
            ps.setInt(4, viaje.getDestino().getIdDestino());
            ps.setInt(5, viaje.getKm());
            ps.setInt(6, viaje.getDias());
            ps.setInt(7, viaje.getTanques());
            ps.setString(8, viaje.getEstado().name());
            ps.executeUpdate();
            return JdbcUtil.obtenerIdGenerado(ps);
        }
    }

    @Override
    public Viaje buscarPorId(Connection con, int idViaje) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(SELECT + "WHERE v.id_viaje = ?")) {
            ps.setInt(1, idViaje);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Viaje> listarTodos(Connection con) throws SQLException {
        List<Viaje> viajes = new ArrayList<>();
        String sql = SELECT + "ORDER BY v.id_viaje DESC";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                viajes.add(mapear(rs));
            }
        }
        return viajes;
    }

    @Override
    public List<Viaje> listarPorChofer(Connection con, int idChofer) throws SQLException {
        List<Viaje> viajes = new ArrayList<>();
        String sql = SELECT + "WHERE v.id_chofer = ? ORDER BY v.id_viaje DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    viajes.add(mapear(rs));
                }
            }
        }
        return viajes;
    }

    @Override
    public void actualizarEstado(Connection con, int idViaje, EstadoViaje estado) throws SQLException {
        String sql;
        switch (estado) {
            case EN_CURSO:
                sql = "UPDATE viajes SET estado = ?, fecha_inicio = NOW() WHERE id_viaje = ?";
                break;
            case FINALIZADO:
                sql = "UPDATE viajes SET estado = ?, fecha_fin = NOW() WHERE id_viaje = ?";
                break;
            default:
                sql = "UPDATE viajes SET estado = ? WHERE id_viaje = ?";
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setInt(2, idViaje);
            ps.executeUpdate();
        }
    }

    @Override
    public String validarAsignacion(Connection con, int idChofer, int idCamion) throws SQLException {
        String sql = "{ call sp_validar_asignacion(?, ?, ?) }";

        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idChofer);
            cs.setInt(2, idCamion);
            // Tercer parametro: resultado de salida
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.execute();
            return cs.getString(3);
        }
    }

    private Viaje mapear(ResultSet rs) throws SQLException {
        Chofer chofer = new Chofer();
        chofer.setIdChofer(rs.getInt("id_chofer"));
        chofer.setNombre(rs.getString("nombre_chofer"));
        chofer.setApellido(rs.getString("apellido"));
        chofer.setDni(rs.getString("dni"));

        Camion camion = new Camion();
        camion.setIdCamion(rs.getInt("id_camion"));
        camion.setMarca(rs.getString("marca"));
        camion.setModelo(rs.getString("modelo"));
        camion.setDominio(rs.getString("dominio"));

        Destino origen = new Destino(rs.getInt("id_origen"), rs.getString("nombre_origen"));
        Destino destino = new Destino(rs.getInt("id_destino"), rs.getString("nombre_destino"));

        Viaje viaje = new Viaje(chofer, camion, origen, destino);
        viaje.setIdViaje(rs.getInt("id_viaje"));
        viaje.setKm(rs.getInt("km"));
        viaje.setDias(rs.getInt("dias"));
        viaje.setTanques(rs.getInt("tanques"));
        viaje.setEstado(EstadoViaje.valueOf(rs.getString("estado")));
        viaje.setFechaCarga(JdbcUtil.aLocalDateTime(rs.getTimestamp("fecha_carga")));
        viaje.setFechaInicio(JdbcUtil.aLocalDateTime(rs.getTimestamp("fecha_inicio")));
        viaje.setFechaFin(JdbcUtil.aLocalDateTime(rs.getTimestamp("fecha_fin")));
        return viaje;
    }
}
