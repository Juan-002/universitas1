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

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends MyServlet {
    private ConexionMySql cnn;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    public void init() {
        cnn = new ConexionMySql();
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>PUT</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        StringBuilder payload = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null){
            payload.append(line);
        }

        Gson gson = new Gson();
        User newUser = gson.fromJson(payload.toString(), User.class);

        try {
            PrintWriter out = response.getWriter();
            User usuario = cnn.getUserByEmail(newUser.getEmail());
            if (usuario == null) {
                out.print(gson.toJson(cnn.insertUser(newUser)));
            } else {
                out.print(gson.toJson(usuario));
            }
            out.flush();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //       Usuario existingUser = userEmail(newUser.getEmail());
//        if (existingUser != null) {
//            sendResponse(response, existingUser, HttpServletResponse.SC_OK);
//        }else {
//
//            Usuario insertedUser = userInserted(newUser);
//            sendResponse(response, insertedUser, HttpServletResponse.SC_CREATED);
//        }

        //response.setContentType("text/html");

        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>"+newUser.getEmail()+"</h1>");
//        out.println("</body></html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            if(request.getParameter("id") == null) {
                ArrayList<User> listUsers = (ArrayList<User>) cnn.getUsers();
                out.print(gson.toJson(listUsers));
            }else{
                User user = cnn.getUser(request.getParameter("id"));
                out.print(gson.toJson(user));
            }
            out.flush();
        } catch (SQLException e) {
            System.out.println("error");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.doPatch(req, resp);
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>PUT</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    public void destroy() {
    }

    @Override
    void saludar() {

    }
}