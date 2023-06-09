package org.example.Services;

import org.example.Model.Admin;
import org.example.Repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminService implements IAdminService{

    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {this.adminRepository = adminRepository;}

    public Admin getAdmin(int id) {return adminRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Admin with id " + id + " does not exist"));}

    public Admin loadUserByUsername(String email) {

        Admin admin = adminRepository.findAll()
                .stream()
                .filter(r -> r.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        return admin;
    }

    public Admin updateAdmin(int id, String email, String password){
        if (adminRepository.existsById(id) && email != null && password != null) {
            Admin admin = adminRepository.findById(id).get();
            admin.setEmail(email);
            admin.setPassword(password);
            adminRepository.saveAndFlush(admin);
            return admin;
        } else {  throw new NotFoundException("Admin with id " + id + " does not exist");}
    }

}
