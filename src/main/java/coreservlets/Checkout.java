package coreservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by gerben on 29-4-15.
 */
public class Checkout extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ShoppingCart cart;
        Random random = new Random();
        double price = 0;
        synchronized (session){
            cart = (ShoppingCart)session.getAttribute("shoppingCart");
            // New visitors get a fresh shopping cart.
            // Previous visitors keep using their existing cart.
            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute("shoppingCart", cart);
            }



            for(ItemOrder order: cart.getItemsOrdered()){
                price += order.getTotalCost();
            }

            //head
            out.println(StandardHTML.HEAD_START);
            out.println(StandardHTML.HEAD_END);

            out.println("<body>");
            out.println("<div class=\"title\">\n" +
                    "        Online Book Store\n" +
                    "    </div>");
            out.println("<div class=\"textDiv\">");
            out.println("Checkout done!");
            out.println(String.format("<p> Total price: %2f</p>", price));
            out.println("<p>Expected delivery time is "+ (random.nextInt(7)+1 )+" day(s).</p>");
            out.println("</div>");
            out.println("<a href = \"/\">" +
                    "<div class=\"button\"> Back to the site </div>" +
                    "</a>");
            out.println("</body> </html>");

            session.setAttribute("shoppingCart", null);
            out.close();


        }
    }
}
