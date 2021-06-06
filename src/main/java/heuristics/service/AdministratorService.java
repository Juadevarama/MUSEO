/* package heuristics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Administrator;
import heuristics.model.User;
import heuristics.repository.AdministratorRepository;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Transactional(readOnly = true)
    public List<Administrator> findAllAdministrator(){
        return administratorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Administrator findAdministratorById(Integer id){
        return administratorRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void saveAdministrator(User user) throws DataAccessException{
        Administrator administrator = new Administrator();
        administrator.setUsername(user.getUsername());
        administrator.setPassword(user.getPassword());
        administrator.setName(user.getName());
        administrator.setSurnames(user.getSurnames());
        administrator.setCompany(user.getCompany());
        administrator.setEmail(user.getEmail());
        administratorRepository.save(administrator);
    }
    
} */
