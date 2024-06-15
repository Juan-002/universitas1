package co.edu.poli.ces3.universitas.servlet;

import co.edu.poli.ces3.universitas.dao.Coments;
import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionMySql;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UserFurebase", value = "/comments")
public class UserFirebase extends MyServlet {
    private ConexionMySql cnn;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    public void init() {
        cnn = new ConexionMySql();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        StringBuilder payload = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null){
            payload.append(line);
        }

        Gson gson = new Gson();
        Coments newComents = gson.fromJson(payload.toString(), Coments.class);

        newComents.insertarComents();

    }

    public void destroy() {
    }

    @Override
    void saludar() {

    }
}