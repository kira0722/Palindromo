package com.example.palindromoces3.Servlets;

import com.example.palindromoces3.Modem.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "studentServlet", value = "/student")

public class StudentServlet extends MyServlet {
    private String message;
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private ArrayList<Student> students;

    public void init() {

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();


        students = new ArrayList<>();
        Student student1 = new Student();
        student1.id = 10;
        student1.setNombre("kira");
        student1.setCedula("1000193193");
        students.add(student1);

        for (int i = 0; i < students.size(); i = i+1){
            System.out.println(students.get(i));
        }

        message = "Hello Poli!!!";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("text/html");
        JsonObject body = this.getParamsFromPost(req);

        Student std = new Student(
                body.get("id").getAsInt(),
                body.get("cedula").getAsString(),
                body.get("nombre").getAsString()
        );

        this.students.add(std);
        out.println(gson.toJson(std));
        out.flush();

        out.println("<b> Hello from  post method </b>");
        out.flush();


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Hello
        String studentId = request.getParameter("studentId");
        PrintWriter out = response.getWriter();
        if (studentId == null){
            out.println(gson.toJson(students));

        } else{
            Student studentSearch = null;
            for (Student s: students){
                if (s.getId() == Integer.parseInt(studentId)){
                    studentSearch = s;
                    break;
                }
            }
            out.println(gson.toJson(studentSearch));
        }

        /*PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
         */
    }


    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(request);

        int studentId = body.get("id").getAsInt();

        Student studentToUpdate = null;
        for (Student student : students) {
            if (student.getId() == studentId) {
                studentToUpdate = student;
                break;
            }
        }

        if (studentToUpdate != null) {
            // Actualiza los campos del estudiante
            studentToUpdate.setNombre(body.get("nombre").getAsString());
            studentToUpdate.setCedula(body.get("cedula").getAsString());

            PrintWriter out = response.getWriter();
            out.println(gson.toJson(studentToUpdate));

            System.out.println("El estudiante con el id: " + studentId + " ha sido actualizado");

        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String studentId = request.getParameter("studentId");
        if (studentId != null) {
            int idToDelete = Integer.parseInt(studentId);
            Student studentToDelete = null;

            for (Student student : students) {
                if (student.getId() == idToDelete) {
                    studentToDelete = student;
                    break;
                }
            }

            if (studentToDelete != null) {
                students.remove(studentToDelete);
                PrintWriter out = response.getWriter();
                out.println("Student with ID " + idToDelete + " has been deleted.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        System.out.println("El estudiante con el id: " + studentId + " ha sido Eliminado");
    }


    public void destroy() {
    }
}