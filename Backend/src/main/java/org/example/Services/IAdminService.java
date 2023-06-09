package org.example.Services;

import org.example.Model.Admin;

import java.util.Optional;

public interface IAdminService {
;
    Admin getAdmin(int id);
    Admin updateAdmin(int id, String email, String password );
    public Admin loadUserByUsername(String email);
}
