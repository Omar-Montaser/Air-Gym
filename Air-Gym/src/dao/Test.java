package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.SqlServerConnect;
import model.accounts.Member;
import model.accounts.Trainer;
import model.gym.members.Session;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        try{
        conn = SqlServerConnect.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}