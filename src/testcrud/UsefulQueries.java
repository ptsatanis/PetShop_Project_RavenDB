/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcrud;

import java.util.*;
import net.ravendb.client.documents.operations.DatabaseStatistics;
import net.ravendb.client.documents.operations.GetStatisticsOperation;
import net.ravendb.client.documents.session.*;


/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */
public class UsefulQueries {
    
    //FInds all pets of a specified breed
    public void retrieveByBreed(String breed) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereEquals("breed", breed).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets with price lower than the given one.
    public void retrieveByPriceLess(int price) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereLessThan("price", price).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets with price higher than the given one.
    public void retrieveByPriceGreater(int price) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereGreaterThan("price", price).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets that are younger than the given age.
    public void retrieveByAgeYounger(int age) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereLessThan("age", age).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets that are older than the given age.
    public void retrieveByAgeOlder(int age) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereGreaterThan("age", age).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets that are lighter than the given weight.
    public void retrieveByWeightLess(int weight) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereLessThan("weight", weight).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets that are heavier than the given weight.
    public void retrieveByWeightGreater(int weight) {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereGreaterThan("weight", weight).toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                it.next().printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
        }
    }
    
    //Find all pets that have not been bought yet.
    public void retrieveAvailablePets() {
        List<Pet> res = null;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            res = session.query(Pet.class).whereEquals("available", "True")
                    .orElse().whereEquals("available", "true").toList();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve by breed!");
        }
        
        //Prints the results
        if(res != null) {
            ListIterator<Pet> it = res.listIterator();
            
            while( it.hasNext() ) {
                Pet p = it.next();
                p.printPet();
            }
            System.out.println("\n" + res.size() + " documents found!");
            System.out.flush();
        }
    }
    
    //Find all orders of a specified customer.
    public void retrieveCustomerOrders(String docID) {
        CRUDOperations op = new CRUDOperations();
        Customer c = op.readCustomer(docID);
        c.printCustomer();
    }
    
    //Find the total amount of money that a customer has spent.
    public void retrieveCustomerLoyalty(String docID) {
        int loyalty = 0;
        CRUDOperations op = new CRUDOperations();
        Customer c = op.readCustomer(docID);
        
        String[] orders = c.getOrders();
        
        for(int i = 0; i < orders.length; i++) {
            Pet p = op.readDoc(orders[i]);
            loyalty += p.getPrice();
        }
        System.out.println("Customer's " + docID + "total order value is " + loyalty);
    }
    
    //Find who has bought the specified pet.
    public void retrieveBuyer(String petID) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            
            List<Customer> customers = session.query(Customer.class).toList();
            ListIterator<Customer> it = customers.listIterator();
            
            while( it.hasNext() ) {
                Customer c = it.next();
                String[] orders = c.getOrders();
                
                for(int i = 0; i < orders.length; i++) {
                    if( orders[i].equals(petID) ) {
                        System.out.println("Buyer of " + petID + "is " + c.getDocID());
                        session.close();
                        return;
                    }
                }
            }
            
            System.out.println(petID + " is currently available!");
            System.out.flush();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in retrieve Buyer!");
        }
    }
    
    //Find how many pets are in the database.
    public void getNumberOfPets() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            long count = session.query(Pet.class).longCount();
            System.out.println("Number of pets in database = " + count);
            
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in get number of pets!");
        }
    }
    
    //Find how many customers are in the database.
    public void getNumberOfCustomers() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            long count = session.query(Customer.class).longCount();
            System.out.println("Number of customers in database = " + count);
            
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in get number of customers!");
        }
    }
    
    //Find the sum of pets and customers in the database.
    public void getDBSize() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            DatabaseStatistics statistics = DocumentStoreHolder.getStore()
                    .maintenance().send(new GetStatisticsOperation());
            
            long DBSize = statistics.getCountOfDocuments() - 2;
            System.out.println("Database number of pets and customers is " + DBSize);
            
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error in get DBSize!");
        }
    }
    
    /*Depending on the user's input select the appropriate query.
      If an invalid input is given print an appropriate message.*/
    public void querySelection(String choice, Scanner Sc) {
        if( choice.equalsIgnoreCase("available") ) {
            retrieveAvailablePets();
        }
        else if( choice.equalsIgnoreCase("number_of_pets") ) {
            getNumberOfPets();
        }
        else if( choice.equalsIgnoreCase("number_of_customers") ) {
            getNumberOfCustomers();
        }
        else if( choice.equalsIgnoreCase("dbsize") ) {
            getDBSize();
        }
        else if( Sc.hasNext() ) {
            if( choice.equalsIgnoreCase("breed") ) {
                String breed = Sc.next();
                retrieveByBreed(breed);
            }
            else if( choice.equalsIgnoreCase("price_less_than") ) {
                int price = Sc.nextInt();
                retrieveByPriceLess(price);
            }
            else if( choice.equalsIgnoreCase("price_more_than") ) {
                int price = Sc.nextInt();
                retrieveByPriceGreater(price);
            }
            else if( choice.equalsIgnoreCase("younger_than") ) {
                int age = Sc.nextInt();
                retrieveByAgeYounger(age);
            }
            else if( choice.equalsIgnoreCase("older_than") ) {
                int age = Sc.nextInt();
                retrieveByAgeOlder(age);
            }
            else if( choice.equalsIgnoreCase("lighter_than") ) {
                int age = Sc.nextInt();
                retrieveByWeightLess(age);
            }
            else if( choice.equalsIgnoreCase("heavier_than") ) {
                int weight = Sc.nextInt();
                retrieveByWeightGreater(weight);
            }
            else if( choice.equalsIgnoreCase("orders") ) {
                String customerID = Sc.next();
                retrieveCustomerOrders(customerID);
            }
            else if( choice.equalsIgnoreCase("loyalty") ) {
                String customerID = Sc.next();
                retrieveCustomerLoyalty(customerID);
            }
            else if( choice.equalsIgnoreCase("buyer") ) {
                String petID = Sc.next();
                retrieveBuyer(petID);
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
        else {
            System.out.println("Invalid choice!");
        }
    }
}
