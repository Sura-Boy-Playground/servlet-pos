package lk.ijse.dep.web.api;

import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.exception.HttpResponseException;
import lk.ijse.dep.web.exception.ResponseExceptionUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/customers/*")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        }catch (Throwable t){
            ResponseExceptionUtil.handle(t,resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()){
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String id = req.getPathInfo().replace("/", "");

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setString(1, id );

            if (pstm.executeUpdate()> 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(404, "There is no such customer exists", null);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()){
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String id = req.getPathInfo().replace("/", "");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO dto = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (dto.getId() != null || dto.getName().trim().isEmpty() || dto.getAddress().trim().isEmpty()){
                throw new HttpResponseException(400, "Invalid details", null);
            }

            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET `name`=?, address=? WHERE id=?");
            pstm.setString(1, dto.getName() );
            pstm.setString(2, dto.getAddress() );
            pstm.setString(3, id );

            if (pstm.executeUpdate()> 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(500, "Failed to update the customer", null);
            }

        }catch (JsonbException exp){
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer");
            List<CustomerDTO> customers = new ArrayList<>();

            while (rst.next()) {
                customers.add(new CustomerDTO(rst.getString("id"),
                        rst.getString("name"),
                        rst.getString("address")));
            }

            resp.setContentType("application/json");
            resp.getWriter().println(jsonb.toJson(customers));

        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Jsonb jsonb = JsonbBuilder.create();
        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {
            CustomerDTO dto = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (dto.getId() == null || dto.getId().trim().isEmpty() || dto.getName() == null || dto.getName().trim().isEmpty() || dto.getAddress()== null || dto.getAddress().trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer details" , null);
            }

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
            pstm.setString(1, dto.getId());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getAddress());

            if (pstm.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().println(jsonb.toJson(dto));
            } else {
                throw new HttpResponseException(500, "Failed to save the customer", null);
            }
        }catch (SQLIntegrityConstraintViolationException exp){
            throw new HttpResponseException(400, "Duplicate entry", exp);
        } catch (JsonbException exp) {
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
