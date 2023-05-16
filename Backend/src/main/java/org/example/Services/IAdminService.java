package org.example.Services;

import org.example.Model.Admin;

import java.util.List;

public interface IAdminService {
;
    Admin getAdmin(int id);
    Admin updateAdmin(int id, String email, String password );
}
