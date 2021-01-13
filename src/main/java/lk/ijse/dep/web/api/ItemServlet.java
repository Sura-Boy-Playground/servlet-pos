package lk.ijse.dep.web.api;

import lk.ijse.dep.web.dto.CustomerDTO;
import lk.ijse.dep.web.dto.ItemDTO;
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

@WebServlet(urlPatterns = "/api/v1/items/*")
public class ItemServlet extends HttpServlet {

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
                throw new HttpResponseException(400, "Invalid item code", null);
            }

            String code = req.getPathInfo().replace("/", "");

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pstm.setString(1, code );

            if (pstm.executeUpdate()> 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(404, "There is no such item exists", null);
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
                throw new HttpResponseException(400, "Invalid item code", null);
            }

            String code = req.getPathInfo().replace("/", "");
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO dto = jsonb.fromJson(req.getReader(), ItemDTO.class);

            if (dto.getCode() != null || dto.getDescription() == null || dto.getDescription().trim().isEmpty() || dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() == 0.0 || dto.getQtyOnHand() == null){
                throw new HttpResponseException(400, "Invalid details", null);
            }

            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, unit_price=?, qty_on_hand=? WHERE code=?");
            pstm.setString(1, dto.getDescription() );
            pstm.setBigDecimal(2, dto.getUnitPrice() );
            pstm.setInt(3, dto.getQtyOnHand() );
            pstm.setString(4, code );

            if (pstm.executeUpdate()> 0){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                throw new HttpResponseException(500, "Failed to update the item", null);
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
            ResultSet rst = stm.executeQuery("SELECT * FROM item");
            List<ItemDTO> items = new ArrayList<>();

            while (rst.next()) {
                items.add(new ItemDTO(rst.getString("code"),
                        rst.getString("description"),
                        rst.getBigDecimal("unitPrice"),
                        rst.getInt("qtyOnHand")));
            }

            resp.setContentType("application/json");
            resp.getWriter().println(jsonb.toJson(items));

        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Jsonb jsonb = JsonbBuilder.create();
        final BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");

        try (Connection connection = cp.getConnection()) {
            ItemDTO dto = jsonb.fromJson(req.getReader(), ItemDTO.class);

            if (dto.getCode() == null || dto.getCode().trim().isEmpty() || dto.getDescription() == null || dto.getDescription().trim().isEmpty() || dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() == 0.0 || dto.getQtyOnHand() == null) {
                throw new HttpResponseException(400, "Invalid item details" , null);
            }

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pstm.setString(1, dto.getCode());
            pstm.setString(2, dto.getDescription());
            pstm.setBigDecimal(3, dto.getUnitPrice());
            pstm.setInt(4, dto.getQtyOnHand());

            if (pstm.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().println(jsonb.toJson(dto));
            } else {
                throw new HttpResponseException(500, "Failed to save the item", null);
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
