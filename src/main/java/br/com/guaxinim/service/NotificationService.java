package br.com.guaxinim.service;

import javax.enterprise.event.Observes;

public class NotificationService {

    public void notificaGestor(@Observes Integer event) {
        System.out.println("Usuario inserido: " + event);
    }
}
