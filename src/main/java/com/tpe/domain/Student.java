package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity // bu classi database persist etmek icin kullanilir, bur classin üzerine bu annotation koydugumuzda mutlaka bir unique fiel koymaliyiz
public class Student {

    @Id // annotation'i unique ligi saglar.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id'ler otomatik olarak generat edilir, "IDENTITY" id'nin birer birer atarak generate edilmesini saglar.
    @Setter(AccessLevel.NONE) // böylelikle id set'lenemez yapiyor.
    private Long id; // int id :default:0
    // Neden Long non-primitive bir data typ secildi?
    // int gibi bir primitive bir data secseydik defaulta
    // örnegim int 0 alirdi set'lenmediginde null yerine bir deger almasindan dolayi non-primitive bir data type seceriz.
    // non-primitive datalarin default degeri yoktur.


    @NotNull(message = "first name can not be null")
    @NotBlank(message = "first name can not be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 25)
    private /*final*/ String name;

    @Column(nullable = false, length = 25)
    private /*final*/ String lastName;

    private /*final*/ Integer grade;

    @Email(message = "Provide valid email") // biz bu annotationla emailin gecerli bir email olmasini kontrol edebilir.
    @Column(nullable = false, length = 25,unique = true)
    private /*final*/ String email;

    private /*final*/ String phoneNumber;

    @Setter(AccessLevel.NONE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey") // LocalDateTime kullanici icin karmasik bir yapi tutar bir bunu bu ann. araciligi ile anlamli bir yapiya ceviririz DB kayitta bir degisiklik yapmaz
    private LocalDateTime createDate = LocalDateTime.now(); // herhangi bir ögrenci olusturuldugu ani tutacak


    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // user olusturunca bizim Role classindakimapping islemi olmus oldu.






     // Entity class'imizda degistirilmesini istemedigimiz field'lari setlemeyiz böylelikle degistirilmesinin önüne gecmis oluruz.
    // Olusturulan parametreli method'da degismesini istemedigimiz degerler parametre olarak verilmez.

    //Lombok kütüphanesi
    //Lombok kütüphanesi boilerpläte kodlarin yazilmasinin önüne gecmemizi saglar.
    /*
    @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   bu annotationlar class düzeyde kullanildiklari gibi feild ve method düzeydede kullanilabilir. Ancak clean kod prensiplerinden dolayi kullanmamak gerek

     */
    // Ancak buda bir problem var ben yukardaki Lombok'tan gelen getter setter gibi yapilari kullandigimizda,
    // setter'larini yapmak istemedigim fieldlar var olari nasil cözeriz id ve createDate gibi.
    // Burda setterlari field ve method düzeyde yapabiliriz. Ancak bu defa da kodumuz clean olmaz.
    // @Setter(AccessLevel.NONE) // böylelikle id set'lenemez yapiyor.
    // Setlemek istemedigimiz field'in üstüne bunu yazdigimizda class level'da dahi setter yapsak bu field setlenemez.
    // Halen bir problemimiz var biz id'e @AllArgsConstructor Annot. yaridimiyla id ye ullasasbiliyoruz.
    // Bunu ellimizle constructor olusturarak önleyebiliriz ancak bunun daha kolayi var
    // @RequiredArgsConstructor ekleyerek classimiza setlemek istedigimiz field'lar ile bir constructor olusturur.
    //  RequiredArgsConstructor bize diyorki sen constructor da olmasini istedigin fieldlari final yap ben sana bu fieldlardan bir constructor olusturayim.

    // Validation kütüphanesi
    // Bu kütüphane field'larimizi valide etmeye yardimci oluyor
    // @NotNull(message = "first name can not be null") örnegim bir field'in üstüne bu validationi koyarsak bu artik null deger almaz
    // ve null birakirsa clienter message'da yazdigimiz mesaji verir.
    // @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long")
    // --> '${validatedValue}' girilen degeri tekrar kullaniciya mesaj icerisinde göstermek istersek.
    // @Column kullaniyoruz cünkü costumeiz etmek icin kullaniriz.
    //@Column(nullable = false, length = 25)  biz burdada nullable= false yaparak bos birakilmasinin önüne gecebiliyoruz!!!
    // o zaman neden validation annotationlari ilede kontrol yapiliyor--> cünkü column ile kontroller database'de yapilir
    // validation ile yapilan kontroller controller'da yapilir daha gelen istek uygun degilse service gecmede controller'da döner;
    // buda hem güvenlik acisinda hemde app hizli calismasi acisindan olumlu etki yapar.
    // o zaman neden column ile kontrol yapiliyor ?
    // controller'da yapilan kontrollar yeterli degildir cünkü applicationimizin bussinies logic katmani olan service katmaninda basi degerler istenmeden null cekilebilir
    // olusabilecek bu tarz hatalri önlemek icin column ilede kontroller database gitmeden yapilir.
    // @JsonFormat database deki header degil sadece client'a gidecek datayi formatlamis oluyor.









    /* chatqpt alindi
    `javax.validation` paketi, Java Bean Validation (JSR 380) spesifikasyonuna dayalı olarak geliştirilen bir kütüphanedir ve nesnelerin doğrulama (validation) işlemlerini yönetmek için kullanılır. Bu kütüphanenin içerdiği bazı temel annotationlar şunlardır:

1. `@NotNull`: Bir alanın null olmamasını gerektirir.
2. `@NotEmpty`: String, koleksiyon veya dizi gibi nesnelerin boş olmamasını gerektirir.
3. `@NotBlank`: String değerlerin boş olmamasını ve boşluk karakterlerinden oluşmamasını gerektirir.
4. `@Min`: Bir sayısal alanın belirli bir değerin üstünde olmasını gerektirir.
5. `@Max`: Bir sayısal alanın belirli bir değerin altında olmasını gerektirir.
6. `@Size`: Bir koleksiyonun, dizi veya stringin belirli bir boyutta olmasını sağlar.
7. `@Pattern`: Belirli bir deseni (regex) karşılayan string değerleri gerektirir.
8. `@Email`: Geçerli bir e-posta adresi formatını kontrol eder.
9. `@AssertTrue`: Belirli bir koşulun doğru olmasını gerektirir.
10. `@AssertFalse`: Belirli bir koşulun yanlış olmasını gerektirir.
11. `@Digits`: Sayısal bir alanın tam sayı ve ondalık kısmını kontrol eder.
12. `@Future` ve `@FutureOrPresent`: Belirli bir tarihin gelecekte olmasını gerektirir.
13. `@Past` ve `@PastOrPresent`: Belirli bir tarihin geçmişte olmasını gerektirir.
@Null: Bir alanın null olmasını gerektirir.
@DecimalMin: Sayısal bir alanın belirli bir değerin üstünde veya eşit olmasını gerektirir.
@DecimalMax: Sayısal bir alanın belirli bir değerin altında veya eşit olmasını gerektirir.
@Positive ve @PositiveOrZero: Pozitif sayılar gerektirir, ikincisi sıfırı da kabul eder.
@Negative ve @NegativeOrZero: Negatif sayılar gerektirir, ikincisi sıfırı da kabul eder.
@Range: Bir alanın belirli bir aralıkta (minimum ve maksimum değerler arasında) olmasını gerektirir.
@CreditCardNumber: Geçerli bir kredi kartı numarasını doğrular.
@Length: Bir stringin belirli bir uzunluk aralığında olmasını sağlar.
@Validated: Bir class veya arayüzün doğrulama grubunu belirtir.
@ScriptAssert: Özel bir koşulu belirtir ve belirtilen koşulun doğru olmasını gerektirir.
@URL: Geçerli bir URL formatını kontrol eder.
@NotBlank: @NotEmpty annotationına benzer şekilde, ancak boşluk karakterlerini geçerli kabul etmez.
*/

}
