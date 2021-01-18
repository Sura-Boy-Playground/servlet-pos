package lk.ijse.dep.web.api;

import lk.ijse.dep.web.business.BOFactory;
import lk.ijse.dep.web.business.BOTypes;
import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.exception.HttpResponseException;
import lk.ijse.dep.web.exception.ResponseExceptionUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@WebServlet(urlPatterns = "/api/v1/customers/*")
public class CustomerServlet extends HttpServlet {

    final Logger logger = LoggerFactory.getLogger(CustomerServlet.class);

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

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setConnection(connection);
            if (customerBO.deleteCustomer(id)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(404, "There is no such customer exists", null);
            }

        } catch (Exception e) {
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

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setConnection(connection);
            if (customerBO.updateCustomer(dto)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(500, "Failed to update the customer", null);
            }

        }catch (JsonbException exp){
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();

        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {
            resp.setContentType("application/json");
            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setConnection(connection);
            resp.getWriter().println(jsonb.toJson(customerBO.findAllCustomers()));

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

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setConnection(connection);
            if (customerBO.saveCustomer(dto)) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
