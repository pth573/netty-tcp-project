package com.example.demonetty.service.serviceImpl;
import com.example.demonetty.entity.T1;
import com.example.demonetty.repository.T1Repository;
import com.example.demonetty.service.T1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class T1ServiceImpl implements T1Service {
    @Autowired
    T1Repository t1Repository;
    @Override
    public void save(T1 t1) {
        t1Repository.save(t1);
    }
}
