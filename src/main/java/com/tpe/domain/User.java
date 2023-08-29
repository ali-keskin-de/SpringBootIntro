package com.tpe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user") // Jpql yazarkan class ismini kullanacagiz natevi Sql yazarken databaase'de verdigim tablo ismini kullanmaliyim
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 25, nullable = false)
    private String name;

    @Column (length = 25, nullable = false)
    private String lastName;


    @Column (length = 255, nullable = false, unique = true)
    private String userName;

    @Column (length = 25, nullable = false)
    private String password;

    // Normalde ücüncü bir tablo olusacaktir ancak ben bu olusacak tablonun isimleri rast gele olmasindiye  @JoinTable koydum
    @JoinTable(name = "tbl_user_role", //Tablo ismi
            joinColumns = @JoinColumn(name = "user_id"), // Header isimleri
            inverseJoinColumns = @JoinColumn(name = "role_id"))// karsi class'tan gelecek yapiyi tutan header. Header isimleri
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    // 1) Set'ler tekrarsiz(unique) elemanlari depolamak icin kullanirlar, List olursa aymi rollü bir kullaniciya birden fazla atayabilirsiz.
   //@OneToOne bir User'a bir roll atayabilirsin yani bir User'a Admin atarsan baska bir user'a admin atayamazsin.--> olmaz
   // @ManyToOne Her User'a bir Role atayabilirsiniz o zaman neden Set yaptik anlamsiz oldu-->burada olamaz
    // @ManyToMany de FetchType.LAZY defaulta budur bu durumda get(User) dersek bize rolleri getirmez get(UserRole) dersem bu defa roller gelecektir.
    // Biz get(User) dedigimizde rollerde beraber gelsin istiyorsak FetchType EAGER cekmeliyiz.


    // Burada Student class'i ile iliskilendirdik.
    @JsonIgnore
    @OneToOne(mappedBy = "user") // rollün eger student tarafinda mapplen sin istiyoursak-->mappedBy = "user" ve adi da user.
    private Student student;

}


