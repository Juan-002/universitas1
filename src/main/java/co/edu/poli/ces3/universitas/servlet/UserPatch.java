package co.edu.poli.ces3.universitas.servlet;

import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionMySql;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UserPatch", value = "/enrollments")
public class UserPatch extends MyServlet {
    private ConexionMySql cnn;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    public void init() {
        cnn = new ConexionMySql();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");

      String idUser = req.getParameter("idUser");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>"+idUser+"</h1>");
        out.println("</body></html>");


    }

    public void destroy() {
    }

    @Override
    void saludar() {

    }
}