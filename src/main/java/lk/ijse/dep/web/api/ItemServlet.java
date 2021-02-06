package lk.ijse.dep.web.api;

import lk.ijse.dep.web.AppInitializer;
import lk.ijse.dep.web.business.custom.ItemBO;
import lk.ijse.dep.web.dto.ItemDTO;
import lk.ijse.dep.web.exception.HttpResponseException;
import lk.ijse.dep.web.exception.ResponseExceptionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

@WebServlet(urlPatterns = "/api/v1/items/*")
public class ItemServlet extends HttpServlet {

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

        try{

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid item code", null);
            }

            String code = req.getPathInfo().replace("/", "");

            ItemBO itemBO = AppInitializer.getContext().getBean(ItemBO.class);
            itemBO.deleteItem(code);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPathInfo() == null || req.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid item code", null);
            }

            String code = req.getPathInfo().replace("/", "");
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO dto = jsonb.fromJson(req.getReader(), ItemDTO.class);

            if (dto.getCode() != null || dto.getDescription() == null || dto.getDescription().trim().isEmpty() || dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() == 0.0 || dto.getQtyOnHand() == null) {
                throw new HttpResponseException(400, "Invalid details", null);
            }

            ItemBO itemBO = AppInitializer.getContext().getBean(ItemBO.class);
            dto.setCode(code);
            itemBO.updateItem(dto);
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

        try {
            resp.setContentType("application/json");
            ItemBO itemBO = AppInitializer.getContext().getBean(ItemBO.class);
            resp.getWriter().println(jsonb.toJson(itemBO.findAllItems()));

        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();

        try {
            ItemDTO dto = jsonb.fromJson(req.getReader(), ItemDTO.class);

            if (dto.getCode() == null || dto.getCode().trim().isEmpty() || dto.getDescription() == null || dto.getDescription().trim().isEmpty() || dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() == 0.0 || dto.getQtyOnHand() == null) {
                throw new HttpResponseException(400, "Invalid item details", null);
            }

            ItemBO itemBO = AppInitializer.getContext().getBean(ItemBO.class);
            itemBO.saveItem(dto);
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
