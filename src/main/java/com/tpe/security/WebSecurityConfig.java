package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Security'nin bu class'in security katmanin Configuration class'i oldugunu aliyor.
@EnableWebSecurity // Bunun bir web seurity oldugunu belirtiyoruz.
@EnableGlobalMethodSecurity(prePostEnabled = true) // Method seviyede security olacagini söylüyoruz
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // !!! bu calss da amacimiz : AuthenticationManager, Provider ve PasswordEncoder lari olusturup birbirleriyle tanistirmak
    @Autowired
    private UserDetailsService userDetailsService;

    // csrf ataklari nedir? -> örnegim bir browser'da siz bankacilik islemlerini yaparken,
    // yan taraftada emaillerinize bakiyorsunuz o sirada art niyetli bir email geldi
    // ve sizde onu actiniz bu arka planda kodlarla sanki bankacilik icin actigisayfayi mail icin actiginiz sayfayq tasiyor
    // dolayisi ile siz bu maili gönderen kisiye tamamiyla bankacilik islemleri icin yetki vermis gibi oluyorsunuz.
    // normalde sanki siz sifrenizle girmis gibi olursunuz ve sizin bütün hesabinizi bosaltabilir, bu tarz ataklara csrf atagi denir
    // normalde default'a csrf() atagini önleyen bu yapi aciktir ancak biz bunu disable yaptik, cünkü security katmanini biz yazacagiz.
    // biz zaten dinamik bir web sayfasi yapmiyoruz, amacimiz RestFull api yapmak biz csrf ihtiyacimizda yok, bizim bir browser sayfamiz olmayacak bu bir api
    // acik olsa ne olur?  Bazi http methodlarini kullanamazsiniz. Biz csrf saglayacagi yapiyi security'de saglayacagim icin onu disable cekiyoruz.

    // authorizeHttpRequests() :Gelen bütün httpRequest'leri burda Authorize(yetki kontrolü) yapiyoruz. ancak bazilarini security katmanindan muaf tutmak istiyorum
    // bunuda antMatchers("/","index.html","/css/*","/js/*","/register") bununla yapiyorum .
    // ancak antMatchers disinda kalan bütün gelen request'leride Autorize et diyorum; bu methodlarla -> permitAll().anyRequest().authenticated().
    // ve sen bulari basicAuthentication ile bunlari yap diyorum bu methodlarla ->  and().httpBasic();
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().
                authorizeHttpRequests().
                antMatchers("/","index.html","/css/*","/js/*","/register").permitAll().
                anyRequest().
                authenticated().
                and().
                httpBasic();
    }

    // springframework'un bize uygulamalarimizi encode etmek icin sagladigi kücük bir uyulamacik diyebiliriz.
    // ancak bu sizden icine parametre olarak sayisal bir deger istiyor. bu deger 4 ile 31 arasinda degerler alabilir,
    // burda yazacaginiz deger ne kadar yükselirse hash'leme katsayisi okadar artiyor.
    // biz 10 yazdik genelde 15 üzerine cikilmiyor cünkü hashlemeni hash'lemesi uzun sürüyor ve daha güvenli oluyor.

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(10);
    }
//
    @Bean
    public DaoAuthenticationProvider authProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); //DaoAuthenticationProvider create ettik userDetailsService ve passwordEncoder iletisime gecirmek icin.

        authProvider.setUserDetailsService(userDetailsService); // bunda provider userDetailsService tanitiliyor. cünkü bularla iletisim icinde olacak.
        authProvider.setPasswordEncoder(passwordEncoder()); // bunda provider passwordEncoder tanitiliyor.

        return authProvider;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider()); // AuthenticationManager ile authenticationProvider iletisime gecirdik.
    }
}