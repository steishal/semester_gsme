package dao;


import entity.Player;
import utils.ConnectionProvider;
import utils.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao {
//    private final ConnectionProvider connectionProvider;
//
//    public PlayerDao(ConnectionProvider connectionProvider) {
//        this.connectionProvider = connectionProvider;
//    }
//
//    public Player getUserByUsername(String username) throws DbException {
//        String sql = "SELECT * FROM Users WHERE name = ?";
//        try (Connection connection = connectionProvider.getConnection()) {
//            connection.setAutoCommit(false);
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, username);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        connection.commit();
//                        return mapResultSetToUser(resultSet);
//                    }
//                }
//            } catch (SQLException e) {
//                connection.rollback();
//                throw new DbException("Error while fetching user by username", e);
//            }
//        } catch (SQLException e) {
//            throw new DbException("Error while managing connection", e);
//        }
//        return null;
//    }
//
//    public Player getUserByEmail(String email) throws DbException {
//        String sql = "SELECT * FROM Users WHERE email = ?";
//        try (Connection connection = connectionProvider.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, email);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return mapResultSetToUser(resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            throw new DbException("Error while fetching user by email", e);
//        }
//        return null;
//    }
//
//    public void saveUser(Player user) throws DbException {
//        String sql = "INSERT INTO Users (name, vk_link, telegram_link, email, phone_number, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection connection = connectionProvider.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, user.getUsername());
//            preparedStatement.setString(2, user.getVkLink());
//            preparedStatement.setString(3, user.getTgLink());
//            preparedStatement.setString(4, user.getEmail());
//            preparedStatement.setString(5, user.getPhoneNumber());
//            preparedStatement.setString(6, user.getPassword());
//            preparedStatement.setString(7, user.getRole());
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DbException("Error saving user", e);
//        }
//    }
//
//    private Player mapResultSetToUser(ResultSet resultSet) throws SQLException {
//        User user = new User();
//        user.setId(resultSet.getInt("user_id"));
//        user.setUsername(resultSet.getString("name"));
//        user.setPassword(resultSet.getString("password_hash"));
//        user.setVkLink(resultSet.getString("vk_link"));
//        user.setTgLink(resultSet.getString("telegram_link"));
//        user.setEmail(resultSet.getString("email"));
//        user.setPhoneNumber(resultSet.getString("phone_number"));
//        user.setRole(resultSet.getString("role"));
//        return user;
//    }
//
//    public Player getUserById(int id) throws DbException {
//        String sql = "SELECT * FROM Users WHERE user_id = ?";
//        try (Connection connection = connectionProvider.getConnection()) {
//            connection.setAutoCommit(false);
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setInt(1, id);
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    if (resultSet.next()) {
//                        connection.commit();
//                        return mapResultSetToUser(resultSet);
//                    }
//                }
//            } catch (SQLException e) {
//                connection.rollback();
//                throw new DbException("Error fetching user by ID", e);
//            }
//        } catch (SQLException e) {
//            throw new DbException("Error while managing connection", e);
//        }
//        return null;
//    }
//
//
//
//
//    public void updateUser(Player user) throws DbException {
//        String sql = "UPDATE Users SET name = ?, vk_link = ?, telegram_link = ?, email = ?, phone_number = ? WHERE user_id = ?";
//
//        try (Connection connection = connectionProvider.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, user.getUsername());
//            preparedStatement.setString(2, user.getVkLink());
//            preparedStatement.setString(3, user.getTgLink());
//            preparedStatement.setString(4, user.getEmail());
//            preparedStatement.setString(5, user.getPhoneNumber());
//            preparedStatement.setInt(6, user.getId());
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DbException("Ошибка обновления пользователя", e);
//        }
//    }

}
