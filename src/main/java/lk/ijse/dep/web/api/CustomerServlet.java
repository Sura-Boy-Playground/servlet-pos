package lk.ijse.dep.web.api;

import lk.ijse.dep.web.business.BOFactory;
import lk.ijse.dep.web.business.BOTypes;
import lk.ijse.dep.web.business.custom.CustomerBO;
import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.exception.HttpResponseException;
import lk.ijse.dep.web.exception.ResponseExceptionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns = "/api/v1/customers/*")
public class CustomerServlet extends HttpServlet {

    final Logger logger = LoggerFactory.getLogger(CustomerServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final SessionFactory sf = (SessionFactory) getServletContext().getAttribute("sf");

        try (Session session = sf.openSession()) {

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String id = req.getPathInfo().replace("/", "");

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setSession(session);
            customerBO.deleteCustomer(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final SessionFactory sf = (SessionFactory) getServletContext().getAttribute("sf");

        try (Session session = sf.openSession()) {

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String id = req.getPathInfo().replace("/", "");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO dto = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (dto.getId() != null || dto.getName().trim().isEmpty() || dto.getAddress().trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid details", null);
            }

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setSession(session);
            customerBO.updateCustomer(dto);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (JsonbException exp) {
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();

        final SessionFactory sf = (SessionFactory) getServletContext().getAttribute("sf");

        try (Session session = sf.openSession()) {
            resp.setContentType("application/json");
            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setSession(session);
            resp.getWriter().println(jsonb.toJson(customerBO.findAllCustomers()));

        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        final SessionFactory sf = (SessionFactory) getServletContext().getAttribute("sf");

        try (Session session = sf.openSession()) {
            CustomerDTO dto = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            if (dto.getId() == null || dto.getId().trim().isEmpty() || dto.getName() == null || dto.getName().trim().isEmpty() || dto.getAddress() == null || dto.getAddress().trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer details", null);
            }

            CustomerBO customerBO = BOFactory.getInstance().getBO(BOTypes.CUSTOMER);
            customerBO.setSession(session);
            customerBO.saveCustomer(dto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            resp.getWriter().println(jsonb.toJson(dto));
        } catch (SQLIntegrityConstraintViolationException exp) {
            throw new HttpResponseException(400, "Duplicate entry", exp);
        } catch (JsonbException exp) {
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
