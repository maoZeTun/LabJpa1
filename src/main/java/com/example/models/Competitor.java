
package com.example.models;


import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Competitor implements Serializable{
     
    private static final long serialVersionUID=1L;
    
    
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@NotNull
@Column(name = "created_at", updatable = false)
@Temporal(TemporalType.DATE)
private Calendar createdAt;


@NotNull
@Column(name = "update_at")
@Temporal(TemporalType.DATE)
private Calendar updateddAt;



    private String name;
    
    private String surname;
    
    private int age;
    
    private String telephone;
    
    private String cellphone;
    
    private String address;
    
    private String city;
    
    private String country;
    
    private boolean winner;

    @OneToMany ( cascade = CascadeType.ALL,mappedBy = "competitor" )
    private Set<Producto> productos; 
    
    private String contra;
    
    public Competitor(){
        
    }
    
    public Competitor(String nameN, String surnameN, int ageN,String telephoneN, String cellphoneN, String addressN, String  cityN, String countryN,boolean winnerN,String contraN){
        name=nameN;
        surname=surnameN;
        age=ageN;
        telephone=telephoneN;
        cellphone=cellphoneN;
        address=addressN;
        city=cityN;
        country=countryN;
        winner=winnerN;
        contra=contraN;
        productos = new HashSet<Producto>();
    }
    
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    
    @PreUpdate
    private void timeStamp(){
        this.updateddAt=Calendar.getInstance();
    }
    
    @PrePersist
    private void creationTimeStamp(){
        this.createdAt= Calendar.getInstance();
        this.updateddAt=Calendar.getInstance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> products) {
        this.productos = products;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
    
    
}
