package com.securesoftbd.cco.bloodbonorlist.Model;

public class Donor {

    private String id;
    private String name;
    private String age;
    private String image;
    private String bloodGroup;
    private String countractNumber;
    private String lastDonationDate;
    private String address;
   // private String firebaseID;
/*
public Donor(Donor donor,String firebaseID)
{
    this.firebaseID = firebaseID;
 Donor(donor.getName(), donor.getAge(), donor.getLastDonationDate(), donor.getBloodGroup(),donor.get contractNumber, lastDonationDate, address);

}*/

public Donor()
{

}

    public Donor(String id, String name, String age, String image, String bloodGroup, String countractNumber, String lastDonationDate, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodGroup = bloodGroup;
        this.countractNumber = countractNumber;
        this.lastDonationDate = lastDonationDate;
        this.address = address;
    }

    public Donor(String name, String age, String image, String bloodGroup, String countractNumber, String lastDonationDate, String address) {
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodGroup = bloodGroup;
        this.countractNumber = countractNumber;
        this.lastDonationDate = lastDonationDate;
        this.address = address;
    }


    public Donor(String id, String bloodGroup) {
        this.id = id;
        this.bloodGroup = bloodGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCountractNumber() {
        return countractNumber;
    }

    public void setCountractNumber(String countractNumber) {
        this.countractNumber = countractNumber;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
