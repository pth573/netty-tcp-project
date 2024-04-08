package com.example.demonetty.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t1")
public class T1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_time")
    private String dateTime;
    @Column(name = "type_equip")
    private String typeEquip;
    @Column(name = "version")
    private String version;
    @Column(name = "serial")
    private String serial;
    @Column(name = "type")
    private String type;
    @Column(name = "sim_num")
    private String simNum;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "password")
    private String password;
    @Column(name = "reason_restart")
    private String reasonRestart;
    @Column(name = "sequence")
    private String sequence;
    @Column(name = "receive_message")
    private String receiveMessage;
}
