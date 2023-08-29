package com.tpe.domain;


import com.tpe.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "tbl_role")
public class Role{


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

// Burda bu classin  Enum type ile iliskiyi kuruyoruz
@Enumerated(EnumType.STRING) //Enum type ile bir classi iliskilendirmek istiyorsak bu ann. ihtiyacimiz var. EnumType.STRING gelen rolen string olarak almasini söylemektir.
@Column(length = 30, nullable = false)
private UserRole name;

    @Override
    public String toString() {
        return "Role [name =" + name + " ]";
    }
}

// Bu class'i olusturmamizin nedeni biz rollerimizi direkt User class ile iliskilendirmek istemiyoruz
// bu iliskiyi Rolle classi araciligi ile yapacagiz ve Rollerde bir degisiklik yaparsak User'larimiz etkilenmemis olacak.
// Bu class'imiz Enum type ile arada köprü olan class; yani Enum degisirse yusur direkt etkilenmeyecek.
