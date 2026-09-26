package edu.usal.hilos;

import java.util.Random;

public class HiloT extends Thread{

    int random;

    public HiloT(){
        this.random = (int) (Math.random()*10);
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println("Valor " + i + "\tTH : " + super.getName());
            if(i == this.random){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}
