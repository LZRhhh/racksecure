package com.lichee.racksecure;


import com.lichee.racksecure.dao.RackDAO;
import com.lichee.racksecure.dao.UserDAO;
import com.lichee.racksecure.pojo.Rack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRackTest {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RackDAO rackDAO;

    @Test
    public void saveRack() {
        Rack rack = new Rack();
//        rack.setId(2);
        rackDAO.save(rack);
    }

//    @Test
//    public void saveUser(){
//        User user = new User();
////        user.setId(1);
//        user.setUsername("Lichee");
//        user.setPassword("D:/resources");
//        Rack rack = rackRepository.findById(1).get();
//        Set<Rack> racks = new HashSet<>();
//        racks.add(rack);
//        user.setRacks(racks);
//        userDAO.save(user);
//    }

    @Test
    public void deleteUser() {

        userDAO.deleteAll();
    }
}
