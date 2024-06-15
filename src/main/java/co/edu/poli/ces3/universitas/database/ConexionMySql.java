package co.edu.poli.ces3.universitas.database;

import co.edu.poli.ces3.universitas.dao.User;

import java.sql.*;
import java.util.*;

public class ConexionMySql {

    private String user;
    private String password;
    private int port;
    private String host;
    private String nameDatabase;
    private Connection cnn;

    public Connection getCnn(){
        return this.cnn;
    }
    public ConexionMySql(){
        this.user = "root";
        password = "";
        port = 3306;
        host = "localhost";
        nameDatabase = "ces3";
    }

    protected void createConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnn = DriverManager.getConnection(
                    "jdbc:mysql://" +host+":"+port+"/"+nameDatabase, user, password
            );
            System.out.println("Successful connection");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("An error occurred during the connection");
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try {
            createConexion();
            Statement stmt = cnn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()){
                list.add(new User(result.getInt("id"),
                        result.getString("name"),
                        result.getString("lastName"),
                        result.getString("mail"),
                        result.getString("password"),
                        result.getDate("createdAt"),
                        result.getDate("updatedAt"),
                        result.getDate("deletedAt")
                ));
            }
            stmt.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(cnn != null)
                cnn.close();
        }
    }

    public static void main(String[] args) {
        ConexionMySql conection = new ConexionMySql();
        try {
            conection.getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            createConexion();
            PreparedStatement stm = cnn.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(id));
            ResultSet result = stm.executeQuery();
            if(result.next())
            return new User(result.getString("name"), result.getString("lastName"), result.getString("mail"));
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            if (cnn != null)
                cnn.close();
        }
        return null;
    }


    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE mail = ?";
        createConexion();
        try {
            PreparedStatement smt = cnn.prepareStatement(sql);
            smt.setString(1, email);
            ResultSet resultSet = smt.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getString("mail"));
            } else {
                return null; // Si no se encuentra ningún usuario con el correo electrónico proporcionado, devuelvo null
            }
        } catch (SQLException error) {
            error.printStackTrace();
            return null;
        } finally {
            // Cierro la conexión aquí en el bloque finally para asegurarme de que siempre se cierre, incluso si ocurre una excepción
            if (cnn != null) {
                cnn.close();
            }
        }
    }
    public User insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, lastName, mail, password) VALUES (?, ?, ?, ?)";
        createConexion();
        try {
            PreparedStatement smt = cnn.prepareStatement(sql);
            smt.setString(1, user.getName());
            smt.setString(2, user.getLastName());
            smt.setString(3, user.getEmail());
            smt.setString(4, user.getPassword());
            smt.executeUpdate();
            // Obtener la clave generada (id) del usuario insertado
            ResultSet generatedKeys = smt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Error al obtener la clave generada para el usuario");
            }

            return user;
        }catch (SQLException error) {
            error.printStackTrace();
            return null;
        }
    }




}
