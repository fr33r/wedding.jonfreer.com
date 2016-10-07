package com.jonfreer.wedding.domain;

public class Address extends Location{

  private String line1;
  private String line2;
  private String postalCode;

  public Address()
  {
    super();
  }

  public Address(String city, String stateOrProvince, String stateOrProvinceCode,
    String country, String countryCode, String line1, String line2, String postalCode){

    super(city, stateOrProvince, stateOrProvinceCode, country, countryCode);
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
  }

  public String getLine1(){
    return this.line1;
  }

  public void setLine1(String line1){
    this.line1 = line1;
  }

  public String getLine2(){
    return this.line2;
  }

  public void setLine2(String line2){
    this.line2 = line2;
  }

  public String getPostalCode(){
    return this.postalCode;
  }

  public void setPostalCode(String postalCode){
    this.postalCode = postalCode;
  }
}
