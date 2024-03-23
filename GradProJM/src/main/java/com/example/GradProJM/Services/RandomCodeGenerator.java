package com.example.GradProJM.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomCodeGenerator {

    private int code;
    public String GenerateCode(){
        Random r=new Random();
        String g="";
        for(int i=0;i<6;i++) {
            int x= r.nextInt(0, 9);
            g+=x;
        }
        return g;
    }


}
