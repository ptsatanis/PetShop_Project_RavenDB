/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcrud;

/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */
public class Customer {
    private long id;
    private String firstName, lastName;
    private String phone;
    private String docID;
    private String[] orders;
    
    public Customer() {}
    
    public Customer(long id, String firstName, String lastName, String phone, String[] orderedPets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.orders = orderedPets;
        this.docID = null;
    }
    
    public Customer(long id, String firstName, String lastName, String phone, String[] orderedPets, String docID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.orders = orderedPets;
        this.docID = docID;
    }
    
    public Customer(Customer c) {
        this(c.getID(), c.getFirstName(), c.getLastName(), c.getPhone(), c.getOrders(), c.getDocID());
    }
    
    public String getDocID() {
        return this.docID;
    }
    
    public void setDocID(String docID) {
        this.docID = docID;
    }
    
    public long getID() {
        return this.id;
    }
    
    public void setID(long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String fname) {
        this.firstName = fname;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lname) {
        this.lastName = lname;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    // Add a new order to customer order history
    public void addOrder(String pet) {
        
        String[] newarray = new String[this.orders.length + 1];
        for(int i = 0; i < this.orders.length; i++) {
            newarray[i] = this.orders[i];
        }
        newarray[this.orders.length] = pet;
        this.orders = newarray;
    }
    
    // Delete an order from customer order history
    public void deleteOrder(String pet) {
        String[] newarray = new String[this.orders.length - 1];
        for(int i = 0; i < newarray.length; i++) {
            if( newarray[i].equals(pet) == false )
                newarray[i] = this.orders[i];
        }
        this.orders = newarray;
    }
    
    /*Copy a customer and add the new orders 
    choosing if its older older history will be kept or not*/
    public void copyCustomer(Customer newc, boolean keepOldOrders) {
        this.setID(newc.getID());
        this.setFirstName(newc.getFirstName());
        this.setLastName(newc.getLastName());
        this.setPhone(newc.getPhone());
        
        if( keepOldOrders ) {
            String[] newarray = new String[this.orders.length + newc.orders.length];
            for(int i = 0; i < this.orders.length; i++) {
                newarray[i] = this.orders[i];
            }
            for(int j = this.orders.length; j < newarray.length; j++) {
                newarray[j] = newc.orders[j - this.orders.length];
            }
            this.orders = newarray;
        }
        else {
            this.orders = newc.orders;
        }
    }
    
    public void printCustomer() {
        System.out.println(this.getDocID());
        System.out.println("ID = " + this.getID());
        System.out.println("First Name = " + this.getFirstName());
        System.out.println("Last Name = " + this.getLastName());
        System.out.println("Phone = " + this.getPhone());
        System.out.println("Ordered:");
        
        for(int i = 0; i < this.orders.length; i++) {
            System.out.println(this.orders[i]);
        }
        System.out.println();
    }
    
    public String[] getOrders() {
        return this.orders;
    }
    
    public void setOrders(String[] neworders) {
        this.orders = neworders;
    }
}
