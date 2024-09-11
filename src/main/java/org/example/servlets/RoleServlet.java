package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
import org.example.mappers.RoleMapperImpl;
import org.example.repositories.RoleRepository;
import org.example.services.RoleServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "RoleServlet", urlPatterns = "/roles/*")
public class RoleServlet extends HttpServlet {
    private final transient RoleMapper roleMapper = new RoleMapperImpl();
    private final transient RoleRepository roleRepository = new RoleRepository();
    private final transient RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            RoleDTO newRoleDTO = gson.fromJson(req.getReader(), RoleDTO.class);
            Role newRole = roleMapper.roleDTOtoRole(newRoleDTO);
            newRole = roleService.createRole(newRole.getRoleName(), newRole.getDescription());
            RoleDTO responseRoleDTO = roleMapper.roleToRoleDTO(newRole);

            out.print(gson.toJson(responseRoleDTO));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();

        if (path == null) {
            try (PrintWriter out = resp.getWriter()) {
                ArrayList<Role> roles = roleService.getAllRoles();
                ArrayList<RoleDTO> rolesDTO = new ArrayList<>();

                for (Role role : roles) {
                    rolesDTO.add(roleMapper.roleToRoleDTO(role));
                }

                Gson gson = new Gson();
                out.println(gson.toJson(rolesDTO));
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PrintWriter out = resp.getWriter()) {
                int id = Integer.parseInt(path.split("/")[1]);
                Role role = roleService.getRoleById(id);
                RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);
                Gson gson = new Gson();

                out.println(gson.toJson(roleDTO));
                out.flush();
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter();) {
            String path = req.getPathInfo();
            int id = Integer.parseInt(path.split("/")[1]);
            Gson gson = new Gson();
            RoleDTO roleDTO = gson.fromJson(req.getReader(), RoleDTO.class);
            Role updatedRole = roleService.updateRoleById(id, roleDTO.getRoleName(), roleDTO.getDescription());
            roleDTO = roleMapper.roleToRoleDTO(updatedRole);

            out.println(gson.toJson(roleDTO));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String path = req.getPathInfo();
            int id = Integer.parseInt(path.split("/")[1]);
            roleService.deleteRoleById(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
