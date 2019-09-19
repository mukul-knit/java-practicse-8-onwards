package com.muk.models;

import java.util.List;

enum Profession {
    Engineer,
    Doctor,
    Player,
    Moviestar,
    Rockstar,
    TVPersonality;
}

enum ProgrammingLanguage {
    JAVA,
    SCALA,
    PYTHON,
    GO,
    JAVAScript,
    FRENCH,
    KOTLIN;
}

class Address {

    private String city;

    private String street;

    private String houseNo;

    private String state;

    private String Conuntry;

    public Address(String city, String street, String houseNo, String state, String conuntry) {
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.state = state;
        Conuntry = conuntry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConuntry() {
        return Conuntry;
    }

    public void setConuntry(String conuntry) {
        Conuntry = conuntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (houseNo != null ? !houseNo.equals(address.houseNo) : address.houseNo != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        return Conuntry != null ? Conuntry.equals(address.Conuntry) : address.Conuntry == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNo != null ? houseNo.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (Conuntry != null ? Conuntry.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", state='" + state + '\'' +
                ", Conuntry='" + Conuntry + '\'' +
                '}';
    }
}

class ContactInformation {

    private Address address;

    private int mobileNumber;

    private String emailId;

    public ContactInformation(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInformation that = (ContactInformation) o;

        if (mobileNumber != that.mobileNumber) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return emailId != null ? emailId.equals(that.emailId) : that.emailId == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + mobileNumber;
        result = 31 * result + (emailId != null ? emailId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "address=" + address +
                ", mobileNumber=" + mobileNumber +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}


public class User {

    private String name;

    private int age;

    private Profession profession;

    private ContactInformation contactInformation;

    private List<ProgrammingLanguage> languages;

    public User(String name, int age, Profession profession, ContactInformation contactInformation, List<ProgrammingLanguage> languages) {
        this.name = name;
        this.age = age;
        this.profession = profession;
        this.contactInformation = contactInformation;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public List<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<ProgrammingLanguage> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (profession != user.profession) return false;
        if (contactInformation != null ? !contactInformation.equals(user.contactInformation) : user.contactInformation != null)
            return false;
        return languages != null ? languages.equals(user.languages) : user.languages == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (contactInformation != null ? contactInformation.hashCode() : 0);
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", profession=" + profession +
                ", contactInformation=" + contactInformation +
                ", languages=" + languages +
                '}';
    }
}
