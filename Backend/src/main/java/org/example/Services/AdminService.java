package org.example.Services;

import org.example.Model.Admin;
import org.example.Repository.AdminRepository;
import org.example.Repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService{

    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {this.adminRepository = adminRepository;}

    public Admin getAdmin(int id) {return adminRepository.findById(id).get();}

    public Admin updateAdmin(int id, String email, String password){
        if (adminRepository.existsById(id) && email != null && password != null) {
            Admin admin = adminRepository.findById(id).get();
            admin.setEmail(email);
            admin.setPassword(password);
            adminRepository.saveAndFlush(admin);
            return admin;
        } else {return null;}
    }

}
