/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcrud;

/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */
public class Pet {
    private long id;
    private int age, weight, price;
    private String breed, gender, docID;
    private boolean available;
    
    public Pet() {}
    
    public Pet(Pet p) {
        this(p.getID(), p.getAge(), p.getWeight(), p.getPrice(), p.getBreed(), p.getGender(), p.getDocID(), 
                p.getAvailable());
    }
    
    public Pet(long id, int age, int weight, int price, String breed, String gender) {
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.price = price;
        this.breed = breed;
        this.gender = gender;
        this.docID = null;
        this.available = true;
    }
    
    public Pet(long id, int age, int weight, int price, String breed, String gender, String docID, boolean avl) {
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.price = price;
        this.breed = breed;
        this.gender = gender;
        this.docID = docID;
        this.available = avl;
    }
    
    public Pet(long id, int age, int weight, int price, String breed, String gender, String docID) {
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.price = price;
        this.breed = breed;
        this.gender = gender;
        this.docID = docID;
    }
    
    public void setID(long id) {
        this.id = id;
    }
    public long getID() {
        return this.id;
    }
    
    public boolean getAvailable() {
        return this.available;
    }
    
    public void setAvailable(boolean avl) {
        this.available = avl;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return this.age;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return this.weight;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return this.price;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public String getBreed() {
        return this.breed;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return this.gender;
    }
    
    public final void setDocID(String docID) {
        this.docID = docID;
    }
    public String getDocID() {
        return this.docID;
    }
    
    //Copies the given pet to the current pet
    public void copyPet(Pet newp) {
        this.setID(newp.getID());
        this.setAge(newp.getAge());
        this.setBreed(newp.getBreed());
        this.setDocID(newp.getDocID());
        this.setGender(newp.getGender());
        this.setPrice(newp.getPrice());
        this.setWeight(newp.getWeight());
        this.setAvailable(newp.getAvailable());
    }
    
    public void printPet() {
        System.out.println( "DocID = " + this.getDocID());
        System.out.println( "ID = " + this.getID());
        System.out.println( "Breed = " + this.getBreed());
        System.out.println( "Gender = " + this.getGender());
        System.out.println( "Age = " + this.getAge() + " years old");
        System.out.println( "Weight = " + this.getWeight() + " kg");
        System.out.println( "Price = " + this.getPrice() + " euros");
        
        if( this.getAvailable() ) {
            System.out.println("Available = true");
        }
        else {
            System.out.println("Available = false");
        }
        System.out.println();
    }
    
}
