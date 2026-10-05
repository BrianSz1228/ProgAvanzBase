package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.CamionDAO;
import negocio.modelo.dominio.Camion;
import negocio.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionDAOImpl implements CamionDAO {

    private static final String SELECT =
            "SELECT cam.id_camion, cam.marca, cam.modelo, cam.dominio, " +
            "cam.toneladas_maximas, cam.litros_tanque, cam.consumo_litros_km " +
            "FROM camiones cam ";

    @Override
    public int crear(Connection con, Camion camion) throws SQLException {
        String sql = "INSERT INTO camiones (marca, modelo, dominio, toneladas_maximas, litros_tanque, " +
                "consumo_litros_km) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, camion.getMarca());
            ps.setString(2, camion.getModelo());
            ps.setString(3, camion.getDominio());
            ps.setDouble(4, camion.getToneladasMaximas());
            ps.setDouble(5, camion.getLitrosTanque());
            ps.setDouble(6, camion.getConsumoLitrosKm());
            ps.executeUpdate();
            return JdbcUtil.obtenerIdGenerado(ps);
        }
    }

    @Override
    public void actualizar(Connection con, Camion camion) throws SQLException {
        String sql = "UPDATE camiones SET marca = ?, modelo = ?, dominio = ?, toneladas_maximas = ?, " +
                "litros_tanque = ?, consumo_litros_km = ? WHERE id_camion = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, camion.getMarca());
            ps.setString(2, camion.getModelo());
            ps.setString(3, camion.getDominio());
            ps.setDouble(4, camion.getToneladasMaximas());
            ps.setDouble(5, camion.getLitrosTanque());
            ps.setDouble(6, camion.getConsumoLitrosKm());
            ps.setInt(7, camion.getIdCamion());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Connection con, int idCamion) throws SQLException {
        String sql = "DELETE FROM camiones WHERE id_camion = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCamion);
            ps.executeUpdate();
        }
    }

    @Override
    public Camion buscarPorId(Connection con, int idCamion) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(SELECT + "WHERE cam.id_camion = ?")) {
            ps.setInt(1, idCamion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Camion> listarTodos(Connection con) throws SQLException {
        List<Camion> camiones = new ArrayList<>();
        String sql = SELECT + "ORDER BY cam.marca, cam.modelo";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                camiones.add(mapear(rs));
            }
        }
        return camiones;
    }

    @Override
    public List<Camion> listarAutorizadosPorChofer(Connection con, int idChofer) throws SQLException {
        String sql = SELECT +
                "JOIN chofer_camion cc ON cc.id_camion = cam.id_camion " +
                "WHERE cc.id_chofer = ? " +
                "ORDER BY cam.marca, cam.modelo";
        return listarPorChofer(con, sql, idChofer);
    }

    @Override
    public List<Camion> listarDisponiblesParaChofer(Connection con, int idChofer) throws SQLException {
        // Autorizados que no tienen ningun viaje sin finalizar
        String sql = SELECT +
                "JOIN chofer_camion cc ON cc.id_camion = cam.id_camion " +
                "WHERE cc.id_chofer = ? " +
                "AND NOT EXISTS (SELECT 1 FROM viajes v " +
                "WHERE v.id_camion = cam.id_camion AND v.estado <> 'FINALIZADO') " +
                "ORDER BY cam.marca, cam.modelo";
        return listarPorChofer(con, sql, idChofer);
    }

    private List<Camion> listarPorChofer(Connection con, String sql, int idChofer) throws SQLException {
        List<Camion> camiones = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    camiones.add(mapear(rs));
                }
            }
        }
        return camiones;
    }

    private Camion mapear(ResultSet rs) throws SQLException {
        return new Camion(
                rs.getInt("id_camion"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("dominio"),
                rs.getDouble("toneladas_maximas"),
                rs.getDouble("litros_tanque"),
                rs.getDouble("consumo_litros_km"));
    }
}
