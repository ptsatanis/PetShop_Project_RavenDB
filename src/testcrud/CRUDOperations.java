/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcrud;

import net.ravendb.client.documents.session.*;
/**
 *
 * @author Tsatanis Panagiotis and Maris Athanasios
 */
public class CRUDOperations {
    
    public CRUDOperations() {}
    
    //Insert a new Pet in the database, returns whether the transaction succeeded or not
    public boolean createDocument(Pet p) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            session.store(p);
            //Update the docID value of pet to match the RavenDB's given id to this document
            p.setDocID(session.advanced().getDocumentId(p));
            session.saveChanges();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while saving document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    // Retrieve a pet's information with the given docID or null in case of error
    public Pet readDoc(String docID) {
        Pet result;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            //load the document with RavenDB id equal to the docID
            
            result = new Pet(session.load(Pet.class, docID));
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while looking for a document: \n" + ex.getMessage());
            return null;
        }
        return result;
    }
    
    //Update a pet's information given its docID and the updated pet,
    //returns whether the transaction succeeded or not
    public boolean updateDoc(String docID, Pet newp, boolean customerTransaction) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            Pet p = session.load(Pet.class, docID);
            
            if( p != null ) {
                
                /*if just updating pet's info and no customer transaction
                is involved don't change the value of the field available*/
                if( customerTransaction == false )
                    newp.setAvailable(p.getAvailable());
                
                //Update pet's info
                p.copyPet(newp);
                session.saveChanges();
                session.close();
            }
            else
                return false;
        }
        catch(Exception ex) {
            System.err.println("Error while updating a document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    //Delete the pet with the given docID, returns whether the transaction succeeded or not
    public boolean deleteDoc(String docID) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            session.delete(docID);
            session.saveChanges();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while deleting a document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    //Create a new customer with the available pets he ordered.
    //If customer tries to order an unavailable pet, the transaction will be cancelled
    public boolean createCustomer(Customer c) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            session.store(c);
            c.setDocID(session.advanced().getDocumentId(c));
            c.printCustomer();
            

            String[] orders = c.getOrders();
            
            //if customer attempts to buy an unavailable pet, transaction is cancelled
            for(int j = 0; j < orders.length; j++) {
                Pet p2 = readDoc(orders[j]);
                if( p2.getAvailable() == false ) {
                    System.out.println(p2.getDocID() + " is not available!");
                    System.out.println("Cancelling transaction!");
                    return false;
                }
            }
            
            //Confirm orders(mark the pets as unavailable)
            for(int i = 0; i < orders.length; i++) {
                Pet p = readDoc(orders[i]);
                p.setAvailable(false);
                updateDoc(orders[i], p, true);
            }
            
            session.saveChanges();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while saving document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    //Returns a customers information(id, name, phone number, docID and order history)
    // given his docID or null in case of error
    public Customer readCustomer(String docID) {
        Customer result;
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            result = new Customer(session.load(Customer.class, docID));
            
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while looking for a document: \n" + ex.getMessage());
            return null;
        }
        return result;
    }
    
    //Update a customer's information and its order history.
    //Additional orders can be made while keeping the old ones or the old ones can
    //be erased in case the customer cancelled his previous order
     public boolean updateCustomer(String docID, Customer newc, boolean keepOldOrders) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            Customer c = session.load(Customer.class, docID);
            
            if( c != null ) {
                
                //Insert new orders in customer's order history
                String[] orders = newc.getOrders();
                
                for(int j = 0; j < orders.length; j++) {
                    Pet p2 = readDoc(orders[j]);
                    if( p2.getAvailable() == false ) {
                        System.out.println(p2.getDocID() + " is not available!");
                        System.out.println("Cancelling transaction!");
                        return false;
                    }
                }
                
                for(int i = 0; i < orders.length; i++) {
                    Pet p = readDoc(orders[i]);
                    p.setAvailable(false);
                    updateDoc(orders[i], p, true);
                }
                
                /*Delete the old orders and mark those pets as available.
                  This option exists in case the customer wants to change his order*/
                if( keepOldOrders == false ) {
                    String[] OldOrders = c.getOrders();
                    for(int i = 0; i < OldOrders.length; i++) {
                        Pet p = readDoc(OldOrders[i]);
                        p.setAvailable(true);
                        updateDoc(OldOrders[i], p, true);
                    }
                }
                
                c.copyCustomer(newc, keepOldOrders);
                session.saveChanges();
                session.close();
            }
            else
                return false;
        }
        catch(Exception ex) {
            System.err.println("Error while updating a document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
    
     //Delete a customer's information given his docID
     public boolean deleteCustomer(String docID) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession("Test")) {
            session.delete(docID);
            session.saveChanges();
            session.close();
        }
        catch(Exception ex) {
            System.err.println("Error while deleting a document: \n" + ex.getMessage());
            return false;
        }
        
        return true;
    }
}
