import  RoomLogic.Room;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SimulatorServlet extends HttpServlet{


    public void init() throws ServletException {
        // Do required initialization
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Servlet called" + request.getContentLength());
        response.setContentType("application/json;charset=UTF-8");

        System.out.println(request.getParameter("roomSize"));
        //System.out.println(request.getParameter("personSet"));

        Room room = new Room(Integer.parseInt(request.getParameter("roomSize")), request.getParameter("infectedPersonSet"), request.getParameter("nonInfectedPersonSet"));
        request.setAttribute("originalMatrix", room.toString());
        request.setAttribute("chanceMatrix", room.chanceMatrix(Integer.valueOf(request.getParameter("timeInterval"))).toString());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
        // do nothing.
    }
}