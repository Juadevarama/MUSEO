package heuristics.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import heuristics.model.User;
import heuristics.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Integer id){
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void saveUser(User user) throws DataAccessException{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
      
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }
    
    // El rol es la columna discriminator de user que determina si es administrator o critic.
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(String role){
        List<GrantedAuthority> res = new ArrayList<>();
        res.add(new SimpleGrantedAuthority(role));
        return res;
    }

}
